package bankutim.model;

import bankutim.datasource.TransaccionesDataSource;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by felipe on 17/05/17.
 */
public class Transaccion {
    private  int id;
    private Cuenta cuenta;
    private String referencia, descripcion, infoIdentificacion;
    private  float cargo, abono;
    private boolean isAbono;
    private Date fecha;
    private Sucursal sucursal;
    private Ejecutivo ejecutivo;

    public Transaccion(Cuenta cuenta, String referencia, String descripcion, String infoIdentificacion, float monto, boolean isAbono, Sucursal sucursal, Ejecutivo ejecutivo) {

        //current id for transaccion
        this.id = (TransaccionesDataSource.Transacciones().size() + 1);

        this.cuenta = cuenta;
        this.referencia = referencia;
        this.descripcion = descripcion;
        this.infoIdentificacion = infoIdentificacion;
        this.isAbono = isAbono;
        this.sucursal = sucursal;
        this.ejecutivo = ejecutivo;

        //set cargo o abono
        this.abono = isAbono ? monto : 0;
        this.cargo = !isAbono ? monto : 0;
    }

    public int getId() {
        return id;
    }

    public Cuenta getCuenta() {
        return cuenta;
    }

    public int getNoCuentaCol(){
        return cuenta.getId();
    }

    public String getReferencia() {
        return referencia;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getInfoIdentificacion() {
        return infoIdentificacion;
    }

    public float getCargo() {
        return cargo;
    }

    public String getCargoFormat(){
        return  NumberFormat.getCurrencyInstance(new Locale("es", "MX")).format(this.cargo);
    }

    public float getAbono() {
        return abono;
    }


    public String getAbonoFormat(){
        return  NumberFormat.getCurrencyInstance(new Locale("es", "MX")).format(this.abono);
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getFechaFormat(){
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return  format.format(fecha);
    }

    public Sucursal getSucursal() {
        return sucursal;
    }


    public Ejecutivo getEjecutivo() {
        return ejecutivo;
    }

    public boolean isAbono() {
        return isAbono;
    }

}
