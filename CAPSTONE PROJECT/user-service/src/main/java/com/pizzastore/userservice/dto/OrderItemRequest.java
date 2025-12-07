package com.pizzastore.userservice.dto;

public class OrderItemRequest {
    private Long menuItemId;
    private String name;
    private Double price;
    private Integer quantity;

    // Default Constructor
    public OrderItemRequest() {}

    public OrderItemRequest(Long menuItemId, String name, Double price, Integer quantity) {
        this.menuItemId = menuItemId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    // Getters and Setters
    public Long getMenuItemId() { return menuItemId; }
    public void setMenuItemId(Long menuItemId) { this.menuItemId = menuItemId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
}