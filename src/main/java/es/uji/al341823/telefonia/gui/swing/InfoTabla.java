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
	private final ArrayList<InfoColumna> columns = new ArrayList<>();

	public InfoTabla(String nombre) {
		this.nombre = nombre;
	}

	public void addColumn(InfoColumna infoColumna) {
		this.columns.add(infoColumna);
	}

	public String getNombre() {
		return this.nombre;
	}

	public ArrayList<InfoColumna> getColumns() {
		return this.columns;
	}

	public InfoColumna getColumn(int i) {
		return this.columns.get(i);
	}

	public int getColumnCount() {
		return this.columns.size();
	}
}
