package bankutim.components.ejecutivos;

import bankutim.datasource.EjecutivosDataSource;
import bankutim.datasource.SucursalesDataSource;
import bankutim.model.Ejecutivo;
import bankutim.model.Sucursal;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by felipe on 9/06/17.
 */
public class EjecutivoController implements Initializable{

    //asociar los controles de la UI
    @FXML
    ComboBox estadoCmb, localidadCmb, sucursalCmb, municipioCmb;
    @FXML
    TextField curpTxt, tel1Txt, nombreTxt, idTxt, tel2Txt, rfcTxt, domicilioTxt, apellidosTxt;
    @FXML
    Button aceptarBtn, cancelarBtn;

    //objeto Ejecutivo
    Ejecutivo ejecutivo = new Ejecutivo();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //fill sucursalesCmb options
        //get sucursales list from datasource
        this.sucursalCmb.getItems().addAll( SucursalesDataSource.Sucursales() );


        this.aceptarBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {


                /*
                {si nombre o apellidos o sucursal no se ingresan, mostrar un mensaje de error y regresar (return)
                }*/

                if(nombreTxt.getText().equals("") || apellidosTxt.getText().equals("") || sucursalCmb.getSelectionModel().getSelectedItem() == null ){
                    //alert

                    return; //cancelar la acción
                }
                /*
                poner los datos de los txt al objeto ejecutivo*/
                ejecutivo.setNombre(nombreTxt.getText());
                ejecutivo.setApellidos(apellidosTxt.getText());
                ejecutivo.setSucursal((Sucursal) sucursalCmb.getSelectionModel().getSelectedItem());

/*
                mandar a guardar el ejecutivo (a EjecutivosDataSource.addEjecutivo( ejecutivo ))
                */
                ejecutivo = EjecutivosDataSource.addEjecutivo( ejecutivo );

                /*
                si se guardó (comprobar que ejecutivo no es null), cerrar la ventana
                sino entonces informar del error y cerrar la ventana
                 */


            }
        });



    }
}
