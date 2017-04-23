/*
 * Copyright (c) 2017. Esta obra está sujeta a la licencia Reconocimiento 4.0 Internacional de Creative Commons.
 * Para ver una copia de esta licencia, visite http://creativecommons.org/licenses/by/4.0/.
 */

package es.uji.al341823.telefonia;

import es.uji.al341823.telefonia.api.AdministradorMenus;
import es.uji.al341823.telefonia.gui.console.menu.Menu;
import es.uji.al341823.telefonia.gui.console.menu.MenuCargarDatos;
import es.uji.al341823.telefonia.gui.console.menu.MenuGuardarDatos;
import es.uji.al341823.telefonia.gui.console.menu.MenuPrincipal;

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
