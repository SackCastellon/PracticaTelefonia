package es.uji.al341823.telefonia.gui;

import es.uji.al341823.telefonia.api.Administrador;

import java.util.Scanner;

/**
 * @author Juanjo González (al341823)
 * @since 0.1
 */
public class Console0GUI {

	private static Scanner in;

	public static void main(String[] args) {

		in = new Scanner(System.in);
		menuPrincipal();
		in.close();
	}

	private static void menuPrincipal() {
		int menu;

		do {
			printTitle("Menú principal");
			menu = selectOption("Clientes", "Llamadas", "Facturas");

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
			printTitle("Menú clientes");
			accion = selectOption("Alta cliente", "Baja cliente", "Cambiar tarifa", "Ver datos cliente", "Lista de clientes");

			switch (accion) {
				case 1:
					//TODO
					break;

				case 2:
					System.out.println("Indica el NIF del cliente");
					String nif = in.next();
					Administrador.bajaCliente(nif);
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

	private static int selectOption(String... opciones) {
		System.out.println("Selecciona una opción (0-" + opciones.length + ")");
		for (int i = 0; i < opciones.length; i++) {
			System.out.println("  " + ++i + ": " + opciones[--i]);
		}
		System.out.println("  0: Salir");

		int seleccion;
		do {
			System.out.print("> ");
			seleccion = in.nextInt();
		} while (seleccion > opciones.length || seleccion < 0);

		return seleccion;
	}

	private static void printTitle(String titulo) {
		for (int i = -10; i < titulo.length(); i++, System.out.print('#')) ;
		System.out.println("\n     " + titulo);
		for (int i = -10; i < titulo.length(); i++, System.out.print('#')) ;
		System.out.println("\n");
	}
}
