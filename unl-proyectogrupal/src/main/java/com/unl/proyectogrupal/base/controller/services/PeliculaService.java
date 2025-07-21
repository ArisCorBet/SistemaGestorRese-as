package com.unl.proyectogrupal.base.controller.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.unl.proyectogrupal.base.controller.dao.dao_models.DaoGenero;
import com.unl.proyectogrupal.base.controller.dao.dao_models.DaoPelicula;
import com.unl.proyectogrupal.base.controller.data_struct.list.LinkedList;
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

    public void createPelicula(@NotEmpty String titulo, @NotEmpty String sinopsis, Integer duracion,
                                @NotEmpty String trailer, Date fechaEstreno, Integer idGenero) throws Exception {
        if (titulo.trim().length() > 0 && sinopsis.trim().length() > 0 && trailer.trim().length() > 0 &&
                duracion != null && duracion > 0 && idGenero != null && idGenero > 0) {

            Pelicula pelicula = new Pelicula();
            pelicula.setTitulo(titulo);
            pelicula.setSinopsis(sinopsis);
            pelicula.setDuracion(duracion);
            pelicula.setTrailer(trailer);
            pelicula.setFechaEstreno(fechaEstreno);

            LinkedList<Integer> generoList = new LinkedList<>();
            generoList.add(idGenero);
            pelicula.setIdGenero(generoList);

            dp.setObj(pelicula);
            if (!dp.save()) {
                throw new Exception("No se pudo guardar los datos de la Película.");
            }
        }
    }

    public void updatePelicula(Integer id, @NotEmpty String titulo, @NotEmpty String sinopsis, Integer duracion,
                                @NotEmpty String trailer, Date fechaEstreno, Integer idGenero) throws Exception {
        if (titulo.trim().length() > 0 && sinopsis.trim().length() > 0 && trailer.trim().length() > 0 &&
                duracion != null && duracion > 0 && idGenero != null && idGenero > 0) {

            Pelicula pelicula = dp.listAll().get(id - 1);
            pelicula.setTitulo(titulo);
            pelicula.setSinopsis(sinopsis);
            pelicula.setDuracion(duracion);
            pelicula.setTrailer(trailer);
            pelicula.setFechaEstreno(fechaEstreno);

            LinkedList<Integer> generoList = new LinkedList<>();
            generoList.add(idGenero);
            pelicula.setIdGenero(generoList);

            dp.setObj(pelicula);
            if (!dp.updatePorId(id)) {
                throw new Exception("No se pudo actualizar la Película.");
            }
        }
    }

    public List<HashMap> listPelicula() {
        List<HashMap> lista = new ArrayList<>();
        if (!dp.listAll().isEmpty()) {
            Pelicula[] arreglo = dp.listAll().toArray();

            for (Pelicula p : arreglo) {
                HashMap<String, String> aux = new HashMap<>();
                aux.put("id", String.valueOf(p.getId()));
                aux.put("titulo", p.getTitulo());
                aux.put("sinopsis", p.getSinopsis());
                aux.put("duracion", String.valueOf(p.getDuracion()));
                aux.put("trailer", p.getTrailer());
                aux.put("fechaEstreno", String.valueOf(p.getFechaEstreno()));
                aux.put("idGenero", p.getIdGenero() != null ? p.getIdGenero().toString() : "");
                lista.add(aux);
            }
        }
        return lista;
    }

    public List<HashMap> listaGeneroCombo() {
        List<HashMap> lista = new ArrayList<>();
        DaoGenero dg = new DaoGenero();
        if (!dg.listAll().isEmpty()) {
            Genero[] arreglo = dg.listAll().toArray();
            for (Genero g : arreglo) {
                HashMap<String, String> aux = new HashMap<>();
                aux.put("value", g.getIdGenero().toString());
                aux.put("label", g.getNombre());
                lista.add(aux);
            }
        }
        return lista;
    }

    public List<HashMap> listAll() throws Exception {
        return Arrays.asList(dp.all().toArray());
    }

    public List<HashMap> search(String attribute, String text, Integer type) throws Exception {
        LinkedList<HashMap<String, Object>> lista = dp.search(attribute, text, type);
        return lista.isEmpty() ? new ArrayList<>() : Arrays.asList(lista.toArray());
    }

    public List<HashMap> order(String attribute, Integer type) throws Exception {
        return Arrays.asList(dp.orderByPelicula(type, attribute).toArray());
    }

    public LinkedList<Pelicula> getPeliculas() {
        return dp.listAll();
    }
}
