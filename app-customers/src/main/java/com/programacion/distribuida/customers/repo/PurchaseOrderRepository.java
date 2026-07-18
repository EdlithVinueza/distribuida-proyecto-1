package com.programacion.distribuida.customers.repo;

import com.programacion.distribuida.customers.db.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long> {
}
