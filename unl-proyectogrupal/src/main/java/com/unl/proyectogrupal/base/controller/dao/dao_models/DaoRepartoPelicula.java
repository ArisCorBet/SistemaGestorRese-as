package com.unl.proyectogrupal.base.controller.dao.dao_models;

import com.unl.proyectogrupal.base.controller.dao.AdapterDao;
import com.unl.proyectogrupal.base.models.RepartoPelicula;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class DaoRepartoPelicula extends AdapterDao<RepartoPelicula> {
    private RepartoPelicula obj;

    public DaoRepartoPelicula() {
        super(RepartoPelicula.class);
        ensureSharedFolderExists();
    }

    private void ensureSharedFolderExists() {
        File folder = new File(base_path);
        if (!folder.exists()) {
            folder.mkdirs();
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

    public Boolean save() {
        try {
            List<RepartoPelicula> lista = getListaRepartos();

            // Evitar duplicados
            boolean existe = lista.stream()
                    .anyMatch(r -> r.getIdActor() == obj.getIdActor() &&
                                   r.getIdPelicula() == obj.getIdPelicula());
            if (existe) {
                System.err.println("Ya existe un reparto con ese actor y película");
                return false;
            }

            lista.add(obj);
            persistAll(lista);
            return true;
        } catch (Exception e) {
            System.err.println("Error al guardar RepartoPelicula: " + e.getMessage());
            return false;
        }
    }

    public Boolean update(RepartoPelicula nuevo) {
        try {
            List<RepartoPelicula> lista = getListaRepartos();
            for (int i = 0; i < lista.size(); i++) {
                RepartoPelicula r = lista.get(i);
                if (r.getIdActor() == nuevo.getIdActor() && r.getIdPelicula() == nuevo.getIdPelicula()) {
                    lista.set(i, nuevo);
                    persistAll(lista);
                    return true;
                }
            }
            System.err.println("Reparto no encontrado para actualizar");
            return false;
        } catch (Exception e) {
            System.err.println("Error al actualizar RepartoPelicula: " + e.getMessage());
            return false;
        }
    }

    public Boolean delete(int idActor, int idPelicula) {
        try {
            List<RepartoPelicula> lista = getListaRepartos();
            boolean removed = lista.removeIf(r -> r.getIdActor() == idActor && r.getIdPelicula() == idPelicula);
            if (removed) {
                persistAll(lista);
                return true;
            }
            System.err.println("Reparto no encontrado para eliminar");
            return false;
        } catch (Exception e) {
            System.err.println("Error al eliminar RepartoPelicula: " + e.getMessage());
            return false;
        }
    }

    public List<RepartoPelicula> getListaRepartos() {
        List<RepartoPelicula> lista = new ArrayList<>();
        var linkedList = super.listAll();
        for (int i = 0; i < linkedList.getLength(); i++) {
            lista.add(linkedList.get(i));
        }
        return lista;
    }

    private void persistAll(List<RepartoPelicula> repartos) throws Exception {
        String json = new com.google.gson.Gson().toJson(repartos);
        saveFile(json);
    }

    private void saveFile(String data) throws Exception {
        File file = new File(base_path + "RepartoPelicula.json");
        if (!file.exists()) {
            file.createNewFile();
        }
        try (FileWriter fw = new FileWriter(file, false)) {
            fw.write(data);
            fw.flush();
        }
    }


    public static void main(String[] args) {
        DaoRepartoPelicula dao = new DaoRepartoPelicula();

        RepartoPelicula r1 = new RepartoPelicula();
        r1.setIdActor(1);
        r1.setIdPelicula(101);
        r1.setPapelActor("Protagonista");
        dao.setObj(r1);
        System.out.println(dao.save() ? "Reparto guardado exitosamente." : "Error al guardar reparto.");

        RepartoPelicula r2 = new RepartoPelicula();
        r2.setIdActor(2);
        r2.setIdPelicula(101);
        r2.setPapelActor("Villano");
        dao.setObj(r2);
        System.out.println(dao.save() ? "Reparto guardado exitosamente." : "Error al guardar reparto.");
    }
}
