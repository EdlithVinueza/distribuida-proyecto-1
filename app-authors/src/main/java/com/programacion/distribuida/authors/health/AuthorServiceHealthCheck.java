package com.programacion.distribuida.authors.health;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

@Readiness
public class AuthorServiceHealthCheck implements HealthCheck {

    @Inject
    EntityManager entityManager;

    @Override
    public HealthCheckResponse call() {
        try {
            entityManager.createNativeQuery("SELECT 1").getSingleResult();
            return HealthCheckResponse.named("AuthorService-DB").up().build();
        } catch (Exception e) {
            return HealthCheckResponse.named("AuthorService-DB").down()
                    .withData("error", e.getMessage())
                    .build();
        }
    }
}
