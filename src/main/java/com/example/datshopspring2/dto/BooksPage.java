package com.example.datshopspring2.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BooksPage {

    private List<BookDto> books;

    private Integer totalPagesCount;
}
