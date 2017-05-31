/*
 * Copyright (c) 2017. Esta obra está sujeta a la licencia Reconocimiento 4.0 Internacional de Creative Commons.
 * Para ver una copia de esta licencia, visite http://creativecommons.org/licenses/by/4.0/.
 */

package es.uji.al341823.telefonia.facturacion.tarifas;

import java.time.temporal.ChronoField;

/**
 * @author Juanjo González (al341823)
 * @author David Agost (al341819)
 * @since 0.3
 */
public class TarifaHoras extends TarifaExtra {


	private static final long serialVersionUID = 8877298626852066178L;

	public TarifaHoras(Tarifa tarifaBase, float precio, int inicioPeriodo, int finPeriodo) {
		super(tarifaBase, precio, ChronoField.HOUR_OF_DAY, inicioPeriodo, finPeriodo);
	}
}
