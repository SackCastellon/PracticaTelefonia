package es.uji.al341823.telefonia.gui.console.menu.clientes.tarifa;

import es.uji.al341823.telefonia.api.AdministradorMenus;
import es.uji.al341823.telefonia.clientes.Cliente;
import es.uji.al341823.telefonia.facturacion.tarifas.Tarifa;
import es.uji.al341823.telefonia.facturacion.tarifas.TarifaDiasSemana;
import es.uji.al341823.telefonia.facturacion.tarifas.TarifaExtra;
import es.uji.al341823.telefonia.facturacion.tarifas.TarifaHoras;
import es.uji.al341823.telefonia.gui.console.menu.Menu;

/**
 * @author Juanjo González (al341823)
 * @author David Agost (al341819)
 * @since 0.3
 */
public class CambiarTarifaExtra extends Menu {
	public CambiarTarifaExtra(Menu padre) {
		super(padre);
	}

	@Override
	public void mostrar() {
		AdministradorMenus.imprimeTitulo(this);

		Cliente cliente = AdministradorMenus.leerClienteNIF();

		if (cliente == null) return;

		Tarifa tarifaBase = cliente.getTarifa();

		TarifaExtra[] tarifasExtra = {
				new TarifaHoras(tarifaBase, 0.05f, 17, 20),
				new TarifaDiasSemana(tarifaBase, 0, 7)}; // FIXME Arreglar cuando se implemente la interfaz gráfica

		Tarifa tarifa = AdministradorMenus.seleccionarOpciones(tarifasExtra);

		cliente.setTarifa(tarifa);

		AdministradorMenus.esperarParaContinuar();
	}

	@Override
	public String getTitulo() {
		return "Cambiar tarifa extra";
	}

	@Override
	public Menu[] getSubmenus() {
		return new Menu[0];
	}
}
