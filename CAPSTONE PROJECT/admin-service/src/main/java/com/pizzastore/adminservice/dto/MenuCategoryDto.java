package com.pizzastore.adminservice.dto;

public class MenuCategoryDto {
    private Long categoryId;
    private String categoryName;

    // Constructors
    public MenuCategoryDto() {}
    public MenuCategoryDto(Long categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    // Getters & Setters
    public Long getCategoryId() { return categoryId; }
    public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }
    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }
}