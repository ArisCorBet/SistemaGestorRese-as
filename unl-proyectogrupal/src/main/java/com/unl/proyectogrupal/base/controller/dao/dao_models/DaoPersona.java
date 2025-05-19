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
            obj.setId(listAll().getLength()+1);
            this.persist(obj);
            return true;
        } catch (Exception e) {
            //LOG DE ERROR
            e.printStackTrace();
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


    public static void main(String[] args) {
        DaoPersona da = new DaoPersona();
        da.getObj().setId(da.listAll().getLength()+1);
        da.getObj().setNombre("Luis Miguel");
        da.getObj().setApellido("Sanches Torres");
        da.getObj().setFechaNacimiento("15/05/2000");
        if (da.save()) {
            System.out.println("Guardado");
        } else {
            System.out.println("Error al guardar");
        }
    }
}
