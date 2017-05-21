/*
 * Copyright (c) 2017. Esta obra está sujeta a la licencia Reconocimiento 4.0 Internacional de Creative Commons.
 * Para ver una copia de esta licencia, visite http://creativecommons.org/licenses/by/4.0/.
 */

package es.uji.al341823.telefonia.facturacion.tarifas;

import es.uji.al341823.telefonia.llamadas.Llamada;

import java.time.LocalDateTime;
import java.time.temporal.TemporalField;
import java.util.Locale;

/**
 * @author Juanjo González (al341823)
 * @author David Agost (al341819)
 * @since 0.3
 */
public abstract class TarifaExtra extends Tarifa {

	private static final long serialVersionUID = -8478572940082399433L;

	private final Tarifa tarifaBase;
	private final TemporalField unidadTemporal;
	private final int inicioPeriodo;
	private final int finPeriodo;

	TarifaExtra(Tarifa tarifaBase, float precio, TemporalField unidadTemporal, int inicioPeriodo, int finPeriodo) {
		super(precio);

		if (tarifaBase == null)
			throw new IllegalArgumentException("La tarifa base no puede ser null");

		if (unidadTemporal == null)
			throw new IllegalArgumentException("La unidad temporal base no puede ser null");

		this.tarifaBase = tarifaBase;
		this.unidadTemporal = unidadTemporal;
		this.inicioPeriodo = inicioPeriodo;
		this.finPeriodo = finPeriodo;
	}

	@Override
	public float getCosteLlamada(Llamada llamada) {
		float costeBase = this.tarifaBase.getCosteLlamada(llamada);

		LocalDateTime fecha = llamada.getFecha();

		if ((fecha.get(this.unidadTemporal) >= this.inicioPeriodo) &&
				(fecha.get(this.unidadTemporal) <= this.finPeriodo)) {
			float costeExtra = (this.getPrecio() * llamada.getDuracionLlamada()) / 60f;

			if (costeExtra < costeBase)
				return costeExtra;
		}

		return costeBase;
	}

	public Tarifa getTarifaBase() {
		return this.tarifaBase;
	}

	@Override
	public String toString() {
		if (this.inicioPeriodo == this.finPeriodo)
			return String.format("Tarifa Extra: %.2f €/min - Durante %s %d", this.getPrecio(),
					this.unidadTemporal.getDisplayName(new Locale("es", "ES")), this.inicioPeriodo);
		else
			return String.format("Tarifa Extra: %.2f €/min - Durante %s %d-%d", this.getPrecio(),
					this.unidadTemporal.getDisplayName(new Locale("es", "ES")), this.inicioPeriodo,
					this.finPeriodo);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if ((o == null) || (this.getClass() != o.getClass())) return false;
		if (!super.equals(o)) return false;

		TarifaExtra that = (TarifaExtra) o;

		return (this.inicioPeriodo == that.inicioPeriodo) && (this.finPeriodo == that.finPeriodo) &&
				this.unidadTemporal.equals(that.unidadTemporal);
	}

	@Override
	public int hashCode() {
		int result = super.hashCode();
		result = (31 * result) + this.unidadTemporal.hashCode();
		result = (31 * result) + this.inicioPeriodo;
		result = (31 * result) + this.finPeriodo;
		return result;
	}
}
