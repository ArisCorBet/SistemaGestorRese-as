import { useState, useEffect } from 'react';
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

export function EditarReparto({ 
  reparto, 
  onCancel, 
  onUpdated 
}: { 
  reparto: any, 
  onCancel: () => void, 
  onUpdated: () => void 
}) {
  const [cargando, setCargando] = useState(false);
  const [formulario, setFormulario] = useState({
    idActor: reparto.idActor.toString(),
    idPelicula: reparto.idPelicula.toString(),
    papelActor: reparto.papelActor
  });

  const [actores, setActores] = useState<ComboItem[]>([]);
  const [peliculas, setPeliculas] = useState<ComboItem[]>([]);

  useEffect(() => {
    const cargarOpciones = async () => {
      setCargando(true);
      try {
        const [actoresData, peliculasData] = await Promise.all([
          RepartoPeliculaService.listActoresForCombo(),
          RepartoPeliculaService.listPeliculasForCombo()
        ]);
        
        setActores(actoresData);
        setPeliculas(peliculasData);
      } catch (error) {
        console.error("Error al cargar opciones:", error);
        Notification.show("Error al cargar opciones", { theme: "error" });
      } finally {
        setCargando(false);
      }
    };
    cargarOpciones();
  }, []);

  const manejarEnviar = async () => {
    if (!formulario.idActor || !formulario.idPelicula || !formulario.papelActor) {
      Notification.show("Todos los campos son requeridos", { theme: "error" });
      return;
    }

    setCargando(true);
    try {
      await RepartoPeliculaService.updateReparto(
        parseInt(formulario.idActor),
        parseInt(formulario.idPelicula),
        formulario.papelActor
      );
      Notification.show("Reparto actualizado exitosamente");
      onUpdated();
    } catch (error: any) {
      console.error("Error al actualizar reparto:", error);
      Notification.show(error.message || "Error al actualizar reparto", { theme: "error" });
    } finally {
      setCargando(false);
    }
  };

  return (
    <Dialog 
      opened={true}
      onOpenedChanged={({detail}) => !detail.value && onCancel()}
      header={`Editar Reparto: ${reparto.nombreActor} en ${reparto.tituloPelicula}`}
      footer={
        <HorizontalLayout style={{ justifyContent: 'flex-end', gap: '1rem' }}>
          <Button onClick={onCancel} theme="tertiary">
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
  );
}