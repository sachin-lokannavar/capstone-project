package com.pizzastore.menuservice.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pizzastore.menuservice.beans.MenuItem;
import com.pizzastore.menuservice.beans.MenuCategory;
import com.pizzastore.menuservice.repository.MenuItemRepository;
import com.pizzastore.menuservice.repository.MenuCategoryRepository;
import com.pizzastore.menuservice.service.MenuService;
import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuItemRepository menuRepository;

    @Autowired
    private MenuCategoryRepository categoryRepository;

    @Override
    public MenuItem addItem(MenuItem item) {
        return menuRepository.save(item);
    }

    @Override
    public List<MenuItem> getAllItems() {
        return menuRepository.findAll();
    }

    @Override
    public MenuItem getItemById(Long id) {
        return menuRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteItem(Long id) {
        menuRepository.deleteById(id);
    }

    @Override
    public List<MenuItem> searchItems(String keyword) {
        if(keyword != null) {
            return menuRepository.findByNameContainingIgnoreCase(keyword);
        }
        return menuRepository.findAll();
    }

    @Override
    public List<MenuCategory> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public MenuCategory addCategory(MenuCategory category) {
        return categoryRepository.save(category);
    }
}