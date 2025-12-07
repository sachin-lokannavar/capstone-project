package com.pizzastore.adminservice.client;

import com.pizzastore.adminservice.dto.OrderDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "order-service")
public interface OrderClient {

    
    @GetMapping("/order/orders/all") 
    List<OrderDto> getAllOrders();
    
    @PostMapping("/order/orders/update/{id}") 
    void updateOrderStatus(@PathVariable("id") Long id, @RequestParam("status") String status);
    
    @GetMapping("/order/orders/revenue")
    Double getTotalRevenue();
}