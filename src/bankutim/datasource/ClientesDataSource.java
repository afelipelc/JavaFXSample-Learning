package bankutim.datasource;

import bankutim.model.Cliente;
import bankutim.model.Estado;
import javafx.scene.control.Alert;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by felipe on 17/05/17.
 */
public final class ClientesDataSource {

    //create static object db connection
    static DBConnection dbConnection = new DBConnection();

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


        //return saved cliente
        return item;
    }


}
