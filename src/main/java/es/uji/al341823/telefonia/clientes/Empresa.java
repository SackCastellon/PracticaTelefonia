/*
 * Copyright (c) 2017. Esta obra está sujeta a la licencia Reconocimiento 4.0 Internacional de Creative Commons.
 * Para ver una copia de esta licencia, visite http://creativecommons.org/licenses/by/4.0/.
 */

package es.uji.al341823.telefonia.clientes;

import es.uji.al341823.telefonia.facturacion.tarifas.Tarifa;
import javafx.util.Pair;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Juanjo González (al341823)
 * @author David Agost (al341819)
 * @since 0.1
 */
public class Empresa extends Cliente {


	private static final long serialVersionUID = -2596901756813162732L;

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

	public static List<String> getIdDatos() {
		List<String> list = new LinkedList<>();

		list.add(ID_NIF);
		list.add(ID_NOMBRE);
		list.add(ID_DIRECCION);
		list.add(ID_EMAIL);
		list.add(ID_FECHA);
		list.add(ID_TARIFA);

		return list;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder("Empresa:\n");

		for (Pair<String, Object> pair : this.getDatos()) {
			builder.append(pair.getKey());
			builder.append(": ");
			builder.append(pair.getValue());
		}

		return builder.toString();
	}
}
