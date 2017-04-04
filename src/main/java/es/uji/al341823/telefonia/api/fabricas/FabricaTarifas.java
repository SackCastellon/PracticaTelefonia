package es.uji.al341823.telefonia.api.fabricas;

import es.uji.al341823.telefonia.facturacion.tarifas.*;

/**
 * Created by al341819 on 4/04/17.
 */
public class FabricaTarifas {

	public Tarifa getTarifaBase(TipoTarifa.Base tarifa) {
		switch (tarifa) {
			case BASICA:
				return new TarifaBasica(0.15f);
		}

		return null;
	}

	public TarifaExtra getTarifaExtra(Tarifa tarifaBase, TipoTarifa.Extra tarifa) {
		switch (tarifa) {
			case TARDES:
				return new TarifaHoras(tarifaBase, 0.05f, 16, 20);
			case DOMINGOS:
				return new TarifaDiasSemana(tarifaBase, 0, 7);
		}

		return null;
	}
}
