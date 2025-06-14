package com.unl.proyectogrupal.base.controller.dao.dao_models;

import com.unl.proyectogrupal.base.controller.dao.AdapterDao;
import com.unl.proyectogrupal.base.models.Director;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class DaoDirector extends AdapterDao<Director> {
    private Director obj;

    public DaoDirector() {
        super(Director.class);
        ensureSharedFolderExists();
    }

    private void ensureSharedFolderExists() {
        File folder = new File(base_path);
        if (!folder.exists()) {
            folder.mkdirs();
        }
    }

    public Director getObj() {

        if (obj == null) {
            obj = new Director();
        }
        return obj;
    }

    public void setObj(Director obj) {
        this.obj = obj;
    }

    public Boolean save() {
        try {
            List<Director> lista = getListaDirectores();
            int newId = 1;
            if (!lista.isEmpty()) {
                newId = lista.stream().mapToInt(Director::getIdDirector).max().getAsInt() + 1;
            }
            obj.setIdDirector(newId);
            this.persist(obj);
            return true;
        } catch (Exception e) {
            System.err.println("Error al guardar Director: " + e.getMessage());
            return false;
        }
    }

    public Boolean updatePorId(int idDirector) {
        try {
            int pos = obtenerPosicionPorId(idDirector);
            if (pos >= 0) {
                this.update(obj, pos);
                return true;
            } else {
                System.err.println("Director con id " + idDirector + " no encontrado");
                return false;
            }
        } catch (Exception e) {
            System.err.println("Error al actualizar Director: " + e.getMessage());
            return false;
        }
    }

    public void delete(Director director) {
        try {
            List<Director> directores = getListaDirectores();
            directores.removeIf(d -> d.getIdDirector() == director.getIdDirector());
            persistAll(directores);
        } catch (Exception e) {
            System.err.println("Error al eliminar Director: " + e.getMessage());
        }
    }

    public List<Director> getListaDirectores() {
        List<Director> lista = new ArrayList<>();
        var linkedList = super.listAll();
        for (int i = 0; i < linkedList.getLength(); i++) {
            lista.add(linkedList.get(i));
        }
        return lista;
    }

    private void persistAll(List<Director> directores) throws Exception {
        String json = new com.google.gson.Gson().toJson(directores);
        saveFile(json);
    }

    private void saveFile(String data) throws Exception {
        File file = new File(base_path + "Director.json");
        if (!file.exists()) {
            file.createNewFile();
        }
        try (FileWriter fw = new FileWriter(file, false)) {
            fw.write(data);
            fw.flush();
        }
    }

    private int obtenerPosicionPorId(int idDirector) {
        var lista = super.listAll();
        for (int i = 0; i < lista.getLength(); i++) {
            if (lista.get(i).getIdDirector() == idDirector) {
                return i;
            }
        }
        return -1;
    }


    public static void main(String[] args) {
        DaoDirector dao = new DaoDirector();


        dao.setObj(new Director());
        dao.getObj().setNombre("Isauro");
        dao.getObj().setAniosCarrera(5);
        System.out.println(dao.save() ? "Director guardado correctamente" : "Error al guardar Director");


        dao.setObj(new Director());
        dao.getObj().setNombre("Pool Ochoa");
        dao.getObj().setAniosCarrera(3);
        System.out.println(dao.save() ? "Director guardado correctamente" : "Error al guardar Director");


        System.out.println("Archivo guardado en: " + new File(base_path + "Director.json").getAbsolutePath());

    }
}
