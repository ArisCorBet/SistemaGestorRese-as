package com.unl.proyectogrupal.base.controller.dao.dao_models;

import com.unl.proyectogrupal.base.controller.dao.AdapterDao;
import com.unl.proyectogrupal.base.models.Actor;

public class DaoActor extends AdapterDao<Actor> {
    private Actor obj;

    public DaoActor() {
        super(Actor.class);
    }

    public Actor getObj() {
        if (obj == null)
            this.obj = new Actor();
        return this.obj;
    }

    public void setObj(Actor obj) {
        this.obj = obj;
    }

    public Boolean save() {
        try {
            obj.setIdActor(this.listAll().getLength() + 1);
            this.persist(obj);
            return true;
        } catch (Exception e) {
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

    public static void main(String[] args) {
        DaoActor dao = new DaoActor();

        // Primer actor
        dao.getObj().setDescripcionActor("Actor dramático");
        dao.getObj().setAnioCarrera(2010);

        if (dao.save())
            System.out.println("GUARDADO");
        else
            System.out.println("Hubo un error");

        // Segundo actor
        dao.setObj(null);
        dao.getObj().setDescripcionActor("Actor de comedia");
        dao.getObj().setAnioCarrera(2015);

        if (dao.save())
            System.out.println("GUARDADO");
        else
            System.out.println("Hubo un error");
    }
}
