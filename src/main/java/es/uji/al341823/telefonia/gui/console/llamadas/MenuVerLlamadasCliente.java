/*
 * Copyright (c) 2017. Esta obra está sujeta a la licencia Reconocimiento 4.0 Internacional de Creative Commons.
 * Para ver una copia de esta licencia, visite http://creativecommons.org/licenses/by/4.0/.
 */

package es.uji.al341823.telefonia.gui.console.llamadas;

import es.uji.al341823.telefonia.api.AdministradorDatos;
import es.uji.al341823.telefonia.api.AdministradorMenus;
import es.uji.al341823.telefonia.api.excepciones.ObjetoNoExisteException;
import es.uji.al341823.telefonia.gui.console.Menu;
import es.uji.al341823.telefonia.llamadas.Llamada;

import java.util.Collection;

/**
 * @author Juanjo González (al341823)
 * @since 0.2
 */
public class MenuVerLlamadasCliente extends Menu {
	public MenuVerLlamadasCliente(Menu padre) {
		super(padre);
	}

	@Override
	public void mostrar() {
		AdministradorMenus.imprimeTitulo(this);

		String nif = AdministradorMenus.leerNIF();

		try {
			System.out.println();
			Collection<Llamada> llamadas = getAdministradorDatos().getLlamadasCliente(nif);

			System.out.println("Este cliente ha  " + llamadas.size() + " llamadas para este cliente:");

			for (Llamada llamada : llamadas) {
				System.out.println(" - " + llamada);
			}
		} catch (ObjetoNoExisteException e) {
			System.out.println("No existe ningún cliente con NIF '" + nif + "'");
		}

		AdministradorMenus.esperarParaContinuar();
	}

	@Override
	public String getTitulo() {
		return "Ver llamadas cliente";
	}
}
