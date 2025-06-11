import { ViewConfig } from '@vaadin/hilla-file-router/types.js';
import { Button, ComboBox, Dialog, Grid, GridColumn, GridItemModel, TextField, VerticalLayout } from '@vaadin/react-components';
import { Notification } from '@vaadin/react-components/Notification';
import { useSignal } from '@vaadin/hilla-react-signals';
import handleError from 'Frontend/views/_ErrorHandler';
import { Group, ViewToolbar } from 'Frontend/components/ViewToolbar';
import { useDataProvider } from '@vaadin/hilla-react-crud';
import Pelicula from 'Frontend/generated/com/unl/proyectogrupal/base/models/Pelicula';
import { PeliculaService } from 'Frontend/generated/endpoints';
import { useNavigate } from 'react-router';
import { DatePicker } from '@vaadin/react-components/DatePicker';




export const config: ViewConfig = {
  title: 'Crear Pelicula',
  menu: {
    icon: 'vaadin:clipboard-check',
    order: 1,
    title: 'Crear Pelicula',
  },
};


type CrearPEntryFormProps = {
  onCrearPCreated?: () => void;
};

type editarPeliculaEntryFormUpdateProps = {
  arguments: any;
  onCrearPCUpdate?: () => void;
};


//Crear Pelicula
function CrearPEntryForm(props: CrearPEntryFormProps) {
  const titulo = useSignal('');
  const sinopsis = useSignal('');
  const duracion= useSignal('');
  const trailer = useSignal('');
  const fechaEstreno = useSignal('');
  const genero = useSignal('');

  const navigate = useNavigate(); // Hook de navegación

  const goToAnotherPage = () => {
    navigate('/crud/pelicula/editarPelicula'); // <-- Ajusta la ruta aquí si es diferente
  };

  const createPelicula = async () => {
    try {
      if (titulo.value.trim().length > 0 && sinopsis.value.trim().length > 0 && trailer.value.trim().length > 0) {
        const idGenero = parseInt(genero.value)+1;
        await PeliculaService.createPelicula(titulo.value, sinopsis.value, parseInt(duracion.value), trailer.value, fechaEstreno.value, idGenero);
        if (props.onCrearPCreated) {
          props.onCrearPCreated();
        }
        titulo.value = '';
        sinopsis.value = '';
        duracion.value = '';
        trailer.value = '';
        fechaEstreno.value = '';
        genero.value = '';
        
        dialogOpened.value = false;
        Notification.show('Pelicula creada', { duration: 5000, position: 'bottom-end', theme: 'success' });
      } else {
        Notification.show('No se pudo crear, faltan datos', { duration: 5000, position: 'top-center', theme: 'error' });
      }

    } catch (error) {
      console.log(error);
      handleError(error);
    }
  };

  
  
  
  const dialogOpened = useSignal(false);
  return (
    <>
      <Dialog
        modeless
        headerTitle="Nueva pelicula"
        opened={dialogOpened.value}
        onOpenedChanged={({ detail }) => {
          dialogOpened.value = detail.value;
        }}
        footer={
          <>
            <Button
              onClick={() => {
                dialogOpened.value = false;
              }}
            >
              Candelar
            </Button>
            <Button onClick={createPelicula} theme="primary">
              Registrar
            </Button>
            
          </>
        }
      >
        <VerticalLayout style={{ alignItems: 'stretch', width: '18rem', maxWidth: '100%' }}>
          <TextField label="Nombre de la pelicula" 
            placeholder="Ingresar nombre de la pelicula"
            aria-label="Nombre de la pelicula"
            value={titulo.value}
            onValueChanged={(evt) => (titulo.value = evt.detail.value)}
          />
          <TextField label="Sinopsis" 
            placeholder="Ingresar la sinopsis de la Pelicula"
            aria-label="Sinopsis"
            value={sinopsis.value}
            onValueChanged={(evt) => (sinopsis.value = evt.detail.value)}
          />
          <TextField label="Duracion" 
            placeholder="Ingresar la duracion de la Pelicula"
            aria-label="Duracion"
            value={duracion.value}
            onValueChanged={(evt) => (duracion.value = evt.detail.value)}
          />
          <TextField label="Trailer" 
            placeholder="Ingresar el Trailer de la Pelicula"
            aria-label="Trailer"
            value={trailer.value}
            onValueChanged={(evt) => (trailer.value = evt.detail.value)}
          />
          <DatePicker
              label="Fecha de creacion"
              placeholder="Seleccione una fecha"
              aria-label="Seleccione una fecha"
              value={fechaEstreno.value}
              onValueChanged={(evt) => (fechaEstreno.value = evt.detail.value)}
            />
            <TextField
            label="Género"
            placeholder="Ingrese el ID del género"
            aria-label="Género"
            value={genero.value}
            onValueChanged={(evt) => (genero.value = evt.detail.value)}
          />
          

        </VerticalLayout>
          
      </Dialog>
      <Button
            onClick={() => {
              dialogOpened.value = true;
            }}
          >
            Agregar
          </Button>
          <Button onClick={goToAnotherPage} theme="primary">
        Ir a Acerca de
      </Button>
    </>
  );
}


function EditarPeliculaEntryFormUpdate(props: editarPeliculaEntryFormUpdateProps) {
  console.log(props);

  const dialogOpened = useSignal(false);

  const ident = useSignal(props.arguments.id);
  const titulo = useSignal(props.arguments.titulo);
  const sinopsis = useSignal(props.arguments.sinopsis);
  const duracion = useSignal(props.arguments.duracion);
  const trailer = useSignal(props.arguments.trailer);
  const fechaEstreno = useSignal(props.arguments.fechaEstreno);
  const genero = useSignal(props.arguments.genero);

  const updatePelicula = async () => {
    try {
      if (
        (titulo.value ?? '').trim().length > 0 &&
        (sinopsis.value ?? '').trim().length > 0 &&
        parseInt(duracion.value) > 0 &&
        (trailer.value ?? '').trim().length > 0 &&
        (fechaEstreno.value ?? '').trim().length > 0 &&
        parseInt(genero.value) > 0
      ) {
        await PeliculaService.updatePelicula(
          parseInt(ident.value),
          titulo.value.trim(),
          sinopsis.value.trim(),
          parseInt(duracion.value),
          trailer.value.trim(),
          fechaEstreno.value,
          parseInt(genero.value)
        );

        if (props.onCrearPCUpdate) {
          props.onCrearPCUpdate();
        }

        titulo.value = '';
        sinopsis.value = '';
        duracion.value = '';
        trailer.value = '';
        fechaEstreno.value = '';
        genero.value = '';
        dialogOpened.value = false;

        Notification.show('Película actualizada exitosamente', {
          duration: 5000,
          position: 'bottom-end',
          theme: 'success',
        });
      } else {
        Notification.show('No se pudo actualizar, faltan datos válidos', {
          duration: 5000,
          position: 'top-center',
          theme: 'error',
        });
      }
    } catch (error) {
      console.error(error);
      handleError(error);
    }
  };

  return (
    <>
      <Dialog
        modeless
        headerTitle="Actualizar película"
        opened={dialogOpened.value}
        onOpenedChanged={({ detail }) => {
          dialogOpened.value = detail.value;
        }}
        footer={
          <>
            <Button onClick={() => { dialogOpened.value = false; }}>
              Cancelar
            </Button>
            <Button onClick={updatePelicula} theme="primary">
              Actualizar
            </Button>
          </>
        }
      >
        <VerticalLayout style={{ alignItems: 'stretch', width: '18rem', maxWidth: '100%' }}>
          <TextField
            label="Título"
            placeholder="Ingrese el título de la película"
            aria-label="Título"
            value={titulo.value}
            onValueChanged={(evt) => (titulo.value = evt.detail.value)}
          />
          <TextField
            label="Sinopsis"
            placeholder="Ingrese la sinopsis"
            aria-label="Sinopsis"
            value={sinopsis.value}
            onValueChanged={(evt) => (sinopsis.value = evt.detail.value)}
          />
          <TextField
            label="Duración (min)"
            placeholder="Ingrese la duración"
            aria-label="Duración"
            value={duracion.value}
            onValueChanged={(evt) => (duracion.value = evt.detail.value)}
          />
          <TextField
            label="Trailer"
            placeholder="Ingrese el enlace del trailer"
            aria-label="Trailer"
            value={trailer.value}
            onValueChanged={(evt) => (trailer.value = evt.detail.value)}
          />
          <DatePicker
            label="Fecha de estreno"
            placeholder="Seleccione la fecha"
            aria-label="Fecha de estreno"
            value={fechaEstreno.value}
            onValueChanged={(evt) => (fechaEstreno.value = evt.detail.value)}
          />
          <TextField
            label="Género"
            placeholder="Ingrese el ID del género"
            aria-label="Género"
            value={genero.value}
            onValueChanged={(evt) => (genero.value = evt.detail.value)}
          />
        </VerticalLayout>
      </Dialog>
      <Button onClick={() => { dialogOpened.value = true; }}>
        Editar
      </Button>
    </>
  );
}




//LISTA DE Peliculas
export default function PeliculaView() {
  const dataProvider = useDataProvider<Pelicula>({
    list: () => PeliculaService.listPelicula(),
  });

 

  function indexIndex({model}:{model:GridItemModel<Pelicula>}) {
    return (
      <span>
        {model.index + 1} 
      </span>
    );
  }

 function EditarBoton({ item }: { item: Pelicula }) {
  return (
    <EditarPeliculaEntryFormUpdate arguments={item} onCrearPCUpdate={dataProvider.refresh} />
  );
}

  return (

    <main className="w-full h-full flex flex-col box-border gap-s p-m">

      <ViewToolbar title="Creacion de Peliculas">
        <Group>
          <CrearPEntryForm onCrearPCreated={dataProvider.refresh}/>
        </Group>
      </ViewToolbar>
      <Grid dataProvider={dataProvider.dataProvider}>
        <GridColumn header="Acciones" renderer={({ item }) => <EditarBoton item={item} />} />
        <GridColumn  renderer={indexIndex} header="Nro" />
        <GridColumn path="titulo" header="Nombre de la pelicula" />
        <GridColumn path="sinopsis" header="Sinopsis" />
        <GridColumn path="duracion" header="Duracion" />
        <GridColumn path="trailer" header="Trailer" />

        <GridColumn path="fechaEstreno" header="Fecha de Estreno">
        
        </GridColumn>

        <GridColumn path="genero" header="Genero" />
        <GridColumn path="" header="">

        </GridColumn>
        
      </Grid>
    </main>
  );
}