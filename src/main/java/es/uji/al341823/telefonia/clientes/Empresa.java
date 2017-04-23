/*
 * Copyright (c) 2017. Esta obra está sujeta a la licencia Reconocimiento 4.0 Internacional de Creative Commons.
 * Para ver una copia de esta licencia, visite http://creativecommons.org/licenses/by/4.0/.
 */

package es.uji.al341823.telefonia.clientes;

import es.uji.al341823.telefonia.facturacion.tarifas.Tarifa;

import java.time.LocalDateTime;

/**
 * @author Juanjo González (al341823)
 * @author David Agost (al341819)
 * @since 0.1
 */
public class Empresa extends Cliente {

	private static final long serialVersionUID = -5040781096494997665L;

	/**
	 * @param nombre     Nombre de la empresa
	 * @param nif        NIF de la empresa
	 * @param direccion  Direcciónde de la empresa
	 * @param email      Email de la empresa
	 * @param fechaAlta  Fecha se alta de la empresa
	 * @param tarifaBase Tarifa contratada por la empresa
	 */
	public Empresa(String nombre, String nif, Direccion direccion, String email, LocalDateTime fechaAlta, Tarifa tarifaBase) {
		super(nombre, nif, direccion, email, fechaAlta, tarifaBase);
	}

	@Override
	public String toString() {
		return "Empresa:\n" +
				"\tNombre: " + this.getNombre() + '\n' +
				"\tNIF: " + this.getNif() + '\n' +
				"\tDirección: " + this.getDireccion() + '\n' +
				"\tEmail: " + this.getEmail() + '\n' +
				"\tFecha de Alta: " + this.getFecha() + '\n' +
				"\tTarifa:\n" +
				"\t\t" + this.getTarifa();
	}
}
