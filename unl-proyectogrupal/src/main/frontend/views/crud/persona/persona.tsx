/*import { ViewConfig } from '@vaadin/hilla-file-router/types.js';
import { Button, DatePicker, Dialog, Grid, GridColumn, GridItemModel, TextField, VerticalLayout } from '@vaadin/react-components';
import { Notification } from '@vaadin/react-components/Notification';
import { PersonaService, TaskService } from 'Frontend/generated/endpoints';
import { useSignal } from '@vaadin/hilla-react-signals';
import handleError from 'Frontend/views/_ErrorHandler';
import { Group, ViewToolbar } from 'Frontend/components/ViewToolbar';

import { useDataProvider } from '@vaadin/hilla-react-crud';
import Persona from 'Frontend/generated/com/unl/proyectogrupal/base/models/Persona';

import { useCallback, useEffect, useState } from 'react';

export const config: ViewConfig = {
  title: 'Persona',
  menu: {
    icon: 'vaadin:clipboard-check',
    order: 1,
    title: 'Persona',
  },
};

type PersonaEntryFormProps = {
  onPersonaCreated?: () => void;
};

//GUARDAR Persona
function PersonaEntryForm(props: PersonaEntryFormProps) {
   
  const nombre = useSignal('');
  const apellido = useSignal('');
  const fechaNacimiento= useSignal('');
  const telefono = useSignal('');
  const diologOpenned = useSignal (false);

  const createPersona = async () => {
    try {
      if (nombre.value.trim() && apellido.value.trim() && fechaNacimiento.value && telefono.value.trim()) {
        
        await PersonaService.create(nombre.value, apellido.value, fechaNacimiento.value, telefono.value);
        if (props.onPersonaCreated) {
          props.onPersonaCreated();
        }
        nombre.value = '';
        apellido.value = '';
        fechaNacimiento.value = '';
        telefono.value = '';
        

        diologOpenned.value = false;
        Notification.show('Persona resgistrada', { duration: 5000, position: 'bottom-end', theme: 'success' });
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
        headerTitle="Nueva Persona"
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
            <Button onClick={createPersona} theme="primary">
              Registrar
            </Button>
            
          </>
        }
      >
        <VerticalLayout style={{ alignItems: 'stretch', width: '18rem', maxWidth: '100%' }}>
          <TextField 
            label="nombre" 
            placeholder="Ingrese el nombre"
            
            value={nombre.value}
            onValueChanged={(evt) => (nombre.value = evt.detail.value)}
          />
         
        
         <TextField 
            label="apellido" 
            placeholder="Ingrese el apellido"
            
            value={apellido.value}
            onValueChanged={(evt) => (apellido.value = evt.detail.value)}
          />


        <DatePicker 
            label="Fceha de Nacimiento" 
                  
            value={fechaNacimiento.value}
            onValueChanged={(e) => (fechaNacimiento.value = e.detail.value??'')}
          />





        <TextField 
            label="telefono" 
            placeholder="Ingrese telefono"
            
            value={telefono.value}
            onValueChanged={(evt) => (telefono.value = evt.detail.value)}
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
export default function PersonaView() {
  
  const dataProvider = useDataProvider<Persona>({
    list: () => PersonaService.listPersona(),
  });

  

  function indexIndex({model}:{model:GridItemModel<Persona>}) {
    return (
      <span>
        {model.index + 1} 
      </span>
    );
  }

  return (

    <main className="w-full h-full flex flex-col box-border gap-s p-m">

      <ViewToolbar title="Lista de Personas">
        <Group>
          <PersonaEntryForm onPersonaCreated={dataProvider.refresh}/>
        </Group>
      </ViewToolbar>
      <Grid dataProvider={dataProvider.dataProvider}>
        <GridColumn renderer={indexIndex} header="Nro" />
        <GridColumn path="nombre" header="Nombre" />
        <GridColumn path="apellido" header="Apellido" />
        <GridColumn path="fechaNacimiento" header="Fecha de Nacimeinto" />
        <GridColumn path="telefono" header="Telefono" />
      </Grid>
    </main>
  );
}  




*/