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

        /*
        //static initial objects to test
        if (DataSource.Clientes.size() == 0) {
            DataSource.Clientes.add(new Cliente(1, "Pedrito", "González Torres"));
            DataSource.Clientes.add(new Cliente(2, "Panfilo", "Martiñon Pérez"));
        }

        return DataSource.Clientes;
*/

        //  recuperar los clientes desde la BD
        String query = "select * from Cliente";
        //devolver el resultado del método que procesa la consulta
        return  ProcesarListaClientesResult(query);
    }

    /**
     * Method that execute a query that returns more than 1 record
     * Query result is converted to Cliente objects collection type List<Cliente>
     * @param query
     * @return
     */
    private static List<Cliente> ProcesarListaClientesResult(String query) {
        Statement statement = null;
        ResultSet resultSet = null;
        List<Cliente> clienteList = new ArrayList<>();

        try{
            //open connection to DB Server and prepare for queries
            statement = dbConnection.OpenConnection().createStatement();

            //execute query
            resultSet = statement.executeQuery(query);

            //read all records one by one
            while (resultSet.next()){
                //create object
                Cliente cliente = new Cliente();

                //read all cliente table fields and store on cliente object
                cliente.setId(resultSet.getInt("Id"));

                cliente.setNombre(resultSet.getString("Nombre"));

                cliente.setApellidos(resultSet.getString("Apellidos"));
                cliente.setDomicilio(resultSet.getString("Domicilio"));

                //for future, state is an object, for test, create a new state
                cliente.setEstado(new Estado(resultSet.getString("Estado")));

                cliente.setMunicipio(resultSet.getString("Municipio"));
                cliente.setLocalidad(resultSet.getString("Localidad"));

                cliente.setCURP(resultSet.getString("CURP"));
                cliente.setRFC(resultSet.getString("RFC"));
                cliente.setTelefono1(resultSet.getString("Telefono1"));
                cliente.setTelefono2(resultSet.getString("Telefono2"));

                //add readed cliente to list
                clienteList.add(cliente);
            }

            //return list
            return  clienteList;

        }catch (SQLException ex){

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("DB Connection Error");
            alert.setHeaderText("Un error ha ocurrido con la conexion a la BD");
            alert.setContentText("Continúe usando la aplicación, si el error persiste\nentonces contacte a Soporte Técnico.");
            alert.showAndWait();

            //nothing to result
            return  null;
        }finally {
            //after all work, try free resulset memory
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException sqlEx) {

                } // ignore

            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException sqlEx) {
                } // ignore
            }

            //close DB connection
            dbConnection.CloseConnection();
        }
    }


    public static Cliente addCliente(Cliente cliente){
        /*//check for sucursal id to set
        if(item.getId() == 0) {

            item.setId(ClientesDataSource.Clientes().size() + 1);
            DataSource.Clientes.add(item);
        }

        */

        //store cliente in DB
        Statement statement = null;
        ResultSet rs = null;
        try {
            //prepare connection for insertion
            statement = dbConnection.OpenConnection().createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_UPDATABLE);

            //prepare query
            String SQL = "INSERT INTO " +
                    "Cliente(Nombre, Apellidos, Domicilio, Localidad, Municipio, CURP, RFC, Telefono1)  " +
                    "values('" + cliente.getNombre() + "', '"
                    +cliente.getApellidos() + "','"
                    + cliente.getDomicilio() + "','"
                    + cliente.getLocalidad() + "', '"
                    + cliente.getMunicipio() + "','"
                    + cliente.getCURP() + "','"
                    + cliente.getRFC() + "','"
                    + cliente.getTelefono1() + "')";

            //System.out.println(SQL);

            statement.executeUpdate(SQL, Statement.RETURN_GENERATED_KEYS);

            //read Id assigned
            int autoIncKeyFromApi = -1;
            rs = statement.getGeneratedKeys();
            if (rs.next()) {
                autoIncKeyFromApi = rs.getInt(1);
            } else {
                autoIncKeyFromApi = 0;
            }
            rs.close();
            rs = null;

            //store id in current cliente object
            cliente.setId(autoIncKeyFromApi);

        } catch (SQLException ex) {
            //System.out.print("Error inserting Cliente: " + ex.getMessage());
            return null;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException sqlEx) {
                } // ignore
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException sqlEx) {
                } // ignore
            }

            dbConnection.CloseConnection();
        }

        //return saved cliente
        return cliente;
    }


    private static boolean updateCliente(Cliente cliente) {
        Statement statement = null;

        try {
            statement = dbConnection.OpenConnection().createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_UPDATABLE);
            String SQL = "UPDATE Cliente set "
                    + " Nombre='" + cliente.getNombre() + "', "
                    + " Apellidos ='" + cliente.getApellidos() + "', "
                    + " Domicilio = '" + cliente.getDomicilio() + "', "
                    + " Localidad = '" + cliente.getLocalidad() + "', "
                    + " Municipio = '" + cliente.getMunicipio() + "', "
                    + " Estado ='" + cliente.getEstado().getNombre() + "', "
                    //add CURP, RFC and Tel1
                    + " Telefono2 = '" + cliente.getTelefono2() + "' "
                    + " where Id = " + cliente.getId() + " limit 1";
            //System.out.println(SQL);
            statement.executeUpdate(SQL);
            return true;

        } catch (SQLException ex) {
            //System.out.print("Error: " + ex.getMessage());
            return false;
        } finally {

            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException sqlEx) {
                } // ignore
            }

            dbConnection.CloseConnection();
        }
    }


    //método que inserta o actualiza un cliente
    public static Cliente saveCliente(Cliente cliente){

        //si el Id del cliente es 0, entonces insertar, sino, actualizar
        if( cliente.getId() == 0 ){
            return addCliente(cliente);
        }else {
            updateCliente(cliente);
            return  cliente;
        }

    }
}
