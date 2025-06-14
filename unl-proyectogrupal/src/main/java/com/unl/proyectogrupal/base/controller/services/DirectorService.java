package com.unl.proyectogrupal.base.controller.services;

<<<<<<< HEAD
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

=======
import java.util.List;
>>>>>>> b333b9b (Subida de cambios en DAOs, Services, Models y LinkedList a rama JOSSIBEL)
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

<<<<<<< HEAD
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
=======
    public void createDirector(@NotEmpty String descripcion, int aniosCarrera) throws Exception {
        Director nuevo = new Director();
        nuevo.setNombre(descripcion);
        nuevo.setAniosCarrera(aniosCarrera);
        dao.setObj(nuevo);
        if (!dao.save())
            throw new Exception("No se pudo guardar los datos del Director");
    }

    public void updateDirector(int id, @NotEmpty String descripcion, int aniosCarrera) throws Exception {
        List<Director> lista = dao.getListaDirectores();
        for (Director a : lista) {
            if (a.getIdDirector() == id) {
                a.setNombre(descripcion);
                a.setAniosCarrera(aniosCarrera);
                dao.setObj(a);
                if (!dao.updatePorId(id))
                    throw new Exception("No se pudo actualizar el Director con ID " + id);
                return;
            }
        }
        throw new Exception("Director no encontrado");
    }

    public void deleteDirector(int id) throws Exception {
        List<Director> lista = dao.getListaDirectores();
        for (Director a : lista) {
            if (a.getIdDirector() == id) {
                dao.delete(a);
                return;
            }
        }
        throw new Exception("Director no encontrado");
    }

    public List<Director> list(Pageable pageable) {
        return dao.getListaDirectores();
    }

    public List<Director> listAll() {
        return dao.getListaDirectores();
>>>>>>> b333b9b (Subida de cambios en DAOs, Services, Models y LinkedList a rama JOSSIBEL)
    }
}
