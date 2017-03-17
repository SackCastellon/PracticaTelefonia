package es.uji.al341823.telefonia.gui.console.menu.llamadas;

import es.uji.al341823.telefonia.api.manager.MenuManager;
import es.uji.al341823.telefonia.gui.console.menu.Menu;

/**
 * Created by Juanjo on 15/03/2017.
 */
public class MenuLlamadas extends Menu {

	public MenuLlamadas(Menu padre) {
		super(padre);
	}

	@Override
	public void mostrar() {
		MenuManager.seleccionarOpcion(this);
	}

	@Override
	public String getTitulo() {
		return "Menú llamadas";
	}

	@Override
	public Menu[] getSubmenus() {
		return new Menu[] {new AltaLlamada(this), new VerLlamadasCliente(this)};
	}
}
