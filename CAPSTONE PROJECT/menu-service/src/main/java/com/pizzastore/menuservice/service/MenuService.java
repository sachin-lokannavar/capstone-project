package com.pizzastore.menuservice.service;

import com.pizzastore.menuservice.beans.MenuItem;
import com.pizzastore.menuservice.beans.MenuCategory;
import java.util.List;

public interface MenuService {
    MenuItem addItem(MenuItem item);
    List<MenuItem> getAllItems();
    MenuItem getItemById(Long id);
    void deleteItem(Long id);
    List<MenuItem> searchItems(String keyword);
    List<MenuCategory> getAllCategories();
    MenuCategory addCategory(MenuCategory category);
}