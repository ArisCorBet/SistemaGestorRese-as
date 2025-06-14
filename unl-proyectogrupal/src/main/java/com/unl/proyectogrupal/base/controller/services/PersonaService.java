package com.unl.proyectogrupal.base.controller.services;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.unl.proyectogrupal.base.models.Cuenta;
import com.unl.proyectogrupal.base.models.Persona;
import com.unl.proyectogrupal.base.models.enums.Estado_cuenta;
import com.unl.proyectogrupal.base.models.enums.Genero;
import com.unl.proyectogrupal.base.models.enums.Tipo_cuenta;
import com.unl.proyectogrupal.base.controller.dao.dao_models.DaoPersona;
import com.unl.proyectogrupal.base.controller.dao.dao_models.DaoCuenta;


import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;

import io.micrometer.common.lang.NonNull;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

@BrowserCallable
@AnonymousAllowed
public class PersonaService {
    private DaoPersona db;

    public PersonaService() {
        db = new DaoPersona();
    }

    public void save(@NotEmpty String nombre, @NotEmpty @Email String correo, @NotEmpty String contrasenia, @NotEmpty String telefono, Date fecha_nacimiento, Estado_cuenta estado_cuenta) throws Exception {
        if(nombre.trim().length() > 0 && correo.trim().length() > 0 && contrasenia.trim().length() > 0 && telefono.trim().length() > 0 && fecha_nacimiento != null) {
            db.getObj().setNombre(nombre);
            db.getObj().setFecha_nacimiento(fecha_nacimiento);
            db.getObj().setTelefono(telefono);
            if(!db.save())
                throw new  Exception("No se pudo guardar los datos de la persona");
            else {
                DaoCuenta dc = new DaoCuenta();
                dc.getObj().setContrasenia(contrasenia);
                dc.getObj().setCorreo(correo);
                dc.getObj().setEstado_cuenta(estado_cuenta.Activo);
                dc.getObj().setId_persona(db.getObj().getId());
                if(!dc.save())
                    throw new  Exception("No se pudo guardar los datos de la cuenta");
            }
        } else {
            throw new  Exception("No se pudo guardar los datos de persona");
        }
    }

    public List<HashMap> listaPersonas() {
        List<HashMap> lista = new ArrayList<>();
        if (!db.listAll().isEmpty()) {
            Persona[] arreglo = db.listAll().toArray();
            for (int i = 0; i < arreglo.length; i++) {

                try {
                    HashMap<String, String> aux = new HashMap<>();
                    aux.put("id", arreglo[i].getId().toString(i));
                    aux.put("nombre", arreglo[i].getNombre());
                    aux.put("fecha_nacimiento", arreglo[i].getFecha_nacimiento().toString());
                    aux.put("telefono", arreglo[i].getTelefono());
                    Cuenta c = new DaoCuenta().listAll().get(arreglo[i].getId() - 1);// ya que todos deben tener cuenta asi que
                    // cuando se guardar, se guardaran tanto
                    // cuenta como persona con el mismo ID
                    aux.put("correo", c.getCorreo());

                    lista.add(aux);
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
        }
        return lista;
    }
}