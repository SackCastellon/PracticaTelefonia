package es.uji.al341823.telefonia.clientes;

/**
 * @author Juanjo González (al341823)
 * @author David Agost (al341819)
 * @since 0.1
 */
public class DireccionPostal {

	/**
	 * Codigo postal
	 */
	private final int codigoPostal;
	/**
	 * Provincia
	 */
	private final String provincia;
	/**
	 * Población
	 */
	private final String poblacion;

	/**
	 * Crea una dirección corespondiente a los dato introducidos
	 *
	 * @param codigoPostal Codigo postal
	 * @param provincia    Provincia
	 * @param poblacion    Población
	 */
	public DireccionPostal(int codigoPostal, String provincia, String poblacion) {
		this.codigoPostal = codigoPostal;
		this.provincia = provincia;
		this.poblacion = poblacion;
	}

	/**
	 * Devuelve el codigo postal de la dirección
	 *
	 * @return Codigo postal
	 */
	public int getCodigoPostal() {
		return codigoPostal;
	}

	/**
	 * Devulve la provincia de la dirección
	 *
	 * @return Provincia
	 */
	public String getProvincia() {
		return provincia;
	}

	/**
	 * Devulve la población de la dirección
	 *
	 * @return Población
	 */
	public String getPoblacion() {
		return poblacion;
	}

	@Override
	public String toString() {
		return "DireccionPostal{" +
				"codigoPostal=" + codigoPostal +
				", provincia='" + provincia + '\'' +
				", poblacion='" + poblacion + '\'' +
				'}';
	}
}
