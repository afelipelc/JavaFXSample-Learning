package bankutim.components.cuentas;

import bankutim.MainApp;
import bankutim.datasource.CuentasDataSource;
import bankutim.datasource.TransaccionesDataSource;
import bankutim.model.Cuenta;
import bankutim.model.Transaccion;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by felipe on 16/06/17.
 */
public class EstadoCuentaController extends BorderPane implements Initializable {
    @FXML BorderPane containerBP;
    @FXML
    TextField cuentaTxt, tipoTxt, titularTxt, sucursalTxt, plazoTxt;
    @FXML
    Label saldoLbl;
    @FXML
    Label fechaAperturaDtp;
    @FXML
    Button nuevaConsultaBtn;

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

    Cuenta cuenta;

    public EstadoCuentaController(double width, double height){

        //load FXML resource
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("EstadoCuenta.fxml"));
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

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.cuentaTxt.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {

                //si presiona enter
                if(event.getCode() == KeyCode.ENTER || event.getCode() == KeyCode.TAB){
                    try {
                        //search cuenta
                        cuenta = CuentasDataSource.buscarCuenta(Integer.parseInt(cuentaTxt.getText()));
                        if(cuenta == null){
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error de cuenta");
                            alert.setHeaderText("Número de cuenta incorrecto");
                            alert.setContentText("La cuenta " + cuentaTxt.getText() + " no existe.");
                            alert.showAndWait();
                            cuentaTxt.setText("");
                            cuentaTxt.requestFocus();
                            return;
                        }

                        //if exist, show cuenta form
                        openAccount(true);
                        event.consume(); //end of event to prevent close primary stage

                        return;
                    }catch (Exception ex){

                        openAccount(false);  //clean view data
                        //show error
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("Error al ingresar cuenta");
                        alert.setContentText("Ingrese un número de cuenta válido");
                        alert.showAndWait();

                        System.out.println("Error: " + ex.getMessage() + " stack: " + ex.getCause());
                        Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            }
        });

        this.nuevaConsultaBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                cuenta = null;
                openAccount(false);
                cuentaTxt.requestFocus();
            }
        });
    }

    private void openAccount(boolean show){
        //display account data
        this.cuentaTxt.setDisable(show);
        this.tipoTxt.setText(show ? cuenta.getTipo().toString() : "");
        this.titularTxt.setText(show ? cuenta.getTitular().toString() : "");
        this.sucursalTxt.setText(show ? cuenta.getSucursalApertura().toString() : "");
        this.saldoLbl.setText(show ? cuenta.getSaldoFormat() : "");
        this.plazoTxt.setText(show ? cuenta.getPlazoDias() + "" : "");
        this.fechaAperturaDtp.setText(show ? cuenta.getFechaFormat() : "");
        setTransaccionesTableViewData();
    }

    private  void setTransaccionesTableViewData(){
        if(cuenta == null) {
            transaccionesTable.setItems(null);
            totalAbonosLbl.setText("$ 0.0");
            totalCargosLbl.setText("$ 0.0");
            return;
        }

        this.transaccionesTable.setItems(FXCollections.observableList(cuenta.getTransacciones()));

        this.totalCargosLbl.setText(NumberFormat.getCurrencyInstance(new Locale("es", "MX"))
                .format(cuenta.totalCargos()));
        this.totalAbonosLbl.setText(NumberFormat.getCurrencyInstance(new Locale("es", "MX"))
                .format(cuenta.totalDepositos()));

    }
}
