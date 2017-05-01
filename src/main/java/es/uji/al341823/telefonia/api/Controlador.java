/*
 * Copyright (c) 2017. Esta obra está sujeta a la licencia Reconocimiento 4.0 Internacional de Creative Commons.
 * Para ver una copia de esta licencia, visite http://creativecommons.org/licenses/by/4.0/.
 */

package es.uji.al341823.telefonia.api;

/**
 * Created by al341819 on 25/04/17.
 */
public interface Controlador {
	void seleccionaCliente();

	void seleccionaLlamada();

	void seleccionaFactura();

	void añadeCliente();

	void añadeLlamada();

	void añadeFactura();

	void eliminaCliente();

	void eliminaLlamada();

	void eliminaFactura();
}
