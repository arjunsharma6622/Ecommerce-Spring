package dev.arjunsharma.ecommerce;

import dev.arjunsharma.ecommerce.models.Product;
import dev.arjunsharma.ecommerce.repository.ProductRepository;
import dev.arjunsharma.ecommerce.repository.projections.ProductProjection;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class EcommerceApplicationTests {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void contextLoads() {
    }

    @Test
    void testQueries(){
        List<ProductProjection> productList = productRepository.getPartOfProductByCategoryId(5L);
        for(ProductProjection p : productList){
            System.out.println(p);
        }
    }
}
