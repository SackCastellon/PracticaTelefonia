/*
 * Copyright (c) 2017. Esta obra está sujeta a la licencia Reconocimiento 4.0 Internacional de Creative Commons.
 * Para ver una copia de esta licencia, visite http://creativecommons.org/licenses/by/4.0/.
 */

package es.uji.al341823.telefonia.api;

import es.uji.al341823.telefonia.api.excepciones.ClienteNoExisteExcepcion;
import es.uji.al341823.telefonia.clientes.Cliente;
import es.uji.al341823.telefonia.clientes.Direccion;
import es.uji.al341823.telefonia.gui.console.Menu;

import java.time.LocalDateTime;
import java.util.LinkedList;

/**
 * Contiene una seria de metodos utiles para los menus
 *
 * @author Juanjo González (al341823)
 * @since 0.2
 */
public class AdministradorMenus {

	/**
	 * Imprime el titulo del menú indicado, mostrando tambien los titulos de sus menús padre de forma recursiva.
	 * <ol>
	 * <li>Limpia la pantalla</li>
	 * <li>Muestra al jerarquia de menús hasta el actual</li>
	 * <li>Muestra el titulo del menú actual</li>
	 * </ol>
	 *
	 * @param menu El menú actual
	 */
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

		System.out.println(space + "- " + titulo + " -");

		System.out.println();
		System.out.println("############################################################");
		System.out.println();
	}

	/**
	 * Muestra una lista con los submenún del menú actual, y una opcion para volver al menu anterior (o salir de
	 * programa en su defecto), y espera a que se selecione uno que posteriormente se mostrará en pantalla
	 *
	 * @param menu El menú actual
	 */
	public static void seleccionarSubmenu(Menu menu) { // TODO Actualizar para que use seleccionarOpciones()
		while (true) {
			AdministradorMenus.clearScreen();
			AdministradorMenus.imprimeTitulo(menu);

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

	/**
	 * Muestra una lista con las opciones especificadas y espera a que se selecione una
	 *
	 * @param opciones Las opciones a seleccionar
	 * @param <T>      Un conjunto de opciones que implementen la interfaz IDescricion
	 *
	 * @return La opción seleccionada
	 */
	public static <T> T seleccionarOpciones(T[] opciones) {
		System.out.println("Elige una opción:");

		for (int i = 0; i < opciones.length; i++)
			System.out.printf("  %d - %s\n", i, opciones[i]);

		while (true) {
			System.out.print("> ");

			try {
				int seleccion = Integer.parseInt(Menu.scanner.nextLine());
				return opciones[seleccion];
			} catch (Exception e) {
				clearLine();
			}
		}
	}

	/**
	 * Muestra el mensaje indicado y espera a que el usuario introduzca un dato del tipo indicado
	 * Solo debuelve el dato intoducido cuando este dato es del tipo indicado y valido
	 *
	 * @param mensaje  En mensaje a mostrar
	 * @param tipoDato El tipo del dato a introducit
	 *
	 * @return El dato introducido
	 */
	public static String leerDato(String mensaje, TipoDato tipoDato) {

		System.out.print(mensaje);
		String line = Menu.scanner.nextLine();

		while (!AdministradorDatos.esDatoValido(line, tipoDato)) {
			clearLine();
			System.out.print(mensaje);
			line = Menu.scanner.nextLine();
		}

		return line;
	}

	/**
	 * Muestra el mensaje indicado y espera a que el usuario introduzca un entero
	 *
	 * @param mensaje En mensaje a mostrar
	 *
	 * @return El entero introducido
	 *
	 * @see AdministradorMenus#leerDato(String, TipoDato)
	 */
	public static int leerEntero(String mensaje) {

		String line = leerDato(mensaje, TipoDato.ENTERO);

		return Integer.parseInt(line);
	}

	/**
	 * Muestra el mensaje indicado y espera a que el usuario introduzca una direccion
	 *
	 * @param mensaje En mensaje a mostrar
	 *
	 * @return La {@link Direccion} introducida
	 *
	 * @see AdministradorMenus#leerDato(String, TipoDato)
	 */
	public static Direccion leerDireccion(String mensaje) {

		String line = leerDato(mensaje, TipoDato.DIRECCION);
		String[] direccion = line.split(", ");

		return new Direccion(Integer.parseInt(direccion[0]), direccion[1], direccion[2]);
	}

	/**
	 * Muestra el mensaje indicado y espera a que el usuario introduzca una fecha con formato '{@code AAAA-MM-DD
	 * hh:mm:ss}' o la cadena '{@code hoy}'
	 *
	 * @param mensaje En mensaje a mostrar
	 *
	 * @return La fecha introducida ({@link LocalDateTime})
	 *
	 * @see AdministradorMenus#leerDato(String, TipoDato)
	 */
	public static LocalDateTime leerFecha(String mensaje) {

		LocalDateTime date = null;

		while (date == null) {

			String line = leerDato(mensaje, TipoDato.FECHA);

			date = "hoy".equalsIgnoreCase(line) ? LocalDateTime.now() : LocalDateTime.parse(line.replace(' ', 'T'));
		}

		return date;
	}

	/**
	 * Espera a que el usuario introduzca el NIF de un cliente
	 *
	 * @return El cliente correspondiente al NIF introducido, {@code null} ni el NIF no corresponde a ningún
	 * cliente
	 *
	 * @see AdministradorMenus#leerDato(String, TipoDato)
	 */
	public static Cliente leerClienteNIF() {

		String nif = leerDato("Introduce el NIF del cliente: ", TipoDato.NIF);

		try {
			return AdministradorDatos.getCliente(nif);
		} catch (ClienteNoExisteExcepcion e) {
			System.out.println();
			System.out.println("No existe ningún cliente con NIF '" + nif + "'");
			esperarParaContinuar();
			return null;
		}
	}

	/**
	 * Muestra el mensaje {@code Pulsa 'Enter' para continuar...} y espera a que el usuario pulse
	 * {@code Enter}
	 */
	public static void esperarParaContinuar() {
		System.out.println();
		System.out.print("Pulsa 'Enter' para continuar...");
		Menu.scanner.nextLine();
	}

	/**
	 * Limpia toda la pantalla de la consola y posiciona el cursor en la esquina superior izquierda
	 */
	private static void clearScreen() {
		System.out.print("\033[2J\033[H");
	}

	/**
	 * Borra la linea anterior y posiciona el cursor al principio de esa linea
	 */
	private static void clearLine() {
		System.out.print("\033[F\033[J");
	}
}
