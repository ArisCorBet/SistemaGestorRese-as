package com.unl.proyectogrupal.base.controller.services;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.unl.proyectogrupal.base.controller.dao.dao_models.DaoReseniaPelicula;
import com.unl.proyectogrupal.base.models.ReseniaPelicula;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;

import jakarta.validation.constraints.NotEmpty;

@BrowserCallable
@AnonymousAllowed

public class ReseniaPeliculaService {
    private DaoReseniaPelicula dp;

    public ReseniaPeliculaService() {
        this.dp = new DaoReseniaPelicula();
    }

    public void createReseniaPelicula(@NotEmpty String resenia, float puntuacion, Date fechaResenia) throws Exception {
        if (resenia.trim().length() > 0 && puntuacion > 0 && fechaResenia != null) {
            dp.getObj().setResenia(resenia);
            dp.getObj().setPuntuacion(puntuacion);
            dp.getObj().setFechaResenia(fechaResenia);

            if (!dp.save())
                throw new Exception("No se pudo guardar los datos de la ReseniaPelicula");
        }
    }
    public void updateReseniaPelicula(Integer id,@NotEmpty String resenia, float puntuacion, Date fechaResenia) throws Exception {
        if (resenia.trim().length() > 0 && puntuacion > 0 && fechaResenia != null) {
            dp.setObj(dp.listAll().get(id - 1));        
            dp.getObj().setResenia(resenia);
            dp.getObj().setPuntuacion(puntuacion);
            dp.getObj().setFechaResenia(fechaResenia);
            if (!dp.update(id -1))
                throw new Exception("No se pudo guardar los datos de la ReseniaPelicula");
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

    public List<HashMap> listReseniaPelicula(){
        List<HashMap> lista = new ArrayList<>();
        if(!dp.listAll().isEmpty()) {
            ReseniaPelicula [] arreglo = dp.listAll().toArray();
      
            for(int i = 0; i < arreglo.length; i++) {
                HashMap<String, String> aux = new HashMap<>();
                aux.put("id", String.valueOf(arreglo[i].getId().toString()));
                aux.put("resenia", arreglo[i].getResenia());
                aux.put("puntuacion", String.valueOf(arreglo[i].getPuntuacion()));
                aux.put("fechaResenia", String.valueOf(arreglo[i].getFechaResenia()));
                
                lista.add(aux);
            }
        }
        return lista;
    
    }

    
}
