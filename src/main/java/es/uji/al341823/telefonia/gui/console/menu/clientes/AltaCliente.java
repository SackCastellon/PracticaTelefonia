package es.uji.al341823.telefonia.gui.console.menu.clientes;

import es.uji.al341823.telefonia.api.manager.MenuManager;
import es.uji.al341823.telefonia.gui.console.menu.Menu;

/**
 * Created by Juanjo on 16/03/2017.
 */
class AltaCliente extends Menu {

	public AltaCliente(Menu padre) {
		super(padre);
	}

	@Override
	public void mostrar() {
		MenuManager.seleccionarOpcion(this);
	}

	@Override
	public String getTitulo() {
		return "Alta cliente";
	}

	@Override
	public Menu[] getSubmenus() {
		return new Menu[] {new AltaParticular(this), new AltaEmpresa(this)};
	}

}
