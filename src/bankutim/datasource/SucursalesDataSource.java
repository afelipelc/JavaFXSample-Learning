package bankutim.datasource;

import bankutim.model.Sucursal;

import java.util.List;

/**
 * Created by felipe on 17/05/17.
 */
public final class SucursalesDataSource {

    /**
     * Return all sucursal objects
     * @return
     */
    public  static List<Sucursal> Sucursales(){

        //static initial objects to test
        if(DataSource.Sucursales.size() == 0){
            DataSource.Sucursales.add(new Sucursal(1,"UTIM Central","Prol. Reforma 168, barrio de Santiago Mihuacán"));
            DataSource.Sucursales.add(new Sucursal(2,"Unidad II","Centenario No. 123, Tulcingo del Valle, Puebla..."));

            //set sucursales Gerente
            DataSource.Sucursales.get(0).setGerente(EjecutivosDataSource.Ejecutivos().get(0));

            DataSource.Sucursales.get(1).setGerente(EjecutivosDataSource.Ejecutivos().get(1));
            //set estado
            DataSource.Sucursales.get(0).setEstado(DataSource.Estados.get(20));
            DataSource.Sucursales.get(1).setEstado(DataSource.Estados.get(20));

        }

        return DataSource.Sucursales;
    }

    /**
     * storage sucursal object
     * @param item
     * @return
     */
    public static Sucursal addSucursal(Sucursal item){
        //check for sucursal id to set
        if(item.getId() == 0) {

            item.setId(SucursalesDataSource.Sucursales().size() + 1);
            DataSource.Sucursales.add(item);
        }

        return item;
    }

}
