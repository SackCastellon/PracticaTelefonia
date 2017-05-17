/*
 * Copyright (c) 2017. Esta obra está sujeta a la licencia Reconocimiento 4.0 Internacional de Creative Commons.
 * Para ver una copia de esta licencia, visite http://creativecommons.org/licenses/by/4.0/.
 */

package es.uji.al341823.telefonia.api.excepciones;

/**
 * @author Juanjo González (al341823)
 * @since 0.4
 */
public class ObjetoYaExisteException extends Exception {

	/**
	 * Excepción que se lanza cuando se intenta añadir un objeto que ya existe
	 */
	public ObjetoYaExisteException(String objeto) {
		super(objeto + " ya existe");
	}
}
