package bankutim.components.cuentas;

import bankutim.datasource.TransaccionesDataSource;
import bankutim.model.Transaccion;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by felipe on 5/06/17.
 */
public class TransaccionesController extends BorderPane implements Initializable {
    //associating UI controls with controller
    @FXML
    BorderPane containerBP;
    @FXML
    Button agregarBtn;
    //end UI controls

    @FXML
    TableView<Transaccion> transaccionesTable;
    @FXML TableColumn<Transaccion, Integer> idCol;
    @FXML
    TableColumn<Transaccion, Integer> noCuentaCol;
    @FXML
    TableColumn<Transaccion, Float> cargoCol;
    @FXML
    TableColumn<Transaccion, Float> abonoCol;
    @FXML TableColumn<Transaccion, String> referenciaCol;
    @FXML
    TableColumn<Transaccion, String> fechaCol;
    @FXML TableColumn<Transaccion, String> sucursalCol;
    @FXML TableColumn<Transaccion, String> ejecutivoCol;
    @FXML TableColumn<Transaccion, String> nombreRetiraCol, identificacionCol;
    @FXML
    Label totalCargosLbl, totalAbonosLbl;

    public TransaccionesController(double width, double height){

        //load FXML resource
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Transacciones.fxml"));
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
        idCol.setCellValueFactory(new PropertyValueFactory<Transaccion, Integer>("id"));
        noCuentaCol.setCellValueFactory(new PropertyValueFactory<Transaccion, Integer>("cuenta"));
        cargoCol.setCellValueFactory(new PropertyValueFactory<Transaccion, Float>("cargoFormat"));
        abonoCol.setCellValueFactory(new PropertyValueFactory<Transaccion, Float>("abonoFormat"));
        referenciaCol.setCellValueFactory(new PropertyValueFactory<Transaccion, String>("referencia"));
        fechaCol.setCellValueFactory(new PropertyValueFactory<Transaccion, String>("fechaFormat"));
        sucursalCol.setCellValueFactory(new PropertyValueFactory<Transaccion, String>("sucursal"));
        ejecutivoCol.setCellValueFactory(new PropertyValueFactory<Transaccion, String>("ejecutivo"));
        nombreRetiraCol.setCellValueFactory(new PropertyValueFactory<Transaccion, String>("nombreRetira"));
        identificacionCol.setCellValueFactory(new PropertyValueFactory<Transaccion, String>("infoIdentificacion"));
        //load initial data
        setTransaccionesTableViewData();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.agregarBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                //load Ejecutivo window form

                try {

                    FXMLLoader fxmlLoader = new FXMLLoader(TransaccionController.class.getResource("Transaccion.fxml"));
                    Parent root = (Parent) fxmlLoader.load();
                    Stage stage = new Stage();
                    stage.setTitle("Realizar nueva transacción");
                    stage.setScene(new Scene(root));
                    stage.initModality(Modality.WINDOW_MODAL);
                    stage.initOwner(((Node) t.getSource()).getScene().getWindow()); //set window parent
                    stage.showAndWait();


                    TransaccionController transaccionController = fxmlLoader.getController();
                    if (transaccionController.getActionResult()) { //if say Save
                        //add or update tableview
                        setTransaccionesTableViewData();

                    }


                } catch (Exception ex) {
                    System.out.println("Error: " + ex.getMessage() + " stack: " + ex.getCause());
                }
            }
        });
    }

    private  void setTransaccionesTableViewData(){
        this.transaccionesTable.setItems(FXCollections.observableList(TransaccionesDataSource.Transacciones()));

        this.totalCargosLbl.setText(NumberFormat.getCurrencyInstance(new Locale("es", "MX"))
                .format(TransaccionesDataSource.totalCargos()));
        this.totalAbonosLbl.setText(NumberFormat.getCurrencyInstance(new Locale("es", "MX"))
                .format(TransaccionesDataSource.totalDepositos()));

    }


}