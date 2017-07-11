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

    /**
     * return total addition of Cargo from all transacciones
     * @return
     */
    public static double totalCargos(){
        return  Transacciones().stream().mapToDouble( obj -> obj.getCargo()).sum();
    }


    /**
     * return total addition of Deposito from all transacciones
     * @return
     */
    public static double totalDepositos(){

        return  Transacciones().stream().mapToDouble( obj -> obj.getAbono()).sum();
    }
}
