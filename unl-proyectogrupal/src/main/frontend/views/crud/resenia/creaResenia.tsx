import { ViewConfig } from '@vaadin/hilla-file-router/types.js';
import { Button, ComboBox, Dialog, Grid, GridColumn, GridItemModel, TextField, VerticalLayout } from '@vaadin/react-components';
import { Notification } from '@vaadin/react-components/Notification';
import { useSignal } from '@vaadin/hilla-react-signals';
import handleError from 'Frontend/views/_ErrorHandler';
import { Group, ViewToolbar } from 'Frontend/components/ViewToolbar';
import { useDataProvider } from '@vaadin/hilla-react-crud';
import ReseniaPelicula from 'Frontend/generated/com/unl/proyectogrupal/base/models/ReseniaPelicula';
import { ReseniaPeliculaService } from 'Frontend/generated/endpoints';
import { useNavigate } from 'react-router';
import { DatePicker } from '@vaadin/react-components/DatePicker';




export const config: ViewConfig = {
  title: 'Crear Resenia',
  menu: {
    icon: 'vaadin:clipboard-check',
    order: 1,
    title: 'Crear Resenia',
  },
};


type CrearReseniaEntryFormProps = {
  onCrearReseniaCreated?: () => void;
};

type editarReseniaEntryFormUpdateProps = {
  arguments: any;
  onCrearReseniaUpdate?: () => void;
};


//Crear Pelicula
function CrearPEntryForm(props: CrearReseniaEntryFormProps) {
  const resenia = useSignal('');
  const puntuacion = useSignal('');
  const fechaResenia = useSignal('');
  

  const navigate = useNavigate(); // Hook de navegación

  const goToAnotherPage = () => {
    navigate('/crud/pelicula/editarPelicula'); // <-- Ajusta la ruta aquí si es diferente
  };

  const createPelicula = async () => {
    try {
      if (resenia.value.trim().length > 0) {
        await ReseniaPeliculaService.createReseniaPelicula(resenia.value, parseFloat(puntuacion.value), fechaResenia.value);
        if (props.onCrearReseniaCreated) {
          props.onCrearReseniaCreated();
        }
        resenia.value = '';
        puntuacion.value = '';
        fechaResenia.value = '';
        
        dialogOpened.value = false;
        Notification.show('Resenia creada', { duration: 5000, position: 'bottom-end', theme: 'success' });
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
        headerTitle="Nueva resenia"
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
          <TextField label="Resenia" 
            placeholder="Ingresar la resenia"
            aria-label="Ingresar la resenia"
            value={resenia.value}
            onValueChanged={(evt) => (resenia.value = evt.detail.value)}
          />
          <TextField label="Puntuacion" 
            placeholder="Ingresar la puntuacion de la resenia"
            aria-label="Ingresar la puntuacion de la resenia"
            value={puntuacion.value}
            onValueChanged={(evt) => (puntuacion.value = evt.detail.value)}
          />
          <DatePicker
              label="Fecha Resenia"
              placeholder="Seleccione una fecha"
              aria-label="Seleccione una fecha"
              value={fechaResenia.value}
              onValueChanged={(evt) => (fechaResenia.value = evt.detail.value)}
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


function EditarPeliculaEntryFormUpdate(props: editarReseniaEntryFormUpdateProps) {
  console.log(props);

  const dialogOpened = useSignal(false);

  const ident = useSignal(props.arguments.id);
  const resenia = useSignal(props.arguments.titulo);
  const puntuacion = useSignal(props.arguments.sinopsis);
  const fechaResenia = useSignal(props.arguments.duracion);
  

  const updatePelicula = async () => {
    try {
      if (
        (resenia.value ?? '').trim().length > 0 &&
        parseFloat(puntuacion.value) > 0 &&
        (fechaResenia.value ?? '').trim().length > 0
      ) {
        await ReseniaPeliculaService.updateReseniaPelicula(
          parseInt(ident.value),
          resenia.value.trim(),
          parseFloat(puntuacion.value),
          fechaResenia.value,
        );

        if (props.onCrearReseniaUpdate) {
          props.onCrearReseniaUpdate();
        }

        resenia.value = '';
        puntuacion.value = '';
        fechaResenia.value = '';
        dialogOpened.value = false;

        Notification.show('Resenia actualizada exitosamente', {
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
        headerTitle="Actualizar Resenia"
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
          <TextField label="Resenia" 
            placeholder="Ingresar la resenia"
            aria-label="Ingresar la resenia"
            value={resenia.value}
            onValueChanged={(evt) => (resenia.value = evt.detail.value)}
          />
          <TextField label="Puntuacion" 
            placeholder="Ingresar la puntuacion de la resenia"
            aria-label="Ingresar la puntuacion de la resenia"
            value={puntuacion.value}
            onValueChanged={(evt) => (puntuacion.value = evt.detail.value)}
          />
          <DatePicker
              label="Fecha Resenia"
              placeholder="Seleccione una fecha"
              aria-label="Seleccione una fecha"
              value={fechaResenia.value}
              onValueChanged={(evt) => (fechaResenia.value = evt.detail.value)}
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
  const dataProvider = useDataProvider<ReseniaPelicula>({
    list: () => ReseniaPeliculaService.listReseniaPelicula(),
  });

 

  function indexIndex({model}:{model:GridItemModel<ReseniaPelicula>}) {
    return (
      <span>
        {model.index + 1} 
      </span>
    );
  }

 function EditarBoton({ item }: { item: ReseniaPelicula }) {
  return (
    <EditarPeliculaEntryFormUpdate arguments={item} onCrearReseniaUpdate={dataProvider.refresh} />
  );
}

  return (

    <main className="w-full h-full flex flex-col box-border gap-s p-m">

      <ViewToolbar title="Creacion de Peliculas">
        <Group>
          <CrearPEntryForm onCrearReseniaCreated={dataProvider.refresh}/>
        </Group>
      </ViewToolbar>
      <Grid dataProvider={dataProvider.dataProvider}>
        <GridColumn header="Acciones" renderer={({ item }) => <EditarBoton item={item} />} />
        <GridColumn  renderer={indexIndex} header="Nro" />
        <GridColumn path="resenia" header="resenia" />
        <GridColumn path="puntuacion" header="puntuacion" />
        <GridColumn path="fechaResenia" header="fechaResenia" />
        
        
      </Grid>
    </main>
  );
}