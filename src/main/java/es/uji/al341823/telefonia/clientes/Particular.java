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
public class Particular extends Cliente {

	public static final String ID_APELLIDOS = "Apellidos";
	private static final long serialVersionUID = 1888737854811115694L;
	/** Los apellidos del cliente */
	private final String apellidos;

	/**
	 * @param nombre     Nombre del particular
	 * @param apellidos  Apellidos del particular
	 * @param nif        NIF del particulas
	 * @param direccion  Dirección del particular
	 * @param email      Email del particular
	 * @param fechaAlta  Fecha de alta del particular
	 * @param tarifaBase Tarifa contratada por el particular
	 */
	public Particular(String nombre, String apellidos, String nif, Direccion direccion, String email, LocalDateTime fechaAlta, Tarifa tarifaBase) {
		super(nombre, nif, direccion, email, fechaAlta, tarifaBase);

		this.apellidos = apellidos;
	}

	public static List<String> getIdDatos() {
		List<String> list = new LinkedList<>();

		list.add(ID_NIF);
		list.add(ID_NOMBRE);
		list.add(ID_APELLIDOS);
		list.add(ID_DIRECCION);
		list.add(ID_EMAIL);
		list.add(ID_FECHA);
		list.add(ID_TARIFA);

		return list;
	}

	/**
	 * Devuelve los apellidos del cliente
	 *
	 * @return Apellidos del cliente
	 */
	public String getApellidos() {
		return this.apellidos;
	}

	/**
	 * Devuelve toda la información del cliente particular
	 *
	 * @return Información del cliente particular
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder("Particular:\n");

		for (Pair<String, Object> pair : this.getDatos()) {
			builder.append(pair.getKey());
			builder.append(": ");
			builder.append(pair.getValue());
		}

		return builder.toString();
	}

	@Override
	public List<Pair<String, Object>> getDatos() {
		List<Pair<String, Object>> list = super.getDatos();

		list.add(2, new Pair<>(ID_APELLIDOS, this.getApellidos()));

		return list;
	}

}
