package com.example.datshopspring2.services.impl;

import com.example.datshopspring2.dto.BookDto;
import com.example.datshopspring2.dto.BooksPage;
import com.example.datshopspring2.exceptions.BookNotFoundException;
import com.example.datshopspring2.models.Book;
import com.example.datshopspring2.models.Categories;
import com.example.datshopspring2.models.User;
import com.example.datshopspring2.models.enums.State;
import com.example.datshopspring2.models.google.Volume;
import com.example.datshopspring2.models.google.Volumes;
import com.example.datshopspring2.repositories.BookRepository;
import com.example.datshopspring2.repositories.googleBooksAPI.CustomGoogleApi;
import com.example.datshopspring2.repositories.googleBooksAPI.GoogleAPIRepository;
import com.example.datshopspring2.services.BookService;
import com.example.datshopspring2.services.CategoriesService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.example.datshopspring2.dto.BookDto.from;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    private final CategoriesService categoriesService;

    private final GoogleAPIRepository googleAPIRepository;

    private final CustomGoogleApi customGoogleApi;

    private final long maxResult = 20;


    @Override
    public List<BookDto> findAll() {
        return from(bookRepository.findAll());
    }

    @Override
    public List<BookDto> findByLikeTitle(String search) {
        search = "%" + search + "%";
        return from(bookRepository.findAllByStateAndTitleIsLike(State.CONFIRMED, search));
    }

    @Override
    public List<BookDto> findAllByCategoriesId(Categories categories) {
        return from(bookRepository.findAllByStateAndCategoriesId(State.CONFIRMED, categories));
    }

    @Override
    public Book findBookByBookId(Long bookId, List<String> state) {
        return bookRepository.findBookByIdAndState(bookId, state).orElseThrow(
                () -> new BookNotFoundException("Book not found", 404)
        );
    }

    @Override
    public BookDto findBookDtoByBookId(Long bookId, List<String> state) {
        return from(bookRepository.findBookByIdAndState(bookId, state).orElseThrow(
                () -> new BookNotFoundException("Book not found", 404)
        ));
    }

    @Override
    public List<BookDto> findBooksBySeller(User user) {
        return from(bookRepository.findAllBySellerAndState(user, State.CONFIRMED));
    }

    @Override
    public List<BookDto> findTop12() {
        return from(bookRepository.findAllByStateLimit12(State.CONFIRMED));
    }

    @Override
    public List<BookDto> findNext3Book(Integer amount) {
        String state = State.CONFIRMED.toString();
        return from(bookRepository.findNext3Book(amount, state));
    }

    @Override
    public List<BookDto> findNext3ByCategoriesId(Integer amount, Long categoriesId) {
        String state = State.CONFIRMED.toString();
        return from(bookRepository.findNext3ByCategoriesId(amount, categoriesId, state));
    }

    @Override
    public void addBook(BookDto book, User user) {
        Book bookToSave = Book.builder()
                .title(book.getTitle())
                .image(book.getImage())
                .price(book.getPrice())
                .author(book.getAuthor())
                .quantitySold(0)
                .description(book.getDescription())
                .categoriesId(categoriesService.findByCategoriesId(book.getCategory()))
                .seller(user)
                .state(State.CONFIRMED)
                .build();
        bookRepository.save(bookToSave);
    }

    @Override
    public void deleteBookById(Long bid, List<String> states) {
        Book bookToDelete = bookRepository.findBookByIdAndState(bid, states).orElseThrow(
                () -> new BookNotFoundException("Book not found", 404)
        );

        bookToDelete.setState(State.DELETED);

        bookRepository.save(bookToDelete);
    }

    @Override
    public BookDto updateBook(BookDto book, Long bid, List<String> states) {
        Book bookToSave = bookRepository.findBookByIdAndState(bid, states).orElseThrow(
                () -> new BookNotFoundException("Book not found", 404)
        );

        bookToSave.setTitle(book.getTitle());
        bookToSave.setImage(book.getImage());
        bookToSave.setPrice(book.getPrice());
        bookToSave.setAuthor(book.getAuthor());
        bookToSave.setDescription(book.getDescription());
        bookToSave.setCategoriesId(categoriesService.findByCategoriesId(book.getCategory()));

        bookRepository.save(bookToSave);

        return from(bookToSave);

    }

    @Override
    public Book findFirstByOrderByQuantitySoldDesc() {
        return bookRepository.findFirstByStateOrderByQuantitySoldDesc(State.CONFIRMED).orElseThrow(
                () -> new BookNotFoundException("Book not found", 404)
        );
    }

    @Override
    public Book findFirstByOrderByBookIdDesc() {
        return bookRepository.findFirstByStateOrderByIdDesc(State.CONFIRMED).orElseThrow(
                () -> new BookNotFoundException("Book not found", 404)
        );
    }

    @Override
    public BooksPage findAll(int page, User user) {
        PageRequest pageRequest = PageRequest.of(page - 1, 5);

        Page<Book> books = bookRepository.findAllBySellerAndState(pageRequest, user, State.CONFIRMED);

        return BooksPage.builder()
                .books(from(books.getContent()))
                .totalPagesCount(books.getTotalPages())
                .build();
    }

    @Override
    public List<BookDto> findBooksFromGoogleAPIWithQuery(String query) throws Exception {
        Volumes volumes = customGoogleApi.searchBook(query);

        List<Volume> volumeList = volumes.getItems();

        List<BookDto> bookList = getListBookDtoFromListVolume(volumeList);

        return bookList;
    }

    @Override
    public List<BookDto> findBooksFromGoogleAPIWithQueryAndStartIndex(String query, Integer exits) throws Exception {
        Volumes volumes = customGoogleApi.searchBookWithStartIndex(query, exits);
        List<Volume> volumeList = volumes.getItems();

        List<BookDto> bookList = getListBookDtoFromListVolume(volumeList);

        return bookList;

    }

    @Override
    public BookDto findGoogleBooksById(String bid) throws Exception {
        Volume volume = customGoogleApi.searchBookById(bid);

        BookDto bookDto = getBookDtoFromVolume(volume);

        return bookDto;
    }

    @Override
    public BooksPage findAllBooks(int page) {
        PageRequest pageRequest = PageRequest.of(page, 5);

        Page<Book> pageBook = bookRepository.findAllByStateOrderById(pageRequest, State.CONFIRMED);

        return BooksPage.builder()
                .totalPagesCount(pageBook.getTotalPages())
                .books(from(pageBook.getContent()))
                .build();
    }

    @Override
    public BookDto addBook(BookDto bookDto) {
        Book book = Book.builder()
                .title(bookDto.getTitle())
                .price(bookDto.getPrice())
                .description(bookDto.getDescription())
                .author(bookDto.getAuthor())
                .image(bookDto.getImage())
                .quantitySold(bookDto.getQuantitySold())
                .state(State.NOT_CONFIRMED)
                .categoriesId(categoriesService.findByCategoriesId(bookDto.getCategory()))
                .build();

        bookRepository.save(book);

        return from(book);
    }

    @Override
    public void publishBookById(Long id, List<String> states) {
        Book bookToPublish = bookRepository.findById(id).orElseThrow(
                () -> new BookNotFoundException("Book not found", 404)
        );

        bookToPublish.setState(State.CONFIRMED);

        bookRepository.save(bookToPublish);
    }

    @Override
    public List<BookDto> findHighestPricedBooks(List<String> states) {
        return from(bookRepository.findAllByMaxPrice(states));
    }


    @Override
    public List<BookDto> findTheMostSoldBooks(List<String> states) {
        return from(bookRepository.findAllByMostSold(states));
    }

    @Override
    public List<BookDto> findBooksWhosePricesGreaterThanAverage(List<String> states) {
        return from(bookRepository.findAllWherePricesGreaterAverage(states));
    }

    private BookDto getBookDtoFromVolume(Volume volume) {
        String id = volume.getId();
        String title = volume.getVolumeInfo().getTitle();
        StringBuilder image = new StringBuilder();
        StringBuilder author = new StringBuilder();
        StringBuilder description = new StringBuilder();
        String webReaderLink = volume.getAccessInfo().getWebReaderLink();

        try {
            image.append(volume.getVolumeInfo().getImageLinks().getThumbnail());
            author.append(volume.getVolumeInfo().getAuthors().toString());
            description.append(volume.getVolumeInfo().getDescription());
        } catch (NullPointerException ignored) {
        }

        if (image.isEmpty()) {
            image.append("https://upload.wikimedia.org/wikipedia/commons/thumb/7/70/Solid_white.svg/2048px-Solid_white.svg.png");
        }

        if (description.isEmpty()) {
            description.append("Secret");
        }

        return BookDto.builder()
                .id(id)
                .title(title)
                .image(String.valueOf(image))
                .author(String.valueOf(author))
                .description(String.valueOf(description))
                .webReaderLink(webReaderLink)
                .build();
    }

    private List<BookDto> getListBookDtoFromListVolume(List<Volume> volumeList) {
        List<BookDto> bookList = new ArrayList<>();

        for (int i = 0; i < maxResult; i++) {
            bookList.add(getBookDtoFromVolume(volumeList.get(i)));
        }

        return bookList;
    }
}
