package dev.arjunsharma.ecommerce.repository;

import dev.arjunsharma.ecommerce.models.Product;
import dev.arjunsharma.ecommerce.repository.projections.ProductProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Product findByTitle(String title);
    Product findByDescription(String description);

    /* HQL implementation */
    @Query("select p from Product p where p.category.id =:categoryId")
    List<Product> getProductByCategoryId(@Param("categoryId") Long categoryId);

    /* HQL implementation */
    @Query("select p.title as title, p.price as price from Product p where p.category.id =:categoryId")
    List<ProductProjection> getPartOfProductByCategoryId(@Param("categoryId") Long categoryId);

    /* Native SQL implementation */
    @Query(value = "select * from product as p where p.category_id =?1", nativeQuery = true)
    List<Product> getProductByCategoryIdBYNativeQuery(Long categoryId);
}
