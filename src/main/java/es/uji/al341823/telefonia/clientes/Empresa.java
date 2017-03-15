package es.uji.al341823.telefonia.clientes;

import es.uji.al341823.telefonia.facturacion.Tarifa;

import java.time.LocalDateTime;

/**
 * @author Juanjo González (al341823)
 * @author David Agost (al341819)
 * @since 0.1
 */
public class Empresa extends Cliente {

	private static final long serialVersionUID = -5040781096494997665L;

	/**
	 * @param nombre    Nombre de la empresa
	 * @param nif       NIF de la empresa
	 * @param direccion Direcciónde de la empresa
	 * @param email     Email de la empresa
	 * @param fechaAlta Fecha se alta de la empresa
	 * @param tarifa    Tarifa contratada por la empresa
	 */
	public Empresa(String nombre, String nif, Direccion direccion, String email, LocalDateTime fechaAlta, Tarifa tarifa) {
		super(nombre, nif, direccion, email, fechaAlta, tarifa);
	}
}
