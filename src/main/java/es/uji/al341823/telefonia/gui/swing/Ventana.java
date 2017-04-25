/*
 * Copyright (c) 2017. Esta obra está sujeta a la licencia Reconocimiento 4.0 Internacional de Creative Commons.
 * Para ver una copia de esta licencia, visite http://creativecommons.org/licenses/by/4.0/.
 */

package es.uji.al341823.telefonia.gui.swing;

import javax.swing.*;

/**
 * Created by Juanjo on 25/04/2017.
 */
public class Ventana extends JFrame {

	public Ventana() {
		super("Telefonía");
	}

	public void ejecutar() {
		this.setSize(400, 400);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
}
