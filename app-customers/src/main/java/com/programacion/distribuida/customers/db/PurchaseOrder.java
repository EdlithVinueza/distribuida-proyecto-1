package com.programacion.distribuida.customers.db;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "purchaseorder")
@Getter
@Setter
public class PurchaseOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "customer_id")
    private Long customerId;
    
    @Column(name = "placedon")
    private LocalDateTime placedOn;
    
    @Column(name = "deliveredon")
    private LocalDateTime deliveredOn;
    
    private Integer status;
    private Integer total;
    private Integer version;
}
