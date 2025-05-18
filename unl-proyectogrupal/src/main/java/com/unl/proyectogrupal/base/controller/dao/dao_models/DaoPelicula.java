package com.unl.proyectogrupal.base.controller.dao.dao_models;
import java.util.Date;

import com.unl.proyectogrupal.base.controller.dao.AdapterDao;  
import com.unl.proyectogrupal.base.models.Pelicula;

public class DaoPelicula extends AdapterDao<Pelicula> {
    private Pelicula obj;

    public DaoPelicula() {
        super(Pelicula.class);
    }

    public Pelicula getObj() {
        if (obj == null) {
            this.obj = new Pelicula();
        }
        return this.obj;
    }
    public void setObj(Pelicula obj) {
        this.obj = obj;
    }
    public Boolean save() {
        try {
            obj.setId(listAll().getLength() + 1);
            this.persist(obj);
            return true;
        } catch (Exception e) {
            //LOG DE ERROR
            e.printStackTrace();
            System.out.println(e);
            return false;
        }
    }
	//borrado
    public Boolean update(Integer pos) {
        try {
            this.update(obj, pos);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    public static void main(String[] args) {
        DaoPelicula da = new DaoPelicula();
        da.getObj().setId(da.listAll().getLength() + 1);
        da.getObj().setTitulo("La vida es bella");
        da.getObj().setSinopsis("Una historia conmovedora sobre un padre y su hijo en un campo de concentración.");
        da.getObj().setDuracion(120);
        da.getObj().setTrailer("https://www.youtube.com/watch?v=123456");
        da.getObj().setFechaEstreno(new Date());
        da.getObj().setIdGenero(1);
        
        if (da.save()) {
            System.out.println("Guardado");
        } else {
            System.out.println("Error al guardar");
        }
    }
}
