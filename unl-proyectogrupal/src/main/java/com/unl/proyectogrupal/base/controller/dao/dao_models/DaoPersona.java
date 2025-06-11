package com.unl.proyectogrupal.base.controller.dao.dao_models;

import java.util.Calendar;
import java.util.Date;

import com.unl.proyectogrupal.base.controller.dao.AdapterDao;
import com.unl.proyectogrupal.base.models.Persona;

public class DaoPersona extends AdapterDao<Persona> {
    private Persona obj;

    public DaoPersona(){
        super(Persona.class);
    }

    public Persona getObj() {
        if (obj == null){
            this.obj = new Persona();
        } 
        return this.obj;
    }

     public void setObj(Persona obj) {
        this.obj = obj;
    }

    public Boolean save() {
        try {
            obj.setId_persona(listAll().getLength()+1);
            this.persist(obj);
            return true;
        } catch (Exception e) {
            //LOG DE ERROR
            System.out.println(e);
            return false;
            // TODO: handle exception
        }
    }

    public Boolean update(Integer pos) {
        try {
            this.update(obj, pos);
            return true;
        } catch (Exception e) {
            
            return false;
            // TODO: handle exception
        }
    }


   
}
