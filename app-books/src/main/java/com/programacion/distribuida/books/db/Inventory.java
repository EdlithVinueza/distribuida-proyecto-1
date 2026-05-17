package com.programacion.distribuida.books.db;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "inventory")
@Getter
@Setter
@ToString
public class Inventory {

    @Id
    @Column(name = "book_isbn")
    private String bookIsbn;

    @OneToOne
    @MapsId
    @JoinColumn(name = "book_isbn")
    @ToString.Exclude
    private Book book;

    @Column(nullable = false)
    private Integer sold = 0;

    @Column(nullable = false)
    private Integer supplied = 0;

}