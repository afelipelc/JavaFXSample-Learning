package bankutim.datasource;

import bankutim.model.Cuenta;

import java.util.List;

/**
 * Created by felipe on 17/05/17.
 */
public class CuentasDataSource {

    public  static List<Cuenta> Cuentas(){
        return DataSource.Cuentas;
    }

    public static Cuenta addCuenta(Cuenta item){
        DataSource.Cuentas.add(item);

        return item;
    }

}
