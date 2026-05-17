-- V0__Create_schema.sql
-- Solo estructura de tablas y llaves foráneas

CREATE TABLE authors (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE books (
    isbn VARCHAR(255) PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    price NUMERIC(10, 2) NOT NULL
);

CREATE TABLE customers (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE inventory (
    book_isbn VARCHAR(255) PRIMARY KEY,
    sold INTEGER NOT NULL DEFAULT 0,
    supplied INTEGER NOT NULL DEFAULT 0,
    CONSTRAINT fk_inventory_book FOREIGN KEY (book_isbn) REFERENCES books (isbn) ON DELETE CASCADE
);

CREATE TABLE books_authors (
    book_isbn VARCHAR(255) NOT NULL,
    author_id BIGINT NOT NULL,
    PRIMARY KEY (book_isbn, author_id),
    CONSTRAINT fk_book_author_book FOREIGN KEY (book_isbn) REFERENCES books (isbn) ON DELETE CASCADE,
    CONSTRAINT fk_book_author_author FOREIGN KEY (author_id) REFERENCES authors (id) ON DELETE CASCADE
);
