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
<<<<<<< HEAD
        if (obj == null)
            this.obj = new Actor();
        return this.obj;
=======
        if (obj == null) {
            obj = new Actor();
        }
        return obj;
>>>>>>> b333b9b (Subida de cambios en DAOs, Services, Models y LinkedList a rama JOSSIBEL)
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
        DaoActor dao = new DaoActor();

<<<<<<< HEAD
        // Primer actor
        dao.getObj().setDescripcionActor("Actor dramático");
        dao.getObj().setAnioCarrera(2010);

        if (dao.save())
            System.out.println("GUARDADO");
        else
            System.out.println("Hubo un error");

        // Segundo actor
        dao.setObj(null);
        dao.getObj().setDescripcionActor("Actor de comedia");
        dao.getObj().setAnioCarrera(2015);

        if (dao.save())
            System.out.println("GUARDADO");
        else
            System.out.println("Hubo un error");
=======
        dao.setObj(new Actor());
        dao.getObj().setNombre("Isauro");
        dao.getObj().setAnioCarrera(2023);
        System.out.println(dao.save() ? "Actor guardado correctamente" : "Error al guardar actor");

        dao.setObj(new Actor());
        dao.getObj().setNombre("Pool Ochoa");
        dao.getObj().setAnioCarrera(2024);
        System.out.println(dao.save() ? "Actor guardado correctamente" : "Error al guardar actor");

        // Debug opcional para verificar dónde se guarda
        System.out.println("Archivo guardado en: " + new File(base_path + "Actor.json").getAbsolutePath());
>>>>>>> b333b9b (Subida de cambios en DAOs, Services, Models y LinkedList a rama JOSSIBEL)
    }
}
