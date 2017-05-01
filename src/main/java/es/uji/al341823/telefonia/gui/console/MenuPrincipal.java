/*
 * Copyright (c) 2017. Esta obra está sujeta a la licencia Reconocimiento 4.0 Internacional de Creative Commons.
 * Para ver una copia de esta licencia, visite http://creativecommons.org/licenses/by/4.0/.
 */

package es.uji.al341823.telefonia.gui.console;

import es.uji.al341823.telefonia.gui.console.clientes.MenuClientes;
import es.uji.al341823.telefonia.gui.console.facturas.MenuFacturas;
import es.uji.al341823.telefonia.gui.console.llamadas.MenuLlamadas;

/**
 * @author Juanjo González (al341823)
 * @since 0.2
 */
public class MenuPrincipal extends Menu {

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
