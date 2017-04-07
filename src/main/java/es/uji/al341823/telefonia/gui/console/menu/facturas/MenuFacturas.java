package es.uji.al341823.telefonia.gui.console.menu.facturas;

import es.uji.al341823.telefonia.gui.console.menu.Menu;

/**
 * @author Juanjo González (al341823)
 * @since 0.2
 */
public class MenuFacturas extends Menu {

	public MenuFacturas(Menu padre) {
		super(padre);
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
