package es.uji.al341823.telefonia.excepciones;

/**
 * Created by al341819 on 14/03/17.
 */
public class ClienteYaExisteExcepcion extends Exception {

    public ClienteYaExisteExcepcion() {
        super("Cliente ya existe");
    }
}
