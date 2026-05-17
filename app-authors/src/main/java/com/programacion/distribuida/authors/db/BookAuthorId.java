package com.programacion.distribuida.authors.db;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class BookAuthorId {

    @Column(name = "book_isbn")
    private String bookIsbn; // clave primaria de books (ISBN)

    @Column(name = "author_id")
    private Long authorId; // clave primaria de authors (ID)

}