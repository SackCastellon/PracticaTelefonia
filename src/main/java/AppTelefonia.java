/*
 * Copyright (c) 2017. Esta obra está sujeta a la licencia Reconocimiento 4.0 Internacional de Creative Commons.
 * Para ver una copia de esta licencia, visite http://creativecommons.org/licenses/by/4.0/.
 */

import es.uji.al341823.telefonia.api.AdministradorDatos;
import es.uji.al341823.telefonia.api.AdministradorMenus;
import es.uji.al341823.telefonia.gui.console.Menu;
import es.uji.al341823.telefonia.gui.console.MenuCargarDatos;
import es.uji.al341823.telefonia.gui.console.MenuGuardarDatos;
import es.uji.al341823.telefonia.gui.console.MenuPrincipal;
import es.uji.al341823.telefonia.gui.swing.controladores.Controlador;
import es.uji.al341823.telefonia.gui.swing.ventanas.VentanaPrincipal;

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
				System.err.printf("Cannot load look and feel '%s'. Using cross platform look and feel", UIManager.getSystemLookAndFeelClassName());
			}

			AdministradorDatos modelo = new AdministradorDatos();
			VentanaPrincipal vista = new VentanaPrincipal();
			Controlador controlador = new Controlador();

			modelo.setVista(vista);
			vista.setModelo(modelo);
			vista.setControlador(controlador);
			controlador.setModelo(modelo);
			controlador.setVista(vista);

			vista.generarVista();
		});
	}

	private static void cargarInterfazConsola() {
		AdministradorDatos administradorDatos = new AdministradorDatos();
		AdministradorMenus administradorMenus = new AdministradorMenus();

		Menu menuCargar = new MenuCargarDatos();
		menuCargar.setAdministradorDatos(administradorDatos);
		menuCargar.setAdministradorMenus(administradorMenus);
		menuCargar.mostrar();

		Menu menuPrincipal = new MenuPrincipal();
		menuPrincipal.setAdministradorDatos(administradorDatos);
		menuPrincipal.setAdministradorMenus(administradorMenus);
		menuPrincipal.mostrar();

		Menu menuGuardar = new MenuGuardarDatos();
		menuGuardar.setAdministradorDatos(administradorDatos);
		menuGuardar.setAdministradorMenus(administradorMenus);
		menuGuardar.mostrar();
	}
}
