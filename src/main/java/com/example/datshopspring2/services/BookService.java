package com.example.datshopspring2.services;

import com.example.datshopspring2.dto.BookDto;
import com.example.datshopspring2.dto.BooksPage;
import com.example.datshopspring2.models.User;
import com.example.datshopspring2.models.Book;
import com.example.datshopspring2.models.Categories;

import java.util.List;

public interface BookService {
    List<BookDto> findAll();

    List<BookDto> findByLikeTitle(String search);

    List<BookDto> findAllByCategoriesId(Categories categories);

    Book findBookByBookId(Long bookId, List<String> state);

    BookDto findBookDtoByBookId(Long bookId, List<String> state);

    List<BookDto> findBooksBySeller(User user);

    List<BookDto> findTop12();

    List<BookDto> findNext3Book(Integer amount);

    List<BookDto> findNext3ByCategoriesId(Integer amount, Long categoriesId);

    void addBook(BookDto book, User user);

    void deleteBookById(Long bid, List<String> states);

    BookDto updateBook(BookDto book, Long bid, List<String> states);

    Book findFirstByOrderByQuantitySoldDesc();

    Book findFirstByOrderByBookIdDesc();

    BooksPage findAll(int page, User user);

    List<BookDto> findBooksFromGoogleAPIWithQuery(String query) throws Exception;

    List<BookDto> findBooksFromGoogleAPIWithQueryAndStartIndex(String query, Integer exits) throws Exception;

    BookDto findGoogleBooksById(String bid) throws Exception;

    BooksPage findAllBooks(int page);

    BookDto addBook(BookDto bookDto);

    void publishBookById(Long id, List<String> states);

    List<BookDto> findHighestPricedBooks(List<String> states);

    List<BookDto> findTheMostSoldBooks(List<String> states);

    List<BookDto> findBooksWhosePricesGreaterThanAverage(List<String> states);
}
