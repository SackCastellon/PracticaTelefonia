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

	/**
	 * <code>Scanner</code> utilizado para leer la entrada por consola
	 */
	private Scanner scanner;

	/**
	 * Inicializa la interfaz por linea de comandos, el Scanner para leer la entrada del usuario y muestra el menú
	 * principal
	 */
	public void iniciar() {
		scanner = new Scanner(System.in);
		setColor(255, 255, 255);
		menuPrincipal();
		System.out.print("\033[0m");
		clearScreen();
		scanner.close();
	}

	// ========================= Menús ========================= //

	/**
	 * Muestra el menú principal hasta que se seleccione un sub-menú o se decida salir del programa
	 */
	private void menuPrincipal() {
		int menu;

		do {
			imprimeTitulos("Menú principal");
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
	private void menuClientes() {
		int opcion;

		do {
			imprimeTitulos("Menú principal", "Menú clientes");
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
	private void altaCliente() {

		imprimeTitulos("Menú principal", "Menú clientes", "Alta clientes");
		int tipoCliente = seleccionarOpcion("Cancelar", "Particular", "Empresa");

		if (tipoCliente == 0) return;
		if (tipoCliente == 1) {
			imprimeTitulos("Menú principal", "Menú clientes", "Alta clientes", "Particular");
			System.out.println("Introduce los datos del particular:");
		} else {
			imprimeTitulos("Menú principal", "Menú clientes", "Alta clientes", "Empresa");
			System.out.println("Introduce los datos de la empresa:");
		}


		String apellidos = null;

		String nombre = leerTexto(" - Nombre:", Administrador.EnumTipoDato.TEXTO);
		if (tipoCliente == 1) apellidos = leerTexto(" - Apellidos:", Administrador.EnumTipoDato.TEXTO);
		String nif = leerTexto(" - NIF:", Administrador.EnumTipoDato.NIF);
		DireccionPostal direccion = leerDireccion(" - Dirección:");
		String email = leerTexto(" - E-mail:", Administrador.EnumTipoDato.EMAIL);
		LocalDateTime fecha = leerFecha(" - Fecha de alta:");
		TarifaTelefonica tarifa = new TarifaTelefonica(leerEntero(" - Tarifa:"));

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
	private void bajaCliente() {
		String nif = leerDatoNIF();

		if (!Administrador.bajaCliente(nif))
			System.out.println("No existe ningún cliente con NIF " + nif);
		else
			System.out.println("Cliente con NIF " + nif + " eliminado");
	}


	/**
	 * Muestra un menú para cambiar la tarifa de un cliente
	 */
	private void cambiarTarifa() {
		String nif = leerDatoNIF();

		if (!Administrador.exixteCliente(nif)) {
			System.out.println("No existe ningún cliente con NIF " + nif);
			return;
		}

		System.out.println("Introduce la nueva tarifa:");

		TarifaTelefonica tarifa = new TarifaTelefonica(leerEntero("Tarifa"));

		Administrador.cambiarTarifa(nif, tarifa);
	}

	/**
	 * Muestra un menú para ver los datos de un cliente
	 */
	private void verDatosCliente() {
		String nif = leerDatoNIF();

		Cliente cliente = Administrador.getCliente(nif);
		if (cliente == null) {
			System.out.println("No existe ningún cliente con NIF " + nif);
			return;
		}

		System.out.println("Información del cliente con NIF " + nif + ":");
		System.out.println(cliente);
	}

	/**
	 * Muestra un menú para ver los datos de todos los clientes
	 */
	private void verDatosTodosClientes() {

		Collection<Cliente> clientes = Administrador.getClientes();

		if (clientes.isEmpty()) {
			System.out.println("No hay ningún cliente");
			return;
		}

		for (Cliente cliente : clientes) {
			System.out.println(cliente);
		}
	}

	/**
	 * Muestra el menú de llamadas hasta que se seleccione un sub-menú o se decida salir del programa
	 */
	private void menuLlamadas() {
		int opcion;

		do {
			imprimeTitulos("Menú principal", "Menú llamadas");
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
	private void altaLlamada() {
		String nif = leerDatoNIF();

		if (!Administrador.exixteCliente(nif)) {
			System.out.println("No existe ningún cliente con NIF " + nif);
			return;
		}

		System.out.println("Introduce los siguientes datos de la llamada:");

		String origen = leerTexto("Numero de origen", Administrador.EnumTipoDato.TELEFONO);
		String destino = leerTexto("Numero de destino", Administrador.EnumTipoDato.TELEFONO);
		LocalDateTime fecha = leerFecha("Fecha de la llamada");
		int duracion = leerEntero("Duracion de la llamada en segundos");

		Llamada llamada = new Llamada(origen, destino, fecha, duracion);
		Administrador.altaLlamada(nif, llamada);
	}

	/**
	 * Muestra un menú para ver todas las llamadas
	 */
	private void verLlamadasCliente() {
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
	private void menuFacturas() {
		int opcion;

		do {
			imprimeTitulos("Menú principal", "Menú facturas");
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
	private void emitirFactura() {
		String nif = leerDatoNIF();

		FacturaTelefonica factura = Administrador.emitirFactura(nif);

		if (factura == null) {
			System.out.println("No existe ningún cliente con NIF " + nif);
			return;
		}

		System.out.println("Información de la factura recien emitida");
		System.out.println(factura);
	}

	/**
	 * Muestra un menú para ver una factura
	 */
	private void verFactura() {
		System.out.println("Introduce los siguientes datos de la factura:");
		int cod = leerEntero("Codigo factura");

		FacturaTelefonica factura = Administrador.getFactura(cod);
		System.out.println("Información de la factura con codigo" + cod + ":");
		System.out.println(factura);
	}

	/**
	 * Muestra un menú para ver todas las facturas de un cliente
	 */
	private void verFacturasCliente() {
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
			System.out.println(factura);
		}
	}

	// ========================= Utilidades ========================= //

	/**
	 * Imprime una lista de tituos especificado por pantalla
	 *
	 * @param titulos Una lista de titulos
	 */
	private void imprimeTitulos(String... titulos) {
		clearScreen();
		System.out.println();
		System.out.println("########################################");
		System.out.println();
		setColor(170, 170, 170);
		for (int i = 0; i < titulos.length - 1; i++) {
			System.out.print("                                        ".substring(0, Math.min(20, 20 - titulos[i].length() / 2)));
			System.out.println(titulos[i]);
		}

		setColor(255, 255, 255);
		System.out.print("                                        ".substring(0, Math.min(20, 20 - titulos[titulos.length - 1].length() / 2 - 2)));
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
				seleccion = Integer.parseInt(scanner.nextLine());
			} catch (NumberFormatException e) {
				seleccion = -1;
			}
		} while (seleccion > opciones.length || seleccion < 0);

		return seleccion;
	}

	// ========================= Leer por pantalla ========================= //

	private String leerTexto(String mensaje, Administrador.EnumTipoDato tipoDato) {

		System.out.print(mensaje);
		String line = scanner.nextLine();

		while (!Administrador.esDatoValido(line, tipoDato)) {
			clearPrevLine();
			System.out.print(mensaje);
			line = scanner.nextLine();
		}

		return line;
	}

	private int leerEntero(String mensaje) {

		int i = -1;

		while (i < 0) {
			try {
				i = Integer.parseInt(leerTexto(mensaje, Administrador.EnumTipoDato.NUME_ENTERO));
			} catch (Exception e) {
				i = -1;
			}
		}

		return i;
	}

	private DireccionPostal leerDireccion(String mensaje) {

		String[] str = leerTexto(mensaje, Administrador.EnumTipoDato.DIRECCION).split(", ");

		return new DireccionPostal(Integer.parseInt(str[0]), str[1], str[2]);
	}

	private LocalDateTime leerFecha(String mensaje) {

		LocalDateTime date = null;

		while (date == null) {
			try {
				String str = leerTexto(mensaje, Administrador.EnumTipoDato.FECHA);
				if (str.equalsIgnoreCase("hoy")) date = LocalDateTime.now();
				else date = LocalDateTime.parse(str.replace(' ', 'T'));
			} catch (Exception e) {
				date = null;
			}
		}

		return date;
	}

	private String leerDatoNIF() {
		System.out.println("Introduce los siguientes datos del cliente:");
		return leerTexto("NIF", Administrador.EnumTipoDato.NIF);
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
