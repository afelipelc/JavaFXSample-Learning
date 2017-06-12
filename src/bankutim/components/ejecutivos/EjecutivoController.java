package bankutim.components.ejecutivos;

import bankutim.datasource.EjecutivosDataSource;
import bankutim.datasource.SucursalesDataSource;
import bankutim.model.Ejecutivo;
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
 * Created by felipe on 7/06/17.
 */
public class EjecutivoController implements Initializable {

    //asociar los controles
    @FXML
    TextField idTxt, nombreTxt, curpTxt, tel1Txt, apellidosTxt, domicilioTxt, rfcTxt,tel2Txt;
    @FXML
    ComboBox estadoCmb, localidadCmb, sucursalCmb, municipioCmb;

    @FXML
    Button aceptarBtn, cancelarBtn;

    //Ejecutivo object
    Ejecutivo ejecutivo = new Ejecutivo();


    Stage primaryStage;
    private boolean actionResult = false;

    public boolean isActionResult() {
        return actionResult;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //fill comboboxes
        //call datasource for Sucursales list
        sucursalCmb.getItems().addAll(SucursalesDataSource.Sucursales());

        //click for aceptarBtn button
        this.aceptarBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                //recuperar la ventana para poder cerrarla
                primaryStage = (Stage) aceptarBtn.getScene().getWindow();

                //data validation
                if(nombreTxt.getText().equals("") ||
                        apellidosTxt.getText().equals("") ||
                        sucursalCmb.getSelectionModel().getSelectedItem() == null){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error...");
                    alert.setHeaderText("Ingrese los datos");
                    alert.setContentText("Ingrese: nombre, apellidos y sucursal");
                    alert.showAndWait();
                    return; //prevent continue
                }

                //set data object from UI controls
                ejecutivo.setNombre(nombreTxt.getText());
                ejecutivo.setApellidos(apellidosTxt.getText());
                ejecutivo.setCURP(curpTxt.getText());
                ejecutivo.setTelefono1(tel1Txt.getText());
                ejecutivo.setTelefono2(tel2Txt.getText());
                ejecutivo.setDomicilio(domicilioTxt.getText());
                ejecutivo.setRFC(rfcTxt.getText());


                ejecutivo.setSucursal( (Sucursal) sucursalCmb.getSelectionModel().getSelectedItem());
                //hacer los demás combobox cuando ya funcionen

                //almacenar el ejecutivo en la colección // o en la BD cuando se tenga

                ejecutivo = EjecutivosDataSource.addEjecutivo(ejecutivo);

                //comprobar si ejecutivo es null, entonces informar del error
                //sino, cerrar la ventana
                if(ejecutivo.equals(null)){
                    //error
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error...");
                    alert.setHeaderText("Error al guardar");
                    alert.setContentText("No se pudo guardar el Ejecutivo.");
                    alert.showAndWait();
                    actionResult = false;
                    primaryStage.close();  //close the window

                }else {
                    //cerrar la ventana
                    actionResult = true; //todo está bien
                    primaryStage.close();
                }
            }
        });

        //cancelarBtn on click
        this.cancelarBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                //cerrar la ventana
                actionResult = false;
                primaryStage = (Stage) cancelarBtn.getScene().getWindow();
                primaryStage.close();
            }
        });


    }
}
