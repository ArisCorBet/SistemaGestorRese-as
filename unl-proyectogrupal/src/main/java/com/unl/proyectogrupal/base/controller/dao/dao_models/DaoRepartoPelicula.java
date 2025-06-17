package com.unl.proyectogrupal.base.controller.dao.dao_models;

import com.unl.proyectogrupal.base.controller.dao.AdapterDao;
import com.unl.proyectogrupal.base.models.RepartoPelicula;
import com.unl.proyectogrupal.base.models.Actor;
import com.unl.proyectogrupal.base.controller.data_struct.list.LinkedList;
import com.unl.proyectogrupal.base.controller.Utiles;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileWriter;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.io.IOException;
import java.nio.file.Files;

public class DaoRepartoPelicula extends AdapterDao<RepartoPelicula> {
    private static final Logger LOGGER = Logger.getLogger(DaoRepartoPelicula.class.getName());
    private static final ReentrantLock lock = new ReentrantLock();
    private RepartoPelicula obj;

    public DaoRepartoPelicula() {
        super(RepartoPelicula.class);
        ensureSharedFolderExists();
    }

    private void ensureSharedFolderExists() {
        File folder = new File(base_path);
        if (!folder.exists() && !folder.mkdirs()) {
            LOGGER.log(Level.SEVERE, "No se pudo crear el directorio: " + base_path);
            throw new RuntimeException("No se pudo crear el directorio para almacenar los datos");
        }
    }

    public RepartoPelicula getObj() {
        if (obj == null) {
            obj = new RepartoPelicula();
        }
        return obj;
    }

    public void setObj(RepartoPelicula obj) {
        this.obj = obj;
    }

    public Boolean save(RepartoPelicula reparto) {
        this.obj = reparto;
        return save();
    }

    public Boolean save() {
        lock.lock();
        try {
            List<RepartoPelicula> lista = getListaRepartos();
            
            // Verificar si ya existe el reparto
            boolean existe = lista.stream()
                .anyMatch(r -> r.getIdActor() == obj.getIdActor() && 
                              r.getIdPelicula() == obj.getIdPelicula());
            
            if (existe) {
                LOGGER.log(Level.WARNING, 
                    "Intento de guardar reparto duplicado. Actor ID: {0}, Película ID: {1}",
                    new Object[]{obj.getIdActor(), obj.getIdPelicula()});
                return false;
            }

            lista.add(obj);
            persistAll(lista);
            return true;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al guardar RepartoPelicula", e);
            return false;
        } finally {
            lock.unlock();
        }
    }

    public Boolean update(RepartoPelicula nuevo) {
        this.obj = nuevo;
        return update();
    }

    public Boolean update() {
        lock.lock();
        try {
            List<RepartoPelicula> lista = getListaRepartos();
            int pos = -1;
            
            for (int i = 0; i < lista.size(); i++) {
                RepartoPelicula r = lista.get(i);
                if (r.getIdActor() == obj.getIdActor() && 
                    r.getIdPelicula() == obj.getIdPelicula()) {
                    pos = i;
                    break;
                }
            }

            if (pos == -1) {
                LOGGER.log(Level.WARNING, "Reparto no encontrado para actualizar");
                return false;
            }

            lista.set(pos, obj);
            persistAll(lista);
            return true;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al actualizar RepartoPelicula", e);
            return false;
        } finally {
            lock.unlock();
        }
    }

    public Boolean delete(int idActor, int idPelicula) {
        lock.lock();
        try {
            List<RepartoPelicula> lista = getListaRepartos();
            boolean removed = lista.removeIf(r -> 
                r.getIdActor() == idActor && r.getIdPelicula() == idPelicula);
            
            if (!removed) {
                LOGGER.log(Level.WARNING, "Reparto no encontrado para eliminar");
                return false;
            }

            persistAll(lista);
            return true;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al eliminar RepartoPelicula", e);
            return false;
        } finally {
            lock.unlock();
        }
    }

    public List<RepartoPelicula> getListaRepartos() {
        lock.lock();
        try {
            File file = new File(base_path + "RepartoPelicula.json");
            if (!file.exists()) {
                return new ArrayList<>();
            }
            
            String content = new String(Files.readAllBytes(file.toPath()));
            if (content.trim().isEmpty()) {
                return new ArrayList<>();
            }
            
            return new Gson().fromJson(content, new TypeToken<List<RepartoPelicula>>(){}.getType());
        } catch (IOException e) {
            LOGGER.log(Level.INFO, "No se encontró el archivo de repartos o está vacío");
            return new ArrayList<>();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al leer el archivo de repartos", e);
            throw new RuntimeException("Error al leer los repartos", e);
        } finally {
            lock.unlock();
        }
    }

    public List<Map<String, Object>> getAllWithNames(List<Actor> actores, List<Map<String, String>> peliculas) {
        List<RepartoPelicula> repartos = getListaRepartos();
        List<Map<String, Object>> resultado = new ArrayList<>();

        Map<Integer, String> nombresActores = actores.stream()
            .collect(Collectors.toMap(Actor::getIdActor, Actor::getNombre));
        
        Map<Integer, String> titulosPeliculas = peliculas.stream()
            .collect(Collectors.toMap(
                p -> Integer.parseInt(p.get("id")), 
                p -> p.get("titulo")
            ));

        for (RepartoPelicula reparto : repartos) {
            Map<String, Object> item = new HashMap<>();
            item.put("idActor", reparto.getIdActor());
            item.put("idPelicula", reparto.getIdPelicula());
            item.put("papelActor", reparto.getPapelActor());
            item.put("nombreActor", nombresActores.getOrDefault(reparto.getIdActor(), "ID " + reparto.getIdActor()));
            item.put("tituloPelicula", titulosPeliculas.getOrDefault(reparto.getIdPelicula(), "ID " + reparto.getIdPelicula()));
            
            resultado.add(item);
        }
        
        return resultado;
    }

    public LinkedList<HashMap<String, String>> search(String attribute, String text, Integer type) throws Exception {
        LinkedList<HashMap<String, String>> lista = all();
        LinkedList<HashMap<String, String>> resp = new LinkedList<>();

        if (!lista.isEmpty()) {
            lista = orderQuickReparto(Utiles.ASCEDENTE, attribute);
            HashMap<String, String>[] arr = lista.toArray();
            
            switch (type) {
                case 1: // startsWith
                    for (int i = 0; i < arr.length; i++) {
                        if (arr[i].get(attribute).toLowerCase().startsWith(text.toLowerCase())) {
                            resp.add(arr[i]);
                        }
                    }
                    break;
                case 2: // endsWith
                    for (int i = 0; i < arr.length; i++) {
                        if (arr[i].get(attribute).toLowerCase().endsWith(text.toLowerCase())) {
                            resp.add(arr[i]);
                        }
                    }
                    break;
                default: // contains (Utiles.CONSTIANS)
                    for (int i = 0; i < arr.length; i++) {
                        if (arr[i].get(attribute).toLowerCase().contains(text.toLowerCase())) {
                            resp.add(arr[i]);
                        }
                    }
                    break;
            }
        }
        return resp;
    }

    public LinkedList<HashMap<String, String>> orderQuickReparto(Integer type, String attribute) throws Exception {
        LinkedList<HashMap<String, String>> lista = all();
        if (!lista.isEmpty()) {
            HashMap<String, String>[] arr = lista.toArray();
            quicksort(arr, 0, arr.length - 1, type, attribute);
            lista.toList(arr);
        }
        return lista;
    }

    private LinkedList<HashMap<String, String>> all() throws Exception {
        LinkedList<HashMap<String, String>> lista = new LinkedList<>();
        List<RepartoPelicula> repartos = getListaRepartos();
        
        for (RepartoPelicula reparto : repartos) {
            lista.add(toDict(reparto));
        }
        
        return lista;
    }

    private HashMap<String, String> toDict(RepartoPelicula reparto) {
        HashMap<String, String> aux = new HashMap<>();
        aux.put("idActor", String.valueOf(reparto.getIdActor()));
        aux.put("idPelicula", String.valueOf(reparto.getIdPelicula()));
        aux.put("papelActor", reparto.getPapelActor());
        return aux;
    }

    private void persistAll(List<RepartoPelicula> repartos) throws Exception {
        String json = new Gson().toJson(repartos);
        saveFile(json);
    }

    private void saveFile(String data) throws Exception {
        lock.lock();
        try {
            File file = new File(base_path + "RepartoPelicula.json");
            if (!file.exists()) {
                if (!file.createNewFile()) {
                    throw new IOException("No se pudo crear el archivo");
                }
            }
            
            try (FileWriter fw = new FileWriter(file, false)) {
                fw.write(data);
                fw.flush();
            }
        } finally {
            lock.unlock();
        }
    }

    private void quicksort(HashMap<String, String>[] arr, int begin, int end, Integer type, String attribute) {
        if (begin < end) {
            int partitionIndex = partition(arr, begin, end, type, attribute);
            quicksort(arr, begin, partitionIndex - 1, type, attribute);
            quicksort(arr, partitionIndex + 1, end, type, attribute);
        }
    }

    private int partition(HashMap<String, String>[] arr, int begin, int end, Integer type, String attribute) {
        HashMap<String, String> pivot = arr[end];
        int i = (begin - 1);

        boolean esNumero = attribute.equals("idActor") || attribute.equals("idPelicula");

        if (type == Utiles.ASCEDENTE) {
            for (int j = begin; j < end; j++) {
                boolean condicion;
                if (esNumero) {
                    int valJ = Integer.parseInt(arr[j].get(attribute));
                    int valPivot = Integer.parseInt(pivot.get(attribute));
                    condicion = valJ < valPivot;
                } else {
                    condicion = arr[j].get(attribute).toLowerCase()
                        .compareTo(pivot.get(attribute).toLowerCase()) < 0;
                }

                if (condicion) {
                    i++;
                    HashMap<String, String> swapTemp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = swapTemp;
                }
            }
        } else {
            for (int j = begin; j < end; j++) {
                boolean condicion;
                if (esNumero) {
                    int valJ = Integer.parseInt(arr[j].get(attribute));
                    int valPivot = Integer.parseInt(pivot.get(attribute));
                    condicion = valJ > valPivot;
                } else {
                    condicion = arr[j].get(attribute).toLowerCase()
                        .compareTo(pivot.get(attribute).toLowerCase()) > 0;
                }

                if (condicion) {
                    i++;
                    HashMap<String, String> swapTemp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = swapTemp;
                }
            }
        }

        HashMap<String, String> swapTemp = arr[i + 1];
        arr[i + 1] = arr[end];
        arr[end] = swapTemp;

        return i + 1;
    }
}