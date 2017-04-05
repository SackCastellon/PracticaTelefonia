package es.uji.al341823.telefonia;

import es.uji.al341823.telefonia.api.AdministradorDatos;
import es.uji.al341823.telefonia.api.AdministradorMenus;
import es.uji.al341823.telefonia.gui.console.menu.CargarDatos;
import es.uji.al341823.telefonia.gui.console.menu.GuardarDatos;
import es.uji.al341823.telefonia.gui.console.menu.Menu;
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

		Menu menuCargar = new CargarDatos(null);
		menuCargar.mostrar();

		Menu menuGuardar = new GuardarDatos(null);
		menuGuardar.mostrar();

		AdministradorMenus.clearScreen();

		Menu.scanner.close();
	}
}
