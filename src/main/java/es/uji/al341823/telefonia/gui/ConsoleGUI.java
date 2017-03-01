package es.uji.al341823.telefonia.gui;

import es.uji.al341823.telefonia.api.Administrador;
import es.uji.al341823.telefonia.clientes.Cliente;
import es.uji.al341823.telefonia.clientes.DireccionPostal;
import es.uji.al341823.telefonia.clientes.Empresa;
import es.uji.al341823.telefonia.clientes.Particular;
import es.uji.al341823.telefonia.facturacion.FacturaTelefonica;
import es.uji.al341823.telefonia.facturacion.TarifaTelefonica;
import es.uji.al341823.telefonia.llamadas.Llamada;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Scanner;

/**
 * @author Juanjo González (al341823)
 * @since 0.1
 */
public class ConsoleGUI {

	/** <code>Scanner</code> utilizado para leer la entrada por consola */
	private static Scanner scanner;

	/**
	 * Inicializa la interfaz por linea de comandos, el Scanner para leer la entrada del usuario y muestra el menú principal
	 */
	public static void iniciar() {
		scanner = new Scanner(System.in);
		menuPrincipal();
		scanner.close();
	}

	// ========================= Menús ========================= //

	/**
	 * Muestra el menú principal hasta que se seleccione un sub-menú o se decida salir del programa
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

	/**
	 * Muestra el menú de clientes hasta que se seleccione un sub-menú o se decida salir del programa
	 */
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

	/**
	 * Muestra un menú para dar de alta un cliente
	 */
	private static void altaCliente() {
		int tipoCliente = seleccionarOpcion("Cancelar", "Particular", "Particular Generado Aleatoriamente", "Empresa");

		if (tipoCliente == 0) return;

		if (tipoCliente == 1) {
			System.out.println("Introduce los datos del particular:");
		} else if (tipoCliente == 2) {
			System.out.println("Introduce los siguientes datos:");
			Administrador.generarParticularesAleatorios((int) leerNumero("Numero de particlulares a generar"));
			return;
		} else {
			System.out.println("Introduce los datos de la empresa:");
		}

		String nombre = leerTexto("Nombre");

		String apellidos = null;
		if (tipoCliente == 1) apellidos = leerTexto("Apellidos");

		String nif = leerTexto("NIF");
		DireccionPostal direccion = leerDireccion("Dirección");
		String email = leerTexto("E-mail");
		LocalDateTime fecha = leerFecha("Fecha de alta");
		TarifaTelefonica tarifa = new TarifaTelefonica(leerNumero("Tarifa"));

		boolean exito;

		if (tipoCliente == 1)
			exito = Administrador.altaCliente(new Particular(nombre, apellidos, nif, direccion, email, fecha, tarifa));
		else
			exito = Administrador.altaCliente(new Empresa(nombre, nif, direccion, email, fecha, tarifa));

		if (!exito)
			System.out.println("No se pudo añadir el cliente, ya existe un cliente con NIF " + nif);

	}

	/**
	 * Muestra un menú para dar de baja un cliente
	 */
	private static void bajaCliente() {
		String nif = leerDatoNIF();

		if (!Administrador.bajaCliente(nif))
			System.out.println("No existe ningún cliente con NIF " + nif);
		else
			System.out.println("Cliente con NIF " + nif + " eliminado");
	}


	/**
	 * Muestra un menú para cambiar la tarifa de un cliente
	 */
	private static void cambiarTarifa() {
		String nif = leerDatoNIF();

		if (!Administrador.exixteCliente(nif)) {
			System.out.println("No existe ningún cliente con NIF " + nif);
			return;
		}

		System.out.println("Introduce la nueva tarifa:");

		TarifaTelefonica tarifa = new TarifaTelefonica(leerNumero("Tarifa"));

		Administrador.cambiarTarifa(nif, tarifa);
	}

	/**
	 * Muestra un menú para ver los datos de un cliente
	 */
	private static void verDatosCliente() {
		String nif = leerDatoNIF();

		Cliente cliente = Administrador.getCliente(nif);
		if (cliente == null) {
			System.out.println("No existe ningún cliente con NIF " + nif);
			return;
		}

		System.out.println("Información del cliente con NIF " + nif + ":");
		System.out.println(cliente.obtenerInformacion());
	}

	/**
	 * Muestra un menú para ver los datos de todos los clientes
	 */
	private static void verDatosTodosClientes() {

		Collection<Cliente> clientes = Administrador.getClientes();

		if (clientes.isEmpty()) {
			System.out.println("No hay ningún cliente");
			return;
		}

		for (Cliente cliente : clientes) {
			System.out.println(cliente.obtenerInformacion());
			System.out.println();
		}
	}

	/**
	 * Muestra el menú de llamadas hasta que se seleccione un sub-menú o se decida salir del programa
	 */
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

	/**
	 * Muestra un menú para dar de alta una llamada
	 */
	private static void altaLlamada() {
		String nif = leerDatoNIF();

		if (!Administrador.exixteCliente(nif)) {
			System.out.println("No existe ningún cliente con NIF " + nif);
			return;
		}

		System.out.println("Introduce los siguientes datos de la llamada:");

		String origen = leerTexto("Numero de origen");
		String destino = leerTexto("Numero de destino");
		LocalDateTime fecha = leerFecha("Fecha de la llamada");
		int duracion = (int) leerNumero("Duracion de la llamada en segundos");

		Llamada llamada = new Llamada(origen, destino, fecha, duracion);
		Administrador.altaLlamada(nif, llamada);
	}

	/**
	 * Muestra un menú para ver todas las llamadas
	 */
	private static void verLlamadasCliente() {
		String nif = leerDatoNIF();

		Collection<Llamada> llamadas = Administrador.getLlamadas(nif);

		if (llamadas == null) {
			System.out.println("No existe ningún cliente con NIF " + nif);
			return;
		}

		if (llamadas.isEmpty()) {
			System.out.println("El cliente con NIF " + nif + " no ha realizado ningúna llamada");
			return;
		}

		System.out.println("Lista de llamadas del cliente con NIF " + nif + ":");

		for (Llamada llamada : llamadas) {
			System.out.print(" - Llamada de " + llamada.getDuracionLlamada() + "seg. del " + llamada.getNumeroOrigen() + " al " + llamada.getNumeroDestino() + " el dia " + llamada.getFecha());
		}
	}

	/**
	 * Muestra el menú de facturas hasta que se seleccione un sub-menú o se decida salir del programa
	 */
	private static void menuFacturas() {
		int opcion;

		do {
			imprimeTitulo("Menú facturas");
			opcion = seleccionarOpcion("Menú principal", "Emitir factura", "Recuperar datos factura", "Recuperar facturas de cliente");

			switch (opcion) {
				case 1:
					emitirFactura();
					break;

				case 2:
					verFactura();
					break;

				case 3:
					verFacturasCliente();
					break;
			}
		} while (opcion != 0);
	}

	/**
	 * Muestra un menú para emitir una factura
	 */
	private static void emitirFactura() {
		String nif = leerDatoNIF();

		FacturaTelefonica factura = Administrador.emitirFactura(nif);

		if (factura == null) {
			System.out.println("No existe ningún cliente con NIF " + nif);
			return;
		}

		System.out.println("Información de la factura recien emitida");
		System.out.println(factura.obtenerInformacion());
	}

	/**
	 * Muestra un menú para ver una factura
	 */
	private static void verFactura() {
		System.out.println("Introduce los siguientes datos de la factura:");
		int cod = (int) leerNumero("Codigo factura");

		FacturaTelefonica factura = Administrador.getFactura(cod);
		System.out.println("Información de la factura con codigo" + cod + ":");
		System.out.println(factura.obtenerInformacion());
	}

	/**
	 * Muestra un menú para ver todas las facturas de un cliente
	 */
	private static void verFacturasCliente() {
		String nif = leerDatoNIF();

		Collection<FacturaTelefonica> facturas = Administrador.getFacturas(nif);

		if (facturas == null) {
			System.out.println("No existe ningún cliente con NIF " + nif);
			return;
		}

		if (facturas.isEmpty()) {
			System.out.println("El cliente con NIF " + nif + " no tiene ningúna factura");
			return;
		}

		for (FacturaTelefonica factura : facturas) {
			System.out.println();
			factura.obtenerInformacion();
		}
	}

	// ========================= Utilidades ========================= //

	/**
	 * Imprime el titulo especificado por pantalla
	 *
	 * @param titulo El titulo
	 */
	private static void imprimeTitulo(String titulo) {
		System.out.println('\n');
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
				seleccion = Integer.parseInt(scanner.nextLine());
			} catch (NumberFormatException e) {
				seleccion = -1;
			}
		} while (seleccion > opciones.length || seleccion < 0);

		return seleccion;
	}

	// ========================= Leer por pantalla ========================= //

	private static String leerTexto(String mensaje) {
		System.out.print(" - " + mensaje + ": ");
		return scanner.nextLine();
	}

	private static float leerNumero(String mensaje) {
		System.out.print(" - " + mensaje + ": ");

		float f = -1;

		do {
			try {
				f = scanner.nextFloat();
			} catch (Exception e) {
				System.out.println("Error en el formato del numero");
				System.out.print(" - " + mensaje + ": ");
			}
		} while (f < 0);

		return f;
	}

	private static DireccionPostal leerDireccion(String mensaje) {
		System.out.print(" - " + mensaje + " (CP, Provincia, Población): ");

		DireccionPostal direccion = null;

		do {
			try {
				direccion = DireccionPostal.parse(scanner.nextLine());
			} catch (Exception e) {
				System.out.println("Error en el formato de la dirección");
				System.out.print(" - " + mensaje + " (CP, Provincia, Población): ");
			}
		} while (direccion == null);

		return direccion;
	}

	private static LocalDateTime leerFecha(String mensaje) {
		String hoy = "hoy";
		System.out.print(" - " + mensaje + " (AAAA-MM-DD HH:mm:ss | " + hoy + "): ");

		LocalDateTime fecha = null;

		do {
			try {
				String s = scanner.nextLine();
				if (s.equalsIgnoreCase(hoy))
					fecha = LocalDateTime.now();
				else
					fecha = LocalDateTime.parse(s.replaceAll("( )+", "T"));
			} catch (Exception e) {
				System.out.println("Error en el formato de la fecha");
				System.out.print(" - " + mensaje + " (AAAA-MM-DD HH:mm:ss | hoy): ");
			}
		} while (fecha == null);

		return fecha;
	}

	private static String leerDatoNIF() {
		System.out.println("Introduce los siguientes datos del cliente:");
		return leerTexto("NIF");
	}

	// ========================= Formato de pantalla ========================= //

	private static void limpiarPantalla() // TODO
	{
		System.out.print("\033[2J\033[H");
	}
}
