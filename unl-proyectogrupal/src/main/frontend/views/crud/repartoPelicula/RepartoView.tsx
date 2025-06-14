import { useState, useEffect } from 'react';
import { HorizontalLayout } from '@vaadin/react-components';
import { CrearReparto } from './CrearReparto';
import { RepartoGrid } from './RepartoGrid';
import { EditarReparto } from './EditarReparto';
import { EliminarReparto } from './EliminarReparto';
import { RepartoPeliculaService } from 'Frontend/generated/endpoints';
import RepartoPelicula from 'Frontend/generated/com/unl/proyectogrupal/base/models/RepartoPelicula';

export default function RepartoView() {
  const [repartos, setRepartos] = useState<RepartoPelicula[]>([]);
  const [repartoAEditar, setRepartoAEditar] = useState<RepartoPelicula | null>(null);
  const [repartoAEliminar, setRepartoAEliminar] = useState<RepartoPelicula | null>(null);

  const cargarRepartos = async () => {
    const data = await RepartoPeliculaService.listAll();
    setRepartos(data);
  };

  useEffect(() => {
    cargarRepartos();
  }, []);

  return (
    <div style={{ padding: '1rem' }}>
      <h2>Gestión de Repartos</h2>

      <HorizontalLayout theme="spacing">
        <CrearReparto onRepartoCreado={cargarRepartos} />
        {repartoAEliminar && (
          <EliminarReparto
            reparto={repartoAEliminar}
            onCancel={() => setRepartoAEliminar(null)}
            onDeleted={() => {
              setRepartoAEliminar(null);
              cargarRepartos();
            }}
          />
        )}
        {repartoAEditar && (
          <EditarReparto
            reparto={repartoAEditar}
            onCancel={() => setRepartoAEditar(null)}
            onUpdated={() => {
              setRepartoAEditar(null);
              cargarRepartos();
            }}
          />
        )}
      </HorizontalLayout>

      <RepartoGrid
        repartos={repartos}
        onSeleccionar={(r, accion) => {
          if (accion === 'editar') setRepartoAEditar(r);
          else setRepartoAEliminar(r);
        }}
      />
    </div>
  );
}
