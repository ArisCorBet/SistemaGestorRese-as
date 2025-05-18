package com.unl.proyectogrupal.base.controller.dao.dao_models;

import com.unl.proyectogrupal.base.controller.dao.AdapterDao;
import com.unl.proyectogrupal.base.models.RepartoPelicula;

public class DaoRepartoPelicula extends AdapterDao<RepartoPelicula> {
    private RepartoPelicula obj;

    public DaoRepartoPelicula() {
        super(RepartoPelicula.class);
    }

    public RepartoPelicula getObj() {
        if (obj == null)
            obj = new RepartoPelicula();
        return obj;
    }

    public void setObj(RepartoPelicula obj) {
        this.obj = obj;
    }

    public Boolean save() {
        try {
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
        DaoRepartoPelicula dao = new DaoRepartoPelicula();

        dao.getObj().setIdActor(1);
        dao.getObj().setIdPelicula(101);
        dao.getObj().setPapelActor("Protagonista");

        if (dao.save()) {
            System.out.println("Reparto guardado exitosamente.");
        } else {
            System.out.println("Error al guardar reparto.");
        }

        dao.setObj(null);
        dao.getObj().setIdActor(2);
        dao.getObj().setIdPelicula(101);
        dao.getObj().setPapelActor("Villano");

        if (dao.save()) {
            System.out.println("Reparto guardado exitosamente.");
        } else {
            System.out.println("Error al guardar reparto.");
        }
    }

}