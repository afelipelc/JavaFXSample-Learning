package bankutim.model;

/**
 * Created by felipe on 17/05/17.
 */
public  class Ejecutivo extends Persona{


    private Sucursal sucursal;


    public Ejecutivo() {
    }

    public Ejecutivo(int id, String nombre, String apellidos) {
        this.setId(id);
        this.setNombre(nombre);
        this.setApellidos(apellidos);
    }

    public Sucursal getSucursal() {
        return sucursal;
    }

    public void setSucursal(Sucursal sucursal) {
        this.sucursal = sucursal;
    }

}
