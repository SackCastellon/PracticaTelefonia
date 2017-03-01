package es.uji.al341823.telefonia.facturacion;

/**
 * @author Juanjo González (al341823)
 * @author David Agost (al341819)
 * @since 0.1
 */
public class TarifaTelefonica {

	/** Precio por minuto de la tarifa (€/min) */
	private final float precio;

	/**
	 * Crea una tarifa con un precio determinado
	 *
	 * @param precio Precio de la tarifa
	 */
	public TarifaTelefonica(float precio) {
		this.precio = precio;
	}

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
		return "Precio=" + precio;
	}
}
