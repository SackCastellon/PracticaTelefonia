package es.uji.al341823.telefonia.api.excepciones;

/**
 * Created by al341819 on 14/03/17.
 */
public class ClienteNoExisteExcepcion extends Exception {

	private static final long serialVersionUID = -894414031355860023L;

	public ClienteNoExisteExcepcion() {
		super("Cliente no existe");
	}
}
