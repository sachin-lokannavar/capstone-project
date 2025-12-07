package com.pizzastore.adminservice.controller;

import com.pizzastore.adminservice.client.UserClient;
import com.pizzastore.adminservice.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import jakarta.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/users") 
public class AdminUserController {

    @Autowired
    private UserClient userClient;

    @GetMapping
    public String viewUsers(Model model, HttpSession session) {
        if (session.getAttribute("loggedInAdmin") == null) {
            return "redirect:/login";
        }

        List<UserDto> users = userClient.getAllUsers();
        model.addAttribute("users", users);
        return "admin/user-list";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id, HttpSession session) {
        if (session.getAttribute("loggedInAdmin") == null) {
            return "redirect:/login";
        }
        
        userClient.deleteUser(id);
        
        //  Redirect to "/users" (Spring adds /admin automatically)
        return "redirect:/users?deleted=true";
    }
 
    @GetMapping("/edit/{id}")
    public String editUserPage(@PathVariable Long id, Model model, HttpSession session) {
        if (session.getAttribute("loggedInAdmin") == null) return "redirect:/login";
        
        UserDto user = userClient.getUserById(id);
        model.addAttribute("user", user);
        return "admin/edit-user";
    }

    
    @PostMapping("/edit/{id}")
    public String updateUser(@PathVariable Long id, @ModelAttribute UserDto user, HttpSession session) {
        if (session.getAttribute("loggedInAdmin") == null) return "redirect:/login";
        
        userClient.updateUser(id, user);
        return "redirect:/users?updated=true";
    }
}