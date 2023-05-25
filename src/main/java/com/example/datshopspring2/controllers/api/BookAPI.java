package com.example.datshopspring2.controllers.api;

import com.example.datshopspring2.dto.BookDto;
import com.example.datshopspring2.dto.BooksPage;
import com.example.datshopspring2.dto.ExceptionDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

@Tags(value = @Tag(name = "Books"))
public interface BookAPI {

    @Operation(summary = "Get All Books")
    @ApiResponses(value = {
            @ApiResponse(
                    description = "Page with book",
                    responseCode = "200",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = BooksPage.class)
                            )
                    }

            ),
            @ApiResponse(
                    description = "Page index must not be less than zero",
                    responseCode = "400",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionDto.class)
                            )
                    }
            )
    })
    ResponseEntity<BooksPage> getAllBooks(int page);

    @Operation(summary = "Add New Book")
    @ApiResponses(value = {
            @ApiResponse(
                    description = "Book added",
                    responseCode = "201",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = BookDto.class)
                            )
                    }
            ),
            @ApiResponse(
                    description = "Error",
                    responseCode = "404",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionDto.class)
                            )
                    }
            ),
            @ApiResponse(
                    description = "Book information is not clear",
                    responseCode = "400",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionDto.class)
                            )
                    }
            )
    })
    ResponseEntity<BookDto> addBook(BookDto bookDto);

    @Operation(summary = "Get Book By Id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Information about book",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = BookDto.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Book not found",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionDto.class)
                            )
                    }
            )
    })
    ResponseEntity<BookDto> getBook(Long id);

    @Operation(summary = "Update Book By Id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "202",
                    description = "Book updated",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = BookDto.class)
                            )
                    }
            ),
            @ApiResponse(
                        responseCode = "400",
                    description = "Book information is not clear",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionDto.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Book or category not found",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionDto.class)
                            )
                    }
            )
    })
    ResponseEntity<BookDto> updateBook(Long id,
                                       BookDto bookDto);

    @Operation(summary = "Delete Book By Id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "202",
                    description = "Book deleted",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = BookDto.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Book not found",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionDto.class)
                            )
                    }
            )
    })
    ResponseEntity<BookDto> deleteBook(Long id);

    @Operation(summary = "Publish Book By Id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "202",
                    description = "Book published",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = BookDto.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Book not found",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionDto.class)
                            )
                    }
            )
    })
    ResponseEntity<BookDto> publishBook(Long id);

    @Operation(summary = "Find the highest priced books")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "The highest priced books",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = BookDto.class)
                            )
                    }
            )
    })
    ResponseEntity<List<BookDto>> getHighestPricedBooks();

    @Operation(summary = "Find the books with the most sales")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "The books with the most sales",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = BookDto.class)
                            )
                    }
            )
    })
    ResponseEntity<List<BookDto>> getTheMostSoldBooks();

    @Operation(summary = "Find the highest priced books")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "The highest priced books",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = BookDto.class)
                            )
                    }
            )
    })
    ResponseEntity<List<BookDto>> getBooksWhosePricesGreaterThanAverage();


}
