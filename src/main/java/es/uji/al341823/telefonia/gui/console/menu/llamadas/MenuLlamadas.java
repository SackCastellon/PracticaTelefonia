package es.uji.al341823.telefonia.gui.console.menu.llamadas;

import es.uji.al341823.telefonia.api.AdministradorMenus;
import es.uji.al341823.telefonia.gui.console.menu.Menu;

/**
 * @author Juanjo González (al341823)
 * @since 0.2
 */
public class MenuLlamadas extends Menu {

	public MenuLlamadas(Menu padre) {
		super(padre);
	}

	@Override
	public void mostrar() {
		AdministradorMenus.seleccionarSubmenu(this);
	}

	@Override
	public String getTitulo() {
		return "Menú llamadas";
	}

	@Override
	public Menu[] getSubmenus() {
		return new Menu[] {new AltaLlamada(this), new VerLlamadasCliente(this),
				new ExtraerLlamadas(this)};
	}
}
