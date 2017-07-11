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

    public static  Cuenta buscarCuenta(int numeroCta){
        //buscar en la lista de cuentas
        //con bd sería un select * from cuenta where numero = numeroCta ...;
        for(Cuenta cuenta : CuentasDataSource.Cuentas()){
            if(cuenta.getId() == numeroCta){
                return  cuenta;
            }
        }
        return  null;
    }

}
