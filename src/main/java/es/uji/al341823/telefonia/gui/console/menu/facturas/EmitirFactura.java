package es.uji.al341823.telefonia.gui.console.menu.facturas;

import es.uji.al341823.telefonia.clientes.Cliente;
import es.uji.al341823.telefonia.facturacion.Factura;
import es.uji.al341823.telefonia.api.manager.MenuManager;
import es.uji.al341823.telefonia.gui.console.menu.Menu;

/**
 * Created by Juanjo on 16/03/2017.
 */
class EmitirFactura extends Menu {
	public EmitirFactura(Menu padre) {
		super(padre);
	}

	@Override
	public void mostrar() {
		MenuManager.imprimeTitulo(this);

		Cliente cliente = MenuManager.leerClienteNIF();

		if (cliente == null) return;

		Factura factura = cliente.emitirFactura();

		System.out.println();

		if (factura == null)
			System.out.println("No se pudo emitir la factura");
		else
			System.out.println("Información de la factura emitida: " + factura);

		MenuManager.esperarParaContinuar();
	}

	@Override
	public String getTitulo() {
		return "Emitir factura";
	}

	@Override
	public Menu[] getSubmenus() {
		return null;
	}
}
