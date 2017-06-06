package bankutim.components.clientes;

import bankutim.datasource.ClientesDataSource;
import bankutim.datasource.DataSource;
import bankutim.model.Cliente;
import bankutim.model.Estado;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by felipe on 22/05/17.
 */
public class ClienteController implements Initializable {

    @FXML
    TextField idTxt, nombreTxt, apellidosTxt, domicilioTxt;
    @FXML
    ComboBox estadoCmb;

    @FXML
    Button aceptarBtn, cancelarBtn;

    Cliente cliente = new Cliente();

    boolean actionResult = false; //use for user action result
    Stage primaryStage;

    public boolean getActionResult() {
        return actionResult;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //fill combobox with objects and set caption
        this.estadoCmb.getItems().addAll(DataSource.Estados);


        this.aceptarBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                //first set data into object
                if(!setSucursalData()){
                    return; //return if not set data
                }

                //get current window
                primaryStage = (Stage) aceptarBtn.getScene().getWindow();

                //call datasource to storage object
                cliente = ClientesDataSource.addCliente(cliente);

                //if no error on saving, close window as OK
                if(cliente != null) {
                    actionResult = true;
                    primaryStage.close();
                }else {
                    actionResult = false;

                    //implement a messageDialog to user notification about error

                    primaryStage.close();
                }
            }
        });

        /**
         * mark action as cancel and close window
         */
        this.cancelarBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                actionResult = false;
                primaryStage = (Stage) cancelarBtn.getScene().getWindow();
                primaryStage.close();
            }
        });
    }

    private boolean setSucursalData()
    {
        if (this.nombreTxt.getText().equals("")
                || this.domicilioTxt.getText().equals("")) {


            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error...");
            alert.setHeaderText("Ingreso de datos");
            alert.setContentText("Debe ingresar todos los datos.");
            //for more JavaFX Dialogs see examples at http://code.makery.ch/blog/javafx-dialogs-official/

            alert.showAndWait();

            this.nombreTxt.requestFocus();
            return false;
        }

        this.cliente.setNombre(this.nombreTxt.getText());
        this.cliente.setApellidos(this.apellidosTxt.getText());
        this.cliente.setDomicilio(this.domicilioTxt.getText());
        this.cliente.setEstado((Estado) estadoCmb.getSelectionModel().getSelectedItem());


        return  true;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;

        if(!cliente.equals(null))
            loadClienteData();
    }

    /**
     * Load data from object to controls
     */
    private void loadClienteData(){
        this.idTxt.setText(cliente.getId()+"");
        this.nombreTxt.setText(cliente.getNombre());
        this.apellidosTxt.setText(cliente.getApellidos());
        this.domicilioTxt.setText(cliente.getDomicilio());
        this.estadoCmb.getSelectionModel().select(cliente.getEstado());
    }
}
