package bankutim.datasource;

import bankutim.model.Ejecutivo;

import javax.xml.crypto.Data;
import java.util.List;

/**
 * Created by felipe on 17/05/17.
 */
public class EjecutivosDataSource {
    /**
     * Devolver la lista de ejecutivos
     * @return
     */
    public  static List<Ejecutivo> Ejecutivos(){
        //static initial objects to test
        if(DataSource.Ejecutivos.size() == 0){
            DataSource.Ejecutivos.add(new Ejecutivo(1, "Juan"));
            DataSource.Ejecutivos.add(new Ejecutivo(2, "Pablo"));
        }
        return DataSource.Ejecutivos;
    }

    /**
     * agregar un nuevo ejecutivo
     * se le asigna un Id y se devuelve el objeto
     * @param ejecutivo
     * @return Ejecutivo
     */
    public static Ejecutivo addEjecutivo(Ejecutivo ejecutivo){

        //verificar si getId() es 0, entonces
        //poner en setId el total de ejecutivos + 1

        if(ejecutivo.getId() == 0){
            ejecutivo.setId(Ejecutivos().size() + 1);

            //agregar a la lista
            DataSource.Ejecutivos.add(ejecutivo);
        }

        //devolver el ejecutivo
        return ejecutivo;
    }

}
