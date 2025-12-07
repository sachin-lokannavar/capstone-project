package com.pizzastore.orderservice.service;

import com.pizzastore.orderservice.beans.Order;
import java.util.List;

public interface OrderService {
    Order placeOrder(Order order);
    List<Order> getAllOrders();
    Order getOrderById(Long id);
    void cancelOrder(Long id);
    Double getTotalRevenue();
}