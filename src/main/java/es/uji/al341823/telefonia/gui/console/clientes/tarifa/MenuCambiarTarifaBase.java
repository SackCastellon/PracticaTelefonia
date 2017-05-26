/*
 * Copyright (c) 2017. Esta obra está sujeta a la licencia Reconocimiento 4.0 Internacional de Creative Commons.
 * Para ver una copia de esta licencia, visite http://creativecommons.org/licenses/by/4.0/.
 */

package es.uji.al341823.telefonia.gui.console.clientes.tarifa;

import es.uji.al341823.telefonia.api.AdministradorMenus;
import es.uji.al341823.telefonia.api.excepciones.ObjetoNoExisteException;
import es.uji.al341823.telefonia.api.fabricas.FabricaTarifas;
import es.uji.al341823.telefonia.api.fabricas.TipoTarifa;
import es.uji.al341823.telefonia.clientes.Cliente;
import es.uji.al341823.telefonia.facturacion.tarifas.Tarifa;
import es.uji.al341823.telefonia.gui.console.Menu;

/**
 * @author Juanjo González (al341823)
 * @author David Agost (al341819)
 * @since 0.3
 */
public class MenuCambiarTarifaBase extends Menu {
	public MenuCambiarTarifaBase(Menu padre) {
		super(padre);
	}

	@Override
	public void mostrar() {
		AdministradorMenus.imprimeTitulo(this);
		String nif = this.getAdministradorMenus().leerNIF();

		try {
			Cliente cliente = this.getAdministradorDatos().getCliente(nif);
			FabricaTarifas fabricaTarifas = new FabricaTarifas();

			Tarifa[] tarifasBase = {
					fabricaTarifas.getTarifaBase(TipoTarifa.Base.BASICA)};

			Tarifa tarifa = AdministradorMenus.seleccionarOpciones(tarifasBase);

			cliente.setTarifa(tarifa);
		} catch (ObjetoNoExisteException e) {
			System.out.println("No existe ningún cliente con NIF '" + nif + "'");
		} finally {
			System.out.println();
			AdministradorMenus.esperarParaContinuar();
		}
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
