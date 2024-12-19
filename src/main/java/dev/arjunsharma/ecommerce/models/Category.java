package dev.arjunsharma.ecommerce.models;

import jakarta.persistence.Entity;

@Entity
public class Category extends BaseModel{
    private String title;

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

    @Override
    public String toString() {
        return "Category{" +
                ", title='" + title + '\'' +
                '}';
    }
}
