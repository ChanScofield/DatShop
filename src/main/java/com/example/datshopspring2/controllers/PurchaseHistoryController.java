package com.example.datshopspring2.controllers;

import com.example.datshopspring2.models.Order;
import com.example.datshopspring2.models.OrderInformation;
import com.example.datshopspring2.models.enums.AuthenticationProvider;
import com.example.datshopspring2.services.OrderInformationService;
import com.example.datshopspring2.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/purchase-history")
@SessionAttributes({"logged", "auth"})
@RequiredArgsConstructor
public class PurchaseHistoryController {

    private final OrderService orderService;

    private final OrderInformationService orderInformationService;

    @GetMapping
    private String getPurchaseHistoryPage(Model model,
                                          Principal principal) {
        AuthenticationProvider auth = (AuthenticationProvider) model.getAttribute("auth");

        List<Order> orderList = orderService.findAllOrderByEmailAndAuth(principal.getName(), auth);

        model.addAttribute("listOrders", orderList);
        return "views/history";
    }

    @GetMapping("/details")
    private String getDetailHistory(Model model, Long orderId) {
        List<OrderInformation> orderInformations = orderInformationService.findInformationOrderByOrderId(orderId);

        model.addAttribute("informations", orderInformations);
        return "views/orderInformation";
    }

    @GetMapping("/global-history")
    private String getGlobalHistory(Model model) {
        List<Order> orderList = orderService.findAllOrder();

        model.addAttribute("listOrders", orderList);
        model.addAttribute("global", true);

        return "views/history";
    }


}
