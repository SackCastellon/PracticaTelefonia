/*
 * Copyright (c) 2017. Esta obra está sujeta a la licencia Reconocimiento 4.0 Internacional de Creative Commons.
 * Para ver una copia de esta licencia, visite http://creativecommons.org/licenses/by/4.0/.
 */

package es.uji.al341823.telefonia.gui.swing;

import es.uji.al341823.telefonia.api.EnumTipoDato;

/**
 * Created by Juanjo on 01/05/2017.
 */
public class InfoColumna {
	private final int ancho;
	private final String nombre;
	private final EnumTipoDato tipoDato;

	public InfoColumna(int ancho, String nombre, EnumTipoDato tipoDato) {

		this.ancho = ancho;
		this.nombre = nombre;
		this.tipoDato = tipoDato;
	}

	public InfoColumna(int ancho, String nombre) {
		this(ancho, nombre, null);
	}

	public int getAncho() {
		return this.ancho;
	}

	public String getNombre() {
		return this.nombre;
	}

	public EnumTipoDato getTipoDato() {
		return this.tipoDato;
	}
}
