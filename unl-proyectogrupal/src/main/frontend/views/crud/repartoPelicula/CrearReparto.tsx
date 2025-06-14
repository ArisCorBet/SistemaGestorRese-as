import {
  Button,
  Dialog,
  Notification,
  TextField,
  VerticalLayout,
  NumberField,
} from '@vaadin/react-components';
import { useSignal } from '@vaadin/hilla-react-signals';
import { RepartoPeliculaService } from 'Frontend/generated/endpoints'; // Asume que creaste servicio con este nombre
import handleError from 'Frontend/views/_ErrorHandler';

export function CrearReparto({
  onRepartoCreado,
}: {
  onRepartoCreado: () => void;
}) {
  const idActor = useSignal('');
  const idPelicula = useSignal('');
  const papelActor = useSignal('');
  const dialogOpened = useSignal(false);

  const crear = async () => {
    try {
      if (!idActor.value.trim() || !idPelicula.value.trim() || !papelActor.value.trim()) {
        Notification.show('Por favor completa todos los campos', { theme: 'error' });
        return;
      }
      await RepartoPeliculaService.createReparto(
        Number(idActor.value),
        Number(idPelicula.value),
        papelActor.value
      );
      Notification.show('Reparto creado exitosamente', { theme: 'success' });
      dialogOpened.value = false;
      idActor.value = '';
      idPelicula.value = '';
      papelActor.value = '';
      onRepartoCreado();
    } catch (err) {
      handleError(err);
    }
  };

  return (
    <>
      <Button theme="primary" onClick={() => (dialogOpened.value = true)}>
        + Nuevo Reparto
      </Button>
      <Dialog
        headerTitle="Nuevo Reparto"
        opened={dialogOpened.value}
        onOpenedChanged={(e) => (dialogOpened.value = e.detail.value)}
      >
        <VerticalLayout style={{ padding: '1rem' }}>
          <NumberField
            label="ID Actor"
            value={Number(idActor.value)}
            onValueChanged={(e) => (idActor.value = String(e.detail.value))}
          />
          <NumberField
            label="ID Película"
            value={Number(idPelicula.value)}
            onValueChanged={(e) => (idPelicula.value = String(e.detail.value))}
          />
          <TextField
            label="Papel del Actor"
            value={papelActor.value}
            onChange={(e) => (papelActor.value = e.target.value)}
          />
          <Button theme="primary" onClick={crear}>
            Guardar
          </Button>
        </VerticalLayout>
      </Dialog>
    </>
  );
}

