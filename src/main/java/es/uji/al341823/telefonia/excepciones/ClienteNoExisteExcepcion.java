package es.uji.al341823.telefonia.excepciones;

/**
 * Created by al341819 on 14/03/17.
 */
public class ClienteNoExisteExcepcion extends Exception {

    public ClienteNoExisteExcepcion(){
        super("Cliente no existe");
    }
}
