/*
 * Copyright (c) 2017. Esta obra está sujeta a la licencia Reconocimiento 4.0 Internacional de Creative Commons.
 * Para ver una copia de esta licencia, visite http://creativecommons.org/licenses/by/4.0/.
 */

package es.uji.al341823.telefonia;

import es.uji.al341823.telefonia.gui.console.Menu;
import es.uji.al341823.telefonia.gui.console.MenuCargarDatos;
import es.uji.al341823.telefonia.gui.console.MenuGuardarDatos;
import es.uji.al341823.telefonia.gui.console.MenuPrincipal;
import es.uji.al341823.telefonia.gui.swing.VentanaPrincipal;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * @author Juanjo González (al341823)
 * @since 0.1
 */
public class AppTelefonia {

	/**
	 * Metodo main, primer metodo que se llama el ejecutar el programa
	 *
	 * @param args Argumentos de ejecución
	 */
	public static void main(String[] args) {
		if ((args.length > 0) && args[0].equals("-terminal"))
			cargarInterfazConsola();
		else
			iniciarInterfazGrafica();

		Menu.scanner.close();
	}

	private static void iniciarInterfazGrafica() {
		SwingUtilities.invokeLater(() -> {
			try {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			} catch (Exception e) {
				System.err.printf("Cannot load look and feel '%s', using cross platform look and feel", UIManager.getSystemLookAndFeelClassName());
			}

			VentanaPrincipal ventana = new VentanaPrincipal();
			ventana.generar();
		});
	}

	private static void cargarInterfazConsola() {
		Menu menuCargar = new MenuCargarDatos();
		menuCargar.mostrar();

		Menu menuPrincipal = new MenuPrincipal();
		menuPrincipal.mostrar();

		Menu menuGuardar = new MenuGuardarDatos();
		menuGuardar.mostrar();
	}
}
