package es.uji.al341823.telefonia.facturacion.tarifas;

import es.uji.al341823.telefonia.llamadas.Llamada;

/**
 * Created by Juanjo on 28/03/2017.
 */
public abstract class TarifaExtra extends Tarifa {

	private final Tarifa tarifaBase;

	public TarifaExtra(Tarifa tarifaBase, float precio) {
		super(precio);

		this.tarifaBase = tarifaBase;
	}

	public Tarifa getTarifaBase() {
		return this.tarifaBase;
	}

	@Override
	public float getCosteLlamada(Llamada llamada) {
		float costeBase = this.tarifaBase.getCosteLlamada(llamada);
		float costeExtra = super.getCosteLlamada(llamada);

		if (costeExtra < costeBase)
			return costeExtra;

		return costeBase;
	}
}
