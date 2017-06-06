package bankutim.model;

import bankutim.datasource.TransaccionesDataSource;
import javafx.scene.control.Alert;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by felipe on 17/05/17.
 */
public class Cuenta {
    private  int id, plazoDias;
    private Cliente titular;
    private Sucursal sucursalApertura;
    private TipoCuenta tipo;
    private Date fechaApertura;
    private float saldo;

    private List<Transaccion> transacciones = new ArrayList<>();

    public Cuenta(int id, Cliente titular, Sucursal sucursal) {
        this.fechaApertura = new Date();
        this.id = id;
        this.titular = titular;
        this.sucursalApertura = sucursal;
    }

    public int getId() {
        return id;
    }

    public int getPlazoDias() {
        return plazoDias;
    }

    public void setPlazoDias(int plazoDias) {
        this.plazoDias = plazoDias;
    }

    public Cliente getTitular() {
        return titular;
    }

    public Sucursal getSucursalApertura() {
        return sucursalApertura;
    }

    public TipoCuenta getTipo() {
        return tipo;
    }

    public void setTipo(TipoCuenta tipo) {
        this.tipo = tipo;
    }

    public Date getFechaApertura() {
        return fechaApertura;
    }

    public String getFechaFormat(){
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return  format.format(fechaApertura);
    }

    public float getSaldo() {
        return saldo;
    }

    public List<Transaccion> getTransacciones() {
        return transacciones;
    }

    public Transaccion tryTransaccion(Transaccion transaccion){

        transaccion = transaccion.isAbono() ? Depositar(transaccion) : Retirar(transaccion);
        if(transaccion != null){
            TransaccionesDataSource.addTransaccion(transaccion); //add to general list
        }
        return  transaccion;
    }

    private Transaccion Depositar(Transaccion transaccion) {

        saldo += transaccion.getAbono();
        transaccion.setFecha(new Date());

        this.transacciones.add(transaccion);

        return  transaccion;
    }

    private Transaccion Retirar(Transaccion transaccion) {

        if(saldo < transaccion.getCargo() && transaccion.getCargo() > 0){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Bank UTIM");
            alert.setHeaderText("Transacción fallida: SALDO");
            alert.setContentText("Saldo Insuficiente. \nTransacción no realizada.");
            alert.showAndWait();
            return  null;
        }

        saldo -= transaccion.getCargo();
        transaccion.setFecha(new Date());

        this.transacciones.add(transaccion);

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Bank UTIM");
        alert.setHeaderText("Transacción realizada: CORRECTO");
        alert.setContentText("Transacción exitosa.");
        alert.showAndWait();

        return  transaccion;
    }

    public String getSaldoFormat() {
        return  NumberFormat.getCurrencyInstance(new Locale("es", "MX"))
                .format(saldo);
    }
}
