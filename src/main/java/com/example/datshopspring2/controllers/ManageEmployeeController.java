package com.example.datshopspring2.controllers;


import com.example.datshopspring2.dto.BookDto;
import com.example.datshopspring2.models.User;
import com.example.datshopspring2.models.UserProfile;
import com.example.datshopspring2.security.details.UserDetailsServiceImpl;
import com.example.datshopspring2.services.BookService;
import com.example.datshopspring2.services.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/manage-employees")
@RequiredArgsConstructor
public class ManageEmployeeController {

    private final UserProfileService userProfileService;

    private final UserDetailsServiceImpl userService;

    private final BookService bookService;

    @GetMapping
    private String getManageEmployeePage(Model model) {
        List<UserProfile> userProfiles = userProfileService.findAllConfirmedEmployeesProfile();
        model.addAttribute("listEmployees", userProfiles);

        return "adminViews/managerEmployees";
    }

    @GetMapping("/load-employee")
    private String loadEmployee(Model model, Long eid) {
        User user = userService.findAccountByAccountId(eid);

        model.addAttribute("user", user);

        return "adminViews/editEmployee";
    }

    @GetMapping("/employee-products")
    private String loadEmployeeProduct(Model model, Long eid) {
        loadProducts(model, eid);

        return "adminViews/employeeProductManagement";
    }

    @GetMapping("/delete-product")
    private String deleteProduct(Model model, Long bid, Long eid) {
        List<String> states = new ArrayList<>();
        states.add("CONFIRMED");
        bookService.deleteBookById(bid, states);

        loadProducts(model, eid);

        return "adminViews/employeeProductManagement";
    }

    @GetMapping("/delete-employee")
    private String deleteEmployee(Long eid) {
        userService.deleteUserById(eid);

        return "redirect:/manage-employees";
    }

    @PostMapping("/edit-employee")
    private String editEmployee(Long eid, String isSeller) {
        userService.updateSeller(eid, isSeller);
        return "redirect:/manage-employees";
    }

    private void loadProducts(Model model, Long eid) {
        User user = userService.findAccountByAccountId(eid);

        List<BookDto> bookList = bookService.findBooksBySeller(user);
        UserProfile seller = userProfileService.findProfileByUserId(eid);
        model.addAttribute("listBook", bookList);
        model.addAttribute("sellerIs", seller);
    }

}
