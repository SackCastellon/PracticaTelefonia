package es.uji.al341823.telefonia.api.excepciones;

/**
 * Created by al341819 on 14/03/17.
 */
public class FacturaNoExisteExcepcion extends Exception {

	private static final long serialVersionUID = 8335451079295606800L;

	public FacturaNoExisteExcepcion() {
		super("Factura no existe");
	}
}
