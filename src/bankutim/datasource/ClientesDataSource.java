package bankutim.datasource;

import bankutim.model.Cliente;

import java.util.List;

/**
 * Created by felipe on 17/05/17.
 */
public class ClientesDataSource {

    public  static List<Cliente> Clientes() {
        //static initial objects to test
        if (DataSource.Clientes.size() == 0) {
            DataSource.Clientes.add(new Cliente(1, "Pedrito", "González Torres"));
            DataSource.Clientes.add(new Cliente(2, "Panfilo", "Martiñon Pérez"));
        }

        return DataSource.Clientes;
    }

    public static Cliente addCliente(Cliente item){
        //check for sucursal id to set
        if(item.getId() == 0) {

            item.setId(ClientesDataSource.Clientes().size() + 1);
            DataSource.Clientes.add(item);
        }

        return item;
    }

}
