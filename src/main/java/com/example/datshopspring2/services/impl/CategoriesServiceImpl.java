package com.example.datshopspring2.services.impl;

import com.example.datshopspring2.exceptions.NotFoundException;
import com.example.datshopspring2.models.Categories;
import com.example.datshopspring2.repositories.CategoriesRepository;
import com.example.datshopspring2.services.CategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriesServiceImpl implements CategoriesService {

    @Autowired
    private CategoriesRepository categoriesRepository;

    @Override
    public Categories findByCategoriesId(Long categoriesId) {
        return categoriesRepository.findByCategoriesId(categoriesId).orElseThrow(
                () -> new NotFoundException("Not found category", 404)
        );
    }

    @Override
    public List<Categories> findAll() {
        return categoriesRepository.findAll();
    }

    @Override
    public void addNewCategory(String category) {
        Categories categories = Categories.builder()
                .categoriesName(category)
                .build();
        categoriesRepository.save(categories);
    }
}
