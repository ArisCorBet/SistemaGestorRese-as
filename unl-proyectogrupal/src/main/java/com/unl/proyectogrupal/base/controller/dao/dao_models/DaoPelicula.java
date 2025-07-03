package com.unl.proyectogrupal.base.controller.dao.dao_models;
import java.util.Date;
import java.util.HashMap;

import com.unl.proyectogrupal.base.controller.Utiles;
import com.unl.proyectogrupal.base.controller.dao.AdapterDao;  
import com.unl.proyectogrupal.base.models.Pelicula;
import com.unl.proyectogrupal.base.models.Genero;
import com.unl.proyectogrupal.base.controller.data_struct.list.*;
import com.unl.proyectogrupal.base.controller.Utiles;

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

    public Boolean listar() {
        try {
            this.listAll();
            for (int i = 0; i > this.listAll().getLength(); i++) {
            }
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
            return false;
        }
    }

    public LinkedList<HashMap<String, Object>> all() throws Exception {
        LinkedList<HashMap<String, Object>> lista = new LinkedList<>();
        if (!this.listAll().isEmpty()) {
            Pelicula[] arreglo = this.listAll().toArray();
            for (int i = 0; i < arreglo.length; i++) {
                lista.add(toDict(arreglo[i], i));
            }
        }
        return lista;
    }

    private HashMap<String, Object> toDict(Pelicula arreglo, Integer i) {
        
        HashMap<String, Object> aux = new HashMap<>();
        aux.put("id", arreglo.getId().toString());
        aux.put("titulo", arreglo.getTitulo());
        aux.put("sinopsis", arreglo.getSinopsis());
        aux.put("duracion", arreglo.getDuracion());
        aux.put("trailer", arreglo.getTrailer());
        aux.put("FechaEstreno", arreglo.getFechaEstreno());
        //aux.put("genero", d.listAll().get(arreglo.getId_genero() - 1).getNombre());

        
        return aux;
    }


    //Metodo Quicksort
    public LinkedList<HashMap<String, Object>> orderByCancion(Integer type, String attribute) throws Exception {
        LinkedList<HashMap<String, Object>> lista = all();
        if (!listAll().isEmpty()) {
            HashMap arr[] = lista.toArray();
            quickSort(arr, 0, arr.length - 1, type, attribute);
            lista.toList(arr);
        }
        return lista;
    }

    public void quickSort(HashMap arr[], int begin, int end, Integer type, String attribute) {
        if (begin < end) {
            int partitionIndex = partition(arr, begin, end, type, attribute);

            quickSort(arr, begin, partitionIndex - 1, type, attribute);
            quickSort(arr, partitionIndex + 1, end, type, attribute);
        }
    }
    
    private int partition(HashMap<String, Object> arr[], int begin, int end, Integer type, String attribute) {
        HashMap<String, Object> pivot = arr[end];
        int i = (begin - 1);
        if (type == Utiles.ASCEDENTE) {
            for (int j = begin; j < end; j++) {
                if (arr[j].get(attribute).toString().compareTo(pivot.get(attribute).toString()) < 0) {
                    // if (arr[j] <= pivot) {
                    i++;
                    HashMap<String, Object> swapTemp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = swapTemp;
                }
            }
        } else {
            for (int j = begin; j < end; j++) {
                if (arr[j].get(attribute).toString().compareTo(pivot.get(attribute).toString()) > 0) {
                    i++;
                    HashMap<String, Object> swapTemp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = swapTemp;
                }
            }
        }
        HashMap<String, Object> swapTemp = arr[i + 1];
        arr[i + 1] = arr[end];
        arr[end] = swapTemp;

        return i + 1;
    }

    public LinkedList<HashMap<String, Object>> search(String attribute, String text, Integer type) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'search'");
    }

    

    
}
