package dev.arjunsharma.ecommerce.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Product extends BaseModel{
    private String title;
    private String description;
    private String imageUrl;
    private Double price;


    @ManyToOne
    private Category category;

    public Product() {
    }

    public Product(String title, String description, String imageUrl, Double price, Category category) {
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.price = price;
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Product{" +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", price=" + price +
                ", category='" + category + '\'' +
                '}';
    }
}
