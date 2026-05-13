package tech.laye.tourisme_api.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product,Long> {

    Page<Product> findAllByIsHiddenFalse(Pageable pageable);

    Page<Product> findAllByIsHiddenTrue(Pageable pageable);

    @Query("""
                    SELECT p 
                    FROM Product p 
                    WHERE p.user.id = :userId
            """)
    Page<Product> findMyProductsById(String userId, Pageable pageable);

    @Query("""
                    SELECT p 
                    FROM Product p 
                    WHERE p.productType = :type
            """)
    Page<Product> findMyProductsByType(ProductType type, Pageable pageable);
}
