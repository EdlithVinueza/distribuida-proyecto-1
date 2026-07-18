package com.programacion.distribuida.customers.rest;

import com.programacion.distribuida.customers.db.PurchaseOrder;
import com.programacion.distribuida.customers.repo.PurchaseOrderRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/purchaseorders")
public class PurchaseOrderRest {

    private final PurchaseOrderRepository purchaseOrderRepository;

    @Value("${server.port}")
    private Integer httpPort;

    public PurchaseOrderRest(PurchaseOrderRepository purchaseOrderRepository) {
        this.purchaseOrderRepository = purchaseOrderRepository;
    }

    @GetMapping
    public List<PurchaseOrder> findAll() {
        return purchaseOrderRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PurchaseOrder> getById(@PathVariable("id") Long id) {
        return purchaseOrderRepository.findById(id)
                .map(it -> {
                    it.setTotal(it.getTotal() + httpPort);
                    return it;
                })
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
