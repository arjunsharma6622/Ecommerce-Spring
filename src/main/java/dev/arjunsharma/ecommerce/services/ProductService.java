package dev.arjunsharma.ecommerce.services;

import dev.arjunsharma.ecommerce.exceptions.ProductNotFoundException;
import dev.arjunsharma.ecommerce.models.Category;
import dev.arjunsharma.ecommerce.models.Product;

import java.util.List;

public interface ProductService {

    /* 1.Get Single Product Service */
    Product getSingleProduct(Long id) throws ProductNotFoundException;

    /* 2.Get All Products Service */
    List<Product> getAllProducts();

    /* 3.Create Product Service */
    Product createProduct(String title, String description, String imageUrl, Double price, Category category);

    /* 4.Update Product Service */
    Product updateProduct(Long id, String title, String description, String imageUrl, Double price, Category category) throws ProductNotFoundException;

    /* 5.Delete Product Service */
    void deleteProduct(Long id) throws ProductNotFoundException;
}