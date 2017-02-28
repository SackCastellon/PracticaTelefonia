package es.uji.al341823.telefonia.cliente;

import es.uji.al341823.telefonia.facturacion.Tarifa;

import java.time.LocalDateTime;

/**
 * @author Juanjo González (al341823)
 * @author David Agost (al341819)
 * @since 0.1
 */
public class Particular extends Cliente {

	/** Los apellidos del cliente */
	private String apellidos;

	/**
	 * @param nombre    Nombre del particular
	 * @param apellidos Apellidos del particular
	 * @param nif       NIF del particulas
	 * @param direccion Dirección del particular
	 * @param email     Email del particular
	 * @param fechaAlta Fecha de alta del particular
	 * @param tarifa    Tarifa contratada por el particular
	 */
	public Particular(String nombre, String apellidos, String nif, Direccion direccion, String email, LocalDateTime fechaAlta, Tarifa tarifa) {
		super(nombre, nif, direccion, email, fechaAlta, tarifa);

		this.apellidos = apellidos;
	}

	/**
	 * Devuelve los apellidos del cliente
	 *
	 * @return Apellidos del cliente
	 */
	public String getApellidos() {
		return apellidos;
	}

	/**
	 * Devuelve un <code>String</code> con toda la información del cliente
	 *
	 * @return Información del cliente
	 */
	@Override
	public String getInformacion() {
		StringBuilder info = new StringBuilder();

		info.append(" - Nombre: " + getNombre() + '\n');
		info.append(" - Apellidos: " + getApellidos() + '\n');
		info.append(" - NIF: " + getNif() + '\n');
		info.append(" - Dirección: " + getDireccion().toString() + '\n');
		info.append(" - Email: " + getEmail() + '\n');
		info.append(" - Fecha alta: " + getFecha().toString() + '\n');
		info.append(" - Tarifa: " + getTarifa().toString() + '\n');

		return info.toString();
	}
}
