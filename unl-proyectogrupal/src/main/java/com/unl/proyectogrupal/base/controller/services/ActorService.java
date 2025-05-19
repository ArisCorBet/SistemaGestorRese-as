package com.unl.proyectogrupal.base.controller.services;

import java.util.Arrays;
import java.util.List;

import com.unl.proyectogrupal.base.controller.dao.dao_models.DaoActor;
import com.unl.proyectogrupal.base.models.Actor;
import com.vaadin.hilla.Endpoint;
import com.vaadin.hilla.mappedtypes.Pageable;
import com.vaadin.flow.server.auth.AnonymousAllowed;

import jakarta.validation.constraints.NotEmpty;

@Endpoint
@AnonymousAllowed
public class ActorService {
    private DaoActor dao;

    public ActorService() {
        dao = new DaoActor();
    }

    public void createActor(@NotEmpty String descripcion, int anioCarrera) throws Exception {
        dao.getObj().setDescripcion(descripcion);
        dao.getObj().setAnioCarrera(anioCarrera);
        if (!dao.save())
            throw new Exception("No se pudo guardar los datos del actor");
    }

    public List<Actor> list(Pageable pageable) {
        return Arrays.asList(dao.listAll().toArray());
    }

    public List<Actor> listAll() {
        return Arrays.asList(dao.listAll().toArray());
    }
}
