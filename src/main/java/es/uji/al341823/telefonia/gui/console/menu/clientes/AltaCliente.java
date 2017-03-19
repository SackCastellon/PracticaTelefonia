package es.uji.al341823.telefonia.gui.console.menu.clientes;

import es.uji.al341823.telefonia.api.AdministradorMenus;
import es.uji.al341823.telefonia.gui.console.menu.Menu;

/**
 * Clase del menu para dar de alta a un cliente
 *
 * @author Juanjo González (al341823)
 * @since 0.2
 */
public class AltaCliente extends Menu {

	public AltaCliente(Menu padre) {
		super(padre);
	}

	@Override
	public void mostrar() {
		AdministradorMenus.seleccionarOpcion(this);
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
