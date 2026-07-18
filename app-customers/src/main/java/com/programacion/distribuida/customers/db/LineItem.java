package com.programacion.distribuida.customers.db;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "lineitem")
@Getter
@Setter
@IdClass(LineItemId.class)
public class LineItem {
    @Id
    @Column(name = "order_id")
    private Long orderId;
    
    @Id
    @Column(name = "book_isbn")
    private String bookIsbn;
    
    private Integer quantity;
    private Integer idx;
}
