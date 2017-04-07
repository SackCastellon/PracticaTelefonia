package es.uji.al341823.telefonia.api.excepciones;

/**
 * @author David Agost (al341819)
 * @since 0.2
 */
public class FechaNoValidaExcepcion extends Exception {

	private static final long serialVersionUID = -7564983546319573279L;

	/**
	 * Excepción que se lanza cuando se intenta realizar alguna acción con una feche o intevalo de tiempo que no es
	 * valido
	 */
	public FechaNoValidaExcepcion() {
		super("Fecha de inicio tiene que ser anterior a fecha de fin");
	}
}
