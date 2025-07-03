import { ViewConfig } from '@vaadin/hilla-file-router/types.js';
import { Button, ComboBox, Dialog, Grid, GridColumn, GridItemModel, GridSortColumn, HorizontalLayout, Icon, Select, TextField, VerticalLayout } from '@vaadin/react-components';
import { Notification } from '@vaadin/react-components/Notification';
import handleError from 'Frontend/views/_ErrorHandler';
import { Group, ViewToolbar } from 'Frontend/components/ViewToolbar';
import Pelicula from 'Frontend/generated/com/unl/proyectogrupal/base/models/Pelicula';
import { PeliculaService } from 'Frontend/generated/endpoints';
import { DatePicker } from '@vaadin/react-components/DatePicker';
import { useEffect, useState } from 'react';

type GeneroOption = { value: string; label: string };

export const config: ViewConfig = {
  title: 'Crear Pelicula',
  menu: {
    icon: 'vaadin:clipboard-check',
    order: 1,
    title: 'Crear Pelicula',
  },
};

// Función para extraer ID del video YouTube del link
function getYouTubeId(url: string): string | null {
  const regExp = /^.*(youtu.be\/|v\/|u\/\w\/|embed\/|watch\?v=|\&v=)([^#\&\?]*).*/;
  const match = url.match(regExp);
  return (match && match[2].length === 11) ? match[2] : null;
}

type CrearPEntryFormProps = {
  onCreated?: () => void;
  listaGenero: GeneroOption[];
};

type EditarPeliculaEntryFormProps = {
  arguments: Pelicula;
  onUpdated?: () => void;
  listaGenero: GeneroOption[];
};

// ----------- Formulario para crear película ------------

function CrearPEntryForm({ onCreated, listaGenero }: CrearPEntryFormProps) {
  const [titulo, setTitulo] = useState('');
  const [sinopsis, setSinopsis] = useState('');
  const [duracion, setDuracion] = useState<number | ''>('');
  const [trailer, setTrailer] = useState('');
  const [fechaEstreno, setFechaEstreno] = useState<string | null>(null);
  const [genero, setGenero] = useState<string | null>(null);

  const [dialogOpened, setDialogOpened] = useState(false);

  const createPelicula = async () => {
    try {
      if (
        titulo.trim().length === 0 ||
        sinopsis.trim().length === 0 ||
        !duracion ||
        duracion <= 0 ||
        trailer.trim().length === 0 ||
        !fechaEstreno ||
        !genero
      ) {
        Notification.show('Faltan datos obligatorios', {
          duration: 5000,
          position: 'top-center',
          theme: 'error',
        });
        return;
      }

      await PeliculaService.createPelicula(
        titulo,
        sinopsis,
        duracion,
        trailer,
        new Date(fechaEstreno),
        parseInt(genero)
      );

      Notification.show('Película creada', {
        duration: 5000,
        position: 'bottom-end',
        theme: 'success',
      });

      // Limpiar formulario
      setTitulo('');
      setSinopsis('');
      setDuracion('');
      setTrailer('');
      setFechaEstreno(null);
      setGenero(null);

      setDialogOpened(false);

      if (onCreated) onCreated();
    } catch (error) {
      console.error(error);
      handleError(error);
    }
  };

  return (
    <>
      <Dialog
        modeless
        headerTitle="Nueva Película"
        opened={dialogOpened}
        onOpenedChanged={({ detail }) => setDialogOpened(detail.value)}
        footer={
          <>
            <Button onClick={() => setDialogOpened(false)}>Cancelar</Button>
            <Button onClick={createPelicula} theme="primary">
              Registrar
            </Button>
          </>
        }
      >
        <VerticalLayout style={{ alignItems: 'stretch', width: '18rem', maxWidth: '100%' }}>
          <TextField
            label="Nombre de la película"
            placeholder="Ingresar nombre de la película"
            aria-label="Nombre de la película"
            value={titulo}
            onValueChanged={(e) => setTitulo(e.detail.value)}
          />
          <TextField
            label="Sinopsis"
            placeholder="Ingresar sinopsis"
            aria-label="Sinopsis"
            value={sinopsis}
            onValueChanged={(e) => setSinopsis(e.detail.value)}
          />
          <TextField
            label="Duración (min)"
            placeholder="Ingresar duración"
            aria-label="Duración"
            type="number"
            value={duracion === '' ? '' : duracion.toString()}
            onValueChanged={(e) => setDuracion(Number(e.detail.value))}
          />
          <TextField
            label="Trailer (link YouTube)"
            placeholder="Ingresar enlace del trailer"
            aria-label="Trailer"
            value={trailer}
            onValueChanged={(e) => setTrailer(e.detail.value)}
          />
          <DatePicker
            label="Fecha de estreno"
            placeholder="Seleccione una fecha"
            aria-label="Fecha de estreno"
            value={fechaEstreno}
            onValueChanged={(e) => setFechaEstreno(e.detail.value)}
          />
          <ComboBox
            label="Género"
            items={listaGenero}
            itemLabelPath="label"
            itemValuePath="value"
            placeholder="Seleccione un género"
            value={genero}
            onValueChanged={(e) => setGenero(e.detail.value)}
          />
        </VerticalLayout>
      </Dialog>
      <Button onClick={() => setDialogOpened(true)}>Agregar Película</Button>
    </>
  );
}

// ----------- Formulario para editar película ------------

function EditarPeliculaEntryForm({ arguments: pelicula, onUpdated, listaGenero }: EditarPeliculaEntryFormProps) {
  const [dialogOpened, setDialogOpened] = useState(false);

  const [titulo, setTitulo] = useState(pelicula.titulo ?? '');
  const [sinopsis, setSinopsis] = useState(pelicula.sinopsis ?? '');
  const [duracion, setDuracion] = useState<number | ''>(pelicula.duracion ?? '');
  const [trailer, setTrailer] = useState(pelicula.trailer ?? '');
  const [fechaEstreno, setFechaEstreno] = useState<string | null>(
    pelicula.fechaEstreno ? pelicula.fechaEstreno.toString() : null
  );
  const [genero, setGenero] = useState<string | null>(pelicula.idGenero?.toString() ?? null);

  const updatePelicula = async () => {
    try {
      if (
        titulo.trim().length === 0 ||
        sinopsis.trim().length === 0 ||
        !duracion ||
        duracion <= 0 ||
        trailer.trim().length === 0 ||
        !fechaEstreno ||
        !genero
      ) {
        Notification.show('Faltan datos obligatorios', {
          duration: 5000,
          position: 'top-center',
          theme: 'error',
        });
        return;
      }

      await PeliculaService.updatePelicula(
        pelicula.id!,
        titulo,
        sinopsis,
        duracion,
        trailer,
        new Date(fechaEstreno),
        parseInt(genero)
      );

      Notification.show('Película actualizada', {
        duration: 5000,
        position: 'bottom-end',
        theme: 'success',
      });

      setDialogOpened(false);
      if (onUpdated) onUpdated();
    } catch (error) {
      console.error(error);
      handleError(error);
    }
  };

  return (
    <>
      <Dialog
        modeless
        headerTitle="Editar Película"
        opened={dialogOpened}
        onOpenedChanged={({ detail }) => setDialogOpened(detail.value)}
        footer={
          <>
            <Button onClick={() => setDialogOpened(false)}>Cancelar</Button>
            <Button onClick={updatePelicula} theme="primary">
              Actualizar
            </Button>
          </>
        }
      >
        <VerticalLayout style={{ alignItems: 'stretch', width: '18rem', maxWidth: '100%' }}>
          <TextField
            label="Nombre de la película"
            value={titulo}
            onValueChanged={(e) => setTitulo(e.detail.value)}
          />
          <TextField
            label="Sinopsis"
            value={sinopsis}
            onValueChanged={(e) => setSinopsis(e.detail.value)}
          />
          <TextField
            label="Duración (min)"
            type="number"
            value={duracion === '' ? '' : duracion.toString()}
            onValueChanged={(e) => setDuracion(Number(e.detail.value))}
          />
          <TextField
            label="Trailer (link YouTube)"
            value={trailer}
            onValueChanged={(e) => setTrailer(e.detail.value)}
          />
          <DatePicker
            label="Fecha de estreno"
            value={fechaEstreno}
            onValueChanged={(e) => setFechaEstreno(e.detail.value)}
          />
          <ComboBox
            label="Género"
            items={listaGenero}
            itemLabelPath="label"
            itemValuePath="value"
            value={genero}
            onValueChanged={(e) => setGenero(e.detail.value)}
          />
        </VerticalLayout>
      </Dialog>
      <Button onClick={() => setDialogOpened(true)}>Editar</Button>
    </>
  );
}

// ------------ Vista principal -------------

export default function PeliculaView() {
  const [items, setItems] = useState<Pelicula[]>([]);
  const [listaGenero, setListaGenero] = useState<GeneroOption[]>([]);

  // Cargar películas
  const fetchPeliculas = () => {
    PeliculaService.listPelicula()
      .then((data) => setItems(data as any))
      .catch(handleError);
  };

  // Cargar géneros
  useEffect(() => {
    fetchPeliculas();

    PeliculaService.listaGeneroCombo()
      .then((data) => setListaGenero(data as any))
      .catch(handleError);
  }, []);

  // Ordenar películas
  const order = (event: CustomEvent, columnId: string) => {
    const direction = event.detail.value;
    const dir = direction === 'asc' ? 1 : 2;
    PeliculaService.order(columnId, dir)
      .then((data) => setItems(data as any))
      .catch(handleError);
  };

  // Buscar películas
  const [criterio, setCriterio] = useState('');
  const [texto, setTexto] = useState('');
  const itemSelect = [{ label: 'titulo', value: 'titulo' }];

  const search = () => {
    PeliculaService.search(criterio, texto, 0)
      .then((data) => setItems(data as any))
      .catch(handleError);
  };

  // Render número índice
  function indexIndex({ model }: { model: GridItemModel<Pelicula> }) {
    return <span>{model.index + 1}</span>;
  }

  // Render botón para editar, pasamos la película a EditarPeliculaEntryForm
  function EditarBoton({ item }: { item: Pelicula }) {
    return (
      <EditarPeliculaEntryForm
        arguments={item}
        onUpdated={fetchPeliculas}
        listaGenero={listaGenero}
      />
    );
  }

  return (
    <main className="w-full h-full flex flex-col box-border gap-s p-m">
      <ViewToolbar title="Creación de Películas">
        <Group>
          <CrearPEntryForm onCreated={fetchPeliculas} listaGenero={listaGenero} />
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

      <Grid items={items}>
        <GridColumn header="Acciones" renderer={EditarBoton} />
        <GridColumn renderer={indexIndex} header="Nro" />
        <GridSortColumn path="titulo" header="titulo" onSortChanged={order} />
        <GridSortColumn path="sinopsis" header="Sinopsis" onSortChanged={order} />
        <GridColumn path="duracion" header="Duración" />
        
        {/* Columna con trailer embebido */}
        <GridColumn
          header="Trailer"
          renderer={({ item }: { item: Pelicula }) => {
            const videoId = getYouTubeId(item.trailer || '');
            if (videoId) {
              return (
                <iframe
                  width="200"
                  height="113"
                  src={`https://www.youtube.com/embed/${videoId}`}
                  title="YouTube trailer"
                  frameBorder="0"
                  allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"
                  allowFullScreen
                  style={{ borderRadius: '8px' }}
                />
              );
            } else {
              return <span>No trailer válido</span>;
            }
          }}
        />

        <GridColumn path="fechaEstreno" header="Fecha de estreno" />
        {/* Aquí mostramos el nombre del género */}
        <GridColumn
          header="Género"
          renderer={({ item }: { item: Pelicula }) => {
            const genero = listaGenero.find(g => g.value === item.idGenero?.toString());
            return genero ? genero.label : 'Sin género';
          }}
        />
      </Grid>
    </main>
  );
}
