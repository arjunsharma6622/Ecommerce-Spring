package dev.arjunsharma.ecommerce.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
public class Category extends BaseModel{
    private String title;

    /*  - here we are using json ignore because, if not used it causes infinite nesting as here in
        category we are returning products list when needed, and those products will have category in them
        and so on... this will cause infinite nesting, so we are ignoring this products in category
        - alternatively we can also use custom DTO (categoryDto) with custom fields in it
        rather than sending the entity
    */

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Product> products;

    public Category(String title){
        this.title = title;
    }

    public Category(){

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "Category{" +
                "title='" + title + '\'' +
                '}';
    }
}
