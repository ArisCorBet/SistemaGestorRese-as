package com.unl.proyectogrupal.base.controller.services;

import java.util.List;
import com.unl.proyectogrupal.base.controller.dao.dao_models.DaoRepartoPelicula;
import com.unl.proyectogrupal.base.models.RepartoPelicula;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.Endpoint;
import jakarta.validation.constraints.NotEmpty;

@Endpoint
@AnonymousAllowed
public class RepartoPeliculaService {
    private DaoRepartoPelicula dao = new DaoRepartoPelicula();

    public void createReparto(int idActor, int idPelicula, @NotEmpty String papelActor) throws Exception {
        RepartoPelicula r = new RepartoPelicula();
        r.setIdActor(idActor);
        r.setIdPelicula(idPelicula);
        r.setPapelActor(papelActor);
        dao.setObj(r);
        if (!dao.save())
            throw new Exception("No se pudo guardar el reparto");
    }

    public void updateReparto(int idActor, int idPelicula, @NotEmpty String papelActor) throws Exception {
        RepartoPelicula r = new RepartoPelicula();
        r.setIdActor(idActor);
        r.setIdPelicula(idPelicula);
        r.setPapelActor(papelActor);
        if (!dao.update(r))
            throw new Exception("No se pudo actualizar el reparto");
    }

    public void deleteReparto(int idActor, int idPelicula) throws Exception {
        if (!dao.delete(idActor, idPelicula))
            throw new Exception("No se pudo eliminar el reparto");
    }

    public List<RepartoPelicula> listAll() {
        return dao.getListaRepartos();
    }
}
