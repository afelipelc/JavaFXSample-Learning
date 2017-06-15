package bankutim.datasource;

import bankutim.model.Transaccion;

import java.util.List;

/**
 * Created by felipe on 17/05/17.
 */
public class TransaccionesDataSource {

    public  static List<Transaccion> Transacciones(){
        return DataSource.Transacciones;
    }

    public static Transaccion addTransaccion(Transaccion item){

        DataSource.Transacciones.add(item);

        return item;
    }

    public static float totalTransacciones(){
        return  0;
    }


    public static float totalDepositos(){
        return  0;
    }
}
