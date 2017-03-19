package es.uji.al341823.telefonia.gui.console.menu.clientes;

import es.uji.al341823.telefonia.api.AdministradorDatos;
import es.uji.al341823.telefonia.api.AdministradorMenus;
import es.uji.al341823.telefonia.clientes.Cliente;
import es.uji.al341823.telefonia.gui.console.menu.Menu;

import java.util.LinkedList;

/**
 * @author Juanjo González (al341823)
 * @since 0.2
 */
public class VerDatosTodosClientes extends Menu {

	public VerDatosTodosClientes(Menu padre) {
		super(padre);
	}

	@Override
	public void mostrar() {
		AdministradorMenus.imprimeTitulo(this);

		LinkedList<Cliente> clientes = AdministradorDatos.getClientes();

		System.out.printf("Hay un total de %d clientes", clientes.size());

		System.out.println();

		for (Cliente cliente : clientes) {
			System.out.println(" - " + cliente);
		}

		AdministradorMenus.esperarParaContinuar();
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
