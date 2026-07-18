-- V1__Insert_sample_data.sql
-- Datos de prueba con relaciones complejas

-- 1. Insertar Autores
INSERT INTO authors (name) VALUES 
('Jorge Luis Borges'),      -- ID 1
('Gabriel García Márquez'), -- ID 2
('Isabel Allende'),         -- ID 3
('Julio Cortázar'),         -- ID 4
('Mario Vargas Llosa');     -- ID 5

-- 2. Insertar Libros
INSERT INTO books (isbn, title, price) VALUES 
('ISBN-001', 'Ficciones', 25.00),
('ISBN-002', 'Cien años de soledad', 30.00),
('ISBN-003', 'Rayuela', 28.50),
('ISBN-004', 'La ciudad y los perros', 22.00),
('ISBN-005', 'Antología Latinoamericana (Colaborativo)', 45.00), -- Libro de varios autores
('ISBN-006', 'El Aleph', 24.00);

-- 3. Crear el inventario inicial
INSERT INTO inventory (book_isbn, sold, supplied) VALUES 
('ISBN-001', 5, 20),
('ISBN-002', 10, 50),
('ISBN-003', 0, 15),
('ISBN-004', 2, 10),
('ISBN-005', 1, 5),
('ISBN-006', 8, 25);

-- 4. Relaciones Muchos-a-Muchos (LA MAGIA AQUÍ)
INSERT INTO books_authors (book_isbn, author_id) VALUES 
('ISBN-001', 1), -- Borges escribió Ficciones
('ISBN-006', 1), -- Borges TAMBIÉN escribió El Aleph (Un autor -> muchos libros)
('ISBN-002', 2), -- Gabo escribió Cien años
('ISBN-003', 4), -- Cortázar escribió Rayuela
('ISBN-004', 5), -- Vargas Llosa escribió La ciudad...

-- EJEMPLO: UN LIBRO CON MUCHOS AUTORES
('ISBN-005', 1), -- El libro colaborativo tiene a Borges
('ISBN-005', 2), -- El libro colaborativo también tiene a Gabo
('ISBN-005', 4), -- El libro colaborativo también tiene a Cortázar
('ISBN-005', 5); -- El libro colaborativo también tiene a Vargas Llosa

-- 5. Clientes
INSERT INTO customers (name, email) VALUES 
('Cliente Uno', 'cliente1@example.com'),
('Cliente Dos', 'cliente2@example.com');

-- 6. Customer (singular)
INSERT INTO customer (email, name, version) VALUES ('juan@example.com', 'Juan Pérez', 0);
INSERT INTO customer (email, name, version) VALUES ('maria@example.com', 'María García', 0);
INSERT INTO customer (email, name, version) VALUES ('carlos@example.com', 'Carlos López', 0);

-- 7. Purchase Order
INSERT INTO purchaseorder (customer_id, placedon, deliveredon, status, total, version) 
VALUES (1, '2024-01-15 10:00:00', '2024-01-20 14:00:00', 1, 35, 0);

INSERT INTO purchaseorder (customer_id, placedon, deliveredon, status, total, version) 
VALUES (2, '2024-02-03 15:30:00', NULL, 0, 18, 0);

INSERT INTO purchaseorder (customer_id, placedon, deliveredon, status, total, version) 
VALUES (3, '2024-02-10 09:45:00', '2024-02-15 11:20:00', 1, 50, 0);

-- 8. Line Items (mapped to ISBN-001, ISBN-002, ISBN-003)
INSERT INTO lineitem (order_id, book_isbn, quantity, idx) VALUES (1, 'ISBN-001', 2, 0);
INSERT INTO lineitem (order_id, book_isbn, quantity, idx) VALUES (2, 'ISBN-002', 1, 0);
INSERT INTO lineitem (order_id, book_isbn, quantity, idx) VALUES (3, 'ISBN-003', 3, 0);
INSERT INTO lineitem (order_id, book_isbn, quantity, idx) VALUES (3, 'ISBN-002', 1, 1);

