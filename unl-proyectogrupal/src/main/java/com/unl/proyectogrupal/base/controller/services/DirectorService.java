package com.unl.proyectogrupal.base.controller.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

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

    public void createDirector(@NotEmpty String descripcionDirector, @NotEmpty Integer aniosCarrera) throws Exception {
        da.getObj().setDescripcionDirector(descripcionDirector);
        da.getObj().setAniosCarrera(aniosCarrera);
        if (!da.save())
            throw new Exception("No se pudo guardar los datos del Director");
    }

    public List<Director> list(Pageable pageable) {
        return Arrays.asList(da.listAll().toArray());
    }

    public List<Director> listAll() {
        return (List<Director>) Arrays.asList(da.listAll().toArray());
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

    public List<String> listTipoDirector() {
        List<String> lista = new ArrayList<>();
        lista.add("Cine independiente");
        lista.add("Documentales");
        lista.add("Comerciales");
        lista.add("Blockbuster");
        return lista;
    }
}
