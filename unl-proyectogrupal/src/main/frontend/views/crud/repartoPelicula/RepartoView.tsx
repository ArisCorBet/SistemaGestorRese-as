import { useEffect, useState, useCallback } from 'react';
import { RepartoPeliculaService } from 'Frontend/generated/endpoints';
import { CrearReparto } from './CrearReparto';
import { EditarReparto } from './EditarReparto';
import { EliminarReparto } from './EliminarReparto';
import {
  Button,
  ComboBox,
  TextField,
  Grid,
  GridColumn,
  VerticalLayout,
  Notification,
  HorizontalLayout
} from '@vaadin/react-components';

interface RepartoCompleto {
  idActor: number;
  idPelicula: number;
  papelActor: string;
  nombreActor: string;
  tituloPelicula: string;
}

type FiltroCampo = 'nombreActor' | 'tituloPelicula' | 'papelActor';

export default function RepartoPeliculaView() {
  const [repartos, setRepartos] = useState<RepartoCompleto[]>([]);
  const [repartoAEditar, setRepartoAEditar] = useState<RepartoCompleto | null>(null);
  const [repartoAEliminar, setRepartoAEliminar] = useState<RepartoCompleto | null>(null);
  const [filtro, setFiltro] = useState('');
  const [campoFiltro, setCampoFiltro] = useState<FiltroCampo>('papelActor');
  const [campoOrden, setCampoOrden] = useState<FiltroCampo>('papelActor');
  const [ascendente, setAscendente] = useState(true);
  const [cargando, setCargando] = useState(false);

  const cargarRepartos = useCallback(async () => {
    setCargando(true);
    try {
      const repartosConNombres = await RepartoPeliculaService.listAllWithNames();
      
      const repartosMapeados = repartosConNombres.map(r => ({
        idActor: Number(r.idActor),
        idPelicula: Number(r.idPelicula),
        papelActor: String(r.papelActor),
        nombreActor: r.nombreActor || `Actor ID: ${r.idActor}`,
        tituloPelicula: r.tituloPelicula || `Película ID: ${r.idPelicula}`
      }));
      
      setRepartos(repartosMapeados);
    } catch (error) {
      console.error("Error al cargar repartos:", error);
      Notification.show("Error al cargar repartos. Verifica la consola para más detalles.", { 
        theme: "error",
        duration: 5000
      });
      setRepartos([]);
    } finally {
      setCargando(false);
    }
  }, []);

  const buscarRepartos = useCallback(async () => {
    setCargando(true);
    try {
      let resultados;
      
      if (filtro.trim() !== '') {
        resultados = await RepartoPeliculaService.searchWithNames(campoFiltro, filtro);
      } else {
        resultados = await RepartoPeliculaService.listAllWithNames();
      }

      // Ordenar resultados
      resultados.sort((a: any, b: any) => {
        const valorA = a[campoOrden]?.toString().toLowerCase() || '';
        const valorB = b[campoOrden]?.toString().toLowerCase() || '';
        return ascendente 
          ? valorA.localeCompare(valorB) 
          : valorB.localeCompare(valorA);
      });

      const repartosMapeados = resultados.map((r: any) => ({
        idActor: Number(r.idActor),
        idPelicula: Number(r.idPelicula),
        papelActor: String(r.papelActor),
        nombreActor: r.nombreActor || `Actor ID: ${r.idActor}`,
        tituloPelicula: r.tituloPelicula || `Película ID: ${r.idPelicula}`
      }));

      setRepartos(repartosMapeados);
    } catch (error) {
      console.error("Error al buscar repartos:", error);
      Notification.show("Error al buscar repartos", { theme: "error" });
      setRepartos([]);
    } finally {
      setCargando(false);
    }
  }, [filtro, campoFiltro, campoOrden, ascendente]);

  useEffect(() => {
    cargarRepartos();
  }, []);

  const IndexRenderer = ({ model }: { model: any }) => (
    <span>{model.index + 1}</span>
  );

  const renderEncabezado = (campo: FiltroCampo, texto: string) => (
    <div
      onClick={() => {
        if (campoOrden === campo) {
          setAscendente(!ascendente);
        } else {
          setCampoOrden(campo);
          setAscendente(true);
        }
        buscarRepartos();
      }}
      style={{
        display: 'flex',
        alignItems: 'center',
        gap: '4px',
        cursor: 'pointer',
        fontWeight: 600,
        color: campoOrden === campo ? '#1976d2' : '#333',
        transition: 'color 0.2s ease'
      }}
    >
      {texto}
      {campoOrden === campo ? (
        ascendente ? (
          <span style={{ color: '#1976d2' }}>▲</span>
        ) : (
          <span style={{ color: '#d32f2f' }}>▼</span>
        )
      ) : null}
    </div>
  );

  const handleDebugRelations = async () => {
    try {
      await RepartoPeliculaService.debugRelations();
      Notification.show("Depuración completada. Verifica la consola del backend", {
        position: 'bottom-start',
        duration: 5000
      });
      cargarRepartos(); // Recargar datos después de la depuración
    } catch (error) {
      console.error("Error en depuración:", error);
      Notification.show("Error al depurar relaciones", { theme: "error" });
    }
  };

  return (
    <VerticalLayout style={{ padding: '2rem', backgroundColor: '#f7f7f7' }}>
      <h2 style={{ 
        fontSize: '24px', 
        color: '#333', 
        fontWeight: 'bold', 
        marginBottom: '1rem',
        textAlign: 'center'
      }}>
        Gestión de Reparto de Películas
      </h2>

      <HorizontalLayout 
        style={{ 
          gap: '1rem', 
          marginBottom: '1.5rem', 
          alignItems: 'center',
          justifyContent: 'center',
          flexWrap: 'wrap'
        }}
      >
        <ComboBox
          label="Filtrar por"
          items={[
            { label: 'Actor', value: 'nombreActor' },
            { label: 'Película', value: 'tituloPelicula' },
            { label: 'Papel', value: 'papelActor' }
          ]}
          value={campoFiltro}
          onValueChanged={(e) => setCampoFiltro(e.detail.value as FiltroCampo)}
          style={{ minWidth: '180px' }}
        />
        
        <TextField
          placeholder="Texto a buscar..."
          value={filtro}
          onChange={(e) => setFiltro(e.target.value)}
          onKeyDown={(e) => e.key === 'Enter' && buscarRepartos()}
          style={{ minWidth: '250px' }}
        />
        
        <Button 
          onClick={buscarRepartos} 
          theme="primary"
          disabled={cargando}
          style={{ minWidth: '120px' }}
        >
          {cargando ? 'Buscando...' : 'Buscar'}
        </Button>
        
        <Button 
          onClick={handleDebugRelations} 
          theme="secondary"
          disabled={cargando}
          title="Verifica relaciones inconsistentes en la consola del backend"
        >
          Depurar Relaciones
        </Button>
        
        <CrearReparto onRepartoCreado={cargarRepartos} />
      </HorizontalLayout>

      {cargando ? (
        <div style={{ 
          textAlign: 'center', 
          padding: '2rem',
          color: '#555',
          fontStyle: 'italic'
        }}>
          Cargando datos de reparto...
        </div>
      ) : repartos.length === 0 ? (
        <div style={{ 
          textAlign: 'center', 
          padding: '2rem',
          color: '#666',
          backgroundColor: '#fff',
          borderRadius: '8px',
          boxShadow: '0 2px 6px rgba(0,0,0,0.1)'
        }}>
          No se encontraron registros de reparto. Crea uno nuevo para comenzar.
        </div>
      ) : (
        <Grid
          items={repartos}
          theme="row-stripes"
          style={{
            backgroundColor: 'white',
            borderRadius: '8px',
            boxShadow: '0 4px 12px rgba(0,0,0,0.1)',
            marginTop: '1rem',
            overflowX: 'auto'
          }}
        >
          <GridColumn 
            header="#" 
            renderer={IndexRenderer} 
            width="70px" 
            frozen 
          />
          
          <GridColumn
            header={renderEncabezado('nombreActor', 'Actor')}
            path="nombreActor"
            autoWidth
            resizable
          />
          
          <GridColumn
            header={renderEncabezado('tituloPelicula', 'Película')}
            path="tituloPelicula"
            autoWidth
            resizable
          />
          
          <GridColumn
            header={renderEncabezado('papelActor', 'Papel')}
            path="papelActor"
            autoWidth
            resizable
          />
          
          <GridColumn
            header="Acciones"
            width="180px"
            frozenToEnd
            renderer={({ item }) => (
              <HorizontalLayout 
                style={{ 
                  gap: '0.5rem',
                  justifyContent: 'center'
                }}
              >
                <Button
                  theme="primary"
                  onClick={() => setRepartoAEditar(item)}
                  disabled={cargando}
                  style={{ padding: '0.3rem 0.6rem' }}
                >
                  Editar
                </Button>
                <Button
                  theme="error"
                  onClick={() => setRepartoAEliminar(item)}
                  disabled={cargando}
                  style={{ padding: '0.3rem 0.6rem' }}
                >
                  Eliminar
                </Button>
              </HorizontalLayout>
            )}
          />
        </Grid>
      )}

      {repartoAEditar && (
        <EditarReparto
          reparto={repartoAEditar}
          onCancel={() => setRepartoAEditar(null)}
          onUpdated={() => {
            setRepartoAEditar(null);
            cargarRepartos();
            Notification.show("Reparto actualizado correctamente", {
              position: 'bottom-start',
              theme: 'success'
            });
          }}
        />
      )}

      {repartoAEliminar && (
        <EliminarReparto
          reparto={repartoAEliminar}
          onCancel={() => setRepartoAEliminar(null)}
          onDeleted={() => {
            setRepartoAEliminar(null);
            cargarRepartos();
            Notification.show("Reparto eliminado correctamente", {
              position: 'bottom-start',
              theme: 'success'
            });
          }}
        />
      )}
    </VerticalLayout>
  );
}