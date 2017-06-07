package bankutim;

import bankutim.components.clientes.ClientesController;
import bankutim.components.cuentas.CuentaController;
import bankutim.components.cuentas.CuentasController;
import bankutim.components.cuentas.TransaccionController;
import bankutim.components.cuentas.TransaccionesController;
import bankutim.components.ejecutivos.EjecutivoController;
import bankutim.components.ejecutivos.EjecutivosController;
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

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    GridPane gridPane; //for initial image
    @FXML
    Pane contenedorPrincipal; //main container, content will be replaced with modules content

    //menu items
    @FXML
    MenuItem sucursalesMI, altaSucursalMI, ejecutivosMI, altaEjecutivoMI, altaCuentaMI, clientesMI, cuentasMI, transaccionesMI, nuevaTransaccionMI; //add all menu items id here....


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

        this.ejecutivosMI.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //load module content into main window container
                EjecutivosController ejecutivosController = new EjecutivosController(application.getWithScreen(), application.getHeightScreen() - 32); //send current size screen
                contenedorPrincipal.getChildren().clear();
                contenedorPrincipal.getChildren().add(ejecutivosController);
            }
        });

        this.altaEjecutivoMI.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                //load Ejecutivo window form

                try {
                    Parent root = FXMLLoader.load(EjecutivoController.class.getResource("Ejecutivo.fxml"));
                    Stage stage = new Stage();
                    stage.setTitle("Agregar nuevo Ejecutivo");
                    stage.setScene(new Scene(root));
                    stage.initModality(Modality.WINDOW_MODAL);
                    stage.initOwner( contenedorPrincipal.getScene().getWindow()); //set window parent
                    stage.showAndWait();

                } catch (Exception ex) {
                    System.out.println("Error: " + ex.getMessage() + " stack: " + ex.getCause());
                }
            }
        });

        this.cuentasMI.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //load module content into main window container
                CuentasController cuentasController = new CuentasController(application.getWithScreen(), application.getHeightScreen() - 32); //send current size screen
                contenedorPrincipal.getChildren().clear();
                contenedorPrincipal.getChildren().add(cuentasController);
            }
        });

        this.altaCuentaMI.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                //load Cuenta window form

                try {
                    Parent root = FXMLLoader.load(CuentaController.class.getResource("CuentaBancaria.fxml"));
                    Stage stage = new Stage();
                    stage.setTitle("Apertura de Cuenta");
                    stage.setScene(new Scene(root));
                    stage.initModality(Modality.WINDOW_MODAL);
                    stage.initOwner( contenedorPrincipal.getScene().getWindow()); //set window parent
                    stage.showAndWait();

                } catch (Exception ex) {
                    System.out.println("Error: " + ex.getMessage() + " stack: " + ex.getCause());
                }
            }
        });


        this.clientesMI.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //load module content into main window container
                ClientesController clientesController = new ClientesController(application.getWithScreen(), application.getHeightScreen() - 32); //send current size screen
                contenedorPrincipal.getChildren().clear();
                contenedorPrincipal.getChildren().add(clientesController);
            }
        });

        this.transaccionesMI.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //load module content into main window container
                TransaccionesController transaccionesController = new TransaccionesController(application.getWithScreen(), application.getHeightScreen() - 32); //send current size screen
                contenedorPrincipal.getChildren().clear();
                contenedorPrincipal.getChildren().add(transaccionesController);
            }
        });

        this.nuevaTransaccionMI.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                //load Transaccion window form

                try {
                    Parent root = FXMLLoader.load(TransaccionController.class.getResource("Transaccion.fxml"));
                    Stage stage = new Stage();
                    stage.setTitle("Nueva transacción");
                    stage.setScene(new Scene(root));
                    stage.initModality(Modality.WINDOW_MODAL);
                    stage.initOwner( contenedorPrincipal.getScene().getWindow()); //set window parent
                    stage.showAndWait();

                } catch (Exception ex) {
                    System.out.println("Error: " + ex.getMessage() + " stack: " + ex.getCause());
                }
            }
        });
    }
}
