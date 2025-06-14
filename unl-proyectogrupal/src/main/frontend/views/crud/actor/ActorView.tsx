import { useState, useEffect } from 'react';
import { HorizontalLayout } from '@vaadin/react-components';
import { CrearActor } from './CrearActor';
import { ActorGrid } from './ActorGrid';
import { EliminarActor } from './EliminarActor';
import { EditarActor } from './EditarActor';
import { ActorService } from 'Frontend/generated/endpoints';
import Actor from 'Frontend/generated/com/unl/proyectogrupal/base/models/Actor';

export default function ActorView() {
  const [actores, setActores] = useState<Actor[]>([]);
  const [actorAEditar, setActorAEditar] = useState<Actor | null>(null);
  const [actorAEliminar, setActorAEliminar] = useState<Actor | null>(null);

  const cargarActores = async () => {
    const data = await ActorService.list();
    setActores(data);
  };

  useEffect(() => {
    cargarActores();
  }, []);

  return (
    <div style={{ display: 'flex', justifyContent: 'center', padding: '2rem' }}>
      <div
        style={{
          width: '100%',
          maxWidth: '1000px',
          backgroundColor: '#fff',
          borderRadius: '16px',
          boxShadow: '0 8px 24px rgba(0, 0, 0, 0.06)',
          padding: '2rem',
          display: 'flex',
          flexDirection: 'column',
          gap: '1.5rem',
          border: '1px solid #e5e7eb',
        }}
      >
        <h2 style={{ margin: 0, fontSize: '1.75rem', fontWeight: '600', color: '#111827' }}>
          Gestión de Actores
        </h2>

        <div style={{ display: 'flex', flexWrap: 'wrap', gap: '1rem' }}>
          <CrearActor onActorCreado={cargarActores} />
          {actorAEliminar && (
            <EliminarActor
              actor={actorAEliminar}
              onCancel={() => setActorAEliminar(null)}
              onDeleted={() => {
                setActorAEliminar(null);
                cargarActores();
              }}
            />
          )}
          {actorAEditar && (
            <EditarActor
              actor={actorAEditar}
              onCancel={() => setActorAEditar(null)}
              onUpdated={() => {
                setActorAEditar(null);
                cargarActores();
              }}
            />
          )}
        </div>

        <div style={{ marginTop: '1rem' }}>
          <ActorGrid
            actores={actores}
            onSeleccionar={(actor, accion) => {
              if (accion === 'editar') setActorAEditar(actor);
              else setActorAEliminar(actor);
            }}
          />
        </div>
      </div>
    </div>
  );
}
