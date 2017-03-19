package es.uji.al341823.telefonia.gui.console.menu.clientes;

import es.uji.al341823.telefonia.api.AdministradorMenus;
import es.uji.al341823.telefonia.gui.console.menu.Menu;

/**
 * @author Juanjo González (al341823)
 * @since 0.2
 */
public class MenuClientes extends Menu {

	public MenuClientes(Menu padre) {
		super(padre);
	}

	@Override
	public void mostrar() {
		AdministradorMenus.seleccionarOpcion(this);
	}

	@Override
	public String getTitulo() {
		return "Menú clientes";
	}

	@Override
	public Menu[] getSubmenus() {
		return new Menu[] {new AltaCliente(this), new BajaCliente(this),
				new CambiarTarifa(this), new VerDatosCliente(this),
				new VerDatosTodosClientes(this), new ExtraerClientes(this)};
	}
}
