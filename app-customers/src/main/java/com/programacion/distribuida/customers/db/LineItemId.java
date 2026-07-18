package com.programacion.distribuida.customers.db;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LineItemId implements Serializable {
    private Long orderId;
    private String bookIsbn;
}

