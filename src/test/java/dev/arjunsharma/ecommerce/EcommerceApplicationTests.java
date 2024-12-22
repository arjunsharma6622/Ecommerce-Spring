package dev.arjunsharma.ecommerce;

import dev.arjunsharma.ecommerce.models.Category;
import dev.arjunsharma.ecommerce.models.Product;
import dev.arjunsharma.ecommerce.repository.CategoryRepository;
import dev.arjunsharma.ecommerce.repository.ProductRepository;
import dev.arjunsharma.ecommerce.repository.projections.ProductProjection;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class EcommerceApplicationTests {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

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

    @Test
    void FetchTypeTest(){
//        Category cat = categoryRepository.findById(5L).get();
//        System.out.println(cat.getId());
//        System.out.println("We are done here");
//        List<Product> currentProds = cat.getProducts();
//        System.out.println(currentProds.size());


        // H/W implement N+1 Problem case
        List<Category> cat = categoryRepository.findAll();

//        List<Product> products = new ArrayList<>();
//        for(Category c : cat){
//            List<Product> p = c.getProducts();
//            products.add
//        }

    }
}
