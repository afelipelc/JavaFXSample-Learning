package bankutim.model;

/**
 * Created by felipe on 31/05/17.
 */
public class Estado {

    private String nombre;

    public Estado() {
    }

    public Estado(String nombre) {
        this.nombre = nombre;
    }



    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
