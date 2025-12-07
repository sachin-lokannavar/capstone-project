package com.pizzastore.userservice.config;

import com.pizzastore.userservice.beans.User;
import com.pizzastore.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("----- LOGIN ATTEMPT DEBUG -----");
        System.out.println("Searching for username: " + username);
        
        User user = userRepository.findByUsername(username);
        
        if (user == null) {
            System.out.println("User NOT FOUND in Database!");
            throw new UsernameNotFoundException("User not found");
        }
        
        System.out.println("User FOUND: " + user.getUsername());
        System.out.println("Stored Password Hash: " + user.getPassword());
        System.out.println("User Role: " + user.getRole());
        System.out.println("-------------------------------");
        
        return new CustomUserDetails(user);
    }
}
