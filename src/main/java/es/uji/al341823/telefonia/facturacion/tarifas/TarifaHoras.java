package es.uji.al341823.telefonia.facturacion.tarifas;

import java.time.temporal.ChronoField;
import java.time.temporal.TemporalField;

/**
 * Created by al341819 on 28/03/17.
 */
public class TarifaHoras extends TarifaExtra{


    public TarifaHoras(Tarifa tarifaBase, float precio, int inicioPeriodo, int finPeriodo) {
        super(tarifaBase, precio, ChronoField.HOUR_OF_DAY, inicioPeriodo, finPeriodo);
    }

    public TarifaHoras(Tarifa tarifaBase, float precio, int momento) {
        super(tarifaBase, precio, ChronoField.HOUR_OF_DAY, momento);
    }
}
