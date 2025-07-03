package com.unl.proyectogrupal.base.controller.dao.dao_models;

import com.unl.proyectogrupal.base.controller.dao.AdapterDao;
import com.unl.proyectogrupal.base.models.Actor;
import com.unl.proyectogrupal.base.controller.data_struct.list.LinkedList;
import com.unl.proyectogrupal.base.controller.Utiles;
import java.util.HashMap;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class DaoActor extends AdapterDao<Actor> {
    private Actor obj;

    public DaoActor() {
        super(Actor.class);
        ensureSharedFolderExists();
    }

    private void ensureSharedFolderExists() {
        File folder = new File(base_path);
        if (!folder.exists()) {
            folder.mkdirs();
        }
    }

    public Actor getObj() {
        if (obj == null) {
            obj = new Actor();
        }
        return obj;
    }

    public void setObj(Actor obj) {
        this.obj = obj;
    }

    public Boolean save() {
        try {
            List<Actor> lista = getListaActores();
            int newId = 1;
            if (!lista.isEmpty()) {
                newId = lista.stream().mapToInt(Actor::getIdActor).max().getAsInt() + 1;
            }
            obj.setIdActor(newId);
            this.persist(obj);
            return true;
        } catch (Exception e) {
            System.err.println("Error al guardar Actor: " + e.getMessage());
            return false;
        }
    }

    public Boolean updatePorId(int idActor) {
        try {
            int pos = obtenerPosicionPorId(idActor);
            if (pos >= 0) {
                this.update(obj, pos);
                return true;
            } else {
                System.err.println("Actor con id " + idActor + " no encontrado");
                return false;
            }
        } catch (Exception e) {
            System.err.println("Error al actualizar Actor: " + e.getMessage());
            return false;
        }
    }

    public void delete(Actor actor) {
        try {
            List<Actor> actores = getListaActores();
            actores.removeIf(a -> a.getIdActor() == actor.getIdActor());
            persistAll(actores);
        } catch (Exception e) {
            System.err.println("Error al eliminar actor: " + e.getMessage());
        }
    }

    public List<Actor> getListaActores() {
        List<Actor> lista = new ArrayList<>();
        var linkedList = super.listAll();
        for (int i = 0; i < linkedList.getLength(); i++) {
            lista.add(linkedList.get(i));
        }
        return lista;
    }

    private void persistAll(List<Actor> actores) throws Exception {
        String json = new com.google.gson.Gson().toJson(actores);
        saveFile(json);
    }

    private void saveFile(String data) throws Exception {
        File file = new File(base_path + "Actor.json");
        if (!file.exists()) {
            file.createNewFile();
        }
        try (FileWriter fw = new FileWriter(file, false)) {
            fw.write(data);
            fw.flush();
        }
    }

    private int obtenerPosicionPorId(int idActor) {
        var lista = super.listAll();
        for (int i = 0; i < lista.getLength(); i++) {
            if (lista.get(i).getIdActor() == idActor) {
                return i;
            }
        }
        return -1;
    }

    public LinkedList<HashMap<String, String>> all() throws Exception {
        LinkedList<HashMap<String, String>> lista = new LinkedList<>();
        if (!this.listAll().isEmpty()) {
            Actor[] arreglo = this.listAll().toArray();
            for (int i = 0; i < arreglo.length; i++) {
                lista.add(toDict(arreglo[i]));
            }
        }
        return lista;
    }

    private HashMap<String, String> toDict(Actor actor) {
        HashMap<String, String> aux = new HashMap<>();
        aux.put("idActor", String.valueOf(actor.getIdActor()));
        aux.put("nombre", actor.getNombre());
        aux.put("anioCarrera", String.valueOf(actor.getAnioCarrera()));
        return aux;
    }

    public LinkedList<HashMap<String, String>> orderQuickActor(Integer type, String attribute) throws Exception {
        LinkedList<HashMap<String, String>> lista = all();
        if (!lista.isEmpty()) {
            HashMap<String, String>[] arr = lista.toArray();
            quicksort(arr, 0, arr.length - 1, type, attribute);
            lista.toList(arr);
        }
        return lista;
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

        boolean esNumero = attribute.equals("anioCarrera");

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

    public LinkedList<HashMap<String, String>> search(String attribute, String text, Integer type) throws Exception {
        LinkedList<HashMap<String, String>> lista = all();
        LinkedList<HashMap<String, String>> resp = new LinkedList<>();

        if (!lista.isEmpty()) {
            lista = orderQuickActor(Utiles.ASCEDENTE, attribute);
            HashMap<String, String>[] arr = lista.toArray();
            Integer n = binaryHelper(arr, attribute, text);

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
                default: // contains
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

    private Integer binaryHelper(HashMap<String, String>[] array, String attribute, String text) {
        if (array.length == 0 || text.isEmpty()) return 0;
        int half = array.length / 2;
        int aux = 0;
        char c = text.toLowerCase().charAt(0);
        char base = array[half].get(attribute).toLowerCase().charAt(0);
        if (c > base) aux = 1;
        else if (c < base) aux = -1;
        return half * aux;
    }
}