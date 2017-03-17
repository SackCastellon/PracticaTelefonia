package es.uji.al341823.telefonia.gui.console.menu.llamadas;

import es.uji.al341823.telefonia.api.excepciones.FechaNoValidaExcepcion;
import es.uji.al341823.telefonia.api.manager.DataManager;
import es.uji.al341823.telefonia.api.manager.MenuManager;
import es.uji.al341823.telefonia.clientes.Cliente;
import es.uji.al341823.telefonia.gui.console.menu.Menu;
import es.uji.al341823.telefonia.llamadas.Llamada;

import java.time.LocalDateTime;
import java.util.Collection;

/**
 * Created by juanjo on 3/17/17.
 */
public class ExtraerLlamadas extends Menu {
	public ExtraerLlamadas(Menu padre) {
		super(padre);
	}

	@Override
	public void mostrar() {
		MenuManager.imprimeTitulo(this);

		Cliente cliente = MenuManager.leerClienteNIF();

		if (cliente == null) return;

		LocalDateTime inicio = MenuManager.leerFecha("Introcuce la fecha de inicio: ");
		LocalDateTime fin = MenuManager.leerFecha("Introcuce la fecha de fin: ");

		Collection<Llamada> llamadas;

		try {
			llamadas = DataManager.extraerConjunto(cliente.getLlamadas(), inicio, fin);
		} catch (FechaNoValidaExcepcion e) {
			System.out.println("El periodo de tiempo especificado no es valido");
			MenuManager.esperarParaContinuar();
			return;
		}

		System.out.println("Durante este periodo de tiempo este cliente realizó un total de " + llamadas.size() + " llamadas");

		System.out.println();

		for (Llamada llamada : llamadas) {
			System.out.println(" - " + llamada);
		}

		MenuManager.esperarParaContinuar();

	}

	@Override
	public String getTitulo() {
		return "Faturas de un cliente emitidas entre dos fechas";
	}

	@Override
	public Menu[] getSubmenus() {
		return new Menu[0];
	}
}