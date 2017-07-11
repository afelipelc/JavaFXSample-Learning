package bankutim.components.cuentas;

import bankutim.datasource.CuentasDataSource;
import bankutim.model.Cuenta;
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
public class CuentasController extends BorderPane implements Initializable {
    //associating UI controls with controller
    @FXML
    BorderPane containerBP;
    @FXML
    Button agregarBtn;
    //end UI controls

    @FXML
    TableView<Cuenta> cuentasTable;
    @FXML
    TableColumn<Cuenta, Integer> numeroCol;
    @FXML
    TableColumn<Cuenta, String> titularCol;
    @FXML
    TableColumn<Cuenta, String> sucursalCol;
    @FXML
    TableColumn<Cuenta, String> tipoCtaCol;
    @FXML TableColumn<Cuenta, String> fechaAperturaCol;
    @FXML TableColumn<Cuenta, Integer> plazoCol;

    public CuentasController(double width, double height){

        //load FXML resource
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Cuentas.fxml"));
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
        numeroCol.setCellValueFactory(new PropertyValueFactory<Cuenta, Integer>("id"));
        titularCol.setCellValueFactory(new PropertyValueFactory<Cuenta, String>("titular"));
        tipoCtaCol.setCellValueFactory(new PropertyValueFactory<Cuenta, String>("tipo"));
        sucursalCol.setCellValueFactory(new PropertyValueFactory<Cuenta, String>("sucursalApertura"));
        fechaAperturaCol.setCellValueFactory(new PropertyValueFactory<Cuenta, String>("fechaFormat"));
        plazoCol.setCellValueFactory(new PropertyValueFactory<Cuenta, Integer>("plazoDias"));
        //load initial data
        setCuentasTableViewData();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.agregarBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                //load Ejecutivo window form

                try {

                    FXMLLoader fxmlLoader = new FXMLLoader(CuentaController.class.getResource("CuentaBancaria.fxml"));
                    Parent root = (Parent) fxmlLoader.load();
                    Stage stage = new Stage();
                    stage.setTitle("Datos de cuenta");
                    stage.setScene(new Scene(root));
                    stage.initModality(Modality.WINDOW_MODAL);
                    stage.initOwner(((Node) t.getSource()).getScene().getWindow()); //set window parent
                    stage.showAndWait();


                    CuentaController cuentaController = fxmlLoader.getController();
                    if (cuentaController.getActionResult()) { //if say Save
                        //add or update tableview
                        setCuentasTableViewData();

                    }


                } catch (Exception ex) {
                    System.out.println("Error: " + ex.getMessage() + " stack: " + ex.getCause());
                }
            }
        });

        this.cuentasTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                if (t.getClickCount() == 2) {
                    //open Sucursal form
                    try {
                        //get selected sucursal
                        FXMLLoader fxmlLoader = new FXMLLoader(CuentaController.class.getResource("CuentaBancaria.fxml"));
                        Parent root = (Parent) fxmlLoader.load();
                        Stage stage = new Stage();
                        stage.setTitle("Datos de Cuenta");
                        stage.setScene(new Scene(root));
                        stage.initModality(Modality.WINDOW_MODAL);

                        stage.initOwner(((Node) t.getSource()).getScene().getWindow());
                        CuentaController controller = fxmlLoader.getController();
                        Cuenta itemEdit = cuentasTable.getSelectionModel().getSelectedItem();
                        int indexedit = cuentasTable.getItems().indexOf(itemEdit);
                        controller.setCuenta(itemEdit);
                        stage.showAndWait();
                        if (controller.getActionResult()) {
                            itemEdit = controller.getCuenta();
                            System.out.println("Actualizado: " + itemEdit.toString());
                            cuentasTable.getItems().set(indexedit, itemEdit);
                        }
                    } catch (Exception ex) {
                        System.out.println("Error: " + ex.getMessage() + " stack: " + ex.getCause());
                    }
                }
            }
        });
    }

    private  void setCuentasTableViewData(){
        this.cuentasTable.setItems(FXCollections.observableList(CuentasDataSource.Cuentas()));
    }


}
