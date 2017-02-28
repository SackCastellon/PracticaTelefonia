package es.uji.al341823.telefonia.gui;

import es.uji.al341823.telefonia.api.Administrador;
import es.uji.al341823.telefonia.cliente.*;
import es.uji.al341823.telefonia.facturacion.Factura;
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

	/** <code>Scanner</code> utilizado para leer la entrada por consola */
	private static Scanner in;

	/**
	 * Inicializa la interfaz por linea de comandos y muestra el menú principal
	 */
	public static void iniciar() {
		in = new Scanner(System.in);
		menuPrincipal();
		in.close();
	}

	// ========================= Menús ========================= //

	/**
	 * Muestra el meú principal hasta que se seleccione un sub-menú o se decida salir del programa
	 */
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
					bajaCliente();
					break;

				case 3:
					cambiarTarifa();
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
		int tipoCliente = seleccionarOpcion("Cancelar", "Particular", "Particular Generado Aleatoriamente", "Empresa");

		if (tipoCliente == 0) return;

		if (tipoCliente == 1) System.out.println("Introduce los datos del particular:");
		else if (tipoCliente == 2) {
			//FIXME Hacer mas bonito
			System.out.println("Cuantos praticulares aleatorios quieres generar?");
			int i = 0;
			do {
				try {
					System.out.print("> ");
					i = in.nextInt();
				} catch (Exception e) {
					continue;
				}
			} while (i < 1 || i > 1000);
			Administrador.generarParticularesAleatorios(i);
			return;
		} else System.out.println("Introduce los datos de la empresa:");

		String nombre = leerTexto("Nombre");

		String apellidos = null;
		if (tipoCliente == 1) apellidos = leerTexto("Apellidos");

		String nif = leerTexto("NIF");
		Direccion direccion = leerDireccion("Dirección");
		String email = leerTexto("E-mail");
		LocalDateTime fecha = leerFecha("Fecha de alta");

		//FIXME
		System.out.print(" - Tarifa: ");
		Tarifa tarifa = new Tarifa(in.nextInt());

		if (tipoCliente == 1)
			Administrador.altaCliente(new Particular(nombre, apellidos, nif, direccion, email, fecha, tarifa));
		else Administrador.altaCliente(new Empresa(nombre, nif, direccion, email, fecha, tarifa));
	}

	private static void bajaCliente() {
		System.out.println("Introduce los siguientes datos del cliente:");
		String nif = leerTexto("NIF");

		if (!Administrador.bajaCliente(nif))
			System.out.println("No existe ningún cliente con NIF " + nif);
		else
			System.out.println("Cliente con NIF " + nif + " eliminado");
	}

	private static void cambiarTarifa() {
		System.out.println("Introduce los siguientes datos del cliente:");
		String nif = leerTexto("NIF");

		Cliente cliente = Administrador.getCliente(nif);
		if (cliente == null) {
			System.out.println("No existe ningún cliente con NIF " + nif);
			return;
		}

		System.out.println("Introduce la nueva tarifa:");

		//FIXME
		System.out.print(" - Tarifa: ");
		Tarifa tarifa = new Tarifa(in.nextInt());

		cliente.setTarifa(tarifa);

	}

	private static void verDatosCliente() {
		System.out.println("Introduce los siguientes datos del cliente:");
		String nif = leerTexto("NIF");

		Cliente cliente = Administrador.getCliente(nif);
		if (cliente == null) {
			System.out.println("No existe ningún cliente con NIF " + nif);
			return;
		}

		System.out.println("Información del cliente con NIF " + nif + ":");
		for (String info : cliente.getInformacion()) {
			System.out.println(" - " + info);
		}
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
					altaLlamada();
					break;

				case 2:
					verLlamadasCliente();
					break;
			}
		} while (opcion != 0);
	}

	private static void altaLlamada() {
		System.out.println("Introduce los siguientes datos del cliente:");
		String nif = leerTexto("NIF");

		Cliente cliente = Administrador.getCliente(nif);
		if (cliente == null) {
			System.out.println("No existe ningún cliente con NIF " + nif);
			return;
		}

		System.out.println("Introduce los siguientes datos de la llamada:");

		String origen = leerTexto("Numero de origen");
		String destino = leerTexto("Numero de destino");
		LocalDateTime fecha = leerFecha("Fecha de la llamada");
		int duracion = (int) leerNumero("Duracion de la llamada en segundos");

		Llamada llamada = new Llamada(origen, destino, fecha, duracion);
		cliente.altaLlamada(llamada);
	}

	private static void verLlamadasCliente() {
		System.out.println("Introduce los siguientes datos del cliente:");
		String nif = leerTexto("NIF");

		Cliente cliente = Administrador.getCliente(nif);
		if (cliente == null) {
			System.out.println("No existe ningún cliente con NIF " + nif);
			return;
		}

		System.out.println("Lista de llamadas del cliente con NIF " + nif + ":");

		LinkedList<Llamada> llamadas = cliente.getLlamadas();

		for (Llamada llamada : llamadas) {
			System.out.print(" - Llamada de " + llamada.getDuracionLlamada() + "seg. del " + llamada.getNumeroOrigen() + " al " + llamada.getNumeroDestino() + " el dia " + llamada.getFecha());
		}
	}

	private static void menuFacturas() {
		int opcion;

		do {
			imprimeTitulo("Menú facturas");
			opcion = seleccionarOpcion("Menú principal", "Emitir factura", "Recuperar datos factura", "Recuperar factura de cliente");

			switch (opcion) {
				case 1:
					emitirFactura();
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

	private static void emitirFactura() {
		//TODO
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

	private static float leerNumero(String mensaje) {
		System.out.print(" - " + mensaje + ": ");

		float f = -1;

		do {
			try {
				f = in.nextFloat();
			} catch (Exception e) {
				System.out.println("Error en el formato del numero");
				System.out.print(" - " + mensaje + ": ");
			}
		} while (f < 0);

		return f;
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
		System.out.print(" - " + mensaje + " (AAAA-MM-DD HH:mm:ss | hoy): ");

		LocalDateTime fecha = null;

		do {
			try {
				String s = in.nextLine();
				if (s.equalsIgnoreCase("hoy"))
					fecha = LocalDateTime.now();
				else
					fecha = LocalDateTime.parse(s.replaceAll("( )+", "T"));
			} catch (Exception e) {
				System.out.println("Error en el formato de la fecha");
				System.out.print(" - " + mensaje + " (AAAA-MM-DD HH:mm:ss): ");
			}
		} while (fecha == null);

		return fecha;
	}

	// TODO
	private static void limpiarPantalla() {
		System.out.print("\033[2J\033[H");
	}
}
