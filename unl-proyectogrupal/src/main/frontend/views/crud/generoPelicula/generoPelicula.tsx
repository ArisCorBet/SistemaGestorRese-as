import { ViewConfig } from '@vaadin/hilla-file-router/types.js';
import { Button, ComboBox, Dialog, Grid, GridColumn, GridItemModel, VerticalLayout } from '@vaadin/react-components';
import { Notification } from '@vaadin/react-components/Notification';
import { GeneroPeliculaService, GeneroService, PeliculaService, TaskService } from 'Frontend/generated/endpoints';
import { useSignal } from '@vaadin/hilla-react-signals';
import handleError from 'Frontend/views/_ErrorHandler';
import { Group, ViewToolbar } from 'Frontend/components/ViewToolbar';

import { useDataProvider } from '@vaadin/hilla-react-crud';
import GeneroPelicula from 'Frontend/generated/com/unl/proyectogrupal/base/models/Genero';

import { useCallback, useEffect, useState } from 'react';
import genero from '../genero/genero';

export const config: ViewConfig = {
  title: 'Genero Pelicula',
  menu: {
    icon: 'vaadin:clipboard-check',
    order: 1,
    title: 'Genero Pelicula',
  },
};

type GeneroPeliculaEntryFormProps = {
  onGeneroPeliculaCreated?: () => void;
};

//GUARDAR Genero
function GeneroPeliculaEntryForm(props: GeneroPeliculaEntryFormProps) {
   
  const genero = useSignal('');
  const pelicula = useSignal('');
  
  const diologOpenned = useSignal (false);

  const createGeneroPelicula = async () => {
    try {
      if (genero.value.trim().length > 0 && pelicula.value.trim().length > 0 ) {
        const idGenero = parseInt(genero.value) +1;
        const idPelicula = parseInt(pelicula.value) +1;

        await GeneroPeliculaService.createGeneroPelicula(idGenero, idPelicula );
        if (props.onGeneroPeliculaCreated) {
          props.onGeneroPeliculaCreated();
        }
        genero.value = '';
        pelicula.value = '';
        

        diologOpenned.value = false;
        Notification.show('Genero de pelicula resgistrada', { duration: 5000, position: 'bottom-end', theme: 'success' });
      } else {
        Notification.show('No se pudo crear, faltan datos', { duration: 5000, position: 'top-center', theme: 'Error' });
      }

    } catch (error) {
      console.log(error);
      handleError(error);
    }
  };



  let listaGenero = useSignal<String[]>([]);
    
  useEffect(() => {
    GeneroPeliculaService.listaGeneroCombo().then(data => {
      listaGenero.value = data;
    });
  }, []);

  let listaPelicula = useSignal<String[]>([]);
  
  useEffect(() => {
    GeneroPeliculaService.listaPeliculaCombo().then(data => {
      listaPelicula.value = data;
    });
  }, []);
  



  return (
    <>
      <Dialog
        modeless
        headerTitle="Nuevo Genero Pelicula"
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
            <Button onClick={createGeneroPelicula} theme="primary">
              Guardar
            </Button>
            
          </>
        }
      >
        <VerticalLayout style={{ alignItems: 'stretch', width: '18rem', maxWidth: '100%' }}>
        <ComboBox
  label="Género"
  items={listaGenero.value}
  placeholder="Seleccione un género"
  aria-label='Seleccione un genero de la lista'
  value={genero.value}
  onValueChanged={(evt) => (genero.value = evt.detail.value)}
/>

<ComboBox
  label="Película"
  items={listaPelicula.value}
  aria-label='Seleccione un genero de la lista'
  placeholder="Seleccione una película"
  value={pelicula.value}
  onValueChanged={(evt) => (pelicula.value = evt.detail.value)}
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




//LISTA DE Genero
export default function GeneroPeliculaView() {
  
  const dataProvider = useDataProvider<GeneroPelicula>({
    list: () => GeneroPeliculaService.listGeneroPelicula(),
  });

  

  function indexIndex({model}:{model:GridItemModel<GeneroPelicula>}) {
    return (
      <span>
        {model.index + 1} 
      </span>
    );
  }

  return (

    <main className="w-full h-full flex flex-col box-border gap-s p-m">

      <ViewToolbar title="Lista de Generos Pelicula">
        <Group>
          <GeneroPeliculaEntryForm onGeneroPeliculaCreated={dataProvider.refresh}/>
        </Group>
      </ViewToolbar>
      <Grid dataProvider={dataProvider.dataProvider}>
        <GridColumn renderer={indexIndex} header="Nro" />
        <GridColumn path="idGenero" header="Genero" />
        <GridColumn path="idPelicula" header="Pelicula" />
        
      </Grid>
    </main>
  );
}  
