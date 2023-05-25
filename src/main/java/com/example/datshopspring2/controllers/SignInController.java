package com.example.datshopspring2.controllers;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@RequestMapping("/sign-in")
public class SignInController {

    @GetMapping
    public String getSignInPage(Model model, HttpServletRequest request) {
//        Cookie[] cookies = request.getCookies();
//        for (Cookie cookie : cookies) {
//            if (cookie.getName().equals("acc")) {
//                model.addAttribute("email", cookie.getValue());
//            }
//            if (cookie.getName().equals("pass")) {
//                model.addAttribute("password", cookie.getValue());
//            }
//            if (cookie.getName().equals("rem")) {
//                model.addAttribute("remember", cookie.getValue());
//            }
//        }
        return "/login/login";
    }

}
