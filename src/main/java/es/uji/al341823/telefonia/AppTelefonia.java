/*
 * Copyright (c) 2017. Esta obra está sujeta a la licencia Reconocimiento 4.0 Internacional de Creative Commons.
 * Para ver una copia de esta licencia, visite http://creativecommons.org/licenses/by/4.0/.
 */

package es.uji.al341823.telefonia;

import es.uji.al341823.telefonia.api.AdministradorMenus;
import es.uji.al341823.telefonia.gui.console.Menu;
import es.uji.al341823.telefonia.gui.console.MenuCargarDatos;
import es.uji.al341823.telefonia.gui.console.MenuGuardarDatos;
import es.uji.al341823.telefonia.gui.console.MenuPrincipal;
import es.uji.al341823.telefonia.gui.swing.Ventana;

import javax.swing.*;

/**
 * @author Juanjo González (al341823)
 * @since 0.1
 */
public class AppTelefonia {

	/**
	 * Metodo main, primer metodo que se llama la ejecutar el programa
	 *
	 * @param args Argumentos de ejecución
	 */
	public static void main(String[] args) {
		cargarInterfazGrafica();
		//cargarInterfazConsola();
	}

	private static void cargarInterfazGrafica() {
		SwingUtilities.invokeLater(() -> {
			try {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			} catch (Exception e) {
				System.err.printf("Cannot load look and feel '%s', using cross platform look and feel", UIManager.getSystemLookAndFeelClassName());
			}

			Ventana ventana = new Ventana();
			ventana.ejecutar();
		});
	}

	private static void cargarInterfazConsola() {
		AdministradorMenus.clearScreen();

		Menu menuCargar = new MenuCargarDatos(null);
		menuCargar.mostrar();

		Menu menuPrincipal = new MenuPrincipal(null);
		menuPrincipal.mostrar();

		Menu menuGuardar = new MenuGuardarDatos(null);
		menuGuardar.mostrar();

		AdministradorMenus.clearScreen();

		Menu.scanner.close();
	}
}
