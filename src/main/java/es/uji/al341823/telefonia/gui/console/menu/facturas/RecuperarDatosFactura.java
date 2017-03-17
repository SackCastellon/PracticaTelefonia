package es.uji.al341823.telefonia.gui.console.menu.facturas;

import es.uji.al341823.telefonia.api.manager.DataManager;
import es.uji.al341823.telefonia.api.excepciones.FacturaNoExisteExcepcion;
import es.uji.al341823.telefonia.facturacion.Factura;
import es.uji.al341823.telefonia.api.manager.MenuManager;
import es.uji.al341823.telefonia.gui.console.menu.Menu;

/**
 * Created by Juanjo on 16/03/2017.
 */
class RecuperarDatosFactura extends Menu {
	public RecuperarDatosFactura(Menu padre) {
		super(padre);
	}

	@Override
	public void mostrar() {
		MenuManager.imprimeTitulo(this);

		int codigo = MenuManager.leerEntero("Introduce el codigo de la factura: ");

		try {
			Factura factura = DataManager.getFactura(codigo);
			System.out.println("Información de la factura: " + factura);
		} catch (FacturaNoExisteExcepcion e) {
			System.out.println("No existe ninguna factura con ese codigo");
		}

		MenuManager.esperarParaContinuar();
	}

	@Override
	public String getTitulo() {
		return "Recuperar datos factura";
	}

	@Override
	public Menu[] getSubmenus() {
		return null;
	}
}
