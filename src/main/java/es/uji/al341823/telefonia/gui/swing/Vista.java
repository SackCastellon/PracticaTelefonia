/*
 * Copyright (c) 2017. Esta obra está sujeta a la licencia Reconocimiento 4.0 Internacional de Creative Commons.
 * Para ver una copia de esta licencia, visite http://creativecommons.org/licenses/by/4.0/.
 */

package es.uji.al341823.telefonia.gui.swing;

import es.uji.al341823.telefonia.api.AdministradorDatos;
import es.uji.al341823.telefonia.gui.swing.controladores.Controlador;

/**
 * @author Juanjo González (al341823)
 * @since 0.4
 */
public abstract class Vista {

	private Controlador controlador;
	private AdministradorDatos modelo;

	public abstract void generarVista();

	public abstract void actualizarVista();

	public Controlador getControlador() {
		return this.controlador;
	}

	public void setControlador(Controlador controlador) {
		this.controlador = controlador;
	}

	public AdministradorDatos getModelo() {
		return this.modelo;
	}

	public void setModelo(AdministradorDatos modelo) {
		this.modelo = modelo;
	}
}
