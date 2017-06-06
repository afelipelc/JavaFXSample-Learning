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
            DataSource.Ejecutivos.add(new Ejecutivo(1, "Juan", "Pérez Montes"));
            DataSource.Ejecutivos.add(new Ejecutivo(2, "Pablo", "Mármol Cervantes"));


            DataSource.Ejecutivos.get(0).setSucursal(SucursalesDataSource.Sucursales().get(0));
            DataSource.Ejecutivos.get(1).setSucursal(SucursalesDataSource.Sucursales().get(1));

            DataSource.Ejecutivos.get(0).setEstado(DataSource.Estados.get(20));
            DataSource.Ejecutivos.get(1).setEstado(DataSource.Estados.get(20));
        }
        return DataSource.Ejecutivos;
    }

    public static Ejecutivo addEjecutivo(Ejecutivo item){
        //check for sucursal id to set
        if(item.getId() == 0) {

            item.setId(EjecutivosDataSource.Ejecutivos().size() + 1);
            DataSource.Ejecutivos.add(item);
        }

        return item;
    }
}
