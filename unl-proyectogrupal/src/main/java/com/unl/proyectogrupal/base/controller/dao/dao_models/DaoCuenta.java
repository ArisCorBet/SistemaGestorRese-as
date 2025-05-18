package com.unl.proyectogrupal.base.controller.dao.dao_models;

import com.unl.proyectogrupal.base.controller.dao.AdapterDao;
import com.unl.proyectogrupal.base.models.Cuenta;

public class DaoCuenta extends AdapterDao<Cuenta>{
    private Cuenta obj;

    public DaoCuenta(){
        super(Cuenta.class);
    }

    public Cuenta getObj() {
        if (obj == null){
            this.obj = new Cuenta();
        } 
        return this.obj;
    }

     public void setObj(Cuenta obj) {
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
        DaoCuenta da = new DaoCuenta();
        da.getObj().setId(da.listAll().getLength()+1);
        da.getObj().setCorreo("luis123@gmail.com");
        da.getObj().setClave("lusi1938");
        da.getObj().setEstado(true);
        if (da.save()) {
            System.out.println("Guardado");
        } else {
            System.out.println("Error al guardar");
        }
    }


}
