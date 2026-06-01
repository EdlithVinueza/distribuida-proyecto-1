package com.programacion.distribuida.books.service;

import com.programacion.distribuida.books.clients.AuthorRestClient;
import com.programacion.distribuida.books.db.Book;
import com.programacion.distribuida.books.db.Inventory;
import com.programacion.distribuida.books.dtos.BookDto;
import com.programacion.distribuida.books.repo.BookRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.ProcessingException;
import jakarta.ws.rs.WebApplicationException;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
@Transactional
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRestClient authorRestClient;

    @Inject
    public BookService(BookRepository bookRepository, @RestClient AuthorRestClient authorRestClient) {
        this.bookRepository = bookRepository;
        this.authorRestClient = authorRestClient;
    }

    public Optional<BookDto> findByIsbn(String isbn) {
        return bookRepository.findByIdOptional(isbn).map(this::toDtoWithAuthorsSafe);
    }

    public List<BookDto> findAll() {
        return bookRepository.streamAll()
                .map(this::toDtoWithAuthors)
                .toList();
    }

    public void update(String isbn, Book book) {
        bookRepository.update(isbn, book);
    }

    public void delete(String isbn) {
        bookRepository.deleteById(isbn);
    }

    private BookDto toDtoWithAuthorsSafe(Book book) {
        BookDto dto = toDtoBase(book);
        try {
            dto.setAuthors(authorRestClient.findByBook(book.getIsbn()));
        } catch (WebApplicationException | ProcessingException ex) {
            dto.setAuthors(List.of());
        }
        return dto;
    }

    private BookDto toDtoWithAuthors(Book book) {
        BookDto dto = toDtoBase(book);
        dto.setAuthors(authorRestClient.findByBook(book.getIsbn()));
        return dto;
    }

    private BookDto toDtoBase(Book book) {
        return BookDto.builder()
                .isbn(book.getIsbn())
                .title(book.getTitle())
                .price(book.getPrice())
                .inventorySold(Optional.ofNullable(book.getInventory()).map(Inventory::getSold).orElse(null))
                .inventorySupplied(Optional.ofNullable(book.getInventory()).map(Inventory::getSupplied).orElse(null))
                .build();
    }
}
