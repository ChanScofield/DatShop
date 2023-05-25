package com.example.datshopspring2.controllers;

import com.example.datshopspring2.controllers.api.BookAPI;
import com.example.datshopspring2.dto.BookDto;
import com.example.datshopspring2.dto.BooksPage;
import com.example.datshopspring2.services.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookAPIRestController implements BookAPI {

    private final BookService bookService;

    @Override
    @GetMapping
    public ResponseEntity<BooksPage> getAllBooks(int page) {
        return ResponseEntity.ok(bookService.findAllBooks(page));
    }

    @Override
    @PostMapping
    public ResponseEntity<BookDto> addBook(@RequestBody @Valid BookDto bookDto) {
        return ResponseEntity.
                status(HttpStatus.CREATED)
                .body(bookService.addBook(bookDto));
    }

    @Override
    @GetMapping("/{book-id}")
    public ResponseEntity<BookDto> getBook(@PathVariable("book-id") Long id) {
        List<String> states = new ArrayList<>();
        states.add("CONFIRMED");
        states.add("NOT_CONFIRMED");
        return ResponseEntity.ok(bookService.findBookDtoByBookId(id, states));
    }

    @Override
    @PutMapping("/{book-id}")
    public ResponseEntity<BookDto> updateBook(@PathVariable("book-id") Long id,
                                              @Valid @RequestBody BookDto bookDto) {
        List<String> states = new ArrayList<>();
        states.add("CONFIRMED");
        states.add("NOT_CONFIRMED");
        return ResponseEntity.accepted().body(bookService.updateBook(bookDto, id, states));
    }

    @Override
    @DeleteMapping("/{book-id}")
    public ResponseEntity<BookDto> deleteBook(@PathVariable("book-id") Long id) {
        List<String> states = new ArrayList<>();
        states.add("CONFIRMED");
        states.add("NOT_CONFIRMED");
        bookService.deleteBookById(id, states);
        return ResponseEntity.accepted().build();
    }

    @Override
    @PutMapping("/{book-id}/publish")
    public ResponseEntity<BookDto> publishBook(@PathVariable("book-id") Long id) {
        List<String> states = new ArrayList<>();
        states.add("CONFIRMED");
        states.add("NOT_CONFIRMED");
        bookService.publishBookById(id, states);
        return ResponseEntity.accepted().build();
    }

    @Override
    @GetMapping("/most-expensive-book")
    public ResponseEntity<List<BookDto>> getHighestPricedBooks() {
        List<String> states = new ArrayList<>();
        states.add("CONFIRMED");
        states.add("NOT_CONFIRMED");
        return ResponseEntity.ok(bookService.findHighestPricedBooks(states));
    }

    @Override
    @GetMapping("/most-sold-book")
    public ResponseEntity<List<BookDto>> getTheMostSoldBooks() {
        List<String> states = new ArrayList<>();
        states.add("CONFIRMED");
        states.add("NOT_CONFIRMED");
        return ResponseEntity.ok(bookService.findTheMostSoldBooks(states));
    }

    @Override
    @GetMapping("/books-prices-greater-average")
    public ResponseEntity<List<BookDto>> getBooksWhosePricesGreaterThanAverage() {
        List<String> states = new ArrayList<>();
        states.add("CONFIRMED");
        states.add("NOT_CONFIRMED");
        return ResponseEntity.ok(bookService.findBooksWhosePricesGreaterThanAverage(states));
    }
}
