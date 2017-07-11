package bankutim.components.cuentas;

import bankutim.datasource.ClientesDataSource;
import bankutim.datasource.CuentasDataSource;
import bankutim.datasource.SucursalesDataSource;
import bankutim.model.Cliente;
import bankutim.model.Cuenta;
import bankutim.model.Sucursal;
import bankutim.model.TipoCuenta;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.net.URL;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by felipe on 22/05/17.
 */
public class CuentaController implements Initializable {
    @FXML
    TextField idTxt;
    @FXML
    ComboBox tipoCmb, titularCmb, sucursalCmb, plazoCmb;
    @FXML
    Label saldoLbl;
    @FXML
    Label fechaAperturaDtp;

    @FXML Button aceptarBtn, cancelarBtn;
    @FXML
    GridPane datosCuenta;

    Cuenta cuenta;
    DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");


    boolean actionResult = false, isEditable = true; //use for user action result
    Stage primaryStage;

    public boolean getActionResult() {
        return actionResult;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        this.sucursalCmb.getItems().addAll(SucursalesDataSource.Sucursales()); //Custom object with toString method for displayData
        this.titularCmb.getItems().addAll(ClientesDataSource.Clientes());
        this.tipoCmb.getItems().addAll(TipoCuenta.values());
        this.plazoCmb.getItems().addAll("0","30", "90", "180", "365");
        this.fechaAperturaDtp.setText(format.format(new Date()));
        this.saldoLbl.setText(NumberFormat.getCurrencyInstance(new Locale("es", "MX"))
                .format(0));

        this.aceptarBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                //get current window
                primaryStage = (Stage) aceptarBtn.getScene().getWindow();

                if(!isEditable){ //if is not editable, close form
                    primaryStage.close();
                    return;
                }

                //first set data into object
                if(!setCuentaData()){
                    return; //return if not set data
                }



                //call datasource to storage object
                cuenta = CuentasDataSource.addCuenta(cuenta);

                //if no error on saving, close window as OK
                if(cuenta != null) {
                    actionResult = true;
                    loadCuentaData();
                    //primaryStage.close();
                    isEditable = false;
                }else {
                    actionResult = false;

                    //implement a messageDialog to user notification about error

                    //primaryStage.close();
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

    public Cuenta getCuenta() {
        return cuenta;
    }


    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
        //cargar los datos de la cuenta
        if(cuenta != null){
            loadCuentaData();
        }
    }

    private boolean setCuentaData()
    {
        if (this.titularCmb.getSelectionModel().isEmpty() ||  this.sucursalCmb.getSelectionModel().isEmpty() || this.tipoCmb.getSelectionModel().isEmpty() || this.plazoCmb.getSelectionModel().isEmpty()) {


            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error...");
            alert.setHeaderText("Ingreso de datos");
            alert.setContentText("Debe ingresar todos los datos.");
            //for more JavaFX Dialogs see examples at http://code.makery.ch/blog/javafx-dialogs-official/
            alert.showAndWait();


            return false;
        }

        cuenta = new Cuenta(
                CuentasDataSource.Cuentas().size() + 1,
                (Cliente) this.titularCmb.getSelectionModel().getSelectedItem(),
                (Sucursal) this.sucursalCmb.getSelectionModel().getSelectedItem()
                );

        cuenta.setTipo((TipoCuenta) this.tipoCmb.getSelectionModel().getSelectedItem());
        cuenta.setPlazoDias(Integer.parseInt(this.plazoCmb.getSelectionModel().getSelectedItem().toString()));


        return  true;
    }

    /**
     * Load data from object to controls
     */
    private void loadCuentaData(){
        this.idTxt.setText(cuenta.getId()+"");
        this.titularCmb.getSelectionModel().select(cuenta.getTitular());
        this.tipoCmb.getSelectionModel().select(cuenta.getTipo());
        this.plazoCmb.getSelectionModel().select(cuenta.getPlazoDias());
        this.sucursalCmb.getSelectionModel().select(cuenta.getSucursalApertura());
        this.fechaAperturaDtp.setText(cuenta.getFechaFormat());
        this.saldoLbl.setText(cuenta.getSaldoFormat());

        //this.datosCuenta.setDisable(true);
        this.titularCmb.setDisable(true);
        //this.titularCmb.getStyleClass().add("disabledCmb");
        this.tipoCmb.setDisable(true);
        this.plazoCmb.setDisable(true);
        this.sucursalCmb.setDisable(true);
        aceptarBtn.setText("Cerrar");
        isEditable = false;

    }
}
