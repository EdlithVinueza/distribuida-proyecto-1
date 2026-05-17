package com.programacion.distribuida.books.rest;

import com.programacion.distribuida.books.db.Book;
import com.programacion.distribuida.books.db.Inventory;
import com.programacion.distribuida.books.repo.BookRepository;
import com.programacion.distribuida.books.clients.AuthorRestClient;
import com.programacion.distribuida.books.dtos.BookDto;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import jakarta.transaction.Transactional;

@Path("/books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequiredArgsConstructor
@Transactional
public class BookRest {

    final BookRepository bookRepository;

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
                        AuthorRestClient client = RestClientBuilder.newBuilder()
                                .baseUri(URI.create("http://127.0.0.1:8070"))
                                .build(AuthorRestClient.class);
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

    @GET
    public List<BookDto> findAll() {
        return bookRepository.listAll().stream()
                .map(it -> BookDto.builder()
                        .isbn(it.getIsbn())
                        .title(it.getTitle())
                        .price(it.getPrice())
                        .inventorySold(Optional.ofNullable(it.getInventory()).map(Inventory::getSold).orElse(null))
                        .inventorySupplied(Optional.ofNullable(it.getInventory()).map(Inventory::getSupplied).orElse(null))
                        .build())
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