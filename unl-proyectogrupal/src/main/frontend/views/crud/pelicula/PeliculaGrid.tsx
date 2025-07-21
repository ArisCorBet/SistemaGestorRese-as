import { useState } from 'react';
import Pelicula from 'Frontend/generated/com/unl/proyectogrupal/base/models/Pelicula';
import EditarPelicula from './EditarPelicula';

type GeneroOption = { value: string; label: string };

type PeliculaCardProps = {
  pelicula: Pelicula;
  listaGenero: GeneroOption[];
  fetchPeliculas: () => void;
  isExpanded: boolean;
  onToggle: () => void;
};

function getYouTubeId(url: string): string | null {
  const regExp = /^.*(youtu.be\/|v\/|u\/\w\/|embed\/|watch\?v=|\&v=)([^#\&\?]*).*/;
  const match = url.match(regExp);
  return (match && match[2].length === 11) ? match[2] : null;
}

export default function PeliculaCard({
  pelicula,
  listaGenero,
  fetchPeliculas,
  isExpanded,
  onToggle,
}: PeliculaCardProps) {
  const videoId = getYouTubeId(pelicula.trailer || '');
  const genero = listaGenero.find(g => g.value === pelicula.idGenero?.toString())?.label || 'Sin género';

  const thumbnailUrl = videoId
    ? `https://img.youtube.com/vi/${videoId}/hqdefault.jpg`
    : 'https://via.placeholder.com/300x180.png?text=Sin+imagen';

  return (
    <div
      style={{
        border: '1px solid #ccc',
        borderRadius: '8px',
        width: '300px',
        padding: '1rem',
        boxShadow: '0 2px 4px rgba(0,0,0,0.1)',
        display: 'flex',
        flexDirection: 'column',
        gap: '0.5rem',
        cursor: 'default',
      }}
    >
      <a
        onClick={onToggle}
        style={{
          display: 'block',
          cursor: 'pointer',
          borderRadius: '6px',
          overflow: 'hidden',
        }}
      >
        <img
          src={thumbnailUrl}
          alt={`Portada de ${pelicula.titulo}`}
          style={{ width: '100%', height: 'auto', borderRadius: '6px' }}
        />
      </a>

      {isExpanded && (
        <>
          <h3 style={{ margin: 0 }}>{pelicula.titulo}</h3>
          <p><strong>Sinopsis:</strong> {pelicula.sinopsis}</p>
          <p><strong>Duración:</strong> {pelicula.duracion} min</p>
          <p><strong>Género:</strong> {genero}</p>
          <p><strong>Estreno:</strong> {pelicula.fechaEstreno}</p>
          {videoId ? (
            <iframe
              width="100%"
              height="180"
              src={`https://www.youtube.com/embed/${videoId}`}
              title="YouTube trailer"
              frameBorder="0"
              allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"
              allowFullScreen
              style={{ borderRadius: '6px' }}
            />
          ) : (
            <span>No trailer válido</span>
          )}
          <EditarPelicula
            arguments={pelicula}
            onUpdated={fetchPeliculas}
            listaGenero={listaGenero}
          />
        </>
      )}
    </div>
  );
}
