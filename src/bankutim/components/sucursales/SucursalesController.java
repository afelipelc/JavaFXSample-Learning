package bankutim.components.sucursales;

import bankutim.datasource.SucursalesDataSource;
import bankutim.model.Sucursal;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by felipe on 18/05/17.
 */
public class SucursalesController extends BorderPane implements Initializable    {
    //associating UI controls with controller
    @FXML
    BorderPane containerBP;
    @FXML
    Button agregarBtn;
    //end UI controls

    @FXML
    TableView<Sucursal> sucursalesTable;
    @FXML
    TableColumn<Sucursal, Integer> numeroCol;
    @FXML
    TableColumn<Sucursal, String> nombreCol;
    @FXML
    TableColumn<Sucursal, String> domicilioCol;


    //Stage primaryStage;


    public SucursalesController(double width, double height){

        //load FXML resource
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Sucursales.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);

        }
        this.containerBP.setPrefSize(width, height);
        //end load resource

        //associating table columns to model object properties
        numeroCol.setCellValueFactory(new PropertyValueFactory<Sucursal, Integer>("id"));
        nombreCol.setCellValueFactory(new PropertyValueFactory<Sucursal, String>("nombre"));
        domicilioCol.setCellValueFactory(new PropertyValueFactory<Sucursal, String>("domicilio"));

        //load initial data
        setSucursalesTableViewData();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //handle add Sucursal item on click event
        this.agregarBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                //load Sucursal window form

                try {

                    FXMLLoader fxmlLoader =  new FXMLLoader(SucursalController.class.getResource("Sucursal.fxml"));
                    Parent root = (Parent) fxmlLoader.load();
                    Stage stage = new Stage();
                    stage.setTitle("Agregar nueva Sucursal");
                    stage.setScene(new Scene(root));
                    stage.initModality(Modality.WINDOW_MODAL);
                    stage.initOwner(((Node) t.getSource()).getScene().getWindow()); //set window parent
                    stage.showAndWait();

                    //access controller for get Sucursal item on action result is Ok
                    SucursalController sucursalController = fxmlLoader.getController();
                    if(sucursalController.getActionResult()){ //if say Save
                        //add or update tableview
                        setSucursalesTableViewData();
                        
                    }


                } catch (Exception ex) {
                    System.out.println("Error: " + ex.getMessage() + " stack: " + ex.getCause());
                }
            }
        });

        this.sucursalesTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                if (t.getClickCount() == 2) {
                    //open Sucursal form
                    try {
                        //get selected sucursal
                        FXMLLoader fxmlLoader = new FXMLLoader(SucursalController.class.getResource("Sucursal.fxml"));
                        Parent root = (Parent) fxmlLoader.load();
                        Stage stage = new Stage();
                        stage.setTitle("Datos de la Sucursal");
                        stage.setScene(new Scene(root));
                        stage.initModality(Modality.WINDOW_MODAL);

                        stage.initOwner(((Node) t.getSource()).getScene().getWindow());
                        SucursalController controller = fxmlLoader.getController();
                        Sucursal itemEdit = sucursalesTable.getSelectionModel().getSelectedItem();
                        int indexedit = sucursalesTable.getItems().indexOf(itemEdit);
                        controller.setSucursal(itemEdit);
                        stage.showAndWait();
                        if (controller.getActionResult()) {
                            itemEdit = controller.getSucursal();
                            System.out.println("Actualizado: " + itemEdit.getNombre());
                            sucursalesTable.getItems().set(indexedit, itemEdit);
                        }
                    } catch (Exception ex) {
                        System.out.println("Error: " + ex.getMessage() + " stack: " + ex.getCause());
                    }
                }
            }
        });
    }

    private  void setSucursalesTableViewData(){
        this.sucursalesTable.setItems(FXCollections.observableList(SucursalesDataSource.Sucursales()));
    }
}
