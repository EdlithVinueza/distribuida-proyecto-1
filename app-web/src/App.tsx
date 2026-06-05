import './App.css'
import axios from "axios";
import {useState} from "react";

interface Author {
  id: number;
  name: string;
}

interface Book {
    isbn: string;
    price: number;
    title: string;
  authors: Array<Author>;
}

function App() {

  const [authors, setAuthors] = useState<Author[]>([]);
  const [books, setBooks] = useState<Book[]>([]);

  const handleClicklistarAuthores = async (): Promise<void> => {
    try {
      const response = await axios.get<Author[]>('/app-authors/authors');
      setAuthors(response.data);
    } catch (error) {
      // show a concise message in case of network/backend error
      alert(String(error));
    }
  };

  const handleClickListarBooks = async (): Promise<void> => {
    try {
      const response = await axios.get<Book[]>('/app-books/books');
      setBooks(response.data);
    } catch (error) {
      alert(String(error));
    }
  };


  return (
    <>
      <section id="center">
        <div>
            <h1>Autores</h1>

        </div>

        <button onClick={handleClicklistarAuthores}> Consultar </button>

          <br />
          {
              authors.map(author =>
                  <p key={author.id}>{author.id} - {author.name}</p>

              )
          }



      </section>

        <section id="center">
            <div>
                <h1>Books</h1>

            </div>

            <button onClick={handleClickListarBooks}> Consultar </button>

            <br />
            {
                books.map((book: Book) => (
                        <ul key={book.isbn}>
                            <li>{book.isbn} - {book.title}</li>
                            {/* Sublista para los autores */}
                            <ul>
                                {book.authors.map((author: Author) => (
                                        <li key={author.name}>{author.name}</li>
                                ))}
                            </ul>
                        </ul>
                ))
            }
        </section>



        <br/>

    </>
  )
}

export default App
