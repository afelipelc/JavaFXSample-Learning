package bankutim.model;

import java.util.Date;

/**
 * Created by felipe on 17/05/17.
 */
public class Cuenta {

    //id, titular, sucursal de apertura, tipo de cuenta –ahorro, débito, inversión-, fecha apertura, saldo, plazo en días.
    private int id, dias;
    private Cliente titular;
    private Sucursal sucursalApertura;
    private TipoCuenta tipoCuenta; //crear un enumerador
    private Date fechaApertura;
    private float saldo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDias() {
        return dias;
    }

    public void setDias(int dias) {
        this.dias = dias;
    }

    public Cliente getTitular() {
        return titular;
    }

    public void setTitular(Cliente titular) {
        this.titular = titular;
    }

    public Sucursal getSucursalApertura() {
        return sucursalApertura;
    }

    public void setSucursalApertura(Sucursal sucursalApertura) {
        this.sucursalApertura = sucursalApertura;
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
