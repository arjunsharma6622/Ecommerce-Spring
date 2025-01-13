package dev.arjunsharma.ecommerce.controllers;

import dev.arjunsharma.ecommerce.dto.ErrorDTO;
import dev.arjunsharma.ecommerce.exceptions.ProductNotFoundException;
import dev.arjunsharma.ecommerce.models.Product;
import dev.arjunsharma.ecommerce.services.ProductService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    private final ProductService productService;

    public ProductController(@Qualifier("SelfProductService") ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products/{id}")
    public Product GetSingleProduct(@PathVariable("id") Long id) throws ProductNotFoundException {
        Product p;
        p = productService.getSingleProduct(id);
        return p;
    }

    @GetMapping("/products")
    public Page<Product> GetAllProducts(
            @RequestParam("pageNumber") int pageNumber,
            @RequestParam("pageSize") int pageSize,
            @RequestParam("fieldName") String fieldName
    ){
        return productService.getAllProducts(pageNumber, pageSize, fieldName);
    }

    @PostMapping("/products")
    public Product CreateProduct(@RequestBody Product product){
        Product createdProduct;
        createdProduct = productService.createProduct(
                product.getTitle(),
                product.getDescription(),
                product.getImageUrl(),
                product.getPrice(),
                product.getCategory()
        );

        return createdProduct;
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<Product> UpdateProduct(@PathVariable("id") Long id, @RequestBody Product product) throws ProductNotFoundException {
        Product updatedProductFromDB = productService.updateProduct(
                id,
                product.getTitle(),
                product.getDescription(),
                product.getImageUrl(),
                product.getPrice(),
                product.getCategory()
        );

        ResponseEntity<Product> res;
        res = new ResponseEntity<>(updatedProductFromDB, HttpStatus.OK);

        return res;
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Object> DeleteProduct(@PathVariable("id") Long id) throws ProductNotFoundException{
        productService.deleteProduct(id);
        ResponseEntity<Object> res;
        res = new ResponseEntity<>(HttpStatus.NO_CONTENT);

        return res;
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleProductNotFoundException(Exception e){
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setMessage(e.getMessage());

        ResponseEntity<ErrorDTO> res;
        res = new ResponseEntity<>(errorDTO, HttpStatus.NOT_FOUND);

        return res;
    }
}