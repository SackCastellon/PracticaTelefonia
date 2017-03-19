package es.uji.al341823.telefonia.gui.console.menu.llamadas;

import es.uji.al341823.telefonia.api.AdministradorMenus;
import es.uji.al341823.telefonia.clientes.Cliente;
import es.uji.al341823.telefonia.gui.console.menu.Menu;
import es.uji.al341823.telefonia.llamadas.Llamada;

import java.util.LinkedList;

/**
 * Created by Juanjo on 16/03/2017.
 */
public class VerLlamadasCliente extends Menu {
	public VerLlamadasCliente(Menu padre) {
		super(padre);
	}

	@Override
	public void mostrar() {
		AdministradorMenus.imprimeTitulo(this);

		Cliente cliente = AdministradorMenus.leerClienteNIF();

		if (cliente == null) return;

		LinkedList<Llamada> llamadas = cliente.getLlamadas();

		System.out.println("Se han emitido un total de " + llamadas.size() + " facturas para este cliente:");

		for (Llamada llamada : llamadas) {
			System.out.println(" - " + llamada);
		}

		AdministradorMenus.esperarParaContinuar();
	}

	@Override
	public String getTitulo() {
		return "Ver llamadas cliente";
	}

	@Override
	public Menu[] getSubmenus() {
		return null;
	}
}
