package com.programacion.distribuida.books.health;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;
import jakarta.inject.Inject;
import com.programacion.distribuida.books.clients.AuthorRestClient;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Readiness
public class BookServiceReadinessCheck implements HealthCheck {

    @Inject
    @RestClient
    AuthorRestClient authorRestClient;

    @Override
    public HealthCheckResponse call() {
        try {
            authorRestClient.ping();
            return HealthCheckResponse.named("BookService-AuthorsAPI").up().build();
        } catch (Exception e) {
            return HealthCheckResponse.named("BookService-AuthorsAPI").down()
                    .withData("error", e.getClass().getSimpleName() + ": " + e.getMessage())
                    .build();
        }
    }
}
