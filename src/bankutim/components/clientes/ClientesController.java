package bankutim.components.clientes;

import bankutim.datasource.ClientesDataSource;
import bankutim.model.Cliente;
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
 * Created by felipe on 22/05/17.
 */
public class ClientesController  extends BorderPane implements Initializable {
    //associating UI controls with controller
    @FXML
    BorderPane containerBP;
    @FXML
    Button agregarBtn;
    //end UI controls

    @FXML
    TableView<Cliente> clientesTable;
    @FXML
    TableColumn<Cliente, Integer> numeroCol;
    @FXML
    TableColumn<Cliente, String> nombreCol;
    @FXML
    TableColumn<Cliente, String> domicilioCol;
    @FXML
    TableColumn<Cliente, String> estadoCol;

    public ClientesController(double width, double height){

        //load FXML resource
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Clientes.fxml"));
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
        numeroCol.setCellValueFactory(new PropertyValueFactory<Cliente, Integer>("id"));
        nombreCol.setCellValueFactory(new PropertyValueFactory<Cliente, String>("nombreCompleto"));
        domicilioCol.setCellValueFactory(new PropertyValueFactory<Cliente, String>("domicilio"));
        estadoCol.setCellValueFactory(new PropertyValueFactory<Cliente, String>("estado"));
        //load initial data
        setEjecutivosTableViewData();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.agregarBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                //load Ejecutivo window form

                try {

                    FXMLLoader fxmlLoader = new FXMLLoader(ClienteController.class.getResource("Cliente.fxml"));
                    Parent root = (Parent) fxmlLoader.load();
                    Stage stage = new Stage();
                    stage.setTitle("Agregar nuevo Cliente");
                    stage.setScene(new Scene(root));
                    stage.initModality(Modality.WINDOW_MODAL);
                    stage.initOwner(((Node) t.getSource()).getScene().getWindow()); //set window parent
                    stage.showAndWait();

                    //access controller for get Sucursal item on action result is Ok
                    ClienteController ejecutivoController = fxmlLoader.getController();
                    if (ejecutivoController.getActionResult()) { //if say Save
                        //add or update tableview
                        setEjecutivosTableViewData();

                    }


                } catch (Exception ex) {
                    System.out.println("Error: " + ex.getMessage() + " stack: " + ex.getCause());
                }
            }
        });

        this.clientesTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                if (t.getClickCount() == 2) {
                    //open Sucursal form
                    try {
                        //get selected sucursal
                        FXMLLoader fxmlLoader = new FXMLLoader(ClienteController.class.getResource("Cliente.fxml"));
                        Parent root = (Parent) fxmlLoader.load();
                        Stage stage = new Stage();
                        stage.setTitle("Datos del Cliente");
                        stage.setScene(new Scene(root));
                        stage.initModality(Modality.WINDOW_MODAL);

                        stage.initOwner(((Node) t.getSource()).getScene().getWindow());
                        ClienteController controller = fxmlLoader.getController();
                        Cliente itemEdit = clientesTable.getSelectionModel().getSelectedItem();
                        int indexedit = clientesTable.getItems().indexOf(itemEdit);
                        controller.setCliente(itemEdit);
                        stage.showAndWait();
                        if (controller.getActionResult()) {
                            itemEdit = controller.getCliente();
                            System.out.println("Actualizado: " + itemEdit.getNombre());
                            clientesTable.getItems().set(indexedit, itemEdit);
                        }
                    } catch (Exception ex) {
                        System.out.println("Error: " + ex.getMessage() + " stack: " + ex.getCause());
                    }
                }
            }
        });
    }

    private  void setEjecutivosTableViewData(){
        this.clientesTable.setItems(FXCollections.observableList(ClientesDataSource.Clientes()));
    }
}

