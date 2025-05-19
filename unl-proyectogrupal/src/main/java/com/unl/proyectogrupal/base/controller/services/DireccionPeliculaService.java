package com.unl.proyectogrupal.base.controller.services;

import java.util.Arrays;
import java.util.List;

import com.unl.proyectogrupal.base.controller.dao.dao_models.DaoDireccionPelicula;
import com.unl.proyectogrupal.base.models.DireccionPelicula;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.Endpoint;
import com.vaadin.hilla.mappedtypes.Pageable;

@Endpoint
@AnonymousAllowed
public class DireccionPeliculaService {
    private DaoDireccionPelicula dao;

    public DireccionPeliculaService() {
        dao = new DaoDireccionPelicula();
    }

    public void createDireccion(int idDirector, int idPelicula) throws Exception {
        dao.getObj().setIdDirector(idDirector);
        dao.getObj().setIdPelicula(idPelicula);
        if (!dao.save())
            throw new Exception("No se pudo guardar la dirección de la película");
    }

    public List<DireccionPelicula> list(Pageable pageable) {
        return Arrays.asList(dao.listAll().toArray());
    }

    public List<DireccionPelicula> listAll() {
        return Arrays.asList(dao.listAll().toArray());
    }
}
