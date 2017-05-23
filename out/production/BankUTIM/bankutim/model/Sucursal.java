package bankutim.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Created by felipe on 17/05/17.
 */
public class Sucursal {

    private SimpleIntegerProperty id = new SimpleIntegerProperty();
    private SimpleStringProperty nombre = new SimpleStringProperty();
    private SimpleStringProperty domicilio = new SimpleStringProperty();
    //...

    //constructors
    public  Sucursal(){}

    public Sucursal(int id, String nombre, String domicilio){
        this.setId(id);
        this.setNombre(nombre);
        this.setDomicilio(domicilio);
    }

    //getters,setters and properties
    public int getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getNombre() {
        return nombre.get();
    }

    public SimpleStringProperty nombreProperty() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre.set(nombre);
    }

    public String getDomicilio() {
        return domicilio.get();
    }

    public SimpleStringProperty domicilioProperty() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio.set(domicilio);
    }

    //...
}
