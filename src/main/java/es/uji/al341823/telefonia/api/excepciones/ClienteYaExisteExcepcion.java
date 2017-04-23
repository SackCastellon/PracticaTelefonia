/*
 * Copyright (c) 2017. Esta obra está sujeta a la licencia Reconocimiento 4.0 Internacional de Creative Commons.
 * Para ver una copia de esta licencia, visite http://creativecommons.org/licenses/by/4.0/.
 */

package es.uji.al341823.telefonia.api.excepciones;

/**
 * @author David Agost (al341819)
 * @since 0.2
 */
public class ClienteYaExisteExcepcion extends Exception {

	private static final long serialVersionUID = -8279809112246865806L;

	/**
	 * Excepción que se lanza cuando se intenta, normalmente añadir, un cliente que ya existe
	 */
	public ClienteYaExisteExcepcion() {
		super("Cliente ya existe");
	}
}
