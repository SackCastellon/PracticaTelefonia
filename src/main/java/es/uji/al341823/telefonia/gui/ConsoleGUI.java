package es.uji.al341823.telefonia.gui;

import es.uji.al341823.telefonia.api.Administrador;
import es.uji.al341823.telefonia.cliente.Cliente;
import es.uji.al341823.telefonia.cliente.Direccion;
import es.uji.al341823.telefonia.cliente.Empresa;
import es.uji.al341823.telefonia.cliente.Particular;
import es.uji.al341823.telefonia.facturacion.Tarifa;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.LinkedList;
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

	// ========================= Menús ========================= //

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
		int opcion;

		do {
			imprimeTitulo("Menú clientes");
			opcion = seleccionarOpcion("Menú principal", "Alta cliente", "Baja cliente", "Cambiar tarifa", "Ver datos cliente", "Lista de clientes");

			switch (opcion) {
				case 1:
					altaCliente();
					break;

				case 2:
					//TODO
					break;

				case 3:
					//TODO
					break;

				case 4:
					verDatosCliente();
					break;

				case 5:
					verDatosTodosClientes();
					break;
			}
		} while (opcion != 0);
	}

	private static void altaCliente() {
		int tipoCliente = seleccionarOpcion("Cancelar", "Particular", "Empresa");

		if (tipoCliente == 0) return;

		if (tipoCliente == 1) System.out.println("Introduce los datos del particular:");
		else System.out.println("Introduce los datos de la empresa:");

		String nombre = leerTexto("Nombre");

		String apellidos = null;
		if (tipoCliente == 1) apellidos = leerTexto("Apellidos");

		String nif = leerTexto("NIF");
		Direccion direccion = leerDireccion("Dirección");
		String email = leerTexto("E-mail");
		LocalDateTime fecha = leerFecha("Fecha de alta");

		//TODO
		System.out.print(" - Tarifa: ");
		Tarifa tarifa = new Tarifa(in.nextInt());

		if (tipoCliente == 1)
			Administrador.altaCliente(new Particular(nombre, apellidos, nif, direccion, email, fecha, tarifa));
		else Administrador.altaCliente(new Empresa(nombre, nif, direccion, email, fecha, tarifa));
	}

	private static void verDatosCliente() {
		System.out.println("Introduce los siguientes datos del cliente:");
		String nif = leerTexto("NIF");

		Cliente cliente = Administrador.getCliente(nif);
		if (cliente != null) {
			System.out.println("Información del cliente con NIF " + nif + ":");
			for (String info : cliente.getInformacion()) {
				System.out.println(" - " + info);
			}
		} else
			System.out.println("El cliente no existe");
	}

	private static void verDatosTodosClientes() {

		Collection<Cliente> clientes = Administrador.getClientes();

		if (clientes.isEmpty()) {
			System.out.println("No hay ningún cliente");
			return;
		}

		for (Cliente cliente : clientes) {
			for (String info : cliente.getInformacion()) {
				System.out.println(" - " + info);
			}
			System.out.println();
		}
	}

	private static void menuLlamadas() {
		int opcion;

		do {
			imprimeTitulo("Menú llamadas");
			opcion = seleccionarOpcion("Menú principal", "Alta llamada", "Lista de llamadas");

			switch (opcion) {
				case 1:
					//TODO
					break;

				case 2:
					//TODO
					break;
			}
		} while (opcion != 0);
	}

	private static void menuFacturas() {
		int opcion;

		do {
			imprimeTitulo("Menú facturas");
			opcion = seleccionarOpcion("Menú principal", "Emitir factura", "Recuperar datos factura", "Recuperar factura de cliente");

			switch (opcion) {
				case 1:
					//TODO
					break;

				case 2:
					//TODO
					break;

				case 3:
					//TODO
					break;
			}
		} while (opcion != 0);
	}

	// ========================= Utilidades ========================= //

	private static void imprimeTitulo(String titulo) {
		System.out.println("\n");
		for (int i = -16; i < titulo.length(); i++) System.out.print('#');
		System.out.println("\n        " + titulo);
		for (int i = -16; i < titulo.length(); i++) System.out.print('#');
		System.out.println();
	}

	private static int seleccionarOpcion(String atras, String... opciones) {
		System.out.println("\nSelecciona una opción (0-" + opciones.length + ")");
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

	private static String leerTexto(String mensaje) {
		System.out.print(" - " + mensaje + ": ");
		return in.nextLine();
	}

	private static Direccion leerDireccion(String mensaje) {
		System.out.print(" - " + mensaje + " (CP, Provincia, Población): ");

		Direccion direccion = null;

		do {
			try {
				direccion = Direccion.parse(in.nextLine());
			} catch (Exception e) {
				System.out.println("Error en el formato de la dirección");
				System.out.print(" - " + mensaje + " (CP, Provincia, Población): ");
			}
		} while (direccion == null);

		return direccion;
	}

	private static LocalDateTime leerFecha(String mensaje) {
		System.out.print(" - " + mensaje + " (AAAA-MM-DD HH:mm:ss): ");

		LocalDateTime fecha = null;

		do {
			try {
				fecha = LocalDateTime.parse(in.nextLine().replaceAll(" *", "T"));
			} catch (Exception e) {
				System.out.println("Error en el formato de la fecha");
				System.out.print(" - " + mensaje + " (AAAA-MM-DD HH:mm:ss): ");
			}
		} while (fecha == null);

		return fecha;
	}
}
