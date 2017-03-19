package es.uji.al341823.telefonia.clientes;

import es.uji.al341823.telefonia.facturacion.Tarifa;

import java.time.LocalDateTime;

/**
 * @author Juanjo González (al341823)
 * @author David Agost (al341819)
 * @since 0.1
 */
public class Particular extends Cliente {

	private static final long serialVersionUID = 6968740004850893790L;
	/**
	 * Los apellidos del cliente
	 */
	private final String apellidos;

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
		return this.apellidos;
	}

	@Override
	public String toString() {
		return "Cliente{" +
				"nombre='" + this.getNombre() + '\'' +
				", apellidos='" + this.apellidos + '\'' +
				", nif='" + this.getNif() + '\'' +
				", dir=" + this.getDireccion() +
				", email='" + this.getEmail() + '\'' +
				", alta=" + this.getFecha() +
				", tarifa=" + this.getTarifa() +
				'}';
	}
}
