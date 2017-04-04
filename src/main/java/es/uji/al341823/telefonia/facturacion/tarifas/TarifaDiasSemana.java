package es.uji.al341823.telefonia.facturacion.tarifas;

import java.time.temporal.ChronoField;

/**
 * @author Juanjo González (al341823)
 * @author David Agost (al341819)
 * @since 0.3
 */
public class TarifaDiasSemana extends TarifaExtra {

	private static final long serialVersionUID = 8531742493015708355L;

	public TarifaDiasSemana(Tarifa tarifaBase, float precio, int momento) {
		super(tarifaBase, precio, ChronoField.DAY_OF_WEEK, momento);
	}
}
