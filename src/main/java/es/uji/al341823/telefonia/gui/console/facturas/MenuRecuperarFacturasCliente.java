/*
 * Copyright (c) 2017. Esta obra está sujeta a la licencia Reconocimiento 4.0 Internacional de Creative Commons.
 * Para ver una copia de esta licencia, visite http://creativecommons.org/licenses/by/4.0/.
 */

package es.uji.al341823.telefonia.gui.console.facturas;

import es.uji.al341823.telefonia.api.excepciones.ObjetoNoExisteException;
import es.uji.al341823.telefonia.facturacion.Factura;
import es.uji.al341823.telefonia.gui.console.Menu;

import java.util.Collection;

/**
 * @author Juanjo González (al341823)
 * @since 0.2
 */
public class MenuRecuperarFacturasCliente extends Menu {
	public MenuRecuperarFacturasCliente(Menu padre) {
		super(padre);
	}

	@Override
	public void mostrar() {
		this.getAdministradorMenus().imprimeTitulo(this);

		String nif = this.getAdministradorMenus().leerNIF();

		try {
			Collection<Factura> facturas = this.getAdministradorDatos().getFacturasCliente(nif);

			System.out.println();
			System.out.println("Se han emitido un total de " + facturas.size() + " facturas para el cliente con NIF '" + nif + "':");

			for (Factura factura : facturas)
				System.out.println(" - " + factura);

		} catch (ObjetoNoExisteException e) {
			System.out.println();
			System.out.println("No existe ningún cliente con NIF '" + nif + "'");
		}

		this.getAdministradorMenus().esperarParaContinuar();
	}

	@Override
	public String getTitulo() {
		return "Recuperar facturas de cliente";
	}
}
