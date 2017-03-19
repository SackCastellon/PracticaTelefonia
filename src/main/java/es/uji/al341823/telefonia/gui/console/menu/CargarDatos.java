package es.uji.al341823.telefonia.gui.console.menu;

import es.uji.al341823.telefonia.api.AdministradorMenus;

/**
 * Created by Juanjo on 19/03/2017.
 */
public class CargarDatos extends Menu {
	public CargarDatos(Menu padre) {
		super(padre);
	}

	@Override
	public void mostrar() {
		AdministradorMenus.imprimeTitulo(this);

		System.out.println("Esta funcionalidad no se implementado todavia");

		AdministradorMenus.esperarParaContinuar();
	}

	@Override
	public String getTitulo() {
		return "Cargar datos desde fichero";
	}

	@Override
	public Menu[] getSubmenus() {
		return null;
	}
}
