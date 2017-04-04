package es.uji.al341823.telefonia.facturacion.tarifas;

/**
 * @author Juanjo González (al341823)
 * @author David Agost (al341819)
 * @since 0.3
 */
public class TarifaBasica extends Tarifa {

	private static final long serialVersionUID = 2220349570647412451L;

	public TarifaBasica(float precio) {
		super(precio);
	}

	@Override
	public String getDescripcion() {
		return "Tarifa Basica " + this.getPrecio() + " €/min";
	}
}
