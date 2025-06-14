import { Grid, GridColumn } from '@vaadin/react-components';
import { Button } from '@vaadin/react-components';
import RepartoPelicula from 'Frontend/generated/com/unl/proyectogrupal/base/models/RepartoPelicula';

export function RepartoGrid({
  repartos,
  onSeleccionar,
}: {
  repartos: RepartoPelicula[];
  onSeleccionar: (reparto: RepartoPelicula, accion: 'editar' | 'eliminar') => void;
}) {
  return (
    <div
      style={{
        width: '100%',
        borderRadius: '16px',
        boxShadow: '0 8px 24px rgba(0, 0, 0, 0.06)',
        overflow: 'hidden',
        border: '1px solid #e5e7eb',
        backgroundColor: '#ffffff',
        marginTop: '1.5rem'
      }}
    >
      <Grid
        items={repartos}
        theme="row-stripes column-borders"
        style={{ minHeight: '300px' }}
      >
        <GridColumn header="ID Actor" path="idActor" />
        <GridColumn header="ID Película" path="idPelicula" />
        <GridColumn header="Papel" path="papelActor" />
        <GridColumn
          header="Acciones"
          autoWidth
          flexGrow={0}
          renderer={({ item }) => (
            <div style={{ display: 'flex', gap: '0.5rem' }}>
              <Button theme="primary small" onClick={() => onSeleccionar(item, 'editar')}>
                Editar
              </Button>
              <Button theme="error small" onClick={() => onSeleccionar(item, 'eliminar')}>
                Eliminar
              </Button>
            </div>
          )}
        />
      </Grid>
    </div>
  );
}
