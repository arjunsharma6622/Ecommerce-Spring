package dev.arjunsharma.ecommerce.services;

import dev.arjunsharma.ecommerce.exceptions.ProductNotFoundException;
import dev.arjunsharma.ecommerce.models.Category;
import dev.arjunsharma.ecommerce.models.Product;
import dev.arjunsharma.ecommerce.repository.CategoryRepository;
import dev.arjunsharma.ecommerce.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service("SelfProductService")
public class SelfProductService implements ProductService{

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public SelfProductService(ProductRepository productRepository, CategoryRepository categoryRepository){
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Product getSingleProduct(Long id) throws ProductNotFoundException {
        Optional<Product> p = productRepository.findById(id);

        if(p.isEmpty()){
            throw new ProductNotFoundException("The product with id " + id + " is not in our DB");
        }

        return p.get();
    }

    @Override
    public Page<Product> getAllProducts(int pageNumber, int pageSize, String fieldName) {
        Page<Product> products;
        products = productRepository.findAll(
                PageRequest.of(
                        pageNumber,
                        pageSize,
                        Sort.by(fieldName).ascending()
                )
        );
        return products;
    }

    @Override
    public Product createProduct(String title, String description, String imageUrl, Double price, Category category) {
        Product p = new Product();


        Optional<Category> categoryFromDB = categoryRepository.findByTitle(category.getTitle());

        // if category is not found then create a new one
        if(categoryFromDB.isEmpty()){
            Category newCategory = new Category();
            newCategory.setTitle(category.getTitle());
            Category newSavedCategory = categoryRepository.save(newCategory);
            p.setCategory(newSavedCategory);
        }
        // if category already exists in DB use it
        else{
            Category currentCategory = categoryFromDB.get();
            p.setCategory(currentCategory);
        }

        p.setTitle(title);
        p.setDescription(description);
        p.setImageUrl(imageUrl);
        p.setPrice(price);
        p.setCategory(category);

        Product savedProduct;
        savedProduct = productRepository.save(p);

        return savedProduct;
    }

    @Override
    public Product updateProduct(Long id, String title, String description, String imageUrl, Double price, Category category) throws ProductNotFoundException {
        Optional<Product> optionalProductInDB = productRepository.findById(id);

        if(optionalProductInDB.isEmpty()) {
            throw new ProductNotFoundException("The product with ID " + id + " Not Found in DB, So cannot update it");
        }

        Product productFromDb = optionalProductInDB.get();

        if(title != null && !title.isBlank()){
            productFromDb.setTitle(title);
        }
        if(description != null && !description.isBlank()){
            productFromDb.setDescription(description);
        }
        if(imageUrl != null && !imageUrl.isBlank()){
            productFromDb.setImageUrl(imageUrl);
        }
        if(price != null && price > 0){
            productFromDb.setPrice(price);
        }
        if(category != null){
            productFromDb.setCategory(category);
        }

        Product updatedProductFromDb;
        updatedProductFromDb = productRepository.save(productFromDb);

        return updatedProductFromDb;
    }

    @Override
    public void deleteProduct(Long id) throws ProductNotFoundException {
        // first we are trying to call getSingleProduct
        // because if the product is not in DB, it should throw exception
        getSingleProduct(id);
        productRepository.deleteById(id);
    }
}