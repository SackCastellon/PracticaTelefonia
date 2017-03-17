package es.uji.al341823.telefonia.gui.console.menu;

import es.uji.al341823.telefonia.api.manager.MenuManager;
import es.uji.al341823.telefonia.gui.console.menu.clientes.MenuClientes;
import es.uji.al341823.telefonia.gui.console.menu.facturas.MenuFacturas;
import es.uji.al341823.telefonia.gui.console.menu.llamadas.MenuLlamadas;

/**
 * Created by Juanjo on 15/03/2017.
 */
public class MenuPrincipal extends Menu {

	public MenuPrincipal(Menu padre) {
		super(padre);
	}

	@Override
	public void mostrar() {
		MenuManager.seleccionarOpcion(this);
	}

	@Override
	public String getTitulo() {
		return "Menú principal";
	}

	@Override
	public Menu[] getSubmenus() {
		return new Menu[] {new MenuClientes(this), new MenuLlamadas(this), new MenuFacturas(this)};
	}
}
