package com.programacion.distribuida.authors.rest;

import com.programacion.distribuida.authors.db.Author;
import com.programacion.distribuida.authors.dtos.AuthorDto;
import com.programacion.distribuida.authors.repo.AuthorsRepository; // Import del repositorio
import com.programacion.distribuida.authors.service.AuthorService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Path("/authors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthorRest {

    private final AtomicInteger counter = new AtomicInteger(0);

    @ConfigProperty(name = "quarkus.http.port", defaultValue = "8080")
    String httpPort;

    @Inject
    AuthorService authorService;

    // 1. Inyectamos tu repositorio con el nombre exacto de la clase
    @Inject
    AuthorsRepository authorsRepository;

    @GET
    public List<Author> getAll() {
        return authorService.findAll();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        return authorService.findById(id)
                .map(Response::ok)
                .orElse(Response.status(Response.Status.NOT_FOUND))
                .build();
    }

    @GET
    @Path("/find/{isbn}")
    public List<AuthorDto> findByBook(@PathParam("isbn") String isbn) {
        int valor = counter.getAndIncrement();

        if (valor != 5) {
            String msg = String.format("Intento %d -- generando error", valor);
            System.out.println(msg);
            throw new RuntimeException(msg);
        }

        // 2. Llamamos a authorsRepository. Devuelve List<Author>, por lo que el Stream
        // procesa entidades Author, permitiendo usar withPort y mapear al DTO sin romper tipos.
        return authorsRepository.findByBook(isbn)
                .stream()
                .map(this::withPort)
                .map(it -> AuthorDto.builder()
                        .id(it.getId())
                        .name(it.getName())
                        .build())
                .toList();
    }

    // 3. Método auxiliar que recibe la entidad Author y la modifica
    private Author withPort(Author it) {
        it.setName(it.getName() + " - " + httpPort);
        return it;
    }
}
