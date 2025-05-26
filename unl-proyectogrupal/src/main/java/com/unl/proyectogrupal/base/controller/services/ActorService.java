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

    public void createActor(@NotEmpty String descripcionActor, @NotEmpty Integer anioCarrera) throws Exception {
        da.getObj().setDescripcionActor(descripcionActor);
        da.getObj().setAnioCarrera(anioCarrera);
        if (!da.save())
            throw new Exception("No se pudo guardar los datos de Actor");
    }

    public List<Actor> list(Pageable pageable) {
        return Arrays.asList(da.listAll().toArray());
    }

    public List<Actor> listAll() {
        return (List<Actor>) Arrays.asList(da.listAll().toArray());
    }

    public List<String> listCountry() {
        List<String> nacionalidades = new ArrayList<>();
        String[] countryCodes = Locale.getISOCountries();
        for (String countryCode : countryCodes) {
            Locale locale = new Locale("", countryCode);
            nacionalidades.add(locale.getDisplayCountry());
        }
        return nacionalidades;
    }

    public List<String> listRolesActorEnum() {
        List<String> lista = new ArrayList<>();
        lista.add("Principal");
        lista.add("Secundario");
        lista.add("Extra");
        return lista;
    }
}
