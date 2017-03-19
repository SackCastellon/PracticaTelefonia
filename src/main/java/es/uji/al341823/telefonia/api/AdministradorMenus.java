package es.uji.al341823.telefonia.api;

import es.uji.al341823.telefonia.api.excepciones.ClienteNoExisteExcepcion;
import es.uji.al341823.telefonia.clientes.Cliente;
import es.uji.al341823.telefonia.clientes.Direccion;
import es.uji.al341823.telefonia.gui.console.menu.Menu;

import java.time.LocalDateTime;
import java.util.LinkedList;

/**
 * Contiene una seria de metodos utiles para los menus
 *
 * @author Juanjo González (al341823)
 * @since 0.2
 */
public class AdministradorMenus {

	public static void imprimeTitulo(Menu menu) {
		String spaceOrigninal = "                              ";
		LinkedList<String> titulos = new LinkedList<>();

		Menu padre = menu;
		while ((padre = padre.getPadre()) != null)
			titulos.add(0, padre.getTitulo());


		clearScreen();

		System.out.println();
		System.out.println("############################################################");
		System.out.println();

		for (String titulo : titulos) {
			String space = spaceOrigninal.substring(0, Math.max(0, 30 - (titulo.length() / 2)));
			System.out.println(space + titulo);
		}

		String titulo = menu.getTitulo();
		String space = spaceOrigninal.substring(0, Math.max(0, 28 - (titulo.length() / 2)));

		System.out.println(space + "- " + menu.getTitulo() + " -");

		System.out.println();
		System.out.println("############################################################");
		System.out.println();
	}

	public static void seleccionarOpcion(Menu menu) {
		while (true) {
			imprimeTitulo(menu);

			System.out.println("Elige una opción:");

			for (int i = 1; i <= menu.getSubmenus().length; i++)
				System.out.printf("  %d - %s\n", i, menu.getSubmenus()[i - 1].getTitulo());

			Menu padre;
			if ((padre = menu.getPadre()) != null)
				System.out.printf("\n  %d - Volver a '%s'\n", 0, padre.getTitulo());
			else
				System.out.printf("\n  %d - %s\n", 0, "Salir de programa");

			while (true) {
				int seleccion;
				System.out.print("> ");

				try {
					seleccion = Integer.parseInt(Menu.scanner.nextLine());
				} catch (Exception e) {
					seleccion = -1;
				}

				if (seleccion == 0)
					return;

				else if ((seleccion <= menu.getSubmenus().length) && (seleccion > 0)) {
					menu.getSubmenus()[seleccion - 1].mostrar();
					break;
				}

				clearLine();
			}
		}
	}

	public static String leerTexto(String mensaje, EnumTipoDato tipoDato) {

		System.out.print(mensaje);
		String line = Menu.scanner.nextLine();

		while (!AdministradorDatos.esDatoValido(line, tipoDato)) {
			clearLine();
			System.out.print(mensaje);
			line = Menu.scanner.nextLine();
		}

		return line;
	}

	public static float leerNumero(String mensaje) {

		String line = leerTexto(mensaje, EnumTipoDato.NUMERO);

		return Float.parseFloat(line);
	}

	public static int leerEntero(String mensaje) {

		String line = leerTexto(mensaje, EnumTipoDato.ENTERO);

		return Integer.parseInt(line);
	}

	public static Direccion leerDireccion(String mensaje) {

		String line = leerTexto(mensaje, EnumTipoDato.DIRECCION);
		String[] direccion = line.split(", ");

		return new Direccion(Integer.parseInt(direccion[0]), direccion[1], direccion[2]);
	}

	public static LocalDateTime leerFecha(String mensaje) {

		LocalDateTime date = null;

		while (date == null) {
			try {
				String line = leerTexto(mensaje, EnumTipoDato.FECHA);
				if ("hoy".equalsIgnoreCase(line)) date = LocalDateTime.now();
				else date = LocalDateTime.parse(line.replace(' ', 'T'));
			} catch (Exception e) {
				date = null;
			}
		}

		return date;
	}

	public static Cliente leerClienteNIF() {

		String nif = leerTexto("Introduce el NIF del cliente: ", EnumTipoDato.NIF);

		try {
			return AdministradorDatos.getCliente(nif);
		} catch (ClienteNoExisteExcepcion e) {
			System.out.println();
			System.out.println("No existe ningún cliente con NIF '" + nif + "'");
			esperarParaContinuar();
			return null;
		}
	}

	public static void esperarParaContinuar() {
		System.out.println();
		System.out.print("Pulsa 'Enter' para continuar...");
		Menu.scanner.nextLine();
	}

	public static void clearScreen() {
		System.out.print("\033[2J\033[H");
	}

	public static void clearLine() {
		System.out.print("\033[F\033[J");
	}
}
