package bankutim.model;

/**
 * Created by felipe on 17/05/17.
 */
public  class Ejecutivo extends Persona{
    private int id;
    private String nombre="";
    private String domicilio="";

    public Ejecutivo() {
    }

    public Ejecutivo(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

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

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    @Override
    public String toString() {
        return nombre ;
    }
}
