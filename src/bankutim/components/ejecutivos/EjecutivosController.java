package bankutim.components.ejecutivos;

import bankutim.components.sucursales.SucursalController;
import bankutim.datasource.EjecutivosDataSource;
import bankutim.datasource.SucursalesDataSource;
import bankutim.model.Ejecutivo;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by felipe on 13/06/17.
 *
 * //si se pretende crear un control de usuario, entonces se debe heredar a un contenedor (BorderPane, VBox, GridPane, HBox, etc.)
 *
 * //el controlador debe de cargar el recurso gráfico (archivo FXML) en el constructor
 */
public class EjecutivosController extends BorderPane implements Initializable {

    //asociar controles
    @FXML
    BorderPane containerBP;
    @FXML
    TableView<Ejecutivo> ejecutivosTable;

    @FXML
    TableColumn<Ejecutivo, Integer> numeroCol; //id
    @FXML TableColumn<Ejecutivo, String> sucursalCol;
    @FXML TableColumn<Ejecutivo, String> nombreCol;
    @FXML TableColumn<Ejecutivo, String> domicilioCol;

    @FXML
    Button agregarBtn;


    /**
     * Constructor
     * Debe recibir información de la dimensión de la ventana para adaptarse al tamaño
     */
    public EjecutivosController(double width, double height){

        //hace que referencíe al archivo FXML (interfaz de usuario)
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Ejecutivos.fxml"));

        fxmlLoader.setRoot(this); //se especifica el elemento padre <fx:root ahora es <BorderPane ...

        fxmlLoader.setController(this); //asociar el controlador con la interfaz de usuario
        //es decir: fx:controller = "EjecutivosController"

        try {
            fxmlLoader.load(); //se carga la ventana
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.containerBP.setPrefSize(width, height); //se establece el ancho y alto del contenedor BorderPane

        //asociar las columnas con los atributos de la clase Ejecutivo
        this.numeroCol.setCellValueFactory(new PropertyValueFactory<Ejecutivo, Integer>("id")); //asocia a ejecutivo.getId()
        this.nombreCol.setCellValueFactory(new PropertyValueFactory<Ejecutivo, String>("nombreCompleto"));
        //"nombreCompleto" >>> ejecutivo.getNombreCompleto()            //nombreCompleto no existe como atributo, pero se tiene un método de lectura

        this.sucursalCol.setCellValueFactory(new PropertyValueFactory<Ejecutivo, String>("sucursal"));
        this.domicilioCol.setCellValueFactory(new PropertyValueFactory<Ejecutivo, String>("domicilio"));

        //cargar los ejecutivos al tableView
        this.ejecutivosTable.setItems(FXCollections.observableList(EjecutivosDataSource.Ejecutivos()));
    }


    @Override
    public void initialize(URL location, ResourceBundle resources)   {
        this.agregarBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //abrir la ventana de alta de ejecutivo
                try {

                    FXMLLoader fxmlLoader =  new FXMLLoader(EjecutivoController.class.getResource("Ejecutivo.fxml"));

                    Parent root = (Parent) fxmlLoader.load();
                    Stage stage = new Stage();
                    stage.setTitle("Agregar nuevo Ejecutivo");
                    stage.setScene(new Scene(root));
                    stage.initModality(Modality.WINDOW_MODAL);
                    stage.initOwner( agregarBtn.getScene().getWindow()); //set window parent
                    stage.showAndWait();

                    //la ventana se ha cerrado

                    //comprobar que el usuario dio click en aceptar (actionResult es true)
                    //primero recuperar el objeto controlador
                    EjecutivoController ejecutivoController = fxmlLoader.getController();
                    if(ejecutivoController.isActionResult()){
                        //recargar la lista de ejecutvos
                        ejecutivosTable.setItems(FXCollections.observableList(EjecutivosDataSource.Ejecutivos()));
                    }



                } catch (Exception ex) {
                    System.out.println("Error: " + ex.getMessage() + " stack: " + ex.getCause());
                }
            }
        });

    }
}
