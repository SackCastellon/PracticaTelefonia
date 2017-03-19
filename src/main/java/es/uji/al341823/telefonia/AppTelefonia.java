package es.uji.al341823.telefonia;

import es.uji.al341823.telefonia.api.AdministradorDatos;
import es.uji.al341823.telefonia.gui.console.menu.Menu;
import es.uji.al341823.telefonia.gui.console.menu.MenuPrincipal;

/**
 * @author Juanjo González (al341823)
 * @since 0.1
 */
public class AppTelefonia {

	private static final String ficheroDatos = "Telefonia.data";

	/**
	 * Metodo main, primer metodo que se llama la ejecutar el programa
	 *
	 * @param args Argumentos de ejecución
	 */
	public static void main(String[] args) {

		AdministradorDatos.cargarDatos(ficheroDatos);

		Menu menuPrincipal = new MenuPrincipal(null);
		menuPrincipal.mostrar();

		AdministradorDatos.guardarDatos(ficheroDatos);

		Menu.scanner.close();
	}
}
