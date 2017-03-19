package es.uji.al341823.telefonia.gui.console.menu.clientes;

import es.uji.al341823.telefonia.api.AdministradorDatos;
import es.uji.al341823.telefonia.api.AdministradorMenus;
import es.uji.al341823.telefonia.api.excepciones.FechaNoValidaExcepcion;
import es.uji.al341823.telefonia.clientes.Cliente;
import es.uji.al341823.telefonia.gui.console.menu.Menu;

import java.time.LocalDateTime;
import java.util.Collection;

/**
 * @author Juanjo González (al341823)
 * @since 0.2
 */
public class ExtraerClientes extends Menu {
	public ExtraerClientes(Menu padre) {
		super(padre);
	}

	@Override
	public void mostrar() {
		AdministradorMenus.imprimeTitulo(this);

		LocalDateTime inicio = AdministradorMenus.leerFecha("Introcuce la fecha de inicio: ");
		LocalDateTime fin = AdministradorMenus.leerFecha("Introcuce la fecha de fin: ");

		Collection<Cliente> clientes;

		try {
			clientes = AdministradorDatos.extraerConjunto(AdministradorDatos.getClientes(), inicio, fin);
		} catch (FechaNoValidaExcepcion e) {
			System.out.println("El periodo de tiempo especificado no es valido");
			AdministradorMenus.esperarParaContinuar();
			return;
		}

		System.out.println();

		System.out.println("Durante este periodo de tiempo se direon de alta un total de " + clientes.size() + " cliente(s)");

		for (Cliente cliente : clientes) {
			System.out.println(" - " + cliente);
		}

		AdministradorMenus.esperarParaContinuar();

	}

	@Override
	public String getTitulo() {
		return "Ver clientes dados de alta entre dos fechas";
	}

	@Override
	public Menu[] getSubmenus() {
		return new Menu[0];
	}
}
