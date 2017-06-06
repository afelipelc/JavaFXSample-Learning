package bankutim.components.ejecutivos;

import bankutim.datasource.DataSource;
import bankutim.datasource.EjecutivosDataSource;
import bankutim.datasource.SucursalesDataSource;
import bankutim.model.Ejecutivo;
import bankutim.model.Estado;
import bankutim.model.Sucursal;
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
 * Created by felipe on 1/06/17.
 */
public class EjecutivoController implements Initializable {

    @FXML
    TextField idTxt, nombreTxt, apellidosTxt, domicilioTxt;
    @FXML
    ComboBox estadoCmb, sucursalCmb;

    @FXML
    Button aceptarBtn, cancelarBtn;

    Ejecutivo ejecutivo = new Ejecutivo();

    boolean actionResult = false; //use for user action result
    Stage primaryStage;

    public boolean getActionResult() {
        return actionResult;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //fill combobox with objects and set caption
        this.estadoCmb.getItems().addAll(DataSource.Estados);

        this.sucursalCmb.getItems().addAll(SucursalesDataSource.Sucursales()); //Custom object with toString method for displayData

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
                ejecutivo = EjecutivosDataSource.addEjecutivo(ejecutivo);

                //if no error on saving, close window as OK
                if(ejecutivo != null) {
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

        this.ejecutivo.setNombre(this.nombreTxt.getText());
        this.ejecutivo.setApellidos(this.apellidosTxt.getText());
        this.ejecutivo.setDomicilio(this.domicilioTxt.getText());
        this.ejecutivo.setSucursal((Sucursal) sucursalCmb.getSelectionModel().getSelectedItem());
        this.ejecutivo.setEstado((Estado) estadoCmb.getSelectionModel().getSelectedItem());


        return  true;
    }

    public Ejecutivo getEjecutivo() {
        return ejecutivo;
    }

    public void setEjecutivo(Ejecutivo ejecutivo) {
        this.ejecutivo = ejecutivo;

        if(!ejecutivo.equals(null))
            loadEjecutivoData();
    }

    /**
     * Load data from object to controls
     */
    private void loadEjecutivoData(){
        this.idTxt.setText(ejecutivo.getId()+"");
        this.nombreTxt.setText(ejecutivo.getNombre());
        this.apellidosTxt.setText(ejecutivo.getApellidos());
        this.domicilioTxt.setText(ejecutivo.getDomicilio());
        this.sucursalCmb.getSelectionModel().select(ejecutivo.getSucursal());
        this.estadoCmb.getSelectionModel().select(ejecutivo.getEstado());
    }
}
