package com.unl.proyectogrupal.base.controller.services;

import com.unl.proyectogrupal.base.controller.dao.dao_models.DaoRepartoPelicula;
import com.unl.proyectogrupal.base.models.RepartoPelicula;
import com.unl.proyectogrupal.base.models.Actor;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.Endpoint;
import jakarta.validation.constraints.NotEmpty;

import java.util.*;
import java.util.stream.Collectors;

@Endpoint
@AnonymousAllowed
public class RepartoPeliculaService {
    private final DaoRepartoPelicula dao;
    private final ActorService actorService;
    private final PeliculaService peliculaService;

    public RepartoPeliculaService() {
        this.dao = new DaoRepartoPelicula();
        this.actorService = new ActorService();
        this.peliculaService = new PeliculaService();
    }

    public void createReparto(int idActor, int idPelicula, @NotEmpty String papelActor) throws Exception {
        // Validar que existan las referencias
        if (!existeActor(idActor)) {
            throw new Exception("El actor con ID " + idActor + " no existe");
        }
        if (!existePelicula(idPelicula)) {
            throw new Exception("La película con ID " + idPelicula + " no existe");
        }

        RepartoPelicula r = new RepartoPelicula();
        r.setIdActor(idActor);
        r.setIdPelicula(idPelicula);
        r.setPapelActor(papelActor);
        
        if (!dao.save(r)) {
            throw new Exception("No se pudo guardar el reparto. Verifique que la combinación actor-película no exista ya.");
        }
    }

    public void updateReparto(int idActor, int idPelicula, @NotEmpty String papelActor) throws Exception {
        // Validar que existan las referencias
        if (!existeActor(idActor)) {
            throw new Exception("El actor con ID " + idActor + " no existe");
        }
        if (!existePelicula(idPelicula)) {
            throw new Exception("La película con ID " + idPelicula + " no existe");
        }

        RepartoPelicula r = new RepartoPelicula();
        r.setIdActor(idActor);
        r.setIdPelicula(idPelicula);
        r.setPapelActor(papelActor);
        
        if (!dao.update(r)) {
            throw new Exception("No se pudo actualizar el reparto. Verifique que exista la relación actor-película.");
        }
    }

    public void deleteReparto(int idActor, int idPelicula) throws Exception {
        if (!dao.delete(idActor, idPelicula)) {
            throw new Exception("No se pudo eliminar el reparto. Verifique que exista la relación actor-película.");
        }
    }

    public List<RepartoPelicula> list() {
        return dao.getListaRepartos();
    }

    public List<Map<String, Object>> listAllWithNames() {
        List<RepartoPelicula> repartos = list();
        List<Actor> actores = actorService.list();
        @SuppressWarnings("unchecked")
        List<Map<String, String>> peliculas = (List<Map<String, String>>) (List<?>) peliculaService.listPelicula();
        
        List<Map<String, Object>> resultado = new ArrayList<>();
        
        for (RepartoPelicula reparto : repartos) {
            Map<String, Object> item = new HashMap<>();
            item.put("idActor", reparto.getIdActor());
            item.put("idPelicula", reparto.getIdPelicula());
            item.put("papelActor", reparto.getPapelActor());
            
            // Obtener nombre del actor
            String nombreActor = actores.stream()
                .filter(a -> a.getIdActor() == reparto.getIdActor())
                .findFirst()
                .map(Actor::getNombre)
                .orElse("ID " + reparto.getIdActor());
            item.put("nombreActor", nombreActor);
            
            // Obtener título de película
            String tituloPelicula = peliculas.stream()
                .filter(p -> Integer.parseInt(p.get("id")) == reparto.getIdPelicula())
                .findFirst()
                .map(p -> p.get("titulo"))
                .orElse("ID " + reparto.getIdPelicula());
            item.put("tituloPelicula", tituloPelicula);
            
            resultado.add(item);
        }
        
        return resultado;
    }

    public List<Map<String, String>> listForCombo() {
        List<Map<String, Object>> repartos = listAllWithNames();
        List<Map<String, String>> comboItems = new ArrayList<>();
        
        for (Map<String, Object> reparto : repartos) {
            Map<String, String> item = new HashMap<>();
            item.put("value", reparto.get("idActor") + "-" + reparto.get("idPelicula"));
            item.put("label", reparto.get("nombreActor") + " en " + reparto.get("tituloPelicula"));
            comboItems.add(item);
        }
        
        return comboItems;
    }

    public List<Map<String, Object>> getByPelicula(int idPelicula) {
        return listAllWithNames().stream()
            .filter(reparto -> reparto.get("idPelicula").equals(idPelicula))
            .collect(Collectors.toList());
    }

    public List<Map<String, Object>> getByActor(int idActor) {
        return listAllWithNames().stream()
            .filter(reparto -> reparto.get("idActor").equals(idActor))
            .collect(Collectors.toList());
    }

    // Métodos auxiliares
    private boolean existeActor(int idActor) {
        try {
            return actorService.list().stream()
                .anyMatch(a -> a.getIdActor() == idActor);
        } catch (Exception e) {
            System.err.println("Error verificando actor: " + e.getMessage());
            return false;
        }
    }

    private boolean existePelicula(int idPelicula) {
        try {
            return peliculaService.listPelicula().stream()
                .anyMatch(p -> Integer.parseInt(String.valueOf(p.get("id"))) == idPelicula);
        } catch (Exception e) {
            System.err.println("Error verificando película: " + e.getMessage());
            return false;
        }
    }

    public void debugRelations() {
        List<RepartoPelicula> repartos = list();
        List<Actor> actores = actorService.list();
        @SuppressWarnings("unchecked")
        List<Map<String, String>> peliculas = (List<Map<String, String>>) (List<?>) peliculaService.listPelicula();

        Set<Integer> actorIds = actores.stream()
            .map(Actor::getIdActor)
            .collect(Collectors.toSet());
        
        Set<Integer> peliculaIds = peliculas.stream()
            .map(p -> Integer.parseInt(p.get("id")))
            .collect(Collectors.toSet());

        System.out.println("=== RELACIONES INCONSISTENTES ===");
        repartos.forEach(r -> {
            if (!actorIds.contains(r.getIdActor())) {
                System.out.println("Actor ID " + r.getIdActor() + " no existe");
            }
            if (!peliculaIds.contains(r.getIdPelicula())) {
                System.out.println("Película ID " + r.getIdPelicula() + " no existe");
            }
        });
    }
}