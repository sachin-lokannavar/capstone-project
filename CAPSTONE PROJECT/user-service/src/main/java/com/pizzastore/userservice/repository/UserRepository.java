package com.pizzastore.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.pizzastore.userservice.beans.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
}
