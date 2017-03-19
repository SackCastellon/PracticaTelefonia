package es.uji.al341823.telefonia.gui.console.menu;

import es.uji.al341823.telefonia.api.AdministradorMenus;

/**
 * Created by Juanjo on 19/03/2017.
 */
public class GuardarDatos extends Menu {
	public GuardarDatos(Menu padre) {
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
		return "Guardar datos en fichero";
	}

	@Override
	public Menu[] getSubmenus() {
		return null;
	}
}