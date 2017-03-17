package es.uji.al341823.telefonia.gui.console.menu.clientes;

import es.uji.al341823.telefonia.api.manager.DataManager;
import es.uji.al341823.telefonia.clientes.Cliente;
import es.uji.al341823.telefonia.api.manager.MenuManager;
import es.uji.al341823.telefonia.gui.console.menu.Menu;

import java.util.LinkedList;

/**
 * Created by Juanjo on 16/03/2017.
 */
class VerDatosTodosClientes extends Menu {

	public VerDatosTodosClientes(Menu padre) {
		super(padre);
	}

	@Override
	public void mostrar() {
		MenuManager.imprimeTitulo(this);

		LinkedList<Cliente> clientes = DataManager.getClientes();

		System.out.printf("Hay un total de %d clientes", clientes.size());

		System.out.println();

		for (Cliente cliente : clientes) {
			System.out.println(" - " + cliente);
		}

		MenuManager.esperarParaContinuar();
	}

	@Override
	public String getTitulo() {
		return "Ver datos todos cliente";
	}

	@Override
	public Menu[] getSubmenus() {
		return null;
	}
}
