package com.example.datshopspring2.dto;

import com.example.datshopspring2.models.Book;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {

    private String id;

    @NotBlank(message = "Please enter the title")
    private String title;

    @NotBlank(message = "We need pictures")
    private String image;

    @NotNull(message = "Please enter the selling price")
    private Integer price;

    @NotBlank(message = "Who is the author?")
    private String author;

    @NotBlank(message = "Please describe it")
    private String description;

    @NotNull
    private Long category;

    private Integer quantitySold;

    private String webReaderLink;

    public static List<BookDto> from(List<Book> books) {
        return books.stream()
                .map(BookDto::from)
                .collect(Collectors.toList());
    }

    public static BookDto from(Book book) {
        return BookDto.builder()
                .id(String.valueOf(book.getId()))
                .title(book.getTitle())
                .image(book.getImage())
                .price(book.getPrice())
                .author(book.getAuthor())
                .description(book.getDescription())
                .category(book.getCategoriesId().getCategoriesId())
                .quantitySold(book.getQuantitySold())
                .build();
    }
}
