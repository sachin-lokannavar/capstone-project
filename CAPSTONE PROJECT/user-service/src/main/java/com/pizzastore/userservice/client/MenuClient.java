package com.pizzastore.userservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;


@FeignClient(name = "menu-service")
public interface MenuClient {

    @GetMapping("/menu/items")
    List<Object> getAllItems();

   
    @PostMapping("/menu/items")
    Object addItem(@RequestBody Object item);
    
    @GetMapping("/menu/search")
    List<Object> searchItems(@RequestParam("keyword") String keyword);

 
    @DeleteMapping("/menu/items/{id}")
    void deleteItem(@PathVariable("id") Long id);
}