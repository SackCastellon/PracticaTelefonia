package es.uji.al341823.telefonia.gui.console.menu.clientes;

import es.uji.al341823.telefonia.clientes.Cliente;
import es.uji.al341823.telefonia.facturacion.Tarifa;
import es.uji.al341823.telefonia.api.manager.MenuManager;
import es.uji.al341823.telefonia.gui.console.menu.Menu;

/**
 * Created by Juanjo on 16/03/2017.
 */
class CambiarTarifa extends Menu {

	public CambiarTarifa(Menu padre) {
		super(padre);
	}

	@Override
	public void mostrar() {
		MenuManager.imprimeTitulo(this);

		Cliente cliente = MenuManager.leerClienteNIF();

		if (cliente == null) return;

		Tarifa tarifa = new Tarifa(MenuManager.leerNumero("La nueva tarifa: ")); // FIXME mover 'new' a clase DataManager

		cliente.setTarifa(tarifa);
		System.out.println("Tarifa cambiada con éxito");

		MenuManager.esperarParaContinuar();
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
