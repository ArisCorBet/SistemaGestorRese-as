package com.unl.proyectogrupal.base.controller.services;


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
    private DaoDirector da;

    public DirectorService() {
        da = new DaoDirector();
    }


    public void createDirector(@NotEmpty String descripcion, int aniosCarrera) throws Exception {
        Director nuevo = new Director();
        nuevo.setNombre(descripcion);
        nuevo.setAniosCarrera(aniosCarrera);
        da.setObj(nuevo);
        if (!da.save())
            throw new Exception("No se pudo guardar los datos del Director");
    }

    public void updateDirector(int id, @NotEmpty String descripcion, int aniosCarrera) throws Exception {
        List<Director> lista = da.getListaDirectores();
        for (Director a : lista) {
            if (a.getIdDirector() == id) {
                a.setNombre(descripcion);
                a.setAniosCarrera(aniosCarrera);
                da.setObj(a);
                if (!da.updatePorId(id))
                    throw new Exception("No se pudo actualizar el Director con ID " + id);
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

    public List<Director> list(Pageable pageable) {
        return da.getListaDirectores();
    }

    public List<Director> listAll() {
        return da.getListaDirectores();
    }

}
