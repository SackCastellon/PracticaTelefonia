/*
 * Copyright (c) 2017. Esta obra está sujeta a la licencia Reconocimiento 4.0 Internacional de Creative Commons.
 * Para ver una copia de esta licencia, visite http://creativecommons.org/licenses/by/4.0/.
 */

package es.uji.al341823.telefonia.gui.console.llamadas;

import es.uji.al341823.telefonia.gui.console.Menu;

/**
 * @author Juanjo González (al341823)
 * @since 0.2
 */
public class MenuLlamadas extends Menu {

	public MenuLlamadas(Menu padre) {
		super(padre);
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
