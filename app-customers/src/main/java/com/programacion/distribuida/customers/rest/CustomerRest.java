package com.programacion.distribuida.customers.rest;

import com.programacion.distribuida.customers.db.Customer;
import com.programacion.distribuida.customers.repo.CustomerRepository;
import io.micrometer.tracing.annotation.NewSpan;
import io.micrometer.tracing.annotation.SpanTag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerRest {

    private final CustomerRepository customerRepository;

    @Value("${server.port}")
    private Integer httpPort;

    public CustomerRest(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @GetMapping
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @GetMapping("/{id}")
    @NewSpan("customer-lookup")
    public ResponseEntity<Customer> getById(@SpanTag("customer.id") @PathVariable("id") Long id) {
        return customerRepository.findById(id)
                .map(it -> {
                    it.setName(it.getName() + " - Puerto: " + httpPort);
                    return it;
                })
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
