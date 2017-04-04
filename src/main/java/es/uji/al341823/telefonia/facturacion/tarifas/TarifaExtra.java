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
	private final TemporalField unidadPeriodo;
	private final int inicioPeriodo;
	private final int finPeriodo;

	TarifaExtra(Tarifa tarifaBase, float precio, TemporalField unidadPeriodo, int inicioPeriodo, int finPeriodo) {
		super(precio);

		this.tarifaBase = tarifaBase;
		this.unidadPeriodo = unidadPeriodo;
		this.inicioPeriodo = inicioPeriodo;
		this.finPeriodo = finPeriodo;
	}

	TarifaExtra(Tarifa tarifaBase, float precio, TemporalField unidadMomento, int momento) {
		this(tarifaBase, precio, unidadMomento, momento, momento);
	}

	@Override
	public float getCosteLlamada(Llamada llamada) {
		float costeBase = this.tarifaBase.getCosteLlamada(llamada);

		LocalDateTime fecha = llamada.getFecha();

		if ((fecha.get(this.unidadPeriodo) >= this.inicioPeriodo) &&
				(fecha.get(this.unidadPeriodo) <= this.finPeriodo)) {
			float costeExtra = this.getPrecio();

			if (costeExtra < costeBase)
				return costeExtra;
		}

		return costeBase;
	}

	@Override
	public String getDescripcion() {
		if (this.inicioPeriodo == this.finPeriodo)
			return String.format("Tarifa Extra: %.2f €/min - Durante %s %d", this.getPrecio(),
					this.unidadPeriodo.getDisplayName(new Locale("es", "ES")), this.inicioPeriodo);
		else
			return String.format("Tarifa Extra: %.2f €/min - Durante %s %d-%d", this.getPrecio(),
					this.unidadPeriodo.getDisplayName(new Locale("es", "ES")), this.inicioPeriodo,
					this.finPeriodo);
	}

	@Override
	public String toString() {
		return "TarifaExtra{" +
				"tarifaBase=" + this.tarifaBase +
				", unidadPeriodo=" + this.unidadPeriodo.getDisplayName(new Locale("es", "ES")) +
				", inicioPeriodo=" + this.inicioPeriodo +
				", finPeriodo=" + this.finPeriodo +
				'}';
	}
}
