package com.pizzastore.userservice.service;

import com.pizzastore.userservice.beans.User;
import java.util.List;

public interface UserService {
    User saveUser(User user);
    User login(String username, String password);
    List<User> getAllUsers();
    void deleteUser(Long id);

   
    User getUserById(Long id);
    User updateUser(Long id, User user);
}