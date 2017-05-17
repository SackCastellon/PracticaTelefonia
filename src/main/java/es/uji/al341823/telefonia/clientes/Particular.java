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

	private static final long serialVersionUID = 6968740004850893790L;

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

	public static List<String> getNombreDatos() {
		List<String> list = new LinkedList<>();

		list.add("NIF");
		list.add("Nombre");
		list.add("Apellidos");
		list.add("Dirección");
		list.add("Email");
		list.add("Fecha de alta");
		list.add("Tarifa");

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
		// TODO Hacer que use "getDatos()"
		return "Particular:\n" +
				"\tNombre: " + this.getNombre() + '\n' +
				"\tApellidos: " + this.getApellidos() + '\n' +
				"\tNIF: " + this.getNif() + '\n' +
				"\tDirección: " + this.getDireccion() + '\n' +
				"\tEmail: " + this.getEmail() + '\n' +
				"\tFecha de Alta: " + this.getFecha() + '\n' +
				"\tTarifas:\n" +
				"\t\t" + this.getTarifa();
	}

	@Override
	public List<Pair<String, Object>> getDatos() {
		List<Pair<String, Object>> list = super.getDatos();

		list.add(2, new Pair<>("Apellidos", this.getApellidos()));

		return list;
	}
}
