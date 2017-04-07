package es.uji.al341823.telefonia.gui.console.menu;

import es.uji.al341823.telefonia.gui.console.menu.clientes.MenuClientes;
import es.uji.al341823.telefonia.gui.console.menu.facturas.MenuFacturas;
import es.uji.al341823.telefonia.gui.console.menu.llamadas.MenuLlamadas;

/**
 * @author Juanjo González (al341823)
 * @since 0.2
 */
public class MenuPrincipal extends Menu {

	public MenuPrincipal(Menu padre) {
		super(padre);
	}

	@Override
	public String getTitulo() {
		return "Menú principal";
	}

	@Override
	public Menu[] getSubmenus() {
		return new Menu[] {new MenuClientes(this), new MenuLlamadas(this),
				new MenuFacturas(this)};
	}
}
