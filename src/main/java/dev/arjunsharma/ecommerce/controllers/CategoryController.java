package dev.arjunsharma.ecommerce.controllers;

import dev.arjunsharma.ecommerce.dto.ErrorDTO;
import dev.arjunsharma.ecommerce.exceptions.CategoryNotFoundException;
import dev.arjunsharma.ecommerce.models.Category;
import dev.arjunsharma.ecommerce.services.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CategoryController {

    CategoryService categoryService;

    public CategoryController(CategoryService categoryService){
        this.categoryService = categoryService;
    }

    /* ✅ 1. GET Category */
    @GetMapping("/category/{id}")
    ResponseEntity<Category> getCategory(@PathVariable("id") Long id) throws CategoryNotFoundException {
        Category category = categoryService.getSingleCategory(id);
        ResponseEntity<Category> res;
        res = new ResponseEntity<>(category, HttpStatus.OK);
        return res;
    }

    /* ✅ 2. GET All Categories */
    @GetMapping("/category")
    ResponseEntity<List<Category>> getAllCategory(){
        List<Category> categories = categoryService.getAllCategories();

        ResponseEntity<List<Category>> res;
        res = new ResponseEntity<>(categories, HttpStatus.OK);

        return res;
    }

    /* ✅ 3. CREATE Category */
    @PostMapping("/category")
    ResponseEntity<Category> createCategory(@RequestBody Category category){
        Category createdCategory;

        createdCategory = categoryService.createCategory(category.getTitle());

        ResponseEntity<Category> res;
        res = new ResponseEntity<>(createdCategory, HttpStatus.OK);

        return res;
    }

    /* ✅ 4. UPDATE Category */
    @PutMapping("/category/{id}")
    ResponseEntity<Category> updateCategory(@PathVariable("id") Long id, @RequestBody Category category){
        Category updatedCategory;

        updatedCategory = categoryService.updateCategory(id, category.getTitle());

        ResponseEntity<Category> res;
        res = new ResponseEntity<>(updatedCategory, HttpStatus.OK);

        return res;
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleCategoryNotFoundException(Exception e){
        ErrorDTO errorDTO;
        errorDTO = new ErrorDTO();
        errorDTO.setMessage(e.getMessage());

        ResponseEntity<ErrorDTO> res;
        res = new ResponseEntity<>(errorDTO, HttpStatus.NOT_FOUND);

        return res;
    }
}
