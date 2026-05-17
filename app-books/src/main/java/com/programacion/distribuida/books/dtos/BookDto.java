package com.programacion.distribuida.books.dtos;

import lombok.*;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {
    private String isbn;
    private String title;
    private BigDecimal price;
    private Integer inventorySold;
    private Integer inventorySupplied;
    private List<AuthorDto> authors;
}
