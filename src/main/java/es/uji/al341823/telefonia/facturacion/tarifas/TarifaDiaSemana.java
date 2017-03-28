package es.uji.al341823.telefonia.facturacion.tarifas;

import java.time.temporal.ChronoField;

/**
 * Created by Juanjo on 28/03/2017.
 */
public class TarifaDiaSemana extends TarifaExtra {

	public TarifaDiaSemana(Tarifa tarifaBase, float precio, int inicioPeriodo, int finPeriodo) {
		super(tarifaBase, precio, ChronoField.DAY_OF_WEEK, inicioPeriodo, finPeriodo);
	}

	public TarifaDiaSemana(Tarifa tarifaBase, float precio, int momento) {
		super(tarifaBase, precio, ChronoField.DAY_OF_WEEK, momento);
	}
}
