package es.uji.al341823.telefonia.gui.console.menu.clientes;

import es.uji.al341823.telefonia.api.AdministradorDatos;
import es.uji.al341823.telefonia.api.AdministradorMenus;
import es.uji.al341823.telefonia.api.EnumTipoDato;
import es.uji.al341823.telefonia.api.excepciones.ClienteNoExisteExcepcion;
import es.uji.al341823.telefonia.gui.console.menu.Menu;

/**
 * Clase del menu para dar de baja un cliente
 *
 * @author Juanjo González (al341823)
 * @since 0.2
 */
public class BajaCliente extends Menu {

	public BajaCliente(Menu padre) {
		super(padre);
	}

	@Override
	public void mostrar() {
		AdministradorMenus.imprimeTitulo(this);

		String nif = AdministradorMenus.leerTexto("Introduce el NIF del cliente: ", EnumTipoDato.NIF);

		System.out.println();

		try {
			AdministradorDatos.bajaCliente(nif);
			System.out.println("Cliente eliminado con éxito");
		} catch (ClienteNoExisteExcepcion e) {
			System.out.println("No existe ningún cliente con NIF '" + nif + "'");
		}

		AdministradorMenus.esperarParaContinuar();
	}

	@Override
	public String getTitulo() {
		return "Baja cliente";
	}

	@Override
	public Menu[] getSubmenus() {
		return new Menu[0];
	}
}
