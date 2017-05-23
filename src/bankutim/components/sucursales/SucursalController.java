package bankutim.components.sucursales;

import bankutim.datasource.EjecutivosDataSource;
import bankutim.datasource.SucursalesDataSource;
import bankutim.model.Ejecutivo;
import bankutim.model.Sucursal;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by felipe on 18/05/17.
 */
public class SucursalController implements Initializable {
    @FXML
    TextField numeroSucursalTxt, nombreTxt, domicilioTxt;
    @FXML
    ComboBox estadoCmb, gerenteCmb;

    @FXML
    Label mensajeLbl;
    @FXML
    Button aceptarBtn, cancelarBtn;


    Sucursal sucursal = new Sucursal();

    boolean actionResult = false; //use for user action result
    Stage primaryStage;

    public boolean getActionResult() {
        return actionResult;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //fill combobox with string items
        this.estadoCmb.getItems().addAll("Aguascalientes","Baja California","Baja California Sur", "Campeche", "Chiapas", "Chihuahua", "Ciudad de México","...","Zacatecas");

        //fill combobox with objects and set caption
        this.gerenteCmb.getItems().addAll(EjecutivosDataSource.Ejecutivos()); //Custom object with toString method for displayData

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
                sucursal = SucursalesDataSource.addSucursal(sucursal);

                //if no error on saving, close window as OK
                if(sucursal != null) {
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

    /**
     * Take data from controls and store in sucursal object
     * @return
     */
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

            mensajeLbl.setText("Ingrese todos los datos"); //message inline form
            this.nombreTxt.requestFocus();
            return false;
        }

        this.sucursal.setNombre(this.nombreTxt.getText());
        this.sucursal.setDomicilio(this.domicilioTxt.getText());

        //test for selected object at combobox
        System.out.println("Ejecutivo seleccionado" + ((Ejecutivo) gerenteCmb.getSelectionModel().getSelectedItem()).toString() );

        return  true;
    }

    public Sucursal getSucursal() {
        return sucursal;
    }


    public void setSucursal(Sucursal sucursal) {
        this.sucursal = sucursal;

        if(!sucursal.equals(null))
            loadSucursalData();
    }

    /**
     * Load data from object to controls
     */
    private void  loadSucursalData(){
        this.numeroSucursalTxt.setText(sucursal.getId()+"");
        this.nombreTxt.setText(sucursal.getNombre());
        this.domicilioTxt.setText(sucursal.getDomicilio());
    }

}
