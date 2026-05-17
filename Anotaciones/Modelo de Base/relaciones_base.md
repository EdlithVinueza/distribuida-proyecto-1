1. La Relación entre Libros y Autores (modulos distintos)
Primero, confirmemos la relación: Es una relación Muchos-a-Muchos (Many-to-Many).

Un Libro puede tener varios autores (ej. un libro de texto escrito por varios profesores).
Un Autor puede escribir muchos libros.
Para resolver esto en SQL se usa siempre una tabla intermedia (junction table) llamada books_authors.

2. ¿Cómo manejarlo en Microservicios sin redundancia?
La clave para no tener que copiar la clase Book dentro del módulo de Author es referenciar por Identidad (ID) en lugar de referenciar por Objeto.
Estrategia: 
el uso de una Entidad de Unión (BookAuthor).

BookAuthorId: Guarda el isbn (como String) y el author_id (como Long).
BookAuthor: Esta entidad mapea la relación. Se conecta con el objeto Author (porque está en el mismo módulo), pero el Libro lo trata simplemente como un String isbn.
De esta forma:

app-authors no sabe qué es un Libro (no tiene sus campos title o price), solo sabe que existe un código isbn asociado a un autor.
No hay redundancia de código porque no copias la entidad Book.


 Si tu docente decidió dejar BookAuthor únicamente en el módulo de app-authors, eso simplifica mucho las cosas y es una decisión de diseño muy clara: el microservicio de autores es el dueño de la relación.

Esto significa que app-books es "ciego" respecto a quién escribió el libro a nivel de base de datos, y siempre debe preguntarle a app-authors por esa información.