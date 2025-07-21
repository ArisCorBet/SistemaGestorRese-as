// CrearPelicula.tsx
import {
  Button,
  DatePicker,
  Dialog,
  Notification,
  TextField,
  VerticalLayout,
  CheckboxGroup,
  Checkbox
} from '@vaadin/react-components';
import { useState } from 'react';
import Pelicula from 'Frontend/generated/com/unl/proyectogrupal/base/models/Pelicula';
import { PeliculaService } from 'Frontend/generated/endpoints';
import handleError from 'Frontend/views/_ErrorHandler';

type CrearPEntryFormProps = {
  onCreated?: () => void;
  listaGenero: { value: string; label: string }[];
};

export default function CrearPelicula({ onCreated, listaGenero }: CrearPEntryFormProps) {
  const [titulo, setTitulo] = useState('');
  const [sinopsis, setSinopsis] = useState('');
  const [duracion, setDuracion] = useState<number | ''>('');
  const [trailer, setTrailer] = useState('');
  const [fechaEstreno, setFechaEstreno] = useState<string | null>(null);
  const [selectedGeneros, setSelectedGeneros] = useState<string[]>([]);
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
        selectedGeneros.length === 0
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
        parseInt(selectedGeneros[0])
      );

      Notification.show('Película creada', {
        duration: 5000,
        position: 'bottom-end',
        theme: 'success',
      });

      setTitulo('');
      setSinopsis('');
      setDuracion('');
      setTrailer('');
      setFechaEstreno(null);
      setSelectedGeneros([]);
      setDialogOpened(false);
      onCreated?.();
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
          <CheckboxGroup
            label="Género"
            value={selectedGeneros}
            onValueChanged={(e) => setSelectedGeneros(e.detail.value)}
            theme="vertical"
          >
            {listaGenero.map(({ value, label }) => (
              <Checkbox key={value} value={value} label={label} />
            ))}
          </CheckboxGroup>
        </VerticalLayout>
      </Dialog>
      <Button onClick={() => setDialogOpened(true)}>Agregar Película</Button>
    </>
  );
}
