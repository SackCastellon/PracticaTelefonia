package es.uji.al341823.telefonia.gui.console.menu.llamadas;

import es.uji.al341823.telefonia.clientes.Cliente;
import es.uji.al341823.telefonia.api.manager.MenuManager;
import es.uji.al341823.telefonia.gui.console.menu.Menu;
import es.uji.al341823.telefonia.llamadas.Llamada;

import java.util.LinkedList;

/**
 * Created by Juanjo on 16/03/2017.
 */
class VerLlamadasCliente extends Menu {
	public VerLlamadasCliente(Menu padre) {
		super(padre);
	}

	@Override
	public void mostrar() {
		MenuManager.imprimeTitulo(this);

		Cliente cliente = MenuManager.leerClienteNIF();

		if (cliente == null) return;

		LinkedList<Llamada> llamadas = cliente.getLlamadas();

		System.out.println("Se han emitido un total de " + llamadas.size() + " facturas para este cliente:");

		for (Llamada llamada : llamadas) {
			System.out.println(" - " + llamada);
		}

		MenuManager.esperarParaContinuar();
	}

	@Override
	public String getTitulo() {
		return "Ver llamadas cliente";
	}

	@Override
	public Menu[] getSubmenus() {
		return null;
	}
}
