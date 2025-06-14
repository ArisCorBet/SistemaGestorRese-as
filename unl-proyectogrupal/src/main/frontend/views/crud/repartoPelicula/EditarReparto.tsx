import {
  Button,
  Dialog,
  Notification,
  TextField,
  VerticalLayout,
  NumberField,
} from '@vaadin/react-components';
import { useSignal } from '@vaadin/hilla-react-signals';
import RepartoPelicula from 'Frontend/generated/com/unl/proyectogrupal/base/models/RepartoPelicula';
import { RepartoPeliculaService } from 'Frontend/generated/endpoints';
import handleError from 'Frontend/views/_ErrorHandler';

export function EditarReparto({
  reparto,
  onCancel,
  onUpdated,
}: {
  reparto: RepartoPelicula;
  onCancel: () => void;
  onUpdated: () => void;
}) {
  const papelActor = useSignal(reparto.papelActor);
  // Los ids no se editan porque forman la clave compuesta
  const guardar = async () => {
    try {
      await RepartoPeliculaService.updateReparto(
        reparto.idActor,
        reparto.idPelicula,
        papelActor.value
      );
      Notification.show('Reparto actualizado correctamente', { theme: 'success' });
      onUpdated();
    } catch (err) {
      handleError(err);
    }
  };

  return (
    <Dialog opened onOpenedChanged={(e) => !e.detail.value && onCancel()}>
      <VerticalLayout style={{ padding: '1rem' }}>
        <h4>Editar Reparto</h4>
        <p>ID Actor: {reparto.idActor}</p>
        <p>ID Película: {reparto.idPelicula}</p>
        <TextField
          label="Papel del Actor"
          value={papelActor.value}
          onChange={(e) => (papelActor.value = e.target.value)}
        />
        <Button theme="primary" onClick={guardar}>
          Guardar
        </Button>
        <Button onClick={onCancel}>Cancelar</Button>
      </VerticalLayout>
    </Dialog>
  );
}
