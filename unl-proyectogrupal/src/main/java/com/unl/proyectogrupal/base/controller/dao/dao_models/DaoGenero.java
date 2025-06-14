package com.unl.proyectogrupal.base.controller.dao.dao_models;

import com.unl.proyectogrupal.base.models.Genero;
import com.unl.proyectogrupal.base.controller.dao.AdapterDao;

public class DaoGenero extends AdapterDao<Genero> {
    private Genero obj;

    public DaoGenero() {
        super(Genero.class);
        // TODO Auto-generated constructor stub
    }

    public Genero getObj() {
        if (obj == null)
            this.obj = new Genero();
        return this.obj;
    }

    public void setObj(Genero obj) {
        this.obj = obj;
    }

    public Boolean save() {
        try {
            obj.setIdGenero(listAll().getLength()+1);
            this.persist(obj);
            return true;
        } catch (Exception e) {
            //TODO
            return false;
            // TODO: handle exception
        }
    }

    public Boolean update(Integer pos) {
        try {
            this.update(obj, pos);
            return true;
        } catch (Exception e) {
            //TODO
            return false;
            // TODO: handle exception
        }
    }

    public static void main(String[] args) {
        DaoGenero dg = new DaoGenero();
        dg.getObj().setIdGenero(dg.listAll().getLength() + 1);
        dg.getObj().setNombre("Comedia");
        if (dg.save())
            System.out.println("GUARDADO");
        else
            System.out.println("Hubo un error");
    }

}

