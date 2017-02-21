package es.uji.al341823.telefonia;

import java.time.LocalDateTime;

/**
 * Created by Juanjo on 21/2/2017.
 */
public class Particular extends Empresa {

    private String apellidos;

    public Particular(String nombre, String apellidos, String nif, Direccion direccion, String email, LocalDateTime fechaAlta, Tarifa tarifa) {
        super(nombre, nif, direccion, email, fechaAlta, tarifa);

        this.apellidos = apellidos;
    }

    public String getApellidos() {
        return apellidos;
    }
}
