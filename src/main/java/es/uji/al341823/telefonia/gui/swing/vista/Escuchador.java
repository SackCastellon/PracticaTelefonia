/*
 * Copyright (c) 2017. Esta obra está sujeta a la licencia Reconocimiento 4.0 Internacional de Creative Commons.
 * Para ver una copia de esta licencia, visite http://creativecommons.org/licenses/by/4.0/.
 */

package es.uji.al341823.telefonia.gui.swing.vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Juanjo on 26/04/2017.
 */
public class Escuchador implements ActionListener {

	public static File file;
	@Override
	public void actionPerformed(ActionEvent e) {

		switch(e.getActionCommand()){
			case "Cargar...":
				JFileChooser fc1=new JFileChooser();
				int returnVal1 = fc1.showOpenDialog(SwingUtilities.getRoot((Component) e.getSource()));

				if (returnVal1 == JFileChooser.APPROVE_OPTION) {
					file = fc1.getSelectedFile();
					//Llamar al controlador
					System.out.println("Hola");
				}
				break;
			case "Guardar":
				if (file == null) {
					JFileChooser fc2 = new JFileChooser();
					int returnVal2 = fc2.showSaveDialog(SwingUtilities.getRoot((Component) e.getSource()));

					if (returnVal2 == JFileChooser.APPROVE_OPTION) {
						file = fc2.getSelectedFile();
						//Llamar al controlador
						System.out.println("Hola");
					}
				}else{
					System.out.println("Hola");
				}
				break;
			case "Guardar como...":
				JFileChooser fc3 = new JFileChooser();
				int returnVal3 = fc3.showSaveDialog(SwingUtilities.getRoot((Component) e.getSource()));

				if (returnVal3 == JFileChooser.APPROVE_OPTION) {
					file = fc3.getSelectedFile();
					//Llamar al controlador
					System.out.println("Hola");
				}
				break;
		}
		//System.out.println(e.getActionCommand());
	}
}
