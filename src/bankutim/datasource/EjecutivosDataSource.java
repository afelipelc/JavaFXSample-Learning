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

    public static Ejecutivo agregarEjecutivo(Ejecutivo ejecutivo){
        if(ejecutivo.getId() == 0){
            ejecutivo.setId( Ejecutivos().size() + 1 );
            DataSource.Ejecutivos.add(ejecutivo);
        }

        return  ejecutivo;
    }
}
