package es.uji.al341823.telefonia;

import es.uji.al341823.telefonia.gui.console.ConsoleGUI;

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

		ConsoleGUI consoleGUI = new ConsoleGUI();
		consoleGUI.iniciar();
	}
}
