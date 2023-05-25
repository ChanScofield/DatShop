package com.example.datshopspring2.controllers;

import com.example.datshopspring2.dto.BookDto;
import com.example.datshopspring2.services.BookService;
import com.google.api.services.books.model.Volume;
import com.google.api.services.books.model.Volumes;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
@RequiredArgsConstructor
@SessionAttributes({"logged", "countProduct", "admin"})
@RequestMapping("/google_api")
public class GoogleBookController {

    private final BookService bookService;
    private final LeftShow leftShow;

    @RequestMapping
    public String getGoogleApiPage(Model model,
                                   HttpServletRequest request) {

        model.addAttribute("listBook", new ArrayList<>());

        getBookInCookie(model, request);

        return "/views/google_book_page";
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


    @PostMapping("/live-search")
    @ResponseBody
    @Cacheable("live-search")
    public String googleSearch(@RequestParam String query, Model model) throws Exception {
        List<BookDto> bookDtos = bookService.findBooksFromGoogleAPIWithQuery(query);

        StringBuffer stringBuffer = getStringBufferForGGSearch(bookDtos);
        model.addAttribute("listBook", bookDtos);

        return stringBuffer.toString();
    }

    @PostMapping("/load-more-google-api")
    @ResponseBody
    @Cacheable("load-more-google-api")
    public String loadMoreBookGoogle(@RequestParam Integer exits,
                                      @RequestParam String query) throws Exception {
        List<BookDto> bookList = bookService.findBooksFromGoogleAPIWithQueryAndStartIndex(query, exits);

        StringBuffer stringBuffer = getStringBufferForGGSearch(bookList);

        return stringBuffer.toString();
    }

    @GetMapping("/show-book")
    @Cacheable("show-book")
    public String showBookPage(String bid, Model model, HttpServletRequest request) throws Exception {
        leftShow.show(model);

        BookDto book = bookService.findGoogleBooksById(bid);

        model.addAttribute("book", book);
        getBookInCookie(model, request);

        return "/views/detail";
    }

    private static StringBuffer getStringBufferForGGSearch(List<BookDto> bookList) {
        StringBuffer stringBuffer = new StringBuffer();

        for (BookDto book : bookList) {
            stringBuffer.append("<div class=\"product col-12 col-md-6 col-lg-3\">\n" +
                    "               <div class=\"card my-card\">\n" +
                    "                   <a href=\"/google_api/show-book?bid=" + book.getId() + "\" target=\"_blank\" title=\"" + book.getTitle() + "\">  " +
                    "                       <img class=\"\" src=\"" + book.getImage() + "\" alt=\"Book image\">\n" +
                    "                   </a>" +
                    "                   <div class=\"card-body\">\n" +
                    "                       <h4 class=\"card-title show_txt\">\n" +
                    "                           <a href=\"/google_api/show-book?bid=" + book.getId() + "\" target=\"_blank\" title=\"" + book.getTitle() + "\">" + book.getTitle() + "</a>\n" +
                    "                       </h4>\n" +
                    "                       <p class=\"card-text show_txt\">" + book.getDescription() + "</p>\n" +
                    "                       <div class=\"row\">\n" +
                    "                           <div class=\"col\">\n" +
                    "                               <a href=\"" + book.getWebReaderLink() + "\" target=\"_blank\" class=\"btn btn-primary btn-block\"> Reading Online </a>\n" +
                    "                           </div>\n" +
                    "                       </div>\n" +
                    "                   </div>\n" +
                    "               </div>\n" +
                    "            </div>");
        }
        return stringBuffer;
    }
}
