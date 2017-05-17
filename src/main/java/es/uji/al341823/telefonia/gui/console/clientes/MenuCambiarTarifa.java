/*
 * Copyright (c) 2017. Esta obra está sujeta a la licencia Reconocimiento 4.0 Internacional de Creative Commons.
 * Para ver una copia de esta licencia, visite http://creativecommons.org/licenses/by/4.0/.
 */

package es.uji.al341823.telefonia.gui.console.clientes;

import es.uji.al341823.telefonia.gui.console.Menu;
import es.uji.al341823.telefonia.gui.console.clientes.tarifa.MenuCambiarTarifaBase;
import es.uji.al341823.telefonia.gui.console.clientes.tarifa.MenuCambiarTarifaExtra;

/**
 * Clase del menu para cambiar la tarifa de un cliente
 *
 * @author Juanjo González (al341823)
 * @since 0.2
 */
public class MenuCambiarTarifa extends Menu {

	public MenuCambiarTarifa(Menu padre) {
		super(padre);
	}

	@Override
	public String getTitulo() {
		return "Cambiar tarifa";
	}

	@Override
	public Menu[] getSubmenus() {
		return new Menu[] {new MenuCambiarTarifaBase(this), new MenuCambiarTarifaExtra(this)};
	}
}
