package com.unl.proyectogrupal.base.controller.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.unl.proyectogrupal.base.controller.dao.dao_models.DaoGenero;
import com.unl.proyectogrupal.base.models.Genero;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;

import jakarta.validation.constraints.NotEmpty;

@BrowserCallable
@AnonymousAllowed

public class GeneroService {
    private DaoGenero dg;

    public GeneroService() {
        this.dg = new DaoGenero();
    }

    public void createGenero(@NotEmpty String Nombre) throws Exception {
        if (Nombre.trim().length() > 0 ) {
            dg.getObj().setNombre(Nombre);

            if (!dg.save())
                throw new Exception("No se pudo guardar los datos de la Genero");
        }
    }
    public void updateGenero(Integer idGenero,@NotEmpty String Nombre ) throws Exception {
        if (Nombre.trim().length() > 0 ) {
            dg.getObj().setIdGenero(idGenero);
            dg.getObj().setNombre(Nombre);   
            if (!dg.update(idGenero -1))
                throw new Exception("No se pudo guardar los datos de la Genero");
        }
    }

    
    /*public List<HashMap> listaAlbumGenero() {
        List<HashMap> lista = new ArrayList<>();
        DaoGenero da = new DaoGenero();
        if(!da.listAll().isEmpty()) {
            Genero [] arreglo = da.listAll().toArray();
            for(int i = 0; i < arreglo.length; i++) {
                HashMap<String, String> aux = new HashMap<>();
                aux.put("value", arreglo[i].getId().toString(i)); 
                aux.put("label", arreglo[i].getNombre()); 
                lista.add(aux);  
            }
        }
        return lista;
    }*/

    public List<HashMap> listGenero(){
        List<HashMap> lista = new ArrayList<>();
        if(!dg.listAll().isEmpty()) {
            Genero [] arreglo = dg.listAll().toArray();
      
            for(int i = 0; i < arreglo.length; i++) {
                HashMap<String, String> aux = new HashMap<>();
                aux.put("idGenero", String.valueOf(arreglo[i].getIdGenero().toString()));
                aux.put("Nombre", String.valueOf(arreglo[i].getNombre()));
                lista.add(aux);
            }
        }
        return lista;
    
    }

    
}
