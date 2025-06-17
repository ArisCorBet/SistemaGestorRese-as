package com.unl.proyectogrupal.base.controller.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Arrays;

import com.unl.proyectogrupal.base.controller.data_struct.list.LinkedList;
import com.unl.proyectogrupal.base.controller.dao.dao_models.DaoActor;
import com.unl.proyectogrupal.base.models.Actor;
import com.unl.proyectogrupal.base.models.Actor;
import com.vaadin.hilla.Endpoint;
import com.vaadin.hilla.Nonnull;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import jakarta.validation.constraints.NotEmpty;
import com.unl.proyectogrupal.base.controller.Utiles;

@Endpoint
@AnonymousAllowed
public class ActorService {
    private DaoActor da;

    public ActorService() {
        da = new DaoActor();
    }

    public void createActor(@NotEmpty String nombre, int anioCarrera) throws Exception {
        da.getObj().setNombre(nombre);
        da.getObj().setAnioCarrera(anioCarrera);
        if (!da.save()) throw new Exception("No se pudo guardar los datos del Actor");
    }

    public void updateActor(int id, @NotEmpty String nombre, int anioCarrera) throws Exception {
        List<Actor> lista = da.getListaActores();
        for (Actor a : lista) {
            if (a.getIdActor() == id) {
                a.setNombre(nombre);
                a.setAnioCarrera(anioCarrera);
                da.setObj(a);
                if (!da.updatePorId(id)) throw new Exception("No se pudo actualizar el Actor");
                return;
            }
        }
        throw new Exception("Actor no encontrado");
    }

    public void deleteActor(int id) throws Exception {
        List<Actor> lista = da.getListaActores();
        for (Actor a : lista) {
            if (a.getIdActor() == id) {
                da.delete(a);
                return;
            }
        }
        throw new Exception("Actor no encontrado");
    }

    public List<Actor> list() {
        return da.getListaActores();
    }

    public List<HashMap<String, String>> order(String attribute, Integer type) throws Exception {
        LinkedList<HashMap<String, String>> lista = da.orderQuickActor(type, attribute);
        return Arrays.asList(lista.toArray());
    }

    public List<HashMap<String, String>> search(String attribute, String text, Integer type) throws Exception {
        LinkedList<HashMap<String, String>> lista = da.search(attribute, text, type);
        return Arrays.asList(lista.toArray());
    }

    public List<HashMap<String, String>> listAll() {
        List<HashMap<String, String>> lista = new ArrayList<>();
        for (Actor a : da.getListaActores()) {
            HashMap<String, String> actorData = new HashMap<>();
            actorData.put("idActor", String.valueOf(a.getIdActor()));
            actorData.put("nombre", a.getNombre());
            actorData.put("anioCarrera", String.valueOf(a.getAnioCarrera()));
            lista.add(actorData);
        }
        return lista;
    }

    public List<HashMap<String, String>> listDirectoCombo() {
        List<HashMap<String, String>> lista = new ArrayList<>();
        for (Actor a : da.getListaActores()) {
            HashMap<String, String> directoData = new HashMap<>();
            directoData.put("value", String.valueOf(a.getIdActor()));
            directoData.put("label", a.getNombre());
            lista.add(directoData);
        }
        return lista;
    }
}