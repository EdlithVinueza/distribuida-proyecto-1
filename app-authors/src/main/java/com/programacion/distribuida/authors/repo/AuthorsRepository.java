package com.programacion.distribuida.authors.repo;

import com.programacion.distribuida.authors.db.Author;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
@Transactional
public class AuthorsRepository implements PanacheRepositoryBase<Author, Long> {

    public List<Author> findByBook(String isbn) {
        // 'o.author' obtiene el objeto Author de la tabla intermedia
        // 'o.id.bookIsbn' es la ruta correcta en nuestra llave compuesta
        return this.find("select o.author from BookAuthor o where o.id.bookIsbn = ?1", isbn).list();
    }
}