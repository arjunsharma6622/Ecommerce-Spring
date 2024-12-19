package dev.arjunsharma.ecommerce.controllers;

import dev.arjunsharma.ecommerce.dto.ErrorDTO;
import dev.arjunsharma.ecommerce.exceptions.ProductNotFoundException;
import dev.arjunsharma.ecommerce.models.Product;
import dev.arjunsharma.ecommerce.services.ProductService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FkStoreProductController {

    private final ProductService productService;

    public FkStoreProductController(@Qualifier("FkStoreProductService") ProductService productService) {
        this.productService = productService;
    }

    /* ✅ 1. GET Single Product */
    @GetMapping("/fkstore/products/{id}")
    public ResponseEntity<Product> GetProduct(@PathVariable("id") Long id) throws ProductNotFoundException {
        Product product;
        product = productService.getSingleProduct(id);

        ResponseEntity<Product> res;
        res = new ResponseEntity<>(product, HttpStatus.OK);
        return res;
    }

    /* ✅ 2. GET ALL Products */
    @GetMapping("/fkstore/products")
    public ResponseEntity<List<Product>> GetProducts(){
        List<Product> products;
        products = productService.getAllProducts();

        ResponseEntity<List<Product>> res;
        res = new ResponseEntity<>(products, HttpStatus.OK);
        return res;
    }

    /* ✅ 3. CREATE Product */
    @PostMapping("/fkstore/products")
    public ResponseEntity<Product> CreateProduct(@RequestBody Product product){
        Product savedProduct = productService.createProduct(
                product.getTitle(),
                product.getDescription(),
                product.getImageUrl(),
                product.getPrice(),
                product.getCategory()
        );
        ResponseEntity<Product> res;
        res = new ResponseEntity<>(savedProduct, HttpStatus.OK);
        return res;
    }

    /* ✅ 4. Update Product */
    @PutMapping("/fkstore/products/{id}")
    public ResponseEntity<Product> UpdateProduct(@PathVariable("id") Long id, @RequestBody Product product) throws ProductNotFoundException {
        Product updatedProduct;
        updatedProduct = productService.updateProduct(
                id,
                product.getTitle(),
                product.getDescription(),
                product.getImageUrl(),
                product.getPrice(),
                product.getCategory()
        );

        ResponseEntity<Product> res;
        res = new ResponseEntity<>(updatedProduct, HttpStatus.OK);
        return res;
    }

    /* ✅ 5. Delete Product */
    @DeleteMapping("/fkstore/products/{id}")
    public ResponseEntity<Object> DeleteProduct(@PathVariable("id") Long id) throws ProductNotFoundException{
        productService.deleteProduct(id);
        ResponseEntity<Object> res;
        res = new ResponseEntity<>(HttpStatus.OK);
        return res;
    }

    /* Defining how we want to handle the exceptions */
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleProductNotFoundException(Exception e){
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setMessage(e.getMessage());

        ResponseEntity<ErrorDTO> res;
        res = new ResponseEntity<>(errorDTO, HttpStatus.NOT_FOUND);

        return res;
    }
}