package com.programacion.distribuida.customers.repo;

import com.programacion.distribuida.customers.db.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
