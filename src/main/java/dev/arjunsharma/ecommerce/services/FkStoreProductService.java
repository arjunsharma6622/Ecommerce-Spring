package dev.arjunsharma.ecommerce.services;

import dev.arjunsharma.ecommerce.dto.FkStoreProductDTO;
import dev.arjunsharma.ecommerce.exceptions.ProductNotFoundException;
import dev.arjunsharma.ecommerce.models.Category;
import dev.arjunsharma.ecommerce.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service("FkStoreProductService")
public class FkStoreProductService implements ProductService{

    final RestTemplate restTemplate;
    private final RedisTemplate<String, Object> redisTemplate;

    public FkStoreProductService(RestTemplate restTemplate, RedisTemplate<String, Object> redisTemplate) {
        this.restTemplate = restTemplate;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Product getSingleProduct(Long id) throws ProductNotFoundException {

        Product redisProduct = (Product) redisTemplate.opsForHash().get(
                "PRODUCTS",
                "PRODUCTS_"+id
        );

        if(redisProduct != null){
            // means cache hit
            return redisProduct;
        }

        FkStoreProductDTO productFromFkStore;
        productFromFkStore = restTemplate.getForObject(
                "https://fakestoreapi.com/products/"+id,
                FkStoreProductDTO.class
        );

        if(productFromFkStore == null){
            throw new ProductNotFoundException("Product Not Found with ID " + id);
        }

        System.out.println(productFromFkStore.getProduct().toString());
        System.out.println(id);

        redisTemplate.opsForHash().put("PRODUCTS", "PRODUCTS_"+id, productFromFkStore.getProduct());


        System.out.println(id);

        return productFromFkStore.getProduct();
    }

    @Override
    public Page<Product> getAllProducts(int pageNumber, int pageSize, String fieldName) {
        return null;
    }

//    @Override
//    public List<Product> getAllProducts(int pageNumber, int pageSize, String fieldName) {
//        FkStoreProductDTO[] productsFromFkStore;
//        productsFromFkStore = restTemplate.getForObject(
//                "https://fakestoreapi.com/products",
//                FkStoreProductDTO[].class
//        );
//        List<Product> products = new ArrayList<>();
//
//        assert productsFromFkStore != null;
//        for(FkStoreProductDTO i : productsFromFkStore){
//            products.add(i.getProduct());
//        }
//
//        return products;
//    }

    @Override
    public Product createProduct(
            String title,
            String description,
            String imageUrl,
            Double price,
            Category category
    ) {
        FkStoreProductDTO fkStoreProduct = new FkStoreProductDTO();
        fkStoreProduct.setTitle(title);
        fkStoreProduct.setDescription(description);
        fkStoreProduct.setPrice(price);
        fkStoreProduct.setImage(imageUrl);
        fkStoreProduct.setCategory(category.getTitle());

        FkStoreProductDTO savedProductFromFkStore;
        savedProductFromFkStore = restTemplate.postForObject(
                "https://fakestoreapi.com/products",
                fkStoreProduct,
                FkStoreProductDTO.class
        );

        assert savedProductFromFkStore != null;
        return savedProductFromFkStore.getProduct();
    }

    @Override
    public Product updateProduct(Long id, String title, String description, String imageUrl, Double price, Category category) throws ProductNotFoundException {

        // so here first making sure that the product with ID exists in FkStore
        // if product is not in FkStore, then the getSingleProduct method anyway will throw the exception
        getSingleProduct(id);

        // if the product was there then we can continue
        FkStoreProductDTO fkStoreProduct = new FkStoreProductDTO();
        fkStoreProduct.setTitle(title);
        fkStoreProduct.setDescription(description);
        fkStoreProduct.setPrice(price);
        fkStoreProduct.setImage(imageUrl);
        fkStoreProduct.setCategory(category.getTitle());

        // restTemplate.put("https://fakestoreapi.com/products/"+id, fkStoreProduct);

        // ℹ️ using exchange instead of put, because put is not returning anything
        // and we want the updated product to be returned

        ResponseEntity<FkStoreProductDTO> res = restTemplate.exchange(
                "https://fakestoreapi.com/products/"+id,
                HttpMethod.PUT,
                new HttpEntity<>(fkStoreProduct),
                FkStoreProductDTO.class
        );

        FkStoreProductDTO updatedFkStoreProduct = res.getBody();

        assert updatedFkStoreProduct != null;
        return updatedFkStoreProduct.getProduct();
    }

    @Override
    public void deleteProduct(Long id) throws ProductNotFoundException {
        // calling this to check if product is in FkStore first
        // if not in FkStore then throw the exception
        getSingleProduct(id);

        restTemplate.delete("https://fakestoreapi.com/products/"+id);
    }
}
