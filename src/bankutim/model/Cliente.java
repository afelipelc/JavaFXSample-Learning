package bankutim.model;

/**
 * Created by felipe on 17/05/17.
 */
public class Cliente extends Persona {

    public Cliente() {
    }

    public Cliente(int id, String nombre, String apellidos) {
        this.setId(id);
        this.setNombre(nombre);
        this.setApellidos(apellidos);
    }

}
