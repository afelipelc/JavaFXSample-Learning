package bankutim.components.cuentas;

import bankutim.MainApp;
import bankutim.datasource.CuentasDataSource;
import bankutim.datasource.EjecutivosDataSource;
import bankutim.datasource.SucursalesDataSource;
import bankutim.model.Cuenta;
import bankutim.model.Ejecutivo;
import bankutim.model.Sucursal;
import bankutim.model.Transaccion;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by felipe on 22/05/17.
 */
public class TransaccionController implements Initializable{

    @FXML
    TextField idTxt, cuentaTxt, referenciaTxt, montoTxt, descripcionTxt, nombreRetiraTxt, infoIdentificacionTxt;
    @FXML
    ComboBox sucursalCmb, ejecutivoCmb;
    @FXML
    Label fechaLbl;
    @FXML
    RadioButton abonoRbtn, retiroRbtn;

    @FXML
    Button aceptarBtn, cancelarBtn;
    @FXML
    HBox datosTransaccion;

    Transaccion transaccion;
    Cuenta cuenta;


    DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    boolean actionResult = false, isAbono = false, isEditable; //use for user action result
    Stage primaryStage;

    public boolean getActionResult() {
        return actionResult;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        this.aceptarBtn.setDisable(true);

        //allow only numbers
        UnaryOperator<TextFormatter.Change> integerFilter = change -> {
            String input = change.getText();
            if (input.matches("\\d{0,7}([\\.]\\d{0,2})?")) {
                return change;
            }

            return null;
        };

        cuentaTxt.setTextFormatter(new TextFormatter<String>(integerFilter));
        montoTxt.setTextFormatter(new TextFormatter<String>(integerFilter));



        this.sucursalCmb.getItems().addAll(SucursalesDataSource.Sucursales()); //Custom object with toString method for displayData
        this.ejecutivoCmb.getItems().addAll(EjecutivosDataSource.Ejecutivos());
        this.fechaLbl.setText(format.format(new Date()));

        //evento key press
        this.cuentaTxt.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {

                //si presiona enter
                if(event.getCode() == KeyCode.ENTER){
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

                        FXMLLoader fxmlLoader = new FXMLLoader(CuentaController.class.getResource("CuentaBancaria.fxml"));
                        Parent root = (Parent) fxmlLoader.load();
                        Stage stage = new Stage();
                        stage.setTitle("Datos de la Cuenta");
                        stage.setScene(new Scene(root));
                        stage.initModality(Modality.WINDOW_MODAL);

                        stage.initOwner(((Node) event.getSource()).getScene().getWindow());
                        CuentaController controller = fxmlLoader.getController();
                        controller.setCuenta(cuenta);
                        stage.showAndWait();

                        cuentaTxt.setDisable(true); //prevent change, user can cancel
                        cuentaTxt.setStyle("-fx-font-weight: 800;");
                        aceptarBtn.setDisable(false);


                        event.consume(); //end of event to prevent close primary stage

                        return;
                    }catch (Exception ex){

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

        //event on radios
        this.abonoRbtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                isAbono = abonoRbtn.isSelected();
                nombreRetiraTxt.setText("");
                nombreRetiraTxt.setDisable(true);
                infoIdentificacionTxt.setText("");
                infoIdentificacionTxt.setDisable(true);
            }
        });

        this.retiroRbtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                isAbono = !retiroRbtn.isSelected();
                nombreRetiraTxt.setDisable(false);
                infoIdentificacionTxt.setDisable(false);
            }
        });


        this.aceptarBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //get current window
                primaryStage = (Stage) aceptarBtn.getScene().getWindow();
                if(!isEditable){ //if is not editable, close form
                    primaryStage.close();
                    return;
                }

                //first set data into object
                if(!setTransaccionData()){
                    return; //return if not set data
                }

                //perform transaccion
                transaccion = cuenta.tryTransaccion(transaccion);
                //if no error on saving, close window as OK
                if(transaccion != null) {
                    actionResult = true;

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Transacción exitosa");
                    alert.setHeaderText("La transacción fue realizada correctamente");
                    alert.setContentText("Transacción realizada. No. " + transaccion.getId() + "\nFecha: " + transaccion.getFechaFormat());
                    alert.showAndWait();

                    idTxt.setText(transaccion.getId() + "");
                    fechaLbl.setText(transaccion.getFechaFormat());
                    sucursalCmb.setDisable(true);
                    ejecutivoCmb.setDisable(true);
                    referenciaTxt.setDisable(true);
                    descripcionTxt.setDisable(true);
                    montoTxt.setDisable(true);
                    nombreRetiraTxt.setDisable(true);
                    infoIdentificacionTxt.setDisable(true);
                    abonoRbtn.setDisable(true);
                    retiroRbtn.setDisable(true);

                    aceptarBtn.setText("Cerrar");


                    //primaryStage.close();
                    isEditable = false;
                }else {
                    actionResult = false;

                    //implement a messageDialog to user notification about error

                    //primaryStage.close();
                }

            }
        });

        /**
         * mark action as cancel and close window
         */
        this.cancelarBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                actionResult = false;
                primaryStage = (Stage) cancelarBtn.getScene().getWindow();
                primaryStage.close();
            }
        });
    }

    private boolean setTransaccionData(){

        if (this.sucursalCmb.getSelectionModel().isEmpty() || this.ejecutivoCmb.getSelectionModel().isEmpty() || cuenta != null || this.referenciaTxt.getText().isEmpty() || this.montoTxt.getText().isEmpty()  ) {


            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error...");
            alert.setHeaderText("Ingreso de datos");
            alert.setContentText("Debe ingresar todos los datos solicitados.");
            //for more JavaFX Dialogs see examples at http://code.makery.ch/blog/javafx-dialogs-official/
            alert.showAndWait();

            return false;
        }

        //if is Cargo
        if(!isAbono && (nombreRetiraTxt.getText().isEmpty() || this.infoIdentificacionTxt.getText().isEmpty() )){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error...");
            alert.setHeaderText("Proporcione información de quien retira");
            alert.setContentText("Debe ingresar todos los datos de quien retira.");
            //for more JavaFX Dialogs see examples at http://code.makery.ch/blog/javafx-dialogs-official/
            alert.showAndWait();
            this.nombreRetiraTxt.requestFocus();

            return false;
        }


        //setup new transaccion, prevent data manipulation
        transaccion = new Transaccion(
                cuenta,
                this.referenciaTxt.getText(),
                this.descripcionTxt.getText(),
                this.nombreRetiraTxt.getText(),
                this.infoIdentificacionTxt.getText(),
                0,
                isAbono,
                (Sucursal) this.sucursalCmb.getSelectionModel().getSelectedItem(),
                (Ejecutivo) this.ejecutivoCmb.getSelectionModel().getSelectedItem()
        );



        return  true;
    }

}
