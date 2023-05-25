package com.example.datshopspring2.controllers;


import com.example.datshopspring2.dto.BookDto;
import com.example.datshopspring2.dto.BooksPage;
import com.example.datshopspring2.models.Categories;
import com.example.datshopspring2.models.User;
import com.example.datshopspring2.models.enums.AuthenticationProvider;
import com.example.datshopspring2.security.details.UserDetailsServiceImpl;
import com.example.datshopspring2.services.BookService;
import com.example.datshopspring2.services.CategoriesService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/manage-product")
@SessionAttributes({"logged", "admin", "auth"})
@RequiredArgsConstructor
public class ManageProductController {

    private final BookService bookService;

    private final UserDetailsServiceImpl userService;

    private final CategoriesService categoriesService;

    @GetMapping
    private String getManageProductPage(Model model,
                                        Principal principal) {
        AuthenticationProvider auth = (AuthenticationProvider) model.getAttribute("auth");

        User user = userService.findUserByEmailAndAuthenticationProvider(principal.getName(), auth);
        BooksPage booksPage = bookService.findAll(1, user);
        List<BookDto> bookList = booksPage.getBooks();

        model.addAttribute("listBook", bookList);
        model.addAttribute("totalPage", booksPage.getTotalPagesCount());
        model.addAttribute("page", 1);

        return "managerViews/managerProduct";
    }

    @GetMapping("/{page}")
    @ResponseBody
    private String loadMoreBook(Model model,
                                @PathVariable Integer page,
                                Principal principal) {
        AuthenticationProvider auth = (AuthenticationProvider) model.getAttribute("auth");

        User user = userService.findUserByEmailAndAuthenticationProvider(principal.getName(), auth);
        BooksPage booksPage = bookService.findAll(page, user);
        List<BookDto> bookList = booksPage.getBooks();

        int totalPage = booksPage.getTotalPagesCount();

        StringBuilder stringBuilder = new StringBuilder();
        for (BookDto bookDto : bookList) {
            stringBuilder.append("<tr class=\"product\">\n" +
                    "                    <td>" + bookDto.getId() + "</td>\n" +
                    "                    <td>" + bookDto.getTitle() + "</td>\n" +
                    "                    <td>\n" +
                    "                        <img src=\"" + bookDto.getImage() + "\">\n" +
                    "                    </td>\n" +
                    "                    <td>" + bookDto.getPrice() + " $</td>\n" +
                    "                    <td>" + bookDto.getQuantitySold() + "</td>\n" +
                    "                    <td> " + bookDto.getAuthor() + " </td>\n" +
                    "                    <td>" + bookDto.getDescription() + " </td>\n" +
                    "                    <td>\n" +
                    "                        <a href=\"/manage-product/load-product?bid=" + bookDto.getId() + "\" class=\"edit\">\n" +
                    "                            <i class=\"material-icons\" data-toggle=\"tooltip\" title=\"Edit\">&#xE254;</i>\n" +
                    "                        </a>\n" +
                    "                        <a href=\"/manage-product/delete-product?bid=" + bookDto.getId() + "\" class=\"delete\">\n" +
                    "                            <i class=\"material-icons\" data-toggle=\"tooltip\" title=\"Delete\">&#xE872;</i>\n" +
                    "                        </a>\n" +
                    "                    </td>\n" +
                    "                </tr>");
        }

        stringBuilder.append("||");

        String disabledPrevious = "";
        String disabledNext = "";

        if (page - 1 < 1) {
            disabledPrevious += "page-item disabled";
        }
        if (page == totalPage) {
            disabledNext += "page-item disabled";
        }

        stringBuilder.append(disabledPrevious + "||");

        if (page - 1 < 1) {
            stringBuilder.append("1||2||3||");
        } else if (page == totalPage) {
            stringBuilder.append((totalPage - 2) + "||" + (totalPage - 1) + "||" + totalPage + "||");
        } else {
            stringBuilder.append((page - 1) + "||" + page + "||" + (page + 1) + "||");
        }

        stringBuilder.append(disabledNext + "||");

        stringBuilder.append(page);


        return stringBuilder.toString();
    }


    @GetMapping("/add-product")
    private String loadAddProductPage(Model model) {
        List<Categories> categoriesList = categoriesService.findAll();
        model.addAttribute("listCategory", categoriesList);
        model.addAttribute("bookDto", new BookDto());
        return "managerViews/addProduct";
    }

    @PostMapping("/add-product")
    private String addProduct(Model model,
                              @Valid BookDto book,
                              BindingResult bindingResult,
                              Principal principal) {
        AuthenticationProvider auth = (AuthenticationProvider) model.getAttribute("auth");

        User user = userService.findUserByEmailAndAuthenticationProvider(principal.getName(), auth);

        if (!bindingResult.hasErrors()) {
            if (book.getPrice() < 0) {
                bindingResult.rejectValue("price", "error.bookDto", "< 0 ?");
            } else {
                bookService.addBook(book, user);
                return "redirect:/manage-product";
            }
        }
        List<Categories> categoriesList = categoriesService.findAll();
        model.addAttribute("listCategory", categoriesList);
        return "managerViews/addProduct";

    }

    @GetMapping("/delete-product")
    private String deleteProduct(Long bid) {
        List<String> states = new ArrayList<>();
        states.add("CONFIRMED");
        bookService.deleteBookById(bid, states);
        return "redirect:/manage-product";
    }

    @GetMapping("/load-product")
    private String loadProduct(Long bid, Model model) {
        List<String> states = new ArrayList<>();
        states.add("CONFIRMED");
        states.add("NOT_CONFIRMED");
        BookDto book = bookService.findBookDtoByBookId(bid, states);
        model.addAttribute("bookDto", book);

        List<Categories> categoriesList = categoriesService.findAll();
        model.addAttribute("listCategory", categoriesList);
        return "managerViews/editProduct";
    }

    @PostMapping("/edit-product")
    private String editProduct(Model model,
                               @Valid BookDto bookDto,
                               BindingResult bindingResult,
                               Long bid) {
        List<String> states = new ArrayList<>();
        states.add("CONFIRMED");
        bookDto.setId(String.valueOf(bid));
        if (!bindingResult.hasErrors()) {
            if (bookDto.getPrice() < 0) {
                bindingResult.rejectValue("price", "error.bookDto", "< 0 ?");
            } else {
                bookService.updateBook(bookDto, bid, states);
                return "redirect:/manage-product";
            }
        }

        List<Categories> categoriesList = categoriesService.findAll();
        model.addAttribute("listCategory", categoriesList);
        return "managerViews/editProduct";
    }

    @PostMapping("/add-category")
    private String addCategory(String category) {
        categoriesService.addNewCategory(category);
        return "redirect:/manage-product";
    }


}
