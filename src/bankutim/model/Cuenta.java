package bankutim.model;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by felipe on 17/05/17.
 */
public class Cuenta {
    //número de cuenta único, titular, sucursal de apertura, tipo de cuenta –ahorro, débito, inversión-, fecha apertura, saldo, plazo en días.

    private int id, plazo;
    private Cliente titular;
    private Sucursal sucursal;
    private TipoCuenta tipoCuenta;
    private Date fechaApertura;
    private float saldo;

    //historial de transacciones
    private List<Transaccion> transacciones = new ArrayList<Transaccion>();

    public List<Transaccion> Transacciones() {
        return transacciones;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPlazo() {
        return plazo;
    }

    public void setPlazo(int plazo) {
        this.plazo = plazo;
    }

    public Cliente getTitular() {
        return titular;
    }

    public void setTitular(Cliente titular) {
        this.titular = titular;
    }

    public Sucursal getSucursal() {
        return sucursal;
    }

    public void setSucursal(Sucursal sucursal) {
        this.sucursal = sucursal;
    }

    public TipoCuenta getTipoCuenta() {
        return tipoCuenta;
    }

    public void setTipoCuenta(TipoCuenta tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    public Date getFechaApertura() {
        return fechaApertura;
    }

    public void setFechaApertura(Date fechaApertura) {
        this.fechaApertura = fechaApertura;
    }

    public float getSaldo() {
        return saldo;
    }

    public void setSaldo(float saldo) {
        this.saldo = saldo;
    }
}
