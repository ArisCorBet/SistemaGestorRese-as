package com.unl.proyectogrupal.base.controller.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import com.unl.proyectogrupal.base.controller.dao.dao_models.DaoActor;
import com.unl.proyectogrupal.base.models.Actor;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.Endpoint;
import com.vaadin.hilla.mappedtypes.Pageable;

import jakarta.validation.constraints.NotEmpty;

@Endpoint // Endpoint que expone los métodos al frontend
@AnonymousAllowed // Hace que este servicio sea accesible sin autenticación
public class ActorService {
    private DaoActor da;

    public ActorService() {
        da = new DaoActor();
    }


    public void createActor(@NotEmpty String descripcion, int anioCarrera) throws Exception {
        da.getObj().setNombre(descripcion);
        da.getObj().setAnioCarrera(anioCarrera);
        if (!da.save())
            throw new Exception("No se pudo guardar los datos del actor");

    }

    public void updateActor(int id, @NotEmpty String descripcion, int anioCarrera) throws Exception {
        List<Actor> lista = da.getListaActores();
        for (Actor a : lista) {
            if (a.getIdActor() == id) {
                a.setNombre(descripcion);
                a.setAnioCarrera(anioCarrera);
                da.setObj(a);
                if (!da.updatePorId(id))
                    throw new Exception("No se pudo actualizar el actor");
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

    public List<Actor> list(Pageable pageable) {

        return da.getListaActores();
    }

    public List<Actor> listAll() {
        return da.getListaActores();

    }
}
