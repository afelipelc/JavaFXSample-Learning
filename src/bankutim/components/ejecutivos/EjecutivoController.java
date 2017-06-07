package bankutim.components.ejecutivos;

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


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //fill comboboxes
        //call datasource for Sucursales list
        sucursalCmb.getItems().addAll(SucursalesDataSource.Sucursales());

        //click for aceptarBtn button
        this.aceptarBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

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
                //apellidos, tels, curp, rfc, ...
                //DEBES ESCRIBIR EL CÓDIGO FALTANTE :(
                //NO COPIAR EL COMENTARIO :'(

                ejecutivo.setSucursal( (Sucursal) sucursalCmb.getSelectionModel().getSelectedItem());
                //hacer los demás combobox cuando ya funcionen




            }
        });

    }
}
