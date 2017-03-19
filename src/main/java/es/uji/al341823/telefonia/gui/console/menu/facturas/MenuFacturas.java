package es.uji.al341823.telefonia.gui.console.menu.facturas;

import es.uji.al341823.telefonia.api.AdministradorMenus;
import es.uji.al341823.telefonia.gui.console.menu.Menu;

/**
 * Created by Juanjo on 15/03/2017.
 */
public class MenuFacturas extends Menu {

	public MenuFacturas(Menu padre) {
		super(padre);
	}

	@Override
	public void mostrar() {
		AdministradorMenus.seleccionarOpcion(this);
	}

	@Override
	public String getTitulo() {
		return "Menú facturas";
	}

	@Override
	public Menu[] getSubmenus() {
		return new Menu[] {new EmitirFactura(this), new RecuperarDatosFactura(this),
				new RecuperarFacturasCliente(this), new ExtraerFacturas(this)};
	}
}
