package tech.laye.tourisme_api.product;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import tech.laye.tourisme_api.common.PurchaseRequest;
import tech.laye.tourisme_api.common.PurchaseResponse;
import tech.laye.tourisme_api.exception.ProductPurchaseException;
import tech.laye.tourisme_api.securityUtils.SecurityUtils;
import tech.laye.tourisme_api.user.Role;
import tech.laye.tourisme_api.user.User;
import tech.laye.tourisme_api.user.UserRepository;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final UserRepository userRepository;

    public Page<ProductResponse> getAllProducts(int page ,int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());

        return productRepository.findAllByIsHiddenFalse( pageable)
                .map(productMapper::toProductResponse);

    }

    public ProductResponse getProduct(Long id){
        return productRepository.findById(id).
            map(productMapper::toProductResponse).orElseThrow(
                        ()-> new EntityNotFoundException("Entity Not Found !")
                );
    }

    @Transactional
    public Integer save(ProductRequest productRequest, Authentication connectedUser) {
        String currentUserId = SecurityUtils.getCurrentUserId(connectedUser);

        User user = userRepository.findById(currentUserId).orElseThrow(
                ()-> new EntityNotFoundException("user not found")
        );

        if(user.getRole() != Role.ARTISAN){
            throw new RuntimeException("Only artisans can do this !");
        }

        Product product = productMapper.toProduct(productRequest);

        return productRepository.save(product).getId();
    }

    @Transactional
    public Integer updateStock(Long id , Integer value , Authentication connectedUser) throws OperationNotSupportedException {
        Product product = productRepository.findById(id).orElseThrow(
                ()-> new EntityNotFoundException("product not found")
        );
        String currentUserId = SecurityUtils.getCurrentUserId(connectedUser);

        if (!Objects.equals(product.getUser().getId(),currentUserId))
        {
            throw new OperationNotSupportedException("cannot update another user's product!");
        }

        product.setStock(value);
        return productRepository.save(product).getId();
    }

    public void deleteProduct(Long id,Authentication connectedUser) throws OperationNotSupportedException {
        Product product = productRepository.findById(id).orElseThrow(
                ()-> new EntityNotFoundException("product not found !")
        );
        String currentUserId = SecurityUtils.getCurrentUserId(connectedUser);

        if (!Objects.equals(product.getUser().getId(),currentUserId))
        {
            throw new OperationNotSupportedException("cannot delete another user's product!");
        }

        productRepository.delete(product);
    }

    public Page<ProductResponse> getMyProducts(int page, int size, Authentication connectedUser) {
        String currentUserId = SecurityUtils.getCurrentUserId(connectedUser);

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());

        return productRepository.findMyProductsById( currentUserId, pageable )
                .map(productMapper::toProductResponse);

    }

    public Page<ProductResponse> getProductByType(int page , int size ,ProductType type) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());

        return productRepository.findMyProductsByType(type ,pageable )
                .map(productMapper::toProductResponse);
    }

    public Integer toggleVisibility(Long id) {
        Product product = productRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Entity not found !")
        );

        product.setHidden(true);
        return productRepository.save(product).getId();
    }

    public Page<ProductResponse> getAllHiddenProduct(int page, int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());

        return productRepository.findAllByIsHiddenTrue( pageable)
                .map(productMapper::toProductResponse);
    }

    public List<PurchaseResponse> purchaseProduct(List<PurchaseRequest> request) {
        var productIds = request
                .stream()
                .map(PurchaseRequest::productId)
                .toList();
        var storedProducts = productRepository.findAllByIdInOrderById(productIds);
        if (productIds.size() != storedProducts.size()){
            throw new ProductPurchaseException("One or more products doesn't exist !");
        }

        var storesRequest = request
                .stream()
                .sorted(Comparator.comparing(PurchaseRequest::productId))
                .toList();
        var purchasedProducts = new ArrayList<PurchaseResponse>();
        for (int i = 0; i < storedProducts.size(); i++) {
            var product = storedProducts.get(i);
            var productRequest = storesRequest.get(i);
            if (product.getStock() < productRequest.quantity()){
                throw new ProductPurchaseException("insufficient stock quantity for product with ID ::!"+ productRequest.productId());
            }
            var newAvailableQuantity = product.getStock() - productRequest.quantity();
            product.setStock(newAvailableQuantity);
            productRepository.save(product);
            purchasedProducts.add(productMapper.toProductPurchaseResponse(product ,productRequest.quantity()));
        }

        return purchasedProducts;
    }
}
