/*
 * Copyright (c) 2017. Esta obra está sujeta a la licencia Reconocimiento 4.0 Internacional de Creative Commons.
 * Para ver una copia de esta licencia, visite http://creativecommons.org/licenses/by/4.0/.
 */

package es.uji.al341823.telefonia.gui.console.facturas;

import es.uji.al341823.telefonia.api.AdministradorDatos;
import es.uji.al341823.telefonia.api.AdministradorMenus;
import es.uji.al341823.telefonia.clientes.Cliente;
import es.uji.al341823.telefonia.facturacion.Factura;
import es.uji.al341823.telefonia.gui.console.Menu;

/**
 * @author Juanjo González (al341823)
 * @since 0.2
 */
public class EmitirFactura extends Menu {
	public EmitirFactura(Menu padre) {
		super(padre);
	}

	@Override
	public void mostrar() {
		AdministradorMenus.imprimeTitulo(this);

		Cliente cliente = AdministradorMenus.leerClienteNIF();

		if (cliente == null) return;

		Factura factura = AdministradorDatos.emitirFactura(cliente);

		System.out.println();

		if (factura == null)
			System.out.println("No se pudo emitir la factura");
		else
			System.out.println("Información de la factura emitida: " + factura);

		AdministradorMenus.esperarParaContinuar();
	}

	@Override
	public String getTitulo() {
		return "Emitir factura";
	}
}
