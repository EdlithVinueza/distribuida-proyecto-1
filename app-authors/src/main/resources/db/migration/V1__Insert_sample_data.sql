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
