import {
  Button,
  Dialog,
  TextField,
  VerticalLayout,
  NumberField,
  Notification
} from '@vaadin/react-components';
import { useSignal } from '@vaadin/hilla-react-signals';
import { ActorService } from 'Frontend/generated/endpoints';
import handleError from 'Frontend/views/_ErrorHandler';

export function CrearActor({ onActorCreado }: { onActorCreado: () => void }) {
  const dialogOpened = useSignal(false);
  const descripcion = useSignal('');
  const anioCarrera = useSignal('');

  const crear = async () => {
    try {
      if (!descripcion.value.trim() || !anioCarrera.value.trim()) {
        Notification.show('Por favor completa todos los campos', { theme: 'error' });
        return;
      }
      await ActorService.createActor(descripcion.value, Number(anioCarrera.value));
      dialogOpened.value = false;
      descripcion.value = '';
      anioCarrera.value = '';
      onActorCreado();
      Notification.show('Actor creado exitosamente', { theme: 'success' });
    } catch (err) {
      handleError(err);
    }
  };

  return (
    <>
      <Button theme="primary" onClick={() => (dialogOpened.value = true)}>
        + Nuevo Actor
      </Button>
      <Dialog
        headerTitle="Nuevo actor"
        opened={dialogOpened.value}
        onOpenedChanged={(e) => (dialogOpened.value = e.detail.value)}
      >
        <VerticalLayout style={{ padding: '1rem' }}>
          <TextField
            label="Nombre"
            value={descripcion.value}
            onChange={(e) => (descripcion.value = e.target.value)}
          />
          <NumberField
            label="Año carrera"
            value={Number(anioCarrera.value)}
            onValueChanged={(e) =>
              (anioCarrera.value = String(e.detail.value))
            }
          />
          <Button theme="primary" onClick={crear}>
            Guardar
          </Button>
        </VerticalLayout>
      </Dialog>
    </>
  );
}
