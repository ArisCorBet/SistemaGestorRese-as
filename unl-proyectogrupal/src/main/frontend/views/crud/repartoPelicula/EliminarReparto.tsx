import { Button, Dialog, VerticalLayout, HorizontalLayout, Notification } from '@vaadin/react-components';
import { useState } from 'react';
import { RepartoPeliculaService } from 'Frontend/generated/endpoints';

export function EliminarReparto({ 
  reparto, 
  onCancel, 
  onDeleted 
}: { 
  reparto: any, 
  onCancel: () => void, 
  onDeleted: () => void 
}) {
  const [cargando, setCargando] = useState(false);

  const manejarEliminar = async () => {
    setCargando(true);
    try {
      await RepartoPeliculaService.deleteReparto(reparto.idActor, reparto.idPelicula);
      Notification.show("Reparto eliminado exitosamente");
      onDeleted();
    } catch (error: any) {
      console.error("Error al eliminar reparto:", error);
      Notification.show(error.message || "Error al eliminar reparto", { theme: "error" });
    } finally {
      setCargando(false);
    }
  };

  return (
    <Dialog 
      opened={true}
      onOpenedChanged={({detail}) => !detail.value && onCancel()}
      header="Confirmar Eliminación"
      footer={
        <HorizontalLayout style={{ justifyContent: 'flex-end', gap: '1rem' }}>
          <Button onClick={onCancel} theme="tertiary">
            Cancelar
          </Button>
          <Button onClick={manejarEliminar} theme="error" disabled={cargando}>
            {cargando ? 'Eliminando...' : 'Confirmar Eliminación'}
          </Button>
        </HorizontalLayout>
      }
    >
      <VerticalLayout style={{ gap: '1rem' }}>
        <p>¿Estás seguro de eliminar este reparto?</p>
        <p><strong>Actor:</strong> {reparto.nombreActor}</p>
        <p><strong>Película:</strong> {reparto.tituloPelicula}</p>
        <p><strong>Papel:</strong> {reparto.papelActor}</p>
      </VerticalLayout>
    </Dialog>
  );
}