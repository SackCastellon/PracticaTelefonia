package es.uji.al341823.telefonia.cliente;

import es.uji.al341823.telefonia.facturacion.Tarifa;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Juanjo González (al341823)
 * @author David Agost (al341819)
 * @since 0.1
 */
public class Particular extends Cliente {

    private String apellidos;

    public Particular(String nombre, String apellidos, String nif, Direccion direccion, String email, LocalDateTime fechaAlta, Tarifa tarifa) {
        super(nombre, nif, direccion, email, fechaAlta, tarifa);

        this.apellidos = apellidos;
    }

    public String getApellidos() {
        return apellidos;
    }

    @Override
    public LinkedList<String> getInformacion() {
        LinkedList<String> info = super.getInformacion();

        info.add(1, "Apellidos: " + getApellidos());

        return info;
    }
}
