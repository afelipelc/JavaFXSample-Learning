package bankutim.datasource;

import bankutim.model.Ejecutivo;

import javax.xml.crypto.Data;
import java.util.List;

/**
 * Created by felipe on 17/05/17.
 */
public class EjecutivosDataSource {
    public  static List<Ejecutivo> Ejecutivos(){
        //static initial objects to test
        if(DataSource.Ejecutivos.size() == 0){
            DataSource.Ejecutivos.add(new Ejecutivo(1, "Juan Pérez"));
            DataSource.Ejecutivos.add(new Ejecutivo(2, "Pablo Mármol"));
        }
        return DataSource.Ejecutivos;
    }


    public static Ejecutivo addEjecutivo(Ejecutivo ejecutivo) {

        //{
        //si el id de ejecutivo es 0, entonces
        //poner el nuevo id = total de ejecutivos +1
          DataSource.Ejecutivos.add(ejecutivo); //agrega el objeto a la colección
        //}

        return ejecutivo;

    }
}
