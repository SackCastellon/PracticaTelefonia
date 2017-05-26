/*
 * Copyright (c) 2017. Esta obra está sujeta a la licencia Reconocimiento 4.0 Internacional de Creative Commons.
 * Para ver una copia de esta licencia, visite http://creativecommons.org/licenses/by/4.0/.
 */

package es.uji.al341823.telefonia.gui.console.clientes;

import es.uji.al341823.telefonia.api.AdministradorMenus;
import es.uji.al341823.telefonia.api.excepciones.ObjetoNoExisteException;
import es.uji.al341823.telefonia.clientes.Cliente;
import es.uji.al341823.telefonia.gui.console.Menu;

/**
 * @author Juanjo González (al341823)
 * @since 0.2
 */
public class MenuVerDatosCliente extends Menu {

	public MenuVerDatosCliente(Menu padre) {
		super(padre);
	}

	@Override
	public void mostrar() {
		AdministradorMenus.imprimeTitulo(this);

		String nif =  this.getAdministradorMenus().leerNIF();

		try {
			Cliente cliente = this.getAdministradorDatos().getCliente(nif);
			System.out.println("Información del cliente: " + cliente);
		} catch (ObjetoNoExisteException e) {
			System.out.println("No existe ningún cliente con NIF '" + nif + "'");
		} finally {
			System.out.println();
			AdministradorMenus.esperarParaContinuar();
		}
	}

	@Override
	public String getTitulo() {
		return "Ver datos cliente";
	}
}
