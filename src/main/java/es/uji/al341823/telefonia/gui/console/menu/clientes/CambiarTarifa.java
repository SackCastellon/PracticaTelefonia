package es.uji.al341823.telefonia.gui.console.menu.clientes;

import es.uji.al341823.telefonia.gui.console.menu.Menu;
import es.uji.al341823.telefonia.gui.console.menu.clientes.tarifa.CambiarTarifaBase;
import es.uji.al341823.telefonia.gui.console.menu.clientes.tarifa.CambiarTarifaExtra;

/**
 * Clase del menu para cambiar la tarifa de un cliente
 *
 * @author Juanjo González (al341823)
 * @since 0.2
 */
public class CambiarTarifa extends Menu {

	public CambiarTarifa(Menu padre) {
		super(padre);
	}

	@Override
	public String getTitulo() {
		return "Cambiar tarifa";
	}

	@Override
	public Menu[] getSubmenus() {
		return new Menu[] {new CambiarTarifaBase(this), new CambiarTarifaExtra(this)};
	}
}
