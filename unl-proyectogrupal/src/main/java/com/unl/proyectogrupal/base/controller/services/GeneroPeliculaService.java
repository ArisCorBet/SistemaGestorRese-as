package com.unl.proyectogrupal.base.controller.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.unl.proyectogrupal.base.controller.dao.dao_models.DaoGeneroPelicula;
import com.unl.proyectogrupal.base.models.GeneroPelicula;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;

import jakarta.validation.constraints.NotEmpty;

@BrowserCallable
@AnonymousAllowed

public class GeneroPeliculaService {
    private DaoGeneroPelicula dp;

    public GeneroPeliculaService() {
        this.dp = new DaoGeneroPelicula();
    }

    public void createGeneroPelicula(@NotEmpty Integer idPelicula) throws Exception {
        if (idPelicula.trim().length() > 0 ) {
            dp.getObj().setIdPelicula(null);

            if (!dp.save())
                throw new Exception("No se pudo guardar los datos de la Genero");
        }
    }
    public void updateGeneroPelicula(Integer idGenero,@NotEmpty String Nombre ) throws Exception {
        if (Nombre.trim().length() > 0 ) {
            dp.getObj().setIdGenero(idGenero);
            dp.getObj().setIdPelicula(idPelicula); 
            if (!dp.update(idGenero -1))
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

    public List<HashMap> listGeneroPelicula(){
        List<HashMap> lista = new ArrayList<>();
        if(!dp.listAll().isEmpty()) {
            GeneroPelicula [] arreglo = dp.listAll().toArray();
      
            for(int i = 0; i < arreglo.length; i++) {
                HashMap<String, String> aux = new HashMap<>();
                aux.put("idGenero", String.valueOf(arreglo[i].getIdGenero().toString()));
                aux.put("idPelicula", String.valueOf(arreglo[i].getIdPelicula()));
                lista.add(aux);
            }
        }
        return lista;
    
    }

    
}

