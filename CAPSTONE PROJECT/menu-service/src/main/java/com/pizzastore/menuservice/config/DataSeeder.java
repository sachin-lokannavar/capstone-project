package com.pizzastore.menuservice.config;

import com.pizzastore.menuservice.beans.MenuCategory;
import com.pizzastore.menuservice.repository.MenuCategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class DataSeeder {

    @Bean
    public CommandLineRunner initData(MenuCategoryRepository categoryRepository) {
        return args -> {
            
            if (categoryRepository.count() == 0) {
                MenuCategory veg = new MenuCategory();
                veg.setCategoryName("Veg Pizza");

                MenuCategory nonVeg = new MenuCategory();
                nonVeg.setCategoryName("Non-Veg Pizza");

                MenuCategory sides = new MenuCategory();
                sides.setCategoryName("Sides & Beverages");

                categoryRepository.saveAll(Arrays.asList(veg, nonVeg, sides));
                System.out.println(" DEFAULT CATEGORIES INSERTED");
            }
        };
    }
}