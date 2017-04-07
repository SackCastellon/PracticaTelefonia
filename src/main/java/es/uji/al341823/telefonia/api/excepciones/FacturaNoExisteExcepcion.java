package es.uji.al341823.telefonia.api.excepciones;

/**
 * @author David Agost (al341819)
 * @since 0.2
 */
public class FacturaNoExisteExcepcion extends Exception {

	private static final long serialVersionUID = 8335451079295606800L;

	/**
	 * Excepción que se lanza cuando se intenta realizar alguna acción con una factura que no existe
	 */
	public FacturaNoExisteExcepcion() {
		super("Factura no existe");
	}
}
