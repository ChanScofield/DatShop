package com.example.datshopspring2.controllers;

import com.example.datshopspring2.dto.BookDto;
import com.example.datshopspring2.models.Book;
import com.example.datshopspring2.models.Categories;
import com.example.datshopspring2.models.User;
import com.example.datshopspring2.models.UserProfile;
import com.example.datshopspring2.models.enums.AuthenticationProvider;
import com.example.datshopspring2.models.enums.State;
import com.example.datshopspring2.security.details.UserDetailsServiceImpl;
import com.example.datshopspring2.services.BookService;
import com.example.datshopspring2.services.CategoriesService;
import com.example.datshopspring2.services.UserProfileService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping({"/home", "/"})
@SessionAttributes({"logged", "countProduct", "admin", "auth"})
@RequiredArgsConstructor
public class HomeController {

    private final BookService bookService;

    private final UserDetailsServiceImpl userService;

    private final UserProfileService userProfileService;

    private final CategoriesService categoriesService;

    private final LeftShow leftShow;

    private final ConversionService conversionService;

    @RequestMapping
    public String getHomePage(Model model,
                              HttpServletRequest request,
                              Authentication authentication,
                              HttpSession httpSession) {

        State state = conversionService.convert("CONFIRMED", State.class);
        System.out.println(state);

        if (authentication != null) {
            String role = httpSession.getAttribute("logged").toString();
            if (role.substring(1).equals("admin")) {
                model.addAttribute("admin", true);
            }
            if (role.substring(1).equals("oauth2_user")) {
                model.addAttribute("logged", "_user");
            } else {
                model.addAttribute("logged", role);
            }

            System.out.println(role);
        }

        leftShow.show(model);
        List<BookDto> bookList = bookService.findTop12();
        model.addAttribute("listBook", bookList);
        model.addAttribute("page", "loadMore");
        getBookInCookie(model, request);

        return "/views/home_page";
    }

    @RequestMapping("/category")
    public String getBooksByCategory(Model model,
                                     @RequestParam("cid") Long categoriesId,
                                     HttpServletRequest request) {
        leftShow.show(model);
        Categories categories = categoriesService.findByCategoriesId(categoriesId);
        List<BookDto> bookList = bookService.findAllByCategoriesId(categories);
        getBookInCookie(model, request);
        model.addAttribute("listBook", bookList);
        model.addAttribute("page", "loadMoreByCategory");
        return "/views/home_page";
    }

    private static void getBookInCookie(Model model, HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        int countProduct = 0;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().startsWith("BookAdded")) {
                    countProduct++;
                }
            }

            model.addAttribute("countProduct", countProduct);
        }
    }

    @RequestMapping("/show-book")
    private String showBookPage(Model model,
                                @RequestParam("bid") Long bookId,
                                HttpServletRequest request) {
        leftShow.show(model);
        List<String> states = new ArrayList<>();
        states.add("CONFIRMED");

        Book book = bookService.findBookByBookId(bookId, states);
        model.addAttribute("book", book);
        getBookInCookie(model, request);

        UserProfile sellerName = userProfileService.findProfileByUserId(book.getSeller().getUserId());
        model.addAttribute("sellerIs", sellerName);
        return "/views/detail";
    }

    @RequestMapping("/book-by")
    private String showBooksByUserId(Model model,
                                     @RequestParam("uid") Long sellerId,
                                     HttpServletRequest request) {
        leftShow.show(model);
        User user = userService.findAccountByAccountId(sellerId);
        List<BookDto> bookList = bookService.findBooksBySeller(user);
        model.addAttribute("listBook", bookList);
        model.addAttribute("page", "loadMore");
        getBookInCookie(model, request);

        return "/views/home_page";

    }

    @PostMapping("/live-search")
    @ResponseBody
    private String updateBooksSearch(@RequestParam String query, Model model) {
        leftShow.show(model);
        List<BookDto> bookList;

        if (query.isEmpty()) {
            bookList = bookService.findTop12();
        } else {
            bookList = bookService.findByLikeTitle(query);
        }

        StringBuffer stringBuffer = getStringBuffer(bookList);

        return stringBuffer.toString();
    }


    @PostMapping("/loadMore")
    @ResponseBody
    private String loadMoreBook(@RequestParam Integer exits, Model model) {
        List<BookDto> bookList;

        bookList = bookService.findNext3Book(exits);
        StringBuffer stringBuffer = getStringBuffer(bookList);

        return stringBuffer.toString();
    }

    @PostMapping("/loadMoreByCategory")
    @ResponseBody
    private String loadMoreBookByCategory(@RequestParam Integer exits,
                                          @RequestParam Long cId,
                                          Model model) {
        List<BookDto> bookList;

        bookList = bookService.findNext3ByCategoriesId(exits, cId);
        StringBuffer stringBuffer = getStringBuffer(bookList);

        return stringBuffer.toString();
    }

    private static StringBuffer getStringBuffer(List<BookDto> bookList) {
        StringBuffer stringBuffer = new StringBuffer();

        for (BookDto book : bookList) {
            stringBuffer.append("<div class=\"product col-12 col-md-6 col-lg-4\">\n" +
                    "               <div class=\"card my-card\">\n" +
                    "                   <a href=\"/home/show-book?bid=" + book.getId() + "\" title=\"" + book.getTitle() + "\">  " +
                    "                       <img class=\"\" src=\"" + book.getImage() + "\" alt=\"Book image\">\n" +
                    "                   </a>" +
                    "                   <div class=\"card-body\">\n" +
                    "                       <h4 class=\"card-title show_txt\">\n" +
                    "                           <a href=\"/home/show-book?bid=" + book.getId() + "\" title=\"View Product\">" + book.getTitle() + "</a>\n" +
                    "                       </h4>" +
                    "                       <p class=\"card-text show_txt\">" + book.getDescription() + "</p>\n" +
                    "                           <div class=\"row\">\n" +
                    "                               <div class=\"col\">\n" +
                    "                                   <a href=\"/DatBookShop_war_exploded/add_cart_controller?bid=" + book.getId() + "\" class=\"btn btn-danger btn-block\">" + book.getPrice() + " $</a>\n" +
                    "                               </div>\n" +
                    "                               <div class=\"col\">\n" +
                    "                                   <button onclick=\"addToCart(" + book.getId() + ")\" class=\"btn btn-success btn-block\">Add to cart</button>\n" +
                    "                               </div>\n" +
                    "                           </div>\n" +
                    "                   </div>\n" +
                    "               </div>\n" +
                    "            </div>");
        }
        return stringBuffer;
    }

}
