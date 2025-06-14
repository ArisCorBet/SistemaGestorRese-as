package com.unl.proyectogrupal.base.controller.dao.dao_models;

import com.unl.proyectogrupal.base.controller.dao.AdapterDao;
import com.unl.proyectogrupal.base.models.Actor;

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
            System.err.println("Error al guardar actor: " + e.getMessage());
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
            System.err.println("Error al actualizar actor: " + e.getMessage());
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


    public static void main(String[] args) {
        DaoActor da = new DaoActor();


        da.setObj(new Actor());
        da.getObj().setNombre("Isauro");
        da.getObj().setAnioCarrera(2023);
        System.out.println(da.save() ? "Actor guardado correctamente" : "Error al guardar actor");

        da.setObj(new Actor());
        da.getObj().setNombre("Pool Ochoa");
        da.getObj().setAnioCarrera(2024);
        System.out.println(da.save() ? "Actor guardado correctamente" : "Error al guardar actor");

        // Debug opcional para verificar dónde se guarda
        System.out.println("Archivo guardado en: " + new File(base_path + "Actor.json").getAbsolutePath());

    }
}
