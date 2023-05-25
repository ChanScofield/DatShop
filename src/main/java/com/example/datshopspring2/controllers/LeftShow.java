package com.example.datshopspring2.controllers;

import com.example.datshopspring2.models.Book;
import com.example.datshopspring2.models.Categories;
import com.example.datshopspring2.services.BookService;
import com.example.datshopspring2.services.CategoriesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import java.util.List;

@Component
@RequiredArgsConstructor
public class LeftShow {

    private final BookService bookService;

    private final CategoriesService categoriesService;

    public void show(Model model) {
        List<Categories> categoriesList = categoriesService.findAll();
        model.addAttribute("listCategories", categoriesList);

        Book hotBook = bookService.findFirstByOrderByQuantitySoldDesc();
        model.addAttribute("hotBook", hotBook);

        Book lastBook = bookService.findFirstByOrderByBookIdDesc();
        model.addAttribute("lastBook", lastBook);

    }

}
