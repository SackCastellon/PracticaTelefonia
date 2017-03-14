package es.uji.al341823.telefonia.excepciones;

/**
 * Created by al341819 on 14/03/17.
 */
public class FechaNoValidaExcepcion extends Exception{

    public FechaNoValidaExcepcion(){
        super("Fecha de inicio tiene que ser anterior a fecha de fin");
    }
}
