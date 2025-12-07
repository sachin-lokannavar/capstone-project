package com.pizzastore.adminservice.controller;

import com.pizzastore.adminservice.client.MenuClient;
import com.pizzastore.adminservice.dto.MenuItemDto;
import com.pizzastore.adminservice.dto.MenuCategoryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/menu")
public class AdminMenuController {

    @Autowired private MenuClient menuClient;

    @GetMapping
    public String viewMenu(Model model) {
        model.addAttribute("menuItems", menuClient.getAllItems());
        return "admin/menu-list"; 
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("item", new MenuItemDto());
        model.addAttribute("categories", menuClient.getAllCategories());
        return "admin/add-item"; 
    }

    @PostMapping("/add")
    public String addItem(@ModelAttribute MenuItemDto item, @RequestParam("categoryId") Long categoryId) {
        
        MenuCategoryDto catDto = new MenuCategoryDto();
        catDto.setCategoryId(categoryId);
        item.setCategory(catDto); 
        
        menuClient.addItem(item);
        return "redirect:/menu"; 
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        MenuItemDto item = menuClient.getItemById(id);
        model.addAttribute("item", item);
        model.addAttribute("categories", menuClient.getAllCategories());
        return "admin/edit-menu-item";
    }

    @PostMapping("/update/{id}")
    public String updateItem(@PathVariable Long id, @ModelAttribute MenuItemDto item, @RequestParam("categoryId") Long categoryId) {
        
        MenuCategoryDto catDto = new MenuCategoryDto();
        catDto.setCategoryId(categoryId);
        item.setCategory(catDto);
        
        menuClient.updateItem(id, item);
        return "redirect:/menu";
    }

    @GetMapping("/delete/{id}")
    public String deleteItem(@PathVariable Long id) {
        menuClient.deleteItem(id);
        return "redirect:/menu";
    }
}