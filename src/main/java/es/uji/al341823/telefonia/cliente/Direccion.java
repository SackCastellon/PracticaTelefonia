package es.uji.al341823.telefonia.cliente;

/**
 * @author Juanjo González (al341823)
 * @author David Agost (al341819)
 * @since 0.1
 */
public class Direccion {

	/** Codigo postal */
	private final int codigoPostal;
	/** Provincia */
	private final String provincia;
	/** Población */
	private final String poblacion;

	/**
	 * Crea una dirección corespondiente a los dato introducidos
	 *
	 * @param codigoPostal Codigo postal
	 * @param provincia    Provincia
	 * @param poblacion    Población
	 */
	public Direccion(int codigoPostal, String provincia, String poblacion) {
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

	/**
	 * Genera una dirección a partir de un <code>String</code> con el formato "<code>CP, Provincia, Población</code>"
	 *
	 * @param direccion Dirección en formato "<code>CP, Provincia, Población</code>"
	 *
	 * @return La dirección creada
	 */
	public static Direccion parse(String direccion) {
		String[] campos = direccion.split(",", 3);

		//Codigo Postal
		int cp = Integer.parseInt(campos[0]);

		//Provincia
		String provincia = campos[1];

		//Poblacion
		String poblacion = campos[2];

		return new Direccion(cp, provincia, poblacion);
	}

	@Override
	public String toString() {
		return codigoPostal + ", " + provincia + ", " + poblacion;
	}
}
