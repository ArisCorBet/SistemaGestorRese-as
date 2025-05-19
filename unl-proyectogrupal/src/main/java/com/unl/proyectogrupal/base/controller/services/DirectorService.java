package com.unl.proyectogrupal.base.controller.services;

import java.util.Arrays;
import java.util.List;

import com.unl.proyectogrupal.base.controller.dao.dao_models.DaoDirector;
import com.unl.proyectogrupal.base.models.Director;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.Endpoint;
import com.vaadin.hilla.mappedtypes.Pageable;

import jakarta.validation.constraints.NotEmpty;

@Endpoint
@AnonymousAllowed
public class DirectorService {
    private DaoDirector dao;

    public DirectorService() {
        dao = new DaoDirector();
    }

    public void createDirector(@NotEmpty String descripcion, int aniosCarrera) throws Exception {
        dao.getObj().setDescripcion(descripcion);
        dao.getObj().setAniosCarrera(aniosCarrera);
        if (!dao.save())
            throw new Exception("No se pudo guardar los datos del director");
    }

    public List<Director> list(Pageable pageable) {
        return Arrays.asList(dao.listAll().toArray());
    }

    public List<Director> listAll() {
        return Arrays.asList(dao.listAll().toArray());
    }
}
