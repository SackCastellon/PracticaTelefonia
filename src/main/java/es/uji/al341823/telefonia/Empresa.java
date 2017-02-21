package es.uji.al341823.telefonia;

import java.time.LocalDateTime;

/**
 * Created by Juanjo on 21/2/2017.
 */
public class Empresa {

    private String nombre;
    private String nif;
    private Direccion direccion;
    private String email;
    private LocalDateTime fechaAlta;
    private Tarifa tarifa;

    public Empresa(String nombre, String nif, Direccion direccion, String email, LocalDateTime fechaAlta, Tarifa tarifa) {
        this.nombre = nombre;
        this.nif = nif;
        this.direccion = direccion;
        this.email = email;
        this.fechaAlta = fechaAlta;
        this.tarifa = tarifa;
    }
}
