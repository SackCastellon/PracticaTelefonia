/*
 * Copyright (c) 2017. Esta obra está sujeta a la licencia Reconocimiento 4.0 Internacional de Creative Commons.
 * Para ver una copia de esta licencia, visite http://creativecommons.org/licenses/by/4.0/.
 */

package es.uji.al341823.telefonia.gui.console.menu.llamadas;

import es.uji.al341823.telefonia.api.AdministradorMenus;
import es.uji.al341823.telefonia.clientes.Cliente;
import es.uji.al341823.telefonia.gui.console.menu.Menu;
import es.uji.al341823.telefonia.llamadas.Llamada;

import java.util.LinkedList;

/**
 * @author Juanjo González (al341823)
 * @since 0.2
 */
public class VerLlamadasCliente extends Menu {
	public VerLlamadasCliente(Menu padre) {
		super(padre);
	}

	@Override
	public void mostrar() {
		AdministradorMenus.imprimeTitulo(this);

		Cliente cliente = AdministradorMenus.leerClienteNIF();

		if (cliente == null) return;

		LinkedList<Llamada> llamadas = cliente.getLlamadas();

		System.out.println("Este cliente ha  " + llamadas.size() + " llamadas para este cliente:");

		for (Llamada llamada : llamadas) {
			System.out.println(" - " + llamada);
		}

		AdministradorMenus.esperarParaContinuar();
	}

	@Override
	public String getTitulo() {
		return "Ver llamadas cliente";
	}
}
