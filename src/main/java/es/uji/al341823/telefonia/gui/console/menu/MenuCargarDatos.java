package es.uji.al341823.telefonia.gui.console.menu;

import es.uji.al341823.telefonia.api.AdministradorDatos;
import es.uji.al341823.telefonia.api.AdministradorMenus;
import es.uji.al341823.telefonia.api.EnumTipoDato;

/**
 * Clase del menu para cargar los datos desde un fichero
 *
 * @author Juanjo González (al341823)
 * @since 0.2
 */
public class MenuCargarDatos extends Menu {

	public MenuCargarDatos(Menu padre) {
		super(padre);
	}

	@Override
	public void mostrar() {
		AdministradorMenus.imprimeTitulo(this);

		String ruta = AdministradorMenus.leerDato("Introduce la ruta al fichero de datos: ", EnumTipoDato.FICHERO);

		AdministradorDatos.cargarDatos(ruta);

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
