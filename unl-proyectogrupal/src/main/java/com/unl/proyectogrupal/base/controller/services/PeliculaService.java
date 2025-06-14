package com.unl.proyectogrupal.base.controller.services;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.springframework.data.domain.Pageable;

import com.unl.proyectogrupal.base.controller.dao.dao_models.DaoGenero;
import com.unl.proyectogrupal.base.controller.dao.dao_models.DaoPelicula;
import com.unl.proyectogrupal.base.models.Genero;
import com.unl.proyectogrupal.base.models.Pelicula;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;

import jakarta.validation.constraints.NotEmpty;

@BrowserCallable
@AnonymousAllowed

public class PeliculaService {
    private DaoPelicula dp;

    public PeliculaService() {
        this.dp = new DaoPelicula();
    }

    public void createPelicula(@NotEmpty String titulo, @NotEmpty String sinopsis, Integer duracion,@NotEmpty String trailer, Date fechaEsteno, Integer idGenero ) throws Exception {
        if (titulo.trim().length() > 0 && sinopsis.trim().length() > 0 && trailer.trim().length() > 0 && duracion > 0 && idGenero > 0) {
            dp.getObj().setTitulo(titulo);
            dp.getObj().setSinopsis(sinopsis);
            dp.getObj().setDuracion(duracion);
            dp.getObj().setTrailer(trailer);
            dp.getObj().setFechaEstreno(fechaEsteno);
            dp.getObj().setIdGenero(idGenero);

            if (!dp.save())
                throw new Exception("No se pudo guardar los datos de la Pelicula");
        }
    }
    public void updatePelicula(Integer id,@NotEmpty String titulo,@NotEmpty String sinopsis, Integer duracion,@NotEmpty String trailer, Date fechaEsteno, Integer idGenero ) throws Exception {
        if (titulo.trim().length() > 0 && sinopsis.trim().length() > 0 && trailer.trim().length() > 0 && duracion > 0 && idGenero > 0) {
            dp.setObj(dp.listAll().get(id - 1));            
            dp.getObj().setTitulo(titulo);
            dp.getObj().setSinopsis(sinopsis);
            dp.getObj().setDuracion(duracion);
            dp.getObj().setTrailer(trailer);
            dp.getObj().setFechaEstreno(fechaEsteno);
            dp.getObj().setIdGenero(idGenero);
    
            if (!dp.update(id -1))
                throw new Exception("No se pudo guardar los datos de la Pelicula");
        }
    }


    public List<HashMap> listPelicula(){
        List<HashMap> lista = new ArrayList<>();
        if(!dp.listAll().isEmpty()) {
            Pelicula [] arreglo = dp.listAll().toArray();
      
            for(int i = 0; i < arreglo.length; i++) {
                HashMap<String, String> aux = new HashMap<>();
                aux.put("id", String.valueOf(arreglo[i].getId().toString()));
                aux.put("titulo", arreglo[i].getTitulo());
                aux.put("sinopsis", arreglo[i].getSinopsis());
                aux.put("duracion", String.valueOf(arreglo[i].getDuracion()));
                aux.put("trailer", arreglo[i].getTrailer());
                aux.put("fechaEstreno", String.valueOf(arreglo[i].getFechaEstreno()));
                aux.put("idGenero", String.valueOf(arreglo[i].getIdGenero()));
                lista.add(aux);
            }
        }
        return lista;
    }

    public List<HashMap> listaGeneroCombo(){
        List<HashMap> lista = new ArrayList<>();
        DaoGenero dg = new DaoGenero();
        if(!dg.listAll().isEmpty()) {
            Genero [] arreglo = dg.listAll().toArray();
            for(int i = 0; i < arreglo.length; i++){
                HashMap<String, String> aux = new HashMap<>();
                aux.put("value", arreglo[i].getIdGenero().toString()); 
                aux.put("label", arreglo[i].getNombre());  
                lista.add(aux);    
            }
        }
        return lista;
    }
    
    }

    

