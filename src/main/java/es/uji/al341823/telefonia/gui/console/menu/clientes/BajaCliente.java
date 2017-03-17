package es.uji.al341823.telefonia.gui.console.menu.clientes;

import es.uji.al341823.telefonia.api.manager.DataManager;
import es.uji.al341823.telefonia.api.manager.EnumTipoDato;
import es.uji.al341823.telefonia.api.excepciones.ClienteNoExisteExcepcion;
import es.uji.al341823.telefonia.api.manager.MenuManager;
import es.uji.al341823.telefonia.gui.console.menu.Menu;

/**
 * Created by Juanjo on 16/03/2017.
 */
class BajaCliente extends Menu {

	public BajaCliente(Menu padre) {
		super(padre);
	}

	@Override
	public void mostrar() {
		MenuManager.imprimeTitulo(this);

		String nif = MenuManager.leerTexto("Introduce el NIF del cliente: ", EnumTipoDato.NIF);

		try {
			DataManager.bajaCliente(nif);
			System.out.println("Cliente eliminado con éxito");
		} catch (ClienteNoExisteExcepcion e) {
			System.out.println("No existe ningún cliente con NIF '" + nif + "'");
		}

		MenuManager.esperarParaContinuar();
	}

	@Override
	public String getTitulo() {
		return "Baja cliente";
	}

	@Override
	public Menu[] getSubmenus() {
		return null;
	}
}
