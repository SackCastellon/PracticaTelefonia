package es.uji.al341823.telefonia.api.fabricas;

import es.uji.al341823.telefonia.facturacion.tarifas.*;

/**
 * Clase <i>Factory</i> para las tarifas:
 * <ul>
 * <li>Tarifas Base</li>
 * <li>Tarifas Extra</li>
 * </ul>
 *
 * @author Juanjo González (al341823)
 * @author David Agost (al341819)
 * @since 0.3
 */
public class FabricaTarifas { //TODO Mover creación de tarifas dentro de TipoTarifa.class

	/**
	 * Crea una nueva tarifa base correspondiente al tipo que se indique
	 *
	 * @param tarifa El tipo de tarifa deseada
	 *
	 * @return La nueva tarifa
	 *
	 * @see TipoTarifa.Base
	 */
	public Tarifa getTarifaBase(TipoTarifa.Base tarifa) {
		switch (tarifa) {
			case BASICA:
				return new TarifaBasica(0.15f);
		}

		return null;
	}

	/**
	 * Crea una nueva tarifa extra, tomando como tarifa base la que se indique y, correspondiente al tipo que se indique
	 *
	 * @param tarifaBase La tarifa base
	 * @param tarifa     El tipo de tarifa deseada
	 *
	 * @return La nueva tarifa
	 *
	 * @see TipoTarifa.Extra
	 */
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
