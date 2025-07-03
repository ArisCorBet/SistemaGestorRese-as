import { useState } from 'react';
import { 
  Button, 
  Dialog, 
  TextField, 
  ComboBox,
  VerticalLayout,
  HorizontalLayout,
  Notification
} from '@vaadin/react-components';
import { RepartoPeliculaService } from 'Frontend/generated/endpoints';

interface ComboItem {
  value: string;
  label: string;
}

export function CrearReparto({ onRepartoCreado }: { onRepartoCreado: () => void }) {
  const [dialogAbierto, setDialogAbierto] = useState(false);
  const [cargando, setCargando] = useState(false);
  const [formulario, setFormulario] = useState({
    idActor: '',
    idPelicula: '',
    papelActor: ''
  });

  const [actores, setActores] = useState<ComboItem[]>([]);
  const [peliculas, setPeliculas] = useState<ComboItem[]>([]);

  const abrirDialog = async () => {
    setCargando(true);
    try {
      const [actoresData, peliculasData] = await Promise.all([
        RepartoPeliculaService.listActoresForCombo(),
        RepartoPeliculaService.listPeliculasForCombo()
      ]);
      
      setActores(actoresData);
      setPeliculas(peliculasData);
      setDialogAbierto(true);
    } catch (error) {
      console.error("Error al cargar opciones:", error);
      Notification.show("Error al cargar opciones", { theme: "error" });
    } finally {
      setCargando(false);
    }
  };

  const cerrarDialog = () => {
    setDialogAbierto(false);
    setFormulario({
      idActor: '',
      idPelicula: '',
      papelActor: ''
    });
  };

  const manejarEnviar = async () => {
    if (!formulario.idActor || !formulario.idPelicula || !formulario.papelActor) {
      Notification.show("Todos los campos son requeridos", { theme: "error" });
      return;
    }

    setCargando(true);
    try {
      await RepartoPeliculaService.createReparto(
        parseInt(formulario.idActor),
        parseInt(formulario.idPelicula),
        formulario.papelActor
      );
      Notification.show("Reparto creado exitosamente");
      cerrarDialog();
      onRepartoCreado();
    } catch (error: any) {
      console.error("Error al crear reparto:", error);
      Notification.show(error.message || "Error al crear reparto", { theme: "error" });
    } finally {
      setCargando(false);
    }
  };

  return (
    <>
      <Button onClick={abrirDialog} theme="primary">
        Nuevo Reparto
      </Button>

      <Dialog 
        opened={dialogAbierto} 
        onOpenedChanged={({detail}) => !detail.value && cerrarDialog()}
        header="Crear Nuevo Reparto"
        footer={
          <HorizontalLayout style={{ justifyContent: 'flex-end', gap: '1rem' }}>
            <Button onClick={cerrarDialog} theme="tertiary">
              Cancelar
            </Button>
            <Button onClick={manejarEnviar} theme="primary" disabled={cargando}>
              {cargando ? 'Guardando...' : 'Guardar'}
            </Button>
          </HorizontalLayout>
        }
      >
        <VerticalLayout style={{ gap: '1rem', width: '300px' }}>
          <ComboBox
            label="Actor"
            items={actores}
            value={formulario.idActor}
            onValueChanged={(e) => setFormulario({...formulario, idActor: e.detail.value})}
            required
            disabled={cargando}
          />

          <ComboBox
            label="Película"
            items={peliculas}
            value={formulario.idPelicula}
            onValueChanged={(e) => setFormulario({...formulario, idPelicula: e.detail.value})}
            required
            disabled={cargando}
          />

          <TextField
            label="Papel del Actor"
            value={formulario.papelActor}
            onValueChanged={(e) => setFormulario({...formulario, papelActor: e.detail.value})}
            required
            disabled={cargando}
          />
        </VerticalLayout>
      </Dialog>
    </>
  );
}