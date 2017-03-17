package es.uji.al341823.telefonia.gui.console.menu.facturas;

import es.uji.al341823.telefonia.clientes.Cliente;
import es.uji.al341823.telefonia.facturacion.Factura;
import es.uji.al341823.telefonia.api.manager.MenuManager;
import es.uji.al341823.telefonia.gui.console.menu.Menu;

import java.util.LinkedList;

/**
 * Created by Juanjo on 16/03/2017.
 */
class RecuperarFacturasCliente extends Menu {
	public RecuperarFacturasCliente(Menu padre) {
		super(padre);
	}

	@Override
	public void mostrar() {
		MenuManager.imprimeTitulo(this);

		Cliente cliente = MenuManager.leerClienteNIF();

		if (cliente == null) return;

		LinkedList<Factura> facturas = cliente.getFacturas();

		System.out.println("Se han emitido un total de " + facturas.size() + " facturas para este cliente:");

		System.out.println();

		for (Factura factura : facturas) {
			System.out.println(" - " + factura);
		}

		MenuManager.esperarParaContinuar();
	}

	@Override
	public String getTitulo() {
		return "Recuperar facturas de cliente";
	}

	@Override
	public Menu[] getSubmenus() {
		return null;
	}
}
