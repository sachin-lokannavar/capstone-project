package com.pizzastore.userservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.pizzastore.userservice.service.UserService;
import com.pizzastore.userservice.beans.User;
import com.pizzastore.userservice.client.MenuClient;
import com.pizzastore.userservice.client.OrderClient;
import com.pizzastore.userservice.client.MessageClient; 
import com.pizzastore.userservice.dto.OrderRequest;
import com.pizzastore.userservice.dto.OrderItemRequest;
import com.pizzastore.userservice.dto.MessageDto; 
import com.pizzastore.userservice.util.JwtUtil;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    @Autowired private UserService userService;
    @Autowired private MenuClient menuClient;
    @Autowired private OrderClient orderClient;
    @Autowired private MessageClient messageClient;
    @Autowired private JwtUtil jwtUtil; 

    @GetMapping("/")
    public String home() { return "home"; }

    @GetMapping("/register")
    public String registerPage() { return "register"; }

    @PostMapping("/register")
    public String register(@ModelAttribute User user, Model model) {
        try {
            userService.saveUser(user);
            return "redirect:/login"; 
        } catch (Exception e) {
            model.addAttribute("error", "Registration failed");
            return "register";
        }
    }

    @GetMapping("/login")
    public String loginPage() { return "login"; }

    @PostMapping("/login")
    public String login(@RequestParam String username, 
                        @RequestParam String password, 
                        HttpSession session, 
                        HttpServletResponse response,
                        Model model) {
        User user = userService.login(username, password);
        
        if (user != null) {
            session.setAttribute("loggedInUser", user);
            
            String token = jwtUtil.generateToken(user.getUsername());
            Cookie jwtCookie = new Cookie("JWT_TOKEN", token);
            jwtCookie.setHttpOnly(true);
            jwtCookie.setPath("/"); 
            jwtCookie.setMaxAge(60 * 30); 
            response.addCookie(jwtCookie);
            
            if (user.getName().toLowerCase().contains("admin") || user.getEmail().toLowerCase().contains("admin")) {
                return "redirect:/admin/dashboard";
            }
            return "redirect:/dashboard";
        }
        
        model.addAttribute("error", "Invalid Credentials");
        return "login";
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        model.addAttribute("name", (user != null) ? user.getName() : "Guest");
        return "dashboard";
    }
    
    @GetMapping("/logout")
    public String logout(HttpSession session, HttpServletResponse response) {
        session.invalidate();
        Cookie cookie = new Cookie("JWT_TOKEN", null);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/login";
    }

    // --- MENU & CART ---

    @GetMapping("/menu")
    public String viewMenu(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
        List<Object> items = new ArrayList<>();
        
        try {
            
            if (keyword != null && !keyword.isEmpty()) {
                items = menuClient.searchItems(keyword);
            } else {
                items = menuClient.getAllItems();
            }
        } catch (Exception e) {
            System.out.println(" Menu Service is down: " + e.getMessage());
            model.addAttribute("error", "Menu is currently unavailable. Please try again later.");
        }
        
        model.addAttribute("menuItems", items);
        model.addAttribute("keyword", keyword); 
        return "user-menu"; 
    }

    @GetMapping("/cart/add/{id}")
    public String addToCart(@PathVariable Long id,
                            @RequestParam String name,
                            @RequestParam Double price,
                            HttpSession session) {
        
        List<OrderItemRequest> cart = (List<OrderItemRequest>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
        }

        boolean exists = false;
        for(OrderItemRequest item : cart) {
            if(item.getMenuItemId().equals(id)) {
                item.setQuantity(item.getQuantity() + 1);
                exists = true;
                break;
            }
        }
        
        if(!exists) {
            cart.add(new OrderItemRequest(id, name, price, 1));
        }

        session.setAttribute("cart", cart);
        return "redirect:/menu"; 
    }

    @GetMapping("/cart/remove/{id}")
    public String removeFromCart(@PathVariable Long id, HttpSession session) {
        List<OrderItemRequest> cart = (List<OrderItemRequest>) session.getAttribute("cart");
        if (cart != null) {
            cart.removeIf(item -> item.getMenuItemId().equals(id));
            session.setAttribute("cart", cart);
        }
        return "redirect:/cart";
    }

    @GetMapping("/cart/clear")
    public String clearCart(HttpSession session) {
        session.removeAttribute("cart");
        return "redirect:/menu";
    }

    @GetMapping("/cart")
    public String viewCart(HttpSession session, Model model) {
        List<OrderItemRequest> cart = (List<OrderItemRequest>) session.getAttribute("cart");
        
        double total = 0.0;
        if(cart != null) {
            for(OrderItemRequest item : cart) {
                total += (item.getPrice() * item.getQuantity());
            }
        }

        model.addAttribute("cartItems", cart);
        model.addAttribute("totalPrice", total);
        return "cart"; 
    }

    @GetMapping("/cart/checkout")
    public String checkout(HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        List<OrderItemRequest> cart = (List<OrderItemRequest>) session.getAttribute("cart");

        if(cart == null || cart.isEmpty()) {
            return "redirect:/cart?error=EmptyCart";
        }

        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setUserId(user != null ? user.getId() : 1L); 
        orderRequest.setItems(cart);

        try {
            orderClient.placeOrder(orderRequest);
            session.removeAttribute("cart");
            return "redirect:/success";
        } catch (Exception e) {
            System.out.println("Order Failed: " + e.getMessage());
            return "redirect:/cart?error=OrderFailed";
        }
    }
    
    @GetMapping("/success")
    public String successPage() { return "order-success"; }
    
    // --- ORDER HISTORY ---
    
    @GetMapping("/my-orders")
    public String viewOrderHistory(Model model, HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) return "redirect:/login";

        try {
            List<Object> orders = orderClient.getOrdersByUser(user.getId());
            model.addAttribute("orders", orders);
        } catch (Exception e) {
            model.addAttribute("error", "Could not fetch orders. Order Service may be down.");
        }

        return "my-orders";
    }
    
    @GetMapping("/my-orders/cancel/{id}")
    public String cancelUserOrder(@PathVariable Long id, HttpSession session) {
        if (session.getAttribute("loggedInUser") == null) return "redirect:/login";

        try {
            orderClient.updateOrderStatus(id, "CANCELLED");
        } catch (Exception e) {
            System.out.println("Error cancelling order: " + e.getMessage());
        }

        return "redirect:/my-orders?cancelled=true";
    }

    // --- CONTACT / MESSAGES ---

    @GetMapping("/contact")
    public String showContactPage(Model model, HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) return "redirect:/login";
        
        try {
            List<MessageDto> history = messageClient.getMessagesByUser(user.getId());
            model.addAttribute("messages", history);
        } catch (Exception e) {
            System.out.println(" Message Service Down: " + e.getMessage());
            model.addAttribute("error", "Messaging unavailable.");
        }
        
        return "contact";
    }

    @PostMapping("/contact")
    public String sendMessage(@RequestParam String content, HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) return "redirect:/login";

        MessageDto msg = new MessageDto();
        msg.setUserId(user.getId());
        msg.setUsername(user.getName());
        msg.setContent(content);

        try {
            messageClient.sendMessage(msg);
            return "redirect:/dashboard?messageSent=true";
        } catch (Exception e) {
            
            System.out.println(" Failed to send message: " + e.getMessage());
            return "redirect:/contact?error=SendFailed";
        }
    }
}
