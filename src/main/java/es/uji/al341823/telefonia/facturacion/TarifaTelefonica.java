package es.uji.al341823.telefonia.facturacion;

/**
 * @author Juanjo González (al341823)
 * @author David Agost (al341819)
 * @since 0.1
 */
public class TarifaTelefonica {

	/** Codigo de la tarifa */
	private final int codigoTarifa;
	/** Precio por minuto de la tarifa (€/min) */
	private final float precio;

	/**
	 * Crea una tarifa con un codigo
	 *
	 * @param codigoTarifa Codigo de la tarifa
	 */
	public TarifaTelefonica(int codigoTarifa) {
		this.codigoTarifa = codigoTarifa;
		this.precio = 0.10f; // TODO
	}

	/**
	 * Devuelve el codigo de la tarifa
	 *
	 * @return Codigo de la tarifa
	 */
	public int getCodigoTarifa() { return codigoTarifa; }

	/**
	 * Devuelve le precio por minuto de la tarifa (€/min)
	 *
	 * @return Precio por minuto
	 */
	public float getPrecio() {
		return precio;
	}

	@Override
	public String toString() {
		return "Tarifa=" + codigoTarifa + ", Precio=" + precio;
	}
}
