package com.pizzastore.adminservice.client;

import com.pizzastore.adminservice.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import java.util.List;

@FeignClient(name = "user-service")
public interface UserClient {

    
    @GetMapping("/user/api/users")
    List<UserDto> getAllUsers();

    @DeleteMapping("/user/api/users/{id}")
    void deleteUser(@PathVariable("id") Long id);

    
    @GetMapping("/user/api/users/{id}")
    UserDto getUserById(@PathVariable("id") Long id);

    @PutMapping("/user/api/users/{id}")
    void updateUser(@PathVariable("id") Long id, @RequestBody UserDto user);
}