package bankutim;

import bankutim.components.ejecutivos.EjecutivoController;
import bankutim.components.sucursales.SucursalController;
import bankutim.components.sucursales.SucursalesController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    GridPane gridPane; //for initial image
    @FXML
    Pane contenedorPrincipal; //main container, content will be replaced with modules content

    //menu items
    @FXML
    MenuItem sucursalesMI, altaSucursalMI, ejecutivosMI, altaEjecutivoMI; //add all menu items id here....


    //end menu items

    protected  MainApp application;
    public void setApp(MainApp application) {
        this.application = application;

        //resize grid
        contenedorPrincipal.setPrefWidth(application.getWithScreen());
        contenedorPrincipal.setPrefHeight(application.getHeightScreen() - 32);

        gridPane.setPrefWidth(application.getWithScreen());
        gridPane.setPrefHeight(contenedorPrincipal.getMaxHeight());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //handle menu items on click event
        this.sucursalesMI.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                //load module content into main window container
                SucursalesController sucursalesController = new SucursalesController(application.getWithScreen(), application.getHeightScreen() - 32); //send current size screen
                contenedorPrincipal.getChildren().clear();
                contenedorPrincipal.getChildren().add(sucursalesController);

            }
        });

        this.altaSucursalMI.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                //load Sucursal window form

                try {
                    Parent root = FXMLLoader.load(SucursalController.class.getResource("Sucursal.fxml"));
                    Stage stage = new Stage();
                    stage.setTitle("Agregar nueva Sucursal");
                    stage.setScene(new Scene(root));
                    stage.initModality(Modality.WINDOW_MODAL);
                    stage.initOwner( contenedorPrincipal.getScene().getWindow()); //set window parent
                    stage.showAndWait();

                    //setTableviewData(SucursalesDataSource.Sucursales()); //implementation required  :D
                } catch (Exception ex) {
                    System.out.println("Error: " + ex.getMessage() + " stack: " + ex.getCause());
                }
            }
            });

        this.altaEjecutivoMI.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                try { //prevenir errores
                    Parent root = FXMLLoader.load(EjecutivoController.class.getResource("Ejecutivo.fxml"));
                    //crear el escenario o ventana
                    Stage stage = new Stage();
                    stage.setTitle("Alta de ejecutivo");

                    //asociar la UI de ejecutivo
                    stage.setScene(new Scene(root));

                    //establecer como ventana MODAL
                    //MODAL: que se mantiene por encima de la ventana principal
                    //no desaparece hasta que se cierra
                    stage.initModality(Modality.WINDOW_MODAL);

                    //establecer el padre de la ventana (ventana principal)
                    //sobre encima de quien se debe de mantener
                    stage.initOwner(contenedorPrincipal.getScene().getWindow());

                    stage.showAndWait(); //mostrar y esperar a que se cierre

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


    }
}
