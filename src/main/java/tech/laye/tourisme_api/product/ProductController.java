package tech.laye.tourisme_api.product;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.naming.OperationNotSupportedException;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;


    @GetMapping
    public ResponseEntity<Page<ProductResponse>> getAllProducts(
            @RequestParam(name = "page", defaultValue = "0" , required = false) int page,
            @RequestParam(name = "size", defaultValue = "10" , required = false)int size
    ){
        return ResponseEntity.ok(productService.getAllProducts(page,size));
    }

    @GetMapping("/owner")
    public ResponseEntity<Page<ProductResponse>> getMyProducts(
            @RequestParam(name = "page", defaultValue = "0" , required = false) int page,
            @RequestParam(name = "size", defaultValue = "10" , required = false)int size,
            Authentication connectedUser
    ){
        return ResponseEntity.ok(productService.getMyProducts(page,size,connectedUser));
    }

    @GetMapping("/{id}")
    public ProductResponse getProduct(@PathVariable Long id){
        return productService.getProduct(id);
    }

    @GetMapping("/type/{type}")
    public Page<ProductResponse> getProductByType(
            @RequestParam(name = "page", defaultValue = "0" , required = false) int page,
            @RequestParam(name = "size", defaultValue = "10" , required = false)int size,
            @PathVariable ProductType type
    ){
        return productService.getProductByType(page,size,type);
    }

    @PostMapping
    public ResponseEntity<Long> saveProduct(
            @RequestBody @Valid ProductRequest productRequest,
            Authentication connectedUser
    ){
        return ResponseEntity.ok(this.productService.save(productRequest,connectedUser));
    }

    @PatchMapping("/{id}/stock")
    public ResponseEntity<Long> updateStock(
            @PathVariable Long id ,
            @RequestParam Integer value,
            Authentication connectedUser
    ) throws OperationNotSupportedException {
        return ResponseEntity.ok(productService.updateStock(id,value ,connectedUser));
    }

    @PatchMapping("/{id}/hide")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Long> toggleProductVisibility(@PathVariable Long id) {
        return ResponseEntity.ok(productService.toggleVisibility(id));
    }

    @GetMapping("/hide")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<ProductResponse>> getAllHiddenProduct(
            @RequestParam(name = "page", defaultValue = "0" , required = false) int page,
            @RequestParam(name = "size", defaultValue = "10" , required = false)int size
    ){
        return ResponseEntity.ok(productService.getAllHiddenProduct(page,size));
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(
            @PathVariable Long id ,
            Authentication connectedUser
    ) throws OperationNotSupportedException {
        productService.deleteProduct(id,connectedUser);
    }


}
