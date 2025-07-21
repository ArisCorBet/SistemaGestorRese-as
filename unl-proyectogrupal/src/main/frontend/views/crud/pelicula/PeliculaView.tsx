import { ViewConfig } from '@vaadin/hilla-file-router/types.js';
import { Button, HorizontalLayout, Icon, Select, TextField } from '@vaadin/react-components';
import { Notification } from '@vaadin/react-components/Notification';
import handleError from 'Frontend/views/_ErrorHandler';
import { Group, ViewToolbar } from 'Frontend/components/ViewToolbar';
import Pelicula from 'Frontend/generated/com/unl/proyectogrupal/base/models/Pelicula';
import { PeliculaService } from 'Frontend/generated/endpoints';
import { useEffect, useState } from 'react';
import CrearPelicula from './crearPelicula';
import PeliculaCard from './PeliculaGrid';

export const config: ViewConfig = {
  title: 'Películas',
  menu: {
    icon: 'vaadin:film',
    order: 1,
    title: 'Películas',
  },
};

type GeneroOption = { value: string; label: string };

export default function PeliculaView() {
  const [items, setItems] = useState<Pelicula[]>([]);
  const [listaGenero, setListaGenero] = useState<GeneroOption[]>([]);
  const [expandedId, setExpandedId] = useState<number | null>(null);

  const fetchPeliculas = () => {
    PeliculaService.listPelicula()
      .then((data) => setItems(data as any))
      .catch(handleError);
  };

  useEffect(() => {
    fetchPeliculas();
    PeliculaService.listaGeneroCombo()
      .then((data) => setListaGenero(data as any))
      .catch(handleError);
  }, []);

  const [criterio, setCriterio] = useState('');
  const [texto, setTexto] = useState('');
  const itemSelect = [{ label: 'titulo', value: 'titulo' }];

  const search = () => {
    PeliculaService.search(criterio, texto, 0)
      .then((data) => setItems(data as any))
      .catch(handleError);
  };

  return (
    <main className="w-full h-full flex flex-col box-border gap-s p-m">
      <ViewToolbar title="Creación de Películas">
        <Group>
          <CrearPelicula onCreated={fetchPeliculas} listaGenero={listaGenero} />
        </Group>
      </ViewToolbar>

      <HorizontalLayout theme="spacing">
        <Select
          items={itemSelect}
          value={criterio}
          onValueChanged={(e) => setCriterio(e.detail.value)}
          placeholder="Seleccione un criterio"
        />
        <TextField
          placeholder="Buscar"
          style={{ width: '50%' }}
          value={texto}
          onValueChanged={(e) => setTexto(e.detail.value)}
        >
          <Icon slot="prefix" icon="vaadin:search" />
        </TextField>
        <Button onClick={search} theme="primary">
          Buscar
        </Button>
      </HorizontalLayout>

      <div
        style={{
          display: 'flex',
          flexWrap: 'wrap',
          gap: '1.5rem',
          marginTop: '1rem',
        }}
      >
        {items.map((pelicula) => (
          <PeliculaCard
            key={pelicula.id}
            pelicula={pelicula}
            listaGenero={listaGenero}
            fetchPeliculas={fetchPeliculas}
            isExpanded={expandedId === pelicula.id}
            onToggle={() => setExpandedId(expandedId === pelicula.id ? null : pelicula.id)}
          />
        ))}
      </div>
    </main>
  );
}
