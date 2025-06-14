import { useState, useEffect } from 'react';
import { HorizontalLayout } from '@vaadin/react-components';
import { CrearDirector } from './CrearDirector';
import { DirectorGrid } from './DirectorGrid';
import { EliminarDirector } from './EliminarDirector';
import { EditarDirector } from './EditarDirector';
import { DirectorService } from 'Frontend/generated/endpoints';
import Director from 'Frontend/generated/com/unl/proyectogrupal/base/models/Director';

export default function DirectorView() {
  const [directores, setDirectores] = useState<Director[]>([]);
  const [directorAEditar, setDirectorAEditar] = useState<Director | null>(null);
  const [directorAEliminar, setDirectorAEliminar] = useState<Director | null>(null);

  const cargarDirectores = async () => {
    const data = await DirectorService.list();
    setDirectores(data);
  };

  useEffect(() => {
    cargarDirectores();
  }, []);

  return (
    <div
      style={{
        padding: '2rem',
        backgroundColor: '#f5f5f5',
        minHeight: '100vh',
        fontFamily: 'Arial, sans-serif',
      }}
    >
      <div
        style={{
          backgroundColor: '#fff',
          padding: '2rem',
          borderRadius: '12px',
          boxShadow: '0 2px 10px rgba(0, 0, 0, 0.1)',
        }}
      >
        <h1 style={{ textAlign: 'center', marginBottom: '1.5rem', color: '#333' }}>
          Administración de Directores
        </h1>

        <HorizontalLayout theme="spacing" style={{ marginBottom: '1.5rem', flexWrap: 'wrap' }}>
          <CrearDirector onDirectorCreado={cargarDirectores} />
        </HorizontalLayout>

        <DirectorGrid
          directores={directores}
          onSeleccionar={(director, accion) => {
            if (accion === 'editar') setDirectorAEditar(director);
            else setDirectorAEliminar(director);
          }}
        />

        {directorAEliminar && (
          <EliminarDirector
            director={directorAEliminar}
            onCancel={() => setDirectorAEliminar(null)}
            onDeleted={() => {
              setDirectorAEliminar(null);
              cargarDirectores();
            }}
          />
        )}
        {directorAEditar && (
          <EditarDirector
            director={directorAEditar}
            onCancel={() => setDirectorAEditar(null)}
            onUpdated={() => {
              setDirectorAEditar(null);
              cargarDirectores();
            }}
          />
        )}
      </div>
    </div>
  );
}
