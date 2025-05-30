package com.unl.proyectogrupal.base.controller.dao.dao_models;

import com.unl.proyectogrupal.base.controller.dao.AdapterDao;
import com.unl.proyectogrupal.base.models.DireccionPelicula;

public class DaoDireccionPelicula extends AdapterDao<DireccionPelicula> {
    private DireccionPelicula obj;

    public DaoDireccionPelicula() {
        super(DireccionPelicula.class);
    }

    public DireccionPelicula getObj() {
        if (obj == null)
            obj = new DireccionPelicula();
        return obj;
    }

    public void setObj(DireccionPelicula obj) {
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
        DaoDireccionPelicula dao = new DaoDireccionPelicula();

        dao.getObj().setIdDirector(1);
        dao.getObj().setIdPelicula(101);

        if (dao.save()) {
            System.out.println("Dirección guardada exitosamente.");
        } else {
            System.out.println("Error al guardar dirección.");
        }

        dao.setObj(null);
        dao.getObj().setIdDirector(2);
        dao.getObj().setIdPelicula(102);

        if (dao.save()) {
            System.out.println("Dirección guardada exitosamente.");
        } else {
            System.out.println("Error al guardar dirección.");
        }
    }

}