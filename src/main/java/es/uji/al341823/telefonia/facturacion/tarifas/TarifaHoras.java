package es.uji.al341823.telefonia.facturacion.tarifas;

import java.time.temporal.ChronoField;

/**
 * @author Juanjo González (al341823)
 * @author David Agost (al341819)
 * @since 0.3
 */
public class TarifaHoras extends TarifaExtra {

	private static final long serialVersionUID = -4769818361637738499L;

	public TarifaHoras(Tarifa tarifaBase, float precio, int inicioPeriodo, int finPeriodo) {
		super(tarifaBase, precio, ChronoField.HOUR_OF_DAY, inicioPeriodo, finPeriodo);
	}
}
