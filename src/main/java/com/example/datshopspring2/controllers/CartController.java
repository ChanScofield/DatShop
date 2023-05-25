package com.example.datshopspring2.controllers;


import com.example.datshopspring2.dto.BookDto;
import com.example.datshopspring2.models.Book;
import com.example.datshopspring2.models.User;
import com.example.datshopspring2.models.UserProfile;
import com.example.datshopspring2.models.enums.AuthenticationProvider;
import com.example.datshopspring2.security.details.UserDetailsServiceImpl;
import com.example.datshopspring2.services.BookService;
import com.example.datshopspring2.services.OrderService;
import com.example.datshopspring2.services.UserProfileService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@SessionAttributes({"logged", "auth"})
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final BookService bookService;

    private final UserProfileService userProfileService;

    private final OrderService orderService;

    private final UserDetailsServiceImpl userService;
    @GetMapping
    private String getCartPage(Model model, HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        List<BookDto> bookList = new ArrayList<>();

        List<String> states = new ArrayList<>();
        states.add("CONFIRMED");

        int countProduct = 0;
        for (Cookie cookie: cookies) {
            if (cookie.getName().startsWith("BookAdded")) {
                BookDto book = bookService.findBookDtoByBookId(Long.parseLong(cookie.getName().substring(9)), states);
                try {
                    book.setQuantitySold(Integer.parseInt(cookie.getValue()));
                } catch (NumberFormatException e){
                    book.setQuantitySold(0);
                }
                bookList.add(book);
                countProduct++;
            }
        }
        int total = 0;
        for (BookDto book: bookList) {
            total += book.getPrice() * book.getQuantitySold();
        }
        model.addAttribute("total", total);
        model.addAttribute("listBookAdded", bookList);
        model.addAttribute("countProduct", countProduct);
        return "views/cart";
    }

    @PostMapping("/buy-product")
    private String buyProduct(HttpServletRequest request,
                              HttpServletResponse response,
                              RedirectAttributes redirectAttributes,
                              Integer total,
                              Principal principal,
                              Model model) {
        AuthenticationProvider auth = (AuthenticationProvider) model.getAttribute("auth");
        User user = userService.findUserByEmailAndAuthenticationProvider(principal.getName(), auth);
        UserProfile userProfile = userProfileService.findProfileByUserId(user.getUserId());

        if (userProfile.getBalance() < total) {
            redirectAttributes.addFlashAttribute("errorBuy", "Money??");
            return "redirect:/cart";
        } else {
            userProfileService.buy(total, userProfile);

            List<Book> bookList = new ArrayList<>();
            List<Integer> quantity = new ArrayList<>();

            Cookie[] cookies = request.getCookies();
            List<String> states = new ArrayList<>();
            states.add("CONFIRMED");
            for (Cookie cookie : cookies) {
                if (cookie.getName().startsWith("BookAdded")) {
                    bookList.add(bookService.findBookByBookId(Long.parseLong(cookie.getName().substring(9)), states));
                    quantity.add(Integer.valueOf(cookie.getValue()));
                    cookie.setMaxAge(0);
                    cookie.setPath("/");
                    response.addCookie(cookie);
                }
            }

            orderService.saveOrder(total, userProfile, bookList, quantity);

        }
        return "redirect:/home";
    }

    @GetMapping("/update-cart")
    private String updateCart(Long bid, HttpServletRequest request, HttpServletResponse response) {
        String book = "BookAdded" + bid;

        Cookie[] cookies = request.getCookies();

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(book)) {
                cookie.setMaxAge(0);
                cookie.setPath("/");
                response.addCookie(cookie);
            }
        }

        return "redirect:/cart";
    }

    @GetMapping("/add-to-cart")
    private String addToCartAndLoadPage(HttpServletRequest request, HttpServletResponse response, String bid) {
        String book = "BookAdded" + bid;
        addBookToCart(request, response, book);

        return "redirect:/cart";
    }

    @PostMapping("/add-to-cart")
    @ResponseBody
    private void addToCart(HttpServletRequest request, HttpServletResponse response, String bookId) throws IOException {
        String book = "BookAdded" + bookId;
        int count = addBookToCart(request, response, book);

        response.getWriter().println(count);
    }

    private int addBookToCart(HttpServletRequest request, HttpServletResponse response, String book) {
        Cookie[] cookies = request.getCookies();
        int count = 0;

        boolean cookieExist = false;
        for (Cookie cookie: cookies) {
            if (cookie.getName().equals(book)) {
                cookie.setPath("/");
                cookie.setValue(String.valueOf(Integer.parseInt(cookie.getValue()) + 1));
                cookieExist = true;
                response.addCookie(cookie);
            }

            if (cookie.getName().startsWith("BookAdded")) {
                count++;
            }
        }
        if (!cookieExist) {
            Cookie cookieBook = new Cookie(book, "1");
            cookieBook.setPath("/");
            count++;
            response.addCookie(cookieBook);
        }
        return count;
    }
}
