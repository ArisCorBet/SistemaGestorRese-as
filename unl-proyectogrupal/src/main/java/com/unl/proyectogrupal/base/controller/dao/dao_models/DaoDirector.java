package com.unl.proyectogrupal.base.controller.dao.dao_models;

import com.unl.proyectogrupal.base.controller.dao.AdapterDao;
import com.unl.proyectogrupal.base.models.Director;

public class DaoDirector extends AdapterDao<Director> {
    private Director obj;

    public DaoDirector() {
        super(Director.class);
    }

    public Director getObj() {
        if (obj == null)
            obj = new Director();
        return obj;
    }

    public void setObj(Director obj) {
        this.obj = obj;
    }

    public Boolean save() {
        try {
            obj.setIdDirector(this.listAll().getLength() + 1);
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
        DaoDirector dao = new DaoDirector();

        dao.getObj().setDescripcion("Director de cine independiente");
        dao.getObj().setAniosCarrera(20);

        if (dao.save()) {
            System.out.println("Director guardado exitosamente.");
        } else {
            System.out.println("Error al guardar director.");
        }

        dao.setObj(null);
        dao.getObj().setDescripcion("Director de documentales");
        dao.getObj().setAniosCarrera(12);

        if (dao.save()) {
            System.out.println("Director guardado exitosamente.");
        } else {
            System.out.println("Error al guardar director.");
        }
    }

}
