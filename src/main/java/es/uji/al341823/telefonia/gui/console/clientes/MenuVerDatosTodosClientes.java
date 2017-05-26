/*
 * Copyright (c) 2017. Esta obra está sujeta a la licencia Reconocimiento 4.0 Internacional de Creative Commons.
 * Para ver una copia de esta licencia, visite http://creativecommons.org/licenses/by/4.0/.
 */

package es.uji.al341823.telefonia.gui.console.clientes;

import es.uji.al341823.telefonia.api.AdministradorMenus;
import es.uji.al341823.telefonia.clientes.Cliente;
import es.uji.al341823.telefonia.gui.console.Menu;

import java.util.LinkedList;

/**
 * @author Juanjo González (al341823)
 * @since 0.2
 */
public class MenuVerDatosTodosClientes extends Menu {

	public MenuVerDatosTodosClientes(Menu padre) {
		super(padre);
	}

	@Override
	public void mostrar() {
		AdministradorMenus.imprimeTitulo(this);

		LinkedList<Cliente> clientes = new LinkedList<>(getAdministradorDatos().getClientes());

		System.out.printf("Hay un total de %d clientes\n", clientes.size());

		for (Cliente cliente : clientes)
			System.out.println("\n - " + cliente);

		AdministradorMenus.esperarParaContinuar();
	}

	@Override
	public String getTitulo() {
		return "Ver datos todos cliente";
	}
}
