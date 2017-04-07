package es.uji.al341823.telefonia.api.fabricas;

/**
 * Clase que contiene los diferentes tipos de tarifas
 *
 * @author Juanjo González (al341823)
 * @author David Agost (al341819)
 * @since 0.3
 */
public class TipoTarifa {

	/**
	 * Enumeración de los posibles tipos de tarifas base
	 */
	public enum Base {
		BASICA
	}

	/**
	 * Enumeración de los posibles tipos de tarifas extra
	 */
	public enum Extra {
		TARDES, DOMINGOS
	}
}
