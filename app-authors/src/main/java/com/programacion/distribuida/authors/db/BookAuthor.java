package com.programacion.distribuida.authors.db;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "books_authors")
@Getter
@Setter
@ToString
public class BookAuthor {

    @EmbeddedId
    private BookAuthorId id;

    @ManyToOne
    @MapsId("authorId") // Mapea la propiedad correspondiente dentro de BookAuthorId
    @JoinColumn(name = "author_id")
    @ToString.Exclude
    private Author author;
}

