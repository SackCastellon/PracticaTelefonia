package es.uji.al341823.telefonia.gui.console.menu.facturas;

import es.uji.al341823.telefonia.api.AdministradorMenus;
import es.uji.al341823.telefonia.clientes.Cliente;
import es.uji.al341823.telefonia.facturacion.Factura;
import es.uji.al341823.telefonia.gui.console.menu.Menu;

import java.util.LinkedList;

/**
 * @author Juanjo González (al341823)
 * @since 0.2
 */
public class RecuperarFacturasCliente extends Menu {
	public RecuperarFacturasCliente(Menu padre) {
		super(padre);
	}

	@Override
	public void mostrar() {
		AdministradorMenus.imprimeTitulo(this);

		Cliente cliente = AdministradorMenus.leerClienteNIF();

		if (cliente == null) return;

		LinkedList<Factura> facturas = cliente.getFacturas();

		System.out.println("Se han emitido un total de " + facturas.size() + " facturas para este cliente:");

		System.out.println();

		for (Factura factura : facturas) {
			System.out.println(" - " + factura);
		}

		AdministradorMenus.esperarParaContinuar();
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
