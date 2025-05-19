package com.unl.proyectogrupal.base.controller.services;

import java.util.Arrays;
import java.util.List;

import com.unl.proyectogrupal.base.controller.dao.dao_models.DaoRepartoPelicula;
import com.unl.proyectogrupal.base.models.RepartoPelicula;
import com.vaadin.hilla.Endpoint;
import com.vaadin.hilla.mappedtypes.Pageable;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Endpoint
@AnonymousAllowed
public class RepartoPeliculaService {
    private DaoRepartoPelicula dao;

    public RepartoPeliculaService() {
        dao = new DaoRepartoPelicula();
    }

    public void createReparto(int idActor, int idPelicula, String papelActor) throws Exception {
        dao.getObj().setIdActor(idActor);
        dao.getObj().setIdPelicula(idPelicula);
        dao.getObj().setPapelActor(papelActor);
        if (!dao.save())
            throw new Exception("No se pudo guardar el reparto de la película");
    }

    public List<RepartoPelicula> list(Pageable pageable) {
        return Arrays.asList(dao.listAll().toArray());
    }

    public List<RepartoPelicula> listAll() {
        return Arrays.asList(dao.listAll().toArray());
    }
}
