/*
 * Copyright (c) 2017. Esta obra está sujeta a la licencia Reconocimiento 4.0 Internacional de Creative Commons.
 * Para ver una copia de esta licencia, visite http://creativecommons.org/licenses/by/4.0/.
 */

package es.uji.al341823.telefonia.gui.swing.vista;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Juanjo on 26/04/2017.
 */
public class Escuchador implements ActionListener {
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println(e.getActionCommand());
	}
}
