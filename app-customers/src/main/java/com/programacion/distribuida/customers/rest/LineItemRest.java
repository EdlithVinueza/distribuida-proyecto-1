package com.programacion.distribuida.customers.rest;

import com.programacion.distribuida.customers.db.LineItem;
import com.programacion.distribuida.customers.repo.LineItemRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/lineitems")
public class LineItemRest {

    private final LineItemRepository lineItemRepository;

    @Value("${server.port}")
    private Integer httpPort;

    public LineItemRest(LineItemRepository lineItemRepository) {
        this.lineItemRepository = lineItemRepository;
    }

    @GetMapping
    public List<LineItem> findAll() {
        return lineItemRepository.findAll();
    }

    @GetMapping("/{orderId}/{bookIsbn}")
    public ResponseEntity<LineItem> getByCompositeKey(
            @PathVariable("orderId") Long orderId,
            @PathVariable("bookIsbn") String bookIsbn
    ) {
        return lineItemRepository.findByOrderIdAndBookIsbn(orderId, bookIsbn)
                .map(it -> {
                    it.setBookIsbn(it.getBookIsbn() + " - Puerto: " + httpPort);
                    return it;
                })
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
