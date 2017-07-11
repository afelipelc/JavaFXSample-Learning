package bankutim.datasource;

import bankutim.model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by felipe on 17/05/17.
 */
public final class DataSource {
    public static List<Cliente> Clientes = new ArrayList<>();

    public static List<Sucursal> Sucursales = new ArrayList<>();
    public static List<Ejecutivo> Ejecutivos = new ArrayList<>();

    public static List<Cuenta> Cuentas = new ArrayList<>();

    public static List<Transaccion> Transacciones = new ArrayList<>();

    public static  List<Estado> Estados = new ArrayList<Estado>(
            Arrays.asList(
                    new Estado("Aguascalientes"),
                    new Estado("Baja California"),
                    new Estado("Baja California Sur"),
                    new Estado("Campeche"),
                    new Estado("Coahuila"),
                    new Estado("Colima"),
                    new Estado("Chiapas"),
                    new Estado("Chihuahua"),
                    new Estado("Distrito Federal"),
                    new Estado("Durango"),
                    new Estado("Guanajuato"),
                    new Estado("Guerrero"),
                    new Estado("Hidalgo"),
                    new Estado("Jalisco"),
                    new Estado("México"),
                    new Estado("Michoacán"),
                    new Estado("Morelos"),
                    new Estado("Nayarit"),
                    new Estado("Nuevo León"),
                    new Estado("Oaxaca"),
                    new Estado("Puebla"),
                    new Estado("Querétaro")
                    )
    );
}
