package com.example.datshopspring2.controllers;


import com.example.datshopspring2.dto.ChangePasswordDto;
import com.example.datshopspring2.dto.ChangeProfileDto;
import com.example.datshopspring2.models.User;
import com.example.datshopspring2.models.UserProfile;
import com.example.datshopspring2.models.enums.AuthenticationProvider;
import com.example.datshopspring2.security.details.UserDetailsServiceImpl;
import com.example.datshopspring2.services.UserProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller
@RequestMapping("/profile")
@SessionAttributes({"logged", "countProduct", "auth"})
@RequiredArgsConstructor
public class ProfileController {

    private final UserProfileService userProfileService;

    private final UserDetailsServiceImpl userService;

    @GetMapping
    private String getProfilePage(Model model,
                                  Principal principal) {
        AuthenticationProvider auth = (AuthenticationProvider) model.getAttribute("auth");
        User user = userService.findUserByEmailAndAuthenticationProvider(principal.getName(), auth);
        UserProfile userProfile = userProfileService.findProfileByUserId(user.getUserId());

        model.addAttribute("changeProfileDto", new ChangeProfileDto());
        model.addAttribute("changePasswordDto", new ChangePasswordDto());
        addAttributes(model, userProfile);
        return "views/profile";
    }

    @PostMapping("/save-profile")
    private String changeProfile(Principal principal,
                                 @Valid ChangeProfileDto changeProfileDTO,
                                 BindingResult bindingResult,
                                 Model model) {
        model.addAttribute("changePasswordDto", new ChangePasswordDto());

        AuthenticationProvider auth = (AuthenticationProvider) model.getAttribute("auth");
        User user = userService.findUserByEmailAndAuthenticationProvider(principal.getName(), auth);
        UserProfile userProfile = userProfileService.findProfileByUserId(user.getUserId());
        if (!bindingResult.hasErrors()) {
            String error = userProfileService.updateProfile(userProfile, changeProfileDTO);
            System.out.println(error);
            if (!error.isBlank()) {
                bindingResult.rejectValue("phoneNumber", "error.changeProfileDto", error);
                System.out.println("==================");
            }
            model.addAttribute("changeSuccess", "Changed");
        }

        addAttributes(model, userProfile);
        return "views/profile";
    }

    @PostMapping("/change-password")
    private String changePassword(Principal principal,
                                  @Valid ChangePasswordDto changePasswordDTO,
                                  BindingResult bindingResult,
                                  Model model) {
        AuthenticationProvider auth = (AuthenticationProvider) model.getAttribute("auth");
        User user = userService.findUserByEmailAndAuthenticationProvider(principal.getName(), auth);
        UserProfile userProfile = userProfileService.findProfileByUserId(user.getUserId());
        if (!bindingResult.hasErrors()) {
            Map<String, String> map = userService.updatePassword(userProfile, changePasswordDTO);
            if (!map.isEmpty()) {
                bindingResult.rejectValue(map.keySet().stream().findFirst().orElseThrow(),
                        "error.changePasswordDto",
                        map.get(map.keySet().stream().findFirst().orElseThrow()));
            } else {
                model.addAttribute("passwordChanged", "Password changed");
            }
        } else {
            try {
                String errorObject = Objects.requireNonNull(bindingResult.getGlobalError()).getDefaultMessage();
                bindingResult.rejectValue("repeatNewPassword",
                        "error.changePasswordDto",
                        Objects.requireNonNull(errorObject));
            } catch (Exception ignored) {
            }
        }


        model.addAttribute("changeProfileDto", new ChangeProfileDto());
        addAttributes(model, userProfile);
        return "views/profile";
    }

    @PostMapping("/donate")
    private String donate(Principal principal,
                          Integer donate,
                          Model model) {
        AuthenticationProvider auth = (AuthenticationProvider) model.getAttribute("auth");
        User user = userService.findUserByEmailAndAuthenticationProvider(principal.getName(), auth);
        UserProfile userProfile = userProfileService.findProfileByUserId(user.getUserId());
        String notification = userProfileService.updateBalance(userProfile, donate);
        if (notification.equals("Done")) {
            model.addAttribute("niceDonate", notification);
        } else {
            model.addAttribute("errorDonate", notification);
        }

        model.addAttribute("changeProfileDto", new ChangeProfileDto());
        model.addAttribute("changePasswordDto", new ChangePasswordDto());
        addAttributes(model, userProfile);
        return "views/profile";
    }

    private static void addAttributes(Model model, UserProfile userProfile) {
        model.addAttribute("userProfile", userProfile);

        List<String> cities = new ArrayList<>();
        cities.add("Samara");
        cities.add("Ufa");
        cities.add("Moscow");
        cities.add("Kazan");
        cities.add("Saint Petersburg");
        cities.add("Novosibirsk");
        cities.add("Yekaterinburg");
        cities.add("Nizhny Novgorod");
        cities.add("Chelyabinsk");
        cities.add("Krasnoyarsk");

        model.addAttribute("cities", cities);


    }
}
