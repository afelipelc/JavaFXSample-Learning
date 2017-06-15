package bankutim.components.ejecutivos;

import bankutim.datasource.EjecutivosDataSource;
import bankutim.model.Ejecutivo;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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


    //asociar el TableView
    @FXML
    TableView <Ejecutivo> ejecutivosTable;

    //asociar las columnas de acuerdo al tipo de dato
    //a desplegar
    @FXML
    TableColumn<Ejecutivo, Integer > idCol;

    @FXML TableColumn<Ejecutivo, String> sucursalCol, nombreCol, domicilioCol;

    @FXML
    Button agregarBtn;


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

        //asociar las columnas con los atributos del objeto
        this.idCol.setCellValueFactory(new PropertyValueFactory<Ejecutivo, Integer>("id"));

        this.sucursalCol.setCellValueFactory(new PropertyValueFactory<Ejecutivo, String>("sucursal"));

        this.nombreCol.setCellValueFactory(new PropertyValueFactory<Ejecutivo, String>("nombre"));

        this.domicilioCol.setCellValueFactory(new PropertyValueFactory<Ejecutivo, String>("domicilio"));

        //cargar la lista de ejecutivos
        ejecutivosTable.setItems(FXCollections.observableList(EjecutivosDataSource.Ejecutivos()));




    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.agregarBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                //abrir la ventana


            }
        });

    }
}
