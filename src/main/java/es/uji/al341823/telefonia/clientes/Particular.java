package es.uji.al341823.telefonia.clientes;

import es.uji.al341823.telefonia.facturacion.TarifaTelefonica;

import java.time.LocalDateTime;

/**
 * @author Juanjo González (al341823)
 * @author David Agost (al341819)
 * @since 0.1
 */
public class Particular extends Cliente {

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
	public Particular(String nombre, String apellidos, String nif, DireccionPostal direccion, String email, LocalDateTime fechaAlta, TarifaTelefonica tarifa) {
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

	@Override
	public String toString() {
		return "Cliente{" +
				"nombre='" + getNombre() + '\'' +
				", apellidos='" + apellidos + '\'' +
				", nif='" + getNif() + '\'' +
				", direccion=" + getDireccion() +
				", email='" + getEmail() + '\'' +
				", fechaAlta=" + getFecha() +
				", tarifa=" + getTarifa() +
				'}';
	}
}
