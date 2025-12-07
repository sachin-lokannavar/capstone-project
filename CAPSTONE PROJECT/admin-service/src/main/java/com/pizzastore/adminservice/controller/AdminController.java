package com.pizzastore.adminservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.pizzastore.adminservice.client.OrderClient;
import com.pizzastore.adminservice.entity.Admin;
import com.pizzastore.adminservice.service.AdminService;
import jakarta.servlet.http.HttpSession;

@Controller
public class AdminController {

    @Autowired
    private AdminService adminService;
    
    @Autowired private OrderClient orderClient;

    @GetMapping("/")
    public String home() { return "home"; }

    @GetMapping("/login")
    public String loginPage(Model model) {
        return "admin-login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, 
                        @RequestParam String password, 
                        Model model, 
                        HttpSession session) {
        
        Admin admin = adminService.login(username, password);
        
        if (admin != null) {
            session.setAttribute("loggedInAdmin", admin);
            return "redirect:/dashboard"; 
        }
        
        model.addAttribute("error", "Invalid Credentials");
        return "admin-login";
    }

    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        model.addAttribute("admin", new Admin());
        return "admin-register";
    }

   
    @PostMapping("/register")
    public String registerAdmin(@ModelAttribute Admin admin, Model model) {
        try {
            
            Admin existing = adminService.login(admin.getUsername(), admin.getPassword()); 
            
            
            adminService.saveAdmin(admin);
            return "redirect:/login"; 
        } catch (Exception e) {
            
            model.addAttribute("error", "Registration Failed: Username likely already exists.");
            return "admin-register";
        }
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        if (session.getAttribute("loggedInAdmin") == null) {
            return "redirect:/login";
        }
        Admin admin = (Admin) session.getAttribute("loggedInAdmin");
        model.addAttribute("name", admin.getUsername());
        
        try {
            Double revenue = orderClient.getTotalRevenue();
            model.addAttribute("revenue", revenue);
        } catch (Exception e) {
            model.addAttribute("revenue", 0.0);
        }

        return "admin-dashboard";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}