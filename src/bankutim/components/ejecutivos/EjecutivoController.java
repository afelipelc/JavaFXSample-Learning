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

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by felipe on 9/06/17.
 */
public class EjecutivoController implements Initializable {

    //asociar controles de la UI
    @FXML
    TextField idTxt, nombreTxt, tel1Txt,curpTxt, apellidosTxt, domicilioTxt, tel2Txt, rfcTxt;
    @FXML
    ComboBox estadoCmb, localidadCmb, sucursalCmb, municipioCmb;
    @FXML
    Button  aceptarBtn, cancelarBtn;

    //objeto que editará el formulario
    private Ejecutivo ejecutivo = new Ejecutivo();




    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //cargar la  lista de sucursales
        //combobox.getItems().addAll( [elementos] );
        this.sucursalCmb.getItems().addAll( SucursalesDataSource.Sucursales() );

        this.aceptarBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                //2. validar los datos ingresados
                if(nombreTxt.getText().equals("") || apellidosTxt.getText().equals("") ||  sucursalCmb.getSelectionModel().getSelectedItem()  == null ){

                    //mensaje de error
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Faltan datos");
                    alert.setContentText("Ingrese nombre, apellidos y sucural");
                    alert.showAndWait();

                    return; //cancelar acción
                }

                //3. pasar los valores al objeto
                ejecutivo.setNombre(nombreTxt.getText());
                ejecutivo.setApellidos(apellidosTxt.getText());
                ejecutivo.setSucursal( (Sucursal) sucursalCmb.getSelectionModel().getSelectedItem());

                //4. Guardar el objeto en la lista
                EjecutivosDataSource.agregarEjecutivo(ejecutivo);

                //cerrar la ventana

            }
        });
    }
}
