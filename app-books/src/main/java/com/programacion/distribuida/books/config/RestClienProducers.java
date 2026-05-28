package com.programacion.distribuida.books.config;

import com.programacion.distribuida.books.clients.AuthorRestClient;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.RestClientBuilder;

@ApplicationScoped
public class RestClienProducers {

    @Inject
    @ConfigProperty(name = "authors.url")
    String url;

    @Produces
    @ApplicationScoped
    public AuthorRestClient authorRestClient() {
        return RestClientBuilder.newBuilder()
                .baseUri("http://127.0.0.1:8070")
                .build(AuthorRestClient.class);
    }
}

//Esta es la forma primitiva de hacer la llamda a cliente


