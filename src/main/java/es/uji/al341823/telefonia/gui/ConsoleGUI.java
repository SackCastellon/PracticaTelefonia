package es.uji.al341823.telefonia.gui;

import java.util.Scanner;

/**
 * @author Juanjo González (al341823)
 * @since 0.1
 */
public class ConsoleGUI {

	private static Scanner in;

	public static void main(String[] args) {

		in = new Scanner(System.in);
		menuPrincipal();
		in.close();
	}

	private static void menuPrincipal() {
		int menu;

		do {
			imprimeTitulo("Menú principal");
			menu = seleccionarOpcion("Salir", "Clientes", "Llamadas", "Facturas");

			switch (menu) {
				case 1:
					menuClientes();
					break;

				case 2:
					menuLlamadas();
					break;

				case 3:
					menuFacturas();
					break;
			}
		} while (menu != 0);
	}

	private static void menuClientes() {
		int accion;

		do {
			imprimeTitulo("Menú clientes");
			accion = seleccionarOpcion("Menú principal", "Alta cliente", "Baja cliente", "Cambiar tarifa", "Ver datos cliente", "Lista de clientes");

			switch (accion) {
				case 1:
					//TODO
					seleccionarOpcion("Cancelar", "Particular", "Empresa");
					break;

				case 2:
					//TODO
					break;

				case 3:
					//TODO
					break;

				case 4:
					//TODO
					break;

				case 5:
					//TODO
					break;
			}
		} while (accion != 0);
	}

	private static void menuLlamadas() {
		//TODO
	}

	private static void menuFacturas() {
		//TODO
	}

	// ========================= Fin de los menús ========================= //

	private static int seleccionarOpcion(String atras, String... opciones) {
		System.out.println("Selecciona una opción (0-" + opciones.length + ")");
		for (int i = 0; i < opciones.length; i++) {
			System.out.println("  " + (i + 1) + ": " + opciones[i]);
		}

		System.out.println("  0: " + atras);

		int seleccion;
		do {
			System.out.print("> ");
			try {
				seleccion = Integer.parseInt(in.nextLine());
			} catch (NumberFormatException e) {
				seleccion = -1;
			}
		} while (seleccion > opciones.length || seleccion < 0);

		return seleccion;
	}

	private static void imprimeTitulo(String titulo) {
		for (int i = -16; i < titulo.length(); i++, System.out.print('#')) ;
		System.out.println("\n        " + titulo);
		for (int i = -16; i < titulo.length(); i++, System.out.print('#')) ;
		System.out.println("\n");
	}
}
