package es.uji.al341823.telefonia.facturacion.tarifas;

import es.uji.al341823.telefonia.llamadas.Llamada;

import java.time.LocalDateTime;
import java.time.temporal.TemporalField;

/**
 * Created by Juanjo on 28/03/2017.
 */
public abstract class TarifaExtra extends Tarifa {

	private final Tarifa tarifaBase;
	private final TemporalField unidadPeriodo;
	private final float inicioPeriodo;
	private final float finPeriodo;

	public TarifaExtra(Tarifa tarifaBase, float precio, TemporalField unidadPeriodo, int inicioPeriodo, int finPeriodo) {
		super(precio);

		this.tarifaBase = tarifaBase;
		this.unidadPeriodo = unidadPeriodo;
		this.inicioPeriodo = inicioPeriodo;
		this.finPeriodo = finPeriodo;
	}

	public TarifaExtra(Tarifa tarifaBase, float precio, TemporalField unidadMomento, int momento) {
		this(tarifaBase, precio, unidadMomento, momento, momento + 1);
	}

	public Tarifa getTarifaBase() {
		return this.tarifaBase;
	}

	@Override
	public float getCosteLlamada(Llamada llamada) {
		float costeBase = this.tarifaBase.getCosteLlamada(llamada);

		LocalDateTime fecha = llamada.getFecha();

		if ((fecha.get(this.unidadPeriodo) > this.inicioPeriodo) &&
				(fecha.get(this.unidadPeriodo) < this.finPeriodo)) {
			float costeExtra = super.getPrecio();

			if (costeExtra < costeBase)
				return costeExtra;
		}

		return costeBase;
	}
}
