package bankutim.model;

/**
 * Created by felipe on 18/05/17.
 */
public abstract class Persona {

    private int id;
    private String nombre="";
    private String apellidos="";
    private String domicilio="";
    private Estado estado = null;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public String getNombreCompleto() {
        return nombre + " " + apellidos;
    }
    public String toString() {
        return nombre + " " + apellidos;
    }
}
