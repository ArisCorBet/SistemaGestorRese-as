import { ViewConfig } from '@vaadin/hilla-file-router/types.js';
import { Button, ComboBox, DatePicker, Dialog, Grid, GridColumn, GridItemModel, NumberField, TextField, VerticalLayout } from '@vaadin/react-components';
import { Notification } from '@vaadin/react-components/Notification';
import { GeneroService, TaskService } from 'Frontend/generated/endpoints';
import { useSignal } from '@vaadin/hilla-react-signals';
import handleError from 'Frontend/views/_ErrorHandler';
import { Group, ViewToolbar } from 'Frontend/components/ViewToolbar';

import { useDataProvider } from '@vaadin/hilla-react-crud';
import Genero from 'Frontend/generated/com/unl/proyectogrupal/base/models/Genero';

import { useCallback, useEffect, useState } from 'react';
import { updateGenero } from 'Frontend/generated/GeneroService';



export const config: ViewConfig = {
  title: 'Genero',
  menu: {
    icon: 'vaadin:clipboard-check',
    order: 1,
    title: 'Genero',
  },
};

type GeneroEntryFormProps = {
  onGeneroCreated?: () => void;
};


type editarGeneroEntryFormUpdateProps = {
  arguments: any;
  onCrearGeneroUpdate?: () => void;
};


//GUARDAR Genero
function GeneroEntryForm(props: GeneroEntryFormProps) {
  const Nombre = useSignal('');
  const diologOpenned = useSignal (false);

  const createGenero = async () => {
    try {
      if (Nombre.value.trim().length > 0 ) {
        
        await GeneroService.createGenero(Nombre.value);
        if (props.onGeneroCreated) {
          props.onGeneroCreated();
        }
        Nombre.value = '';
        

        diologOpenned.value = false;
        Notification.show('Genero creado', { duration: 5000, position: 'bottom-end', theme: 'success' });
      } else {
        Notification.show('No se pudo crear, faltan datos', { duration: 5000, position: 'top-center', theme: 'Error' });
      }

    } catch (error) {
      console.log(error);
      handleError(error);
    }
  };
  
  
  return (
    <>
      <Dialog
        modeless
        headerTitle="Nuevo Genero"
        opened={diologOpenned.value}
        onOpenedChanged={({ detail }) => {
          diologOpenned.value = detail.value;
        }}
        footer={
          <>
            <Button
              onClick={() => {
                diologOpenned.value = false;
              }}
            >
              Cancelar
            </Button>
            <Button onClick={createGenero} theme="primary">
              Registrar
            </Button>
            
          </>
        }
      >
        <VerticalLayout style={{ alignItems: 'stretch', width: '18rem', maxWidth: '100%' }}>
          <TextField label="Nombre del genero" 
            placeholder="Ingrese el nombre del genero"
            
            value={Nombre.value}
            onValueChanged={(evt) => (Nombre.value = evt.detail.value)}
          />
         
          
        </VerticalLayout>
      </Dialog>
      <Button
            onClick={() => {
              diologOpenned.value = true;
            }}
          >
            Agregar
          </Button>
    </>
  );
}




function EditarGeneroEntryFormUpdate(props: editarGeneroEntryFormUpdateProps) {
  //console.log(props);
  const dialogOpened = useSignal(false);
  const idGenero = useSignal(props.arguments.idGenero);
  const Nombre = useSignal('');
  
  useEffect(() => {
    if (props.arguments?.Nombre) {
      Nombre.value = props.arguments.Nombre;
    }
  }, [dialogOpened.value]);


  const updateGenero = async () => {
    
      try {
        if (Nombre.value.trim().length > 0 ) {
          
          await GeneroService.updateGenero(parseInt(idGenero.value), Nombre.value);
          if (props.onCrearGeneroUpdate) {
            props.onCrearGeneroUpdate();
          }
          Nombre.value = '';
         

          dialogOpened.value = false;
          Notification.show('Genero actualizado exitosamente', { duration: 5000, position: 'bottom-end', theme: 'success' });
        } else {
          Notification.show('No se pudo actualizar, faltan datos', { duration: 5000, position: 'top-center', theme: 'error' });
        }
  
      } catch (error) {
        console.log(error);
        handleError(error);
      }
    };



  /////////////////////////  
  return (
    <>
      <Dialog
        aria-label="Editar Genero"
        draggable
        modeless
        opened={dialogOpened.value}
        onOpenedChanged={(event) => {
          dialogOpened.value = event.detail.value;
        }}
        header={
          <h2
            className="draggable"
            style={{
              flex: 1,
              cursor: 'move',
              margin: 0,
              fontSize: '1.5em',
              fontWeight: 'bold',
              padding: 'var(--lumo-space-m) 0',
            }}
          >
            Editar Genero
          </h2>
        }
        footerRenderer={() => (
          <>
            <Button onClick={close}>Cancelar</Button>
            <Button theme="primary" onClick={updateGenero}>
              Actualizar
            </Button>
          </>
        )}
      >
        <VerticalLayout
          theme="spacing"
          style={{ width: '300px', maxWidth: '100%', alignItems: 'stretch' }}
        >
          <VerticalLayout style={{ alignItems: 'stretch' }}>
          <TextField label="Nombre del genero" 
            placeholder="Ingrese el nombre del genero"
            
            value={Nombre.value}
            onValueChanged={(evt) => (Nombre.value = evt.detail.value)}
          />
            
          </VerticalLayout>
        </VerticalLayout>
      </Dialog>
      <Button onClick={() => dialogOpened.value = true}>Editar</Button>
    </>
  );
}















//LISTA DE Genero
export default function GeneroView() {
  
  const dataProvider = useDataProvider<Genero>({
    list: () => GeneroService.listGenero(),
  });

  

  function indexIndex({model}:{model:GridItemModel<Genero>}) {
    return (
      <span>
        {model.index + 1} 
      </span>
    );
  }


  function EditarBoton({ item }: { item: Genero }) {
    return (
      <EditarGeneroEntryFormUpdate arguments={item} onCrearGeneroUpdate={dataProvider.refresh} />
    );
  }

  return (

    <main className="w-full h-full flex flex-col box-border gap-s p-m">

      <ViewToolbar title="Lista de Generos">
        <Group>
          <GeneroEntryForm onGeneroCreated={dataProvider.refresh}/>
        </Group>
      </ViewToolbar>
      <Grid dataProvider={dataProvider.dataProvider}>
        <GridColumn renderer={indexIndex} header="Nro" />
        <GridColumn path="Nombre" header="Nombre del Genero" />
        <GridColumn header="Acciones" renderer={({ item }) => <EditarBoton item={item} />} />

      </Grid>
    </main>
  );
}  