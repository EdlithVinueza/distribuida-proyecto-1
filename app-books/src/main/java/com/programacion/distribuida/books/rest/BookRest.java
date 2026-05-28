package com.programacion.distribuida.books.rest;

import com.programacion.distribuida.books.db.Book;
import com.programacion.distribuida.books.db.Inventory;
import com.programacion.distribuida.books.repo.BookRepository;
import com.programacion.distribuida.books.clients.AuthorRestClient;
import com.programacion.distribuida.books.dtos.BookDto;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Optional;
import jakarta.transaction.Transactional;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Path("/books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Transactional
@ApplicationScoped
public class BookRest {

    private final BookRepository bookRepository;
    private final AuthorRestClient client;

    @Inject
    public BookRest(BookRepository bookRepository, @RestClient AuthorRestClient client) {
        this.bookRepository = bookRepository;
        this.client = client;
    }

    @GET
    @Path("/{isbn}")
    public Response findByIsbn(@PathParam("isbn") String isbn) {
        return bookRepository.findByIdOptional(isbn)
                .map(book -> {
                    BookDto dto = BookDto.builder()
                            .isbn(book.getIsbn())
                            .title(book.getTitle())
                            .price(book.getPrice())
                            .inventorySold(Optional.ofNullable(book.getInventory()).map(Inventory::getSold).orElse(null))
                            .inventorySupplied(Optional.ofNullable(book.getInventory()).map(Inventory::getSupplied).orElse(null))
                            .build();

                    try {
                        dto.setAuthors(client.findByBook(isbn));
                    } catch (Exception e) {
                        dto.setAuthors(List.of());
                    }

                    return dto;
                })
                .map(Response::ok)
                .orElse(Response.status(Response.Status.NOT_FOUND))
                .build();
    }

    // Método actualizado
    @GET
    public List<BookDto> findAll() {
        return bookRepository.streamAll()
                .map(book -> {
                    // Consultar los autores en http://127.0.0.1:8070
                    var authors = client.findByBook(book.getIsbn());

                    return BookDto.builder()
                            .isbn(book.getIsbn())
                            .title(book.getTitle())
                            .price(book.getPrice())
                            .authors(authors)
                            .inventorySold(book.getInventory() != null ? book.getInventory().getSold() : null)
                            .inventorySupplied(book.getInventory() != null ? book.getInventory().getSupplied() : null)
                            .build();
                })
                .toList();
    }

    @PUT
    @Path("/{isbn}")
    public Response update(@PathParam("isbn") String isbn, Book book) {
        bookRepository.update(isbn, book);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{isbn}")
    public Response delete(@PathParam("isbn") String isbn) {
        bookRepository.deleteById(isbn);
        return Response.ok().build();
    }
}
