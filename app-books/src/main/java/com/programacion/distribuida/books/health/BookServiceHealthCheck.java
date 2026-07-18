package com.programacion.distribuida.books.health;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

@Readiness
public class BookServiceHealthCheck implements HealthCheck {

    @Inject
    EntityManager entityManager;

    @Override
    public HealthCheckResponse call() {
        try {
            entityManager.createNativeQuery("SELECT 1").getSingleResult();
            return HealthCheckResponse.named("BookService-DB").up().build();
        } catch (Exception e) {
            return HealthCheckResponse.named("BookService-DB").down()
                    .withData("error", e.getMessage())
                    .build();
        }
    }
}
