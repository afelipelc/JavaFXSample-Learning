package bankutim.datasource;

import bankutim.model.Cliente;
import bankutim.model.Ejecutivo;
import bankutim.model.Sucursal;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by felipe on 17/05/17.
 */
public final class DataSource {
    public static List<Cliente> Clientes = new ArrayList<>();

    public static List<Sucursal> Sucursales = new ArrayList<>();
    public static List<Ejecutivo> Ejecutivos = new ArrayList<>();
}
