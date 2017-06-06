package bankutim.components.ejecutivos;

import bankutim.datasource.EjecutivosDataSource;
import bankutim.model.Ejecutivo;
import bankutim.model.Estado;
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
 * Created by felipe on 1/06/17.
 */
public class EjecutivosController extends BorderPane  implements Initializable {
    //associating UI controls with controller
    @FXML
    BorderPane containerBP;
    @FXML
    Button agregarBtn;
    //end UI controls

    @FXML
    TableView<Ejecutivo> ejecutivosTable;
    @FXML
    TableColumn<Ejecutivo, Integer> numeroCol;
    @FXML
    TableColumn<Ejecutivo, String> nombreCol;
    @FXML
    TableColumn<Ejecutivo, String> domicilioCol;
    @FXML
    TableColumn<Ejecutivo, String> sucursalCol;
    @FXML
    TableColumn<Ejecutivo, String> estadoCol;

    public EjecutivosController(double width, double height){

        //load FXML resource
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Ejecutivos.fxml"));
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
        numeroCol.setCellValueFactory(new PropertyValueFactory<Ejecutivo, Integer>("id"));
        nombreCol.setCellValueFactory(new PropertyValueFactory<Ejecutivo, String>("nombreCompleto"));
        domicilioCol.setCellValueFactory(new PropertyValueFactory<Ejecutivo, String>("domicilio"));
        sucursalCol.setCellValueFactory(new PropertyValueFactory<Ejecutivo, String>("sucursal"));
        estadoCol.setCellValueFactory(new PropertyValueFactory<Ejecutivo, String>("estado"));
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

                    FXMLLoader fxmlLoader = new FXMLLoader(EjecutivoController.class.getResource("Ejecutivo.fxml"));
                    Parent root = (Parent) fxmlLoader.load();
                    Stage stage = new Stage();
                    stage.setTitle("Agregar nuevo Ejecutivo");
                    stage.setScene(new Scene(root));
                    stage.initModality(Modality.WINDOW_MODAL);
                    stage.initOwner(((Node) t.getSource()).getScene().getWindow()); //set window parent
                    stage.showAndWait();

                    //access controller for get Ejecutivo item on action result is Ok
                    EjecutivoController ejecutivoController = fxmlLoader.getController();
                    if (ejecutivoController.getActionResult()) { //if say Save
                        //add or update tableview
                        setEjecutivosTableViewData();

                    }


                } catch (Exception ex) {
                    System.out.println("Error: " + ex.getMessage() + " stack: " + ex.getCause());
                }
            }
        });

        this.ejecutivosTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                if (t.getClickCount() == 2) {
                    //open Sucursal form
                    try {
                        //get selected sucursal
                        FXMLLoader fxmlLoader = new FXMLLoader(EjecutivoController.class.getResource("Ejecutivo.fxml"));
                        Parent root = (Parent) fxmlLoader.load();
                        Stage stage = new Stage();
                        stage.setTitle("Datos del Ejecutivo");
                        stage.setScene(new Scene(root));
                        stage.initModality(Modality.WINDOW_MODAL);

                        stage.initOwner(((Node) t.getSource()).getScene().getWindow());
                        EjecutivoController controller = fxmlLoader.getController();
                        Ejecutivo itemEdit = ejecutivosTable.getSelectionModel().getSelectedItem();
                        int indexedit = ejecutivosTable.getItems().indexOf(itemEdit);
                        controller.setEjecutivo(itemEdit);
                        stage.showAndWait();
                        if (controller.getActionResult()) {
                            itemEdit = controller.getEjecutivo();
                            System.out.println("Actualizado: " + itemEdit.getNombre());
                            ejecutivosTable.getItems().set(indexedit, itemEdit);
                        }
                    } catch (Exception ex) {
                        System.out.println("Error: " + ex.getMessage() + " stack: " + ex.getCause());
                    }
                }
            }
        });
    }

    private  void setEjecutivosTableViewData(){
        this.ejecutivosTable.setItems(FXCollections.observableList(EjecutivosDataSource.Ejecutivos()));
    }
}
