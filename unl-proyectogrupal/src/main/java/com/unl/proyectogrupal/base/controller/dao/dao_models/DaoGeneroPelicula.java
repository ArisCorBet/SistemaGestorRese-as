package com.unl.proyectogrupal.base.controller.dao.dao_models;

import com.unl.proyectogrupal.base.models.GeneroPelicula;
import com.unl.proyectogrupal.base.controller.dao.AdapterDao;

public class DaoGeneroPelicula extends AdapterDao<GeneroPelicula> {
    private GeneroPelicula obj;

    public DaoGeneroPelicula() {
        super(GeneroPelicula.class);
        // TODO Auto-generated constructor stub
    }

    public GeneroPelicula getObj() {
        if (obj == null)
            this.obj = new GeneroPelicula();
        return this.obj;
    }

    public void setObj(GeneroPelicula obj) {
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
        DaoGeneroPelicula da = new DaoGeneroPelicula();
        da.getObj().setIdGenero(da.listAll().getLength() + 1);
        da.getObj().setIdPelicula("P001");
        if (da.save())
            System.out.println("GUARDADO");
        else
            System.out.println("Hubo un error");
    }

}
