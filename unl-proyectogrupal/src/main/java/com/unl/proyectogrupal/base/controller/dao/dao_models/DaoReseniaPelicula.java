package com.unl.proyectogrupal.base.controller.dao.dao_models;

import com.unl.proyectogrupal.base.controller.dao.AdapterDao;
import com.unl.proyectogrupal.base.models.ReseniaPelicula;

public class DaoReseniaPelicula extends AdapterDao<ReseniaPelicula>{
    private ReseniaPelicula obj;

    public DaoReseniaPelicula(){
        super(ReseniaPelicula.class);
    }

    public ReseniaPelicula getObj() {
        if (obj == null){
            this.obj = new ReseniaPelicula();
        } 
        return this.obj;
    }

     public void setObj(ReseniaPelicula obj) {
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
        }
    }

    public Boolean update(Integer pos) {
        try {
            this.update(obj, pos);
            return true;
        } catch (Exception e) {
            
            return false;
        }
    }

 
}