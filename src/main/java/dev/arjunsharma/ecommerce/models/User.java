package dev.arjunsharma.ecommerce.models;

import jakarta.persistence.Entity;

@Entity
public class User extends BaseModel{
    private String name;
    private String email;
    private String gender;

    public User(String name, String email, String gender){
        this.name = name;
        this.email = email;
        this.gender = gender;
    }

    public User(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}