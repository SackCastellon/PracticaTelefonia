package es.uji.al341823.telefonia;

import es.uji.al341823.telefonia.api.Administrador;
import es.uji.al341823.telefonia.gui.console.ConsoleGUI;

import java.io.*;

/**
 * @author Juanjo González (al341823)
 * @since 0.1
 */
public class AppTelefonia {

	private static final File ficheroDatos = new File("Telefonia.data");

	private static Administrador administrador;

	/**
	 * Metodo main, primer metodo que se llama la ejecutar el programa
	 *
	 * @param args Argumentos de ejecución
	 */
	public static void main(String[] args) {
		cargarDatos();

		ConsoleGUI consoleGUI = new ConsoleGUI(administrador);
		consoleGUI.iniciar();

		guardarDatos();
	}

	private static void cargarDatos() {
		try {
			FileInputStream in = new FileInputStream(ficheroDatos);
			ObjectInputStream obj = new ObjectInputStream(in);
			administrador = (Administrador) obj.readObject();
			obj.close();
		} catch (Exception e) {
			System.err.println("ERROR: No se pudieron cargar los datos");
			administrador = new Administrador();
			e.printStackTrace();
		}
	}

	private static void guardarDatos() {
		try {
			ficheroDatos.createNewFile();

			FileOutputStream out = new FileOutputStream(ficheroDatos);
			ObjectOutputStream obj = new ObjectOutputStream(out);
			obj.writeObject(administrador);
			obj.close();
		} catch (Exception e) {
			System.err.println("ERROR: No se pudieron guardar los datos");
			e.printStackTrace();
		}
	}
}
