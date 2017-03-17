package es.uji.al341823.telefonia.gui.console.menu.clientes;

import es.uji.al341823.telefonia.clientes.Cliente;
import es.uji.al341823.telefonia.api.manager.MenuManager;
import es.uji.al341823.telefonia.gui.console.menu.Menu;

/**
 * Created by Juanjo on 16/03/2017.
 */
class VerDatosCliente extends Menu {

	public VerDatosCliente(Menu padre) {
		super(padre);
	}

	@Override
	public void mostrar() {
		MenuManager.imprimeTitulo(this);

		Cliente cliente = MenuManager.leerClienteNIF();

		if (cliente == null) return;

		System.out.println("Información del cliente: " + cliente);

		MenuManager.esperarParaContinuar();
	}

	@Override
	public String getTitulo() {
		return "Ver datos cliente";
	}

	@Override
	public Menu[] getSubmenus() {
		return null;
	}
}
