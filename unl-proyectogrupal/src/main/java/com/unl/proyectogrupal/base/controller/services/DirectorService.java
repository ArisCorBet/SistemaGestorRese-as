package com.unl.proyectogrupal.base.controller.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Arrays;

import com.unl.proyectogrupal.base.controller.data_struct.list.LinkedList;
import com.unl.proyectogrupal.base.controller.dao.dao_models.DaoDirector;
import com.unl.proyectogrupal.base.models.Director;
import com.vaadin.hilla.Endpoint;
import com.vaadin.hilla.Nonnull;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import jakarta.validation.constraints.NotEmpty;
import com.unl.proyectogrupal.base.controller.Utiles;

@Endpoint
@AnonymousAllowed
public class DirectorService {
    private DaoDirector da;

    public DirectorService() {
        da = new DaoDirector();
    }

    public void createDirector(@NotEmpty String nombre, int anioCarrera) throws Exception {
        da.getObj().setNombre(nombre);
        da.getObj().setAniosCarrera(anioCarrera);
        if (!da.save()) throw new Exception("No se pudo guardar los datos del Director");
    }

    public void updateDirector(int id, @NotEmpty String nombre, int anioCarrera) throws Exception {
        List<Director> lista = da.getListaDirectores();
        for (Director a : lista) {
            if (a.getIdDirector() == id) {
                a.setNombre(nombre);
                a.setAniosCarrera(anioCarrera);
                da.setObj(a);
                if (!da.updatePorId(id)) throw new Exception("No se pudo actualizar el Director");
                return;
            }
        }
        throw new Exception("Director no encontrado");
    }

    public void deleteDirector(int id) throws Exception {
        List<Director> lista = da.getListaDirectores();
        for (Director a : lista) {
            if (a.getIdDirector() == id) {
                da.delete(a);
                return;
            }
        }
        throw new Exception("Director no encontrado");
    }

    public List<Director> list() {
        return da.getListaDirectores();
    }

    public List<HashMap<String, String>> order(String attribute, Integer type) throws Exception {
        LinkedList<HashMap<String, String>> lista = da.orderQuickDirector(type, attribute);
        return Arrays.asList(lista.toArray());
    }

    public List<HashMap<String, String>> search(String attribute, String text, Integer type) throws Exception {
        LinkedList<HashMap<String, String>> lista = da.search(attribute, text, type);
        return Arrays.asList(lista.toArray());
    }

    public List<HashMap<String, String>> listAll() {
        List<HashMap<String, String>> lista = new ArrayList<>();
        for (Director a : da.getListaDirectores()) {
            HashMap<String, String> directorData = new HashMap<>();
            directorData.put("idDirector", String.valueOf(a.getIdDirector()));
            directorData.put("nombre", a.getNombre());
            directorData.put("aniosCarrera", String.valueOf(a.getAniosCarrera()));
            lista.add(directorData);
        }
        return lista;
    }

    public List<HashMap<String, String>> listDirectoCombo() {
        List<HashMap<String, String>> lista = new ArrayList<>();
        for (Director a : da.getListaDirectores()) {
            HashMap<String, String> directoData = new HashMap<>();
            directoData.put("value", String.valueOf(a.getIdDirector()));
            directoData.put("label", a.getNombre());
            lista.add(directoData);
        }
        return lista;
    }
}