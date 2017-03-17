package es.uji.al341823.telefonia.gui.console.menu.clientes;

import es.uji.al341823.telefonia.api.excepciones.FechaNoValidaExcepcion;
import es.uji.al341823.telefonia.api.manager.DataManager;
import es.uji.al341823.telefonia.api.manager.MenuManager;
import es.uji.al341823.telefonia.clientes.Cliente;
import es.uji.al341823.telefonia.gui.console.menu.Menu;

import java.time.LocalDateTime;
import java.util.Collection;

/**
 * Created by juanjo on 3/17/17.
 */
public class ExtraerClientes extends Menu {
	public ExtraerClientes(Menu padre) {
		super(padre);
	}

	@Override
	public void mostrar() {
		MenuManager.imprimeTitulo(this);

		LocalDateTime inicio = MenuManager.leerFecha("Introcuce la fecha de inicio: ");
		LocalDateTime fin = MenuManager.leerFecha("Introcuce la fecha de fin: ");

		Collection<Cliente> clientes;

		try {
			clientes = DataManager.extraerConjunto(DataManager.getClientes(), inicio, fin);
		} catch (FechaNoValidaExcepcion e) {
			System.out.println("El periodo de tiempo especificado no es valido");
			MenuManager.esperarParaContinuar();
			return;
		}

		System.out.println("Durante este periodo de tiempo se direon de alta un total de " + clientes.size() + " clientes");

		System.out.println();

		for (Cliente cliente : clientes) {
			System.out.println(" - " + cliente);
		}

		MenuManager.esperarParaContinuar();

	}

	@Override
	public String getTitulo() {
		return "Clientes dados de alta entre dos fechas";
	}

	@Override
	public Menu[] getSubmenus() {
		return new Menu[0];
	}
}
