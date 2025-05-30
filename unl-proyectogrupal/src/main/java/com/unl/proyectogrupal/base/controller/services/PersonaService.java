package com.unl.proyectogrupal.base.controller.services;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.unl.proyectogrupal.base.controller.dao.dao_models.DaoPersona;
import com.unl.proyectogrupal.base.models.Persona;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;
import jakarta.validation.constraints.NotEmpty;

@BrowserCallable
@AnonymousAllowed


public class PersonaService {
    private DaoPersona dp;

    public PersonaService() {
    this.dp = new DaoPersona();
    }

    public void create(@NotEmpty String nombre,@NotEmpty String apellido,  @NotEmpty String fechaNacimiento, @NotEmpty String telefono) throws Exception{
        if (nombre.trim().length() > 0 && apellido.trim().length() > 0 && fechaNacimiento.toString().length() > 0 && telefono.trim().length() > 0){
            dp.getObj().setNombre(nombre);
            dp.getObj().setApellido(apellido);
            dp.getObj().setFechaNacimiento(fechaNacimiento);
            dp.getObj().setTelefono(telefono);
            if(!dp.save())
                throw new Exception("No se pudo guardar los datos de la persona");
            } 
    }

    public void update(Integer id,@NotEmpty String nombre,@NotEmpty String apellido, @ NotEmpty String fechaNacimiento , @NotEmpty String telefono ) throws Exception {
        if (nombre.trim().length() > 0 && apellido.trim().length() > 0 && fechaNacimiento.toString().length() > 0 && telefono.trim().length() > 0 ) {
            dp.setObj(dp.listAll().get(id - 1));        
            dp.getObj().setNombre(nombre);
            dp.getObj().setApellido(apellido);
            dp.getObj().setFechaNacimiento(fechaNacimiento);
            dp.getObj().setTelefono(telefono);
            
            if (!dp.update(id -1))
                throw new Exception("No se pudo guardar los datos de la persona");
        }
    }

    public List<HashMap> listPersona(){
        List<HashMap> lista = new ArrayList<>();
        if(!dp.listAll().isEmpty()) {
            Persona [] arreglo = dp.listAll().toArray();
            for(int i = 0; i < arreglo.length; i++) {
                HashMap<String, String> aux = new HashMap<>();
                aux.put("id", String.valueOf(arreglo[i].getId().toString()));
                aux.put("nombre", arreglo[i].getNombre());
                aux.put("apellido", arreglo[i].getApellido());
                aux.put("fechaNacimiento", String.valueOf(arreglo[i].getFechaNacimiento()));
                aux.put("telefono", arreglo[i].getTelefono());
                lista.add(aux);

        }
    }
        return lista;
    
    }




}



   



