package dev.arjunsharma.ecommerce.services;

import dev.arjunsharma.ecommerce.exceptions.CategoryNotFoundException;
import dev.arjunsharma.ecommerce.models.Category;
import dev.arjunsharma.ecommerce.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    public Category getSingleCategory(Long id) throws CategoryNotFoundException {
        Optional<Category> optionalCategory = categoryRepository.findById(id);

        if(optionalCategory.isEmpty()){
            throw new CategoryNotFoundException("The category with id " + id + " is not in the DB");
        }

        Category category;
        category = optionalCategory.get();

        return category;
    }

    public List<Category> getAllCategories(){
        List<Category> categories;
        categories = categoryRepository.findAll();

        return categories;
    }

    public Category createCategory(String title){
        Category c = new Category();

        // first check if the category with the given title is already there in DB
        Optional<Category> categoryFromDB = categoryRepository.findByTitle(title);

        if(categoryFromDB.isPresent()){
            System.out.println("The category already was in DB, so directly sent it, not saving a new one");
            return categoryFromDB.get();
        }

        c.setTitle(title);

        Category savedCategory;
        savedCategory = categoryRepository.save(c);

        return savedCategory;
    }

    public Category updateCategory(Long id, String title) throws CategoryNotFoundException{
        Optional<Category> categoryFromDB = categoryRepository.findById(id);

        if(categoryFromDB.isEmpty()){
            throw new CategoryNotFoundException(
                    "Category with ID " + id + " not found in DB, so you can't update it"
            );
        }

        Category c = categoryFromDB.get();
        c.setTitle(title);

        Category updatedCategory;
        updatedCategory = categoryRepository.save(c);

        return updatedCategory;
    }
}
