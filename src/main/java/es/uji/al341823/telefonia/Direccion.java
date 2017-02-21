package es.uji.al341823.telefonia;

/**
 * Created by Juanjo on 21/2/2017.
 */
public class Direccion {

    private final int codigoPostal;
    private final String provincia;
    private final String poblacion;

    public Direccion(int codigoPostal, String provincia, String poblacion) {
        this.codigoPostal = codigoPostal;
        this.provincia = provincia;
        this.poblacion = poblacion;
    }

    public int getCodigoPostal() {
        return codigoPostal;
    }

    public String getProvincia() {
        return provincia;
    }

    public String getPoblacion() {
        return poblacion;
    }
}
