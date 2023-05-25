package com.example.datshopspring2.services;

import com.example.datshopspring2.models.Categories;

import java.util.List;

public interface CategoriesService {
    Categories findByCategoriesId(Long categoriesId);

    List<Categories> findAll();

    void addNewCategory(String category);
}
