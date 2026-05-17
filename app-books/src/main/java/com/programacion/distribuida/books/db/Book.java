package com.programacion.distribuida.books.db;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "books")
@Getter
@Setter
public class Book {

    @Id
    private String isbn;
    private String title;
    private BigDecimal price;

    // AQUÍ NO HAY BookAuthor ni Author.
    // Este módulo no sabe que existe la tabla books_authors.

    @OneToOne(mappedBy = "book", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Inventory inventory;
}