package com.pizzastore.adminservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
// ⭐ ADD THESE IMPORTS
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class MenuItemDto {

    private Long id;

    //  Add Annotation
    @NotBlank(message = "Name cannot be empty")
    private String name;

    private String description;

    //  Add Annotation
    @NotNull(message = "Price is required")
    @Min(value = 0, message = "Price cannot be negative")
    private Double price;

    private Boolean available;
    private String imageUrl;
    
    private MenuCategoryDto category;
    private Long categoryId; 

    // --- Getters & Setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
    
    public Boolean getAvailable() { return available; }
    public void setAvailable(Boolean available) { this.available = available; }
    
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public MenuCategoryDto getCategory() { return category; }
    public void setCategory(MenuCategoryDto category) { this.category = category; }

    public Long getCategoryId() { return categoryId; }
    public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }
}