package es.uji.al341823.telefonia.cliente;

/**
 * @author Juanjo González (al341823)
 * @author David Agost (al341819)
 * @since 0.1
 */
public class Direccion {

	private final int codigoPostal;
	private final String provincia;
	private final String poblacion;

	public Direccion(int codigoPostal, String provincia, String poblacion) {
		this.codigoPostal = codigoPostal;
		this.provincia = provincia;
		this.poblacion = poblacion;
	}

	public int getCodigoPostal() {
		return codigoPostal;
	}

	public String getProvincia() {
		return provincia;
	}

	public String getPoblacion() {
		return poblacion;
	}

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
