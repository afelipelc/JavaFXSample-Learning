package bankutim.components.cuentas;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

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

    boolean actionResult = false, isEditable = true; //use for user action result
    Stage primaryStage;

    public boolean getActionResult() {
        return actionResult;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
