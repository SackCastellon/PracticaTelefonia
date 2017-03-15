package es.uji.al341823.telefonia.gui.console;

import es.uji.al341823.telefonia.api.Administrador;
import es.uji.al341823.telefonia.api.EnumTipoDato;
import es.uji.al341823.telefonia.api.excepciones.ClienteNoExisteExcepcion;
import es.uji.al341823.telefonia.api.excepciones.ClienteYaExisteExcepcion;
import es.uji.al341823.telefonia.api.excepciones.FacturaNoExisteExcepcion;
import es.uji.al341823.telefonia.clientes.Cliente;
import es.uji.al341823.telefonia.clientes.Direccion;
import es.uji.al341823.telefonia.facturacion.Factura;
import es.uji.al341823.telefonia.facturacion.Tarifa;
import es.uji.al341823.telefonia.llamadas.Llamada;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * @author Juanjo González (al341823)
 * @since 0.1
 */
public class ConsoleGUI {

	private final Administrador admin;
	/**
	 * <code>Scanner</code> utilizado para leer la entrada por consola
	 */
	private Scanner scanner;

	public ConsoleGUI(Administrador administrador) {
		super();

		this.admin = administrador;
	}

	/**
	 * Inicializa la interfaz por linea de comandos, el Scanner para leer la entrada del usuario y muestra el menú
	 * principal
	 */
	public void iniciar() {
		this.scanner = new Scanner(System.in);
		this.setColor(255, 255, 255);
		this.menuPrincipal(); // TODO Strategy
		System.out.print("\033[0m");
		this.clearScreen();
		this.scanner.close();
	}

	// ========================= Menús ========================= //

	/**
	 * Muestra el menú principal hasta que se seleccione un sub-menú o se decida salir del programa
	 */
	private void menuPrincipal() {
		int menu;

		do {
			this.imprimeTitulos("Menú principal");
			menu = this.seleccionarOpcion("Salir", "Clientes", "Llamadas", "Facturas");

			switch (menu) {
				case 1:
					this.menuClientes();
					break;

				case 2:
					this.menuLlamadas();
					break;

				case 3:
					this.menuFacturas();
					break;
			}
		} while (menu != 0);
	}

	/**
	 * Muestra el menú de clientes hasta que se seleccione un sub-menú o se decida salir del programa
	 */
	private void menuClientes() {
		int opcion;

		do {
			this.imprimeTitulos("Menú principal", "Menú clientes");
			opcion = this.seleccionarOpcion("Menú principal", "Alta cliente", "Baja cliente", "Cambiar tarifa", "Ver datos cliente", "Lista de clientes");

			switch (opcion) {
				case 1:
					this.altaCliente();
					break;

				case 2:
					this.bajaCliente();
					break;

				case 3:
					this.cambiarTarifa();
					break;

				case 4:
					this.verDatosCliente();
					break;

				case 5:
					this.verDatosTodosClientes();
					break;
			}
		} while (opcion != 0);
	}

	/**
	 * Muestra un menú para dar de alta un cliente
	 */
	private void altaCliente() {

		this.imprimeTitulos("Menú principal", "Menú clientes", "Alta clientes");
		int tipoCliente = this.seleccionarOpcion("Cancelar", "Particular", "Empresa");

		if (tipoCliente == 0) return;
		if (tipoCliente == 1) {
			this.imprimeTitulos("Menú principal", "Menú clientes", "Alta clientes", "Particular");
			System.out.println("Introduce los datos del particular:");
		} else {
			this.imprimeTitulos("Menú principal", "Menú clientes", "Alta clientes", "Empresa");
			System.out.println("Introduce los datos de la empresa:");
		}

		String apellidos = null;

		String nombre = this.leerTexto(" - Nombre:", EnumTipoDato.TEXTO);
		if (tipoCliente == 1) apellidos = this.leerTexto(" - Apellidos:", EnumTipoDato.TEXTO);
		String nif = this.leerTexto(" - NIF:", EnumTipoDato.NIF);
		Direccion direccion = this.leerDireccion(" - Dirección:");
		String email = this.leerTexto(" - E-mail:", EnumTipoDato.EMAIL);
		LocalDateTime fecha = this.leerFecha(" - Fecha de alta:");
		Tarifa tarifa = new Tarifa(this.leerEntero(" - Tarifa:")); // FIXME mover 'new' a clase Administrador

		try {
			if (tipoCliente == 1)
				this.admin.altaParticular(nombre, apellidos, nif, direccion, email, fecha, tarifa);
			else
				this.admin.altaEmpresa(nombre, nif, direccion, email, fecha, tarifa);
		} catch (ClienteYaExisteExcepcion clienteYaExisteExcepcion) {
			System.out.println("No se pudo añadir el cliente, ya existe un cliente con NIF " + nif);
		}
	}

	/**
	 * Muestra un menú para dar de baja un cliente
	 */
	private void bajaCliente() {
		String nif = this.leerTexto("Introduce el NIF del cliente: ", EnumTipoDato.NIF);

		try {
			this.admin.bajaCliente(nif);
			System.out.println("Cliente eliminado");
		} catch (ClienteNoExisteExcepcion clienteNoExisteExcepcion) {
			System.out.println("No existe ningún cliente con NIF '" + nif + "'");
		}
	}


	/**
	 * Muestra un menú para cambiar la tarifa de un cliente
	 */
	private void cambiarTarifa() {
		Cliente cliente = this.getClienteFromInput();

		if (cliente == null) return;

		Tarifa tarifa = new Tarifa(this.leerEntero("Introduce el precio de la nueva tarifa: ")); // FIXME mover 'new' a clase Administrador

		cliente.setTarifa(tarifa);
		System.out.println("Tarifa cambiada a " + tarifa);
	}

	/**
	 * Muestra un menú para ver los datos de un cliente
	 */
	private void verDatosCliente() {
		Cliente cliente = this.getClienteFromInput();

		if (cliente == null) return;

		System.out.println("Información del cliente: " + cliente);
	}

	/**
	 * Muestra un menú para ver los datos de todos los clientes
	 */
	private void verDatosTodosClientes() {
		LinkedList<Cliente> clientes = this.admin.getClientes();

		System.out.println("Hay un total de " + clientes.size() + " clientes");

		for (Cliente cliente : clientes) {
			System.out.println(" - " + cliente);
		}
	}

	/**
	 * Muestra el menú de llamadas hasta que se seleccione un sub-menú o se decida salir del programa
	 */
	private void menuLlamadas() {
		int opcion;

		do {
			this.imprimeTitulos("Menú principal", "Menú llamadas");
			opcion = this.seleccionarOpcion("Menú principal", "Alta llamada", "Lista de llamadas");

			switch (opcion) {
				case 1:
					this.altaLlamada();
					break;

				case 2:
					this.verLlamadasCliente();
					break;
			}
		} while (opcion != 0);
	}

	/**
	 * Muestra un menú para dar de alta una llamada
	 */
	private void altaLlamada() {
		Cliente cliente = this.getClienteFromInput();

		if (cliente == null) return;

		System.out.println("Introduce los siguientes datos de la llamada:");

		String origen = this.leerTexto(" - Numero de origen", EnumTipoDato.TELEFONO);
		String destino = this.leerTexto(" - Numero de destino", EnumTipoDato.TELEFONO);
		LocalDateTime fecha = this.leerFecha(" - Fecha de la llamada");
		int duracion = this.leerEntero(" - Duracion de la llamada en segundos");

		Llamada llamada = new Llamada(origen, destino, fecha, duracion); // FIXME mover 'new' a clase Administrador
		cliente.altaLlamada(llamada);
	}

	/**
	 * Muestra un menú para ver todas las llamadas
	 */
	private void verLlamadasCliente() {
		Cliente cliente = this.getClienteFromInput();

		if (cliente == null) return;

		LinkedList<Llamada> llamadas = cliente.getLlamadas();

		System.out.println("Este cliente ha realizado " + llamadas.size() + " llamadas:");

		for (Llamada llamada : llamadas) {
			System.out.println(" - " + llamada);
		}
	}

	/**
	 * Muestra el menú de facturas hasta que se seleccione un sub-menú o se decida salir del programa
	 */
	private void menuFacturas() {
		int opcion;

		do {
			this.imprimeTitulos("Menú principal", "Menú facturas");
			opcion = this.seleccionarOpcion("Menú principal", "Emitir factura", "Recuperar datos factura", "Recuperar facturas de cliente");

			switch (opcion) {
				case 1:
					this.emitirFactura();
					break;

				case 2:
					this.verFactura();
					break;

				case 3:
					this.verFacturasCliente();
					break;
			}
		} while (opcion != 0);
	}

	/**
	 * Muestra un menú para emitir una factura
	 */
	private void emitirFactura() {
		Cliente cliente = this.getClienteFromInput();

		if (cliente == null) return;

		Factura factura = cliente.emitirFactura();

		System.out.println("Información de la factura recien emitida:");
		System.out.println(factura);
	}

	/**
	 * Muestra un menú para ver una factura
	 */
	private void verFactura() {
		int cod = this.leerEntero("Introduce el codigo factura: ");

		try {
			Factura factura = this.admin.getFactura(cod);
			System.out.println("Información de la factura: " + factura);
		} catch (FacturaNoExisteExcepcion facturaNoExisteExcepcion) {
			System.out.println("No existe ninguna factura con ese codigo");
		}
	}

	/**
	 * Muestra un menú para ver todas las facturas de un cliente
	 */
	private void verFacturasCliente() {
		Cliente cliente = this.getClienteFromInput();

		if (cliente == null) return;

		LinkedList<Factura> facturas = cliente.getFacturas();

		System.out.println("Se han emitido un total de " + facturas.size() + " facturas para este cliente:");

		for (Factura factura : facturas) {
			System.out.println(" - " + factura);
		}
	}

	// ========================= Utilidades ========================= //

	/**
	 * Imprime una lista de tituos especificado por pantalla
	 *
	 * @param titulos Una lista de titulos
	 */
	private void imprimeTitulos(String... titulos) {
		this.clearScreen();
		System.out.println();
		System.out.println("########################################");
		System.out.println();
		this.setColor(170, 170, 170);
		for (int i = 0; i < (titulos.length - 1); i++) {
			System.out.print("                                        ".substring(0, Math.min(20, 20 - (titulos[i].length() / 2))));
			System.out.println(titulos[i]);
		}

		this.setColor(255, 255, 255);
		System.out.print("                                        ".substring(0, Math.min(20, 20 - (titulos[titulos.length - 1].length() / 2) - 2)));
		System.out.println("- " + titulos[titulos.length - 1] + " -");

		System.out.println();
		System.out.println("########################################");
		System.out.println();
	}

	private int seleccionarOpcion(String atras, String... opciones) {
		System.out.println("\nSelecciona una opción (0-" + opciones.length + ")");
		for (int i = 0; i < opciones.length; i++) {
			System.out.println("  " + (i + 1) + ": " + opciones[i]);
		}

		System.out.println("  0: " + atras);

		int seleccion;
		do {
			System.out.print("> ");
			try {
				seleccion = Integer.parseInt(this.scanner.nextLine());
			} catch (NumberFormatException e) {
				seleccion = -1;
			}
		} while ((seleccion > opciones.length) || (seleccion < 0));

		return seleccion;
	}

	// ========================= Leer por pantalla ========================= //

	private String leerTexto(String mensaje, EnumTipoDato tipoDato) {

		System.out.print(mensaje);
		String line = this.scanner.nextLine();

		while (!this.admin.esDatoValido(line, tipoDato)) {
			this.clearPrevLine();
			System.out.print(mensaje);
			line = this.scanner.nextLine();
		}

		return line;
	}

	private int leerEntero(String mensaje) {

		int i = -1;

		while (i < 0) {
			try {
				i = Integer.parseInt(this.leerTexto(mensaje, EnumTipoDato.NUME_ENTERO));
			} catch (Exception e) {
				i = -1;
			}
		}

		return i;
	}

	private Direccion leerDireccion(String mensaje) {

		String direccion = this.leerTexto(mensaje, EnumTipoDato.DIRECCION);
		String[] str = direccion.split(", ");

		return new Direccion(Integer.parseInt(str[0]), str[1], str[2]); // FIXME mover 'new' a clase Administrador
	}

	private LocalDateTime leerFecha(String mensaje) {

		LocalDateTime date = null;

		while (date == null) {
			try {
				String str = this.leerTexto(mensaje, EnumTipoDato.FECHA);
				if ("hoy".equalsIgnoreCase(str)) date = LocalDateTime.now();
				else date = LocalDateTime.parse(str.replace(' ', 'T'));
			} catch (Exception e) {
				date = null;
			}
		}

		return date;
	}

	private Cliente getClienteFromInput() {
		String nif = this.leerTexto("Introduce el NIF del cliente: ", EnumTipoDato.NIF);
		try {
			return this.admin.getCliente(nif);
		} catch (ClienteNoExisteExcepcion clienteNoExisteExcepcion) {
			System.out.println("No existe ningún cliente con NIF '" + nif + "'");
		}
		return null;
	}

	// ========================= Formato de pantalla ========================= //

	private void clearScreen() {
		System.out.print("\033[2J\033[H");
	}

	private void clearPrevLine() {
		System.out.print("\033[F\033[J");
	}

	private void setColor(int r, int g, int b) {
		System.out.print("\033[38;2;" + r + ";" + g + ";" + b + "m");
	}

	// ========================= Tipos de datos ========================= //

}
