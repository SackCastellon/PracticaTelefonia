package es.uji.al341823.telefonia.api.excepciones;

/**
 * Created by al341819 on 14/03/17.
 */
public class ClienteYaExisteExcepcion extends Exception {

	private static final long serialVersionUID = -8279809112246865806L;

	public ClienteYaExisteExcepcion() {
		super("Cliente ya existe");
	}
}
