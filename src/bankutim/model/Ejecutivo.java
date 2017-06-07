package bankutim.model;

/**
 * Created by felipe on 17/05/17.
 */
public class Ejecutivo extends Persona{

    private Sucursal sucursal; //sucursal donde trabaja (FK)

    public Ejecutivo() {
    }

    public Ejecutivo(int id, String nombre) {
        this.setId(id);
        this.setNombre(nombre);
    }

    public Sucursal getSucursal() {
        return sucursal;
    }

    public void setSucursal(Sucursal sucursal) {
        this.sucursal = sucursal;
    }
}
