package es.uji.al341823.telefonia;

import es.uji.al341823.telefonia.api.manager.DataManager;
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

		DataManager.cargarDatos();

		Menu menuPrincipal = new MenuPrincipal(null);
		menuPrincipal.mostrar();
		Menu.scanner.close();

		DataManager.guardarDatos();
	}
}
