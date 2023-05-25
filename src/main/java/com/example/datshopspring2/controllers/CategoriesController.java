package com.example.datshopspring2.controllers;

import com.example.datshopspring2.repositories.CategoriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CategoriesController {

    @Autowired
    private CategoriesRepository categoriesRepository;



}
