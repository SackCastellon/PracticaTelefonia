package es.uji.al341823.telefonia.gui.console.menu.clientes;

import es.uji.al341823.telefonia.api.AdministradorMenus;
import es.uji.al341823.telefonia.clientes.Cliente;
import es.uji.al341823.telefonia.facturacion.Tarifa;
import es.uji.al341823.telefonia.gui.console.menu.Menu;

/**
 * Clase del menu para cambiar la tarifa de un cliente
 *
 * @author Juanjo González (al341823)
 * @since 0.2
 */
public class CambiarTarifa extends Menu {

	public CambiarTarifa(Menu padre) {
		super(padre);
	}

	@Override
	public void mostrar() {
		AdministradorMenus.imprimeTitulo(this);

		Cliente cliente = AdministradorMenus.leerClienteNIF();

		if (cliente == null) return;

		Tarifa tarifa = new Tarifa(AdministradorMenus.leerNumero("La nueva tarifa: "));

		System.out.println();

		cliente.setTarifa(tarifa);
		System.out.println("Tarifa cambiada con éxito");

		AdministradorMenus.esperarParaContinuar();
	}

	@Override
	public String getTitulo() {
		return "Cambiar tarifa";
	}

	@Override
	public Menu[] getSubmenus() {
		return null;
	}
}
