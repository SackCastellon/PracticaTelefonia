package es.uji.al341823.telefonia.gui.console.menu.facturas;

import es.uji.al341823.telefonia.api.AdministradorDatos;
import es.uji.al341823.telefonia.api.AdministradorMenus;
import es.uji.al341823.telefonia.api.excepciones.FacturaNoExisteExcepcion;
import es.uji.al341823.telefonia.facturacion.Factura;
import es.uji.al341823.telefonia.gui.console.menu.Menu;

/**
 * Created by Juanjo on 16/03/2017.
 */
public class RecuperarDatosFactura extends Menu {
	public RecuperarDatosFactura(Menu padre) {
		super(padre);
	}

	@Override
	public void mostrar() {
		AdministradorMenus.imprimeTitulo(this);

		int codigo = AdministradorMenus.leerEntero("Introduce el codigo de la factura: ");

		System.out.println();

		try {
			Factura factura = AdministradorDatos.getFactura(codigo);
			System.out.println("Información de la factura: " + factura);
		} catch (FacturaNoExisteExcepcion e) {
			System.out.println("No existe ninguna factura con ese codigo");
		}

		AdministradorMenus.esperarParaContinuar();
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
