package es.uji.al341823.telefonia.facturacion.tarifas;

/**
 * Created by Juanjo on 28/03/2017.
 */
public abstract class TarifaExtra extends Tarifa {

	public TarifaExtra(Tarifa tarifaBase, float precio) {
		super(precio);
	}
}
