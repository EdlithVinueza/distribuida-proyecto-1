package com.programacion.distribuida.customers.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.persistence.EntityManager;

@Component
public class CustomerServiceHealthIndicator implements HealthIndicator {

    @Autowired
    private EntityManager entityManager;

    @Override
    public Health health() {
        try {
            entityManager.createNativeQuery("SELECT 1").getSingleResult();
            return Health.up()
                    .withDetail("service", "CustomerService")
                    .withDetail("status", "Database connection is healthy")
                    .build();
        } catch (Exception e) {
            return Health.down()
                    .withDetail("service", "CustomerService")
                    .withDetail("error", e.getMessage())
                    .build();
        }
    }
}
