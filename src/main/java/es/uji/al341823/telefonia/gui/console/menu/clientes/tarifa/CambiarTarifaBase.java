package es.uji.al341823.telefonia.gui.console.menu.clientes.tarifa;

import es.uji.al341823.telefonia.api.AdministradorMenus;
import es.uji.al341823.telefonia.api.fabricas.FabricaTarifas;
import es.uji.al341823.telefonia.api.fabricas.TipoTarifa;
import es.uji.al341823.telefonia.clientes.Cliente;
import es.uji.al341823.telefonia.facturacion.tarifas.Tarifa;
import es.uji.al341823.telefonia.gui.console.menu.Menu;

/**
 * @author Juanjo González (al341823)
 * @author David Agost (al341819)
 * @since 0.3
 */
public class CambiarTarifaBase extends Menu {
	public CambiarTarifaBase(Menu padre) {
		super(padre);
	}

	@Override
	public void mostrar() {
		AdministradorMenus.imprimeTitulo(this);

		Cliente cliente = AdministradorMenus.leerClienteNIF();

		if (cliente == null) return;

		FabricaTarifas fabricaTarifas = new FabricaTarifas();

		Tarifa[] tarifasBase = {
				fabricaTarifas.getTarifaBase(TipoTarifa.Base.BASICA)};

		Tarifa tarifa = AdministradorMenus.seleccionarOpciones(tarifasBase);

		cliente.setTarifa(tarifa);

		AdministradorMenus.esperarParaContinuar();
	}

	@Override
	public String getTitulo() {
		return "Cambiar tarifa base";
	}

	@Override
	public Menu[] getSubmenus() {
		return new Menu[0];
	}
}
