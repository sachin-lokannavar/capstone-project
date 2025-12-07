package com.pizzastore.menuservice.beans;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore; // Import this!
import java.util.List;

@Entity
@Table(name = "MENU_CATEGORIES")
public class MenuCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;
    
    private String categoryName;

    
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    @JsonIgnore 
    private List<MenuItem> items;

    // Getters and Setters
    public Long getCategoryId() { return categoryId; }
    public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }
    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }
    public List<MenuItem> getItems() { return items; }
    public void setItems(List<MenuItem> items) { this.items = items; }
}