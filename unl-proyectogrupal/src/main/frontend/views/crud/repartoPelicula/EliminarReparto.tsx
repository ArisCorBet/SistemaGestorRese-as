import { Button, Dialog, Notification, VerticalLayout } from '@vaadin/react-components';
import { RepartoPeliculaService } from 'Frontend/generated/endpoints';
import handleError from 'Frontend/views/_ErrorHandler';
import RepartoPelicula from 'Frontend/generated/com/unl/proyectogrupal/base/models/RepartoPelicula';

export function EliminarReparto({
  reparto,
  onCancel,
  onDeleted,
}: {
  reparto: RepartoPelicula;
  onCancel: () => void;
  onDeleted: () => void;
}) {
  const confirmar = async () => {
    try {
      await RepartoPeliculaService.deleteReparto(reparto.idActor, reparto.idPelicula);
      Notification.show('Reparto eliminado correctamente', { theme: 'success' });
      onDeleted();
    } catch (err) {
      handleError(err);
    }
  };

  return (
    <Dialog opened onOpenedChanged={(e) => !e.detail.value && onCancel()}>
      <VerticalLayout style={{ padding: '1rem' }}>
        <h4>
          ¿Estás seguro que deseas eliminar el reparto del actor {reparto.idActor} en la película {reparto.idPelicula}?
        </h4>
        <Button theme="error" onClick={confirmar}>
          Eliminar
        </Button>
        <Button onClick={onCancel}>Cancelar</Button>
      </VerticalLayout>
    </Dialog>
  );
}
