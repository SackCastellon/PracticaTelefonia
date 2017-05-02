/*
 * Copyright (c) 2017. Esta obra está sujeta a la licencia Reconocimiento 4.0 Internacional de Creative Commons.
 * Para ver una copia de esta licencia, visite http://creativecommons.org/licenses/by/4.0/.
 */

package es.uji.al341823.telefonia.gui.console.clientes;

import es.uji.al341823.telefonia.api.AdministradorDatos;
import es.uji.al341823.telefonia.api.AdministradorMenus;
import es.uji.al341823.telefonia.api.TipoDato;
import es.uji.al341823.telefonia.api.excepciones.ClienteNoExisteExcepcion;
import es.uji.al341823.telefonia.gui.console.Menu;

/**
 * Clase del menu para dar de baja un cliente
 *
 * @author Juanjo González (al341823)
 * @since 0.2
 */
public class BajaCliente extends Menu {

	public BajaCliente(Menu padre) {
		super(padre);
	}

	@Override
	public void mostrar() {
		AdministradorMenus.imprimeTitulo(this);

		String nif = AdministradorMenus.leerDato("Introduce el NIF del cliente: ", TipoDato.NIF);

		System.out.println();

		try {
			AdministradorDatos.bajaCliente(nif);
			System.out.println("Cliente eliminado con éxito");
		} catch (ClienteNoExisteExcepcion e) {
			System.out.println("No existe ningún cliente con NIF '" + nif + "'");
		}

		AdministradorMenus.esperarParaContinuar();
	}

	@Override
	public String getTitulo() {
		return "Baja cliente";
	}
}
