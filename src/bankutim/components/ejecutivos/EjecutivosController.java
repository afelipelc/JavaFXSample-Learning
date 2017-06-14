package bankutim.components.ejecutivos;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * EjecutivosController es un control de usuario que se va a insertar en la ventana principal
 *
 * Created by felipe on 14/06/17.
 */
public class EjecutivosController extends BorderPane implements Initializable {

    @FXML
    BorderPane containerBP;


    /**
     * el constructor recibe el ancho y alto que debe de tener
     * @param width
     * @param height
     */
    public EjecutivosController(double width, double height){

        //indicar cual es la interfaz de usuario
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Ejecutivos.fxml"));

        //indicar quien es el elemento root
        fxmlLoader.setRoot(this); //este controlador es el root

        //indicar quien es controlador
        fxmlLoader.setController(this);

        //indicar que se cargue
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        containerBP.setPrefSize(width, height);


    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
