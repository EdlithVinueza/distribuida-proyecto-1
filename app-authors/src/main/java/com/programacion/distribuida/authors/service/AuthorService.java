package com.programacion.distribuida.authors.service;

import com.programacion.distribuida.authors.db.Author;
import com.programacion.distribuida.authors.dtos.AuthorDto;
import com.programacion.distribuida.authors.repo.AuthorsRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class AuthorService {

    @Inject
    AuthorsRepository authorsRepository;

    @Inject
    @ConfigProperty(name = "quarkus.http.port", defaultValue = "8080")
    Integer port;

    public List<Author> findAll() {
        return authorsRepository.listAll();
    }

    public Optional<Author> findById(Long id) {
        return authorsRepository.findByIdOptional(id).map(this::withPort);
    }

    public List<AuthorDto> findByBook(String isbn) {
        return authorsRepository.findByBook(isbn)
                .stream()
                .map(this::withPort)
                .map(it -> AuthorDto.builder()
                        .id(it.getId())
                        .name(it.getName())
                        .build())
                .toList();
    }

    private Author withPort(Author author) {
        Author copy = new Author();
        copy.setId(author.getId());
        copy.setName(author.getName() + " - " + port);
        return copy;
    }
}
