package com.example.datshopspring2.controllers;


import com.example.datshopspring2.dto.SignUpDto;
import com.example.datshopspring2.exceptions.UserNotFoundException;
import com.example.datshopspring2.models.enums.AuthenticationProvider;
import com.example.datshopspring2.security.details.UserDetailsServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class SignUpController {

    private final UserDetailsServiceImpl userService;

    @GetMapping("/sign-up")
    public String getSignUpPage(Model model) {
        model.addAttribute("signUpDto", new SignUpDto());
        return "/login/signup";
    }

    @PostMapping("/sign-up")
    public String signUpAccount(@Valid SignUpDto signUpDto,
                                BindingResult bindingResult) {
        try {
            userService.findUserByEmailAndAuthenticationProvider(signUpDto.getEmail(), AuthenticationProvider.LOCAL);
            bindingResult.rejectValue("email", "error.signUpDto", "Email used");
        } catch (UserNotFoundException e) {
            if (!bindingResult.hasErrors()) {
                if (!signUpDto.getPassword().equals(signUpDto.getConfirmPassword())) {
                    bindingResult.rejectValue("confirmPassword",
                            "error.SignUpDto",
                            "Confirm password incorrect");
                } else {
                    userService.saveAccount(signUpDto);
                    return "redirect:/sign-in";
                }
            }
        }

        return "/login/signup";

    }

}
