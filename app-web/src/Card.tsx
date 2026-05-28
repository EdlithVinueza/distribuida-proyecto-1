
import React from 'react'

// ...existing code...

type Author = {
    id: number;
    name: string;
}

interface Props {
  autor: Author;
}

const AuthorCard: React.FC<Props> = ({ autor }) => {
  const id = autor.id;
  const name = autor.name;

  return (
    <div className="card_country">
        <p className="card_country_id">id: {id}</p>
        <p className="card_country_name">nombre: {name}</p>
    </div>
  )
}

export default AuthorCard