package com.programacion.distribuida.authors.db;

import jakarta.persistence.*;
import jakarta.json.bind.annotation.JsonbTransient;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Entity
@Table(name = "authors")
@Getter
@Setter
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    /**
     * RELACIÓN: Un autor puede estar en muchos registros de la tabla intermedia.
     * Usamos mappedBy porque el "dueño" del mapeo físico es la clase BookAuthor.
     */
    @OneToMany(mappedBy = "author")
    @JsonbTransient
    private List<BookAuthor> bookAuthors;
}
