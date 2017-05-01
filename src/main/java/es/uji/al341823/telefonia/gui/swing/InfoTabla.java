/*
 * Copyright (c) 2017. Esta obra está sujeta a la licencia Reconocimiento 4.0 Internacional de Creative Commons.
 * Para ver una copia de esta licencia, visite http://creativecommons.org/licenses/by/4.0/.
 */

package es.uji.al341823.telefonia.gui.swing;

import java.util.ArrayList;

/**
 * Created by Juanjo on 01/05/2017.
 */
public class InfoTabla {

	private final String nombre;
	private final ArrayList<InfoColumna> columnas = new ArrayList<>();

	public InfoTabla(String nombre) {
		this.nombre = nombre;
	}

	public void addColumna(InfoColumna infoColumna) {
		this.columnas.add(infoColumna);
	}

	public String getNombre() {
		return nombre;
	}

	public ArrayList<InfoColumna> getColumnas() {
		return columnas;
	}
}
