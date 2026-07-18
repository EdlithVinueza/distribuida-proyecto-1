package com.programacion.distribuida.customers.repo;

import com.programacion.distribuida.customers.db.LineItem;
import com.programacion.distribuida.customers.db.LineItemId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LineItemRepository extends JpaRepository<LineItem, LineItemId> {
    Optional<LineItem> findByOrderIdAndBookIsbn(Long orderId, String bookIsbn);
}
