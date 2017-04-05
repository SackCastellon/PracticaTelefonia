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
public class CargarDatos extends Menu {

	public CargarDatos(Menu padre) {
		super(padre);
	}

	@Override
	public void mostrar() {
		AdministradorMenus.imprimeTitulo(this);

		String ruta = AdministradorMenus.leerTexto("Introduce la ruta al fichero de datos: ", EnumTipoDato.FICHERO);

		AdministradorDatos.cargarDatos(ruta);

		AdministradorMenus.esperarParaContinuar();

		Menu menuPrincipal = new MenuPrincipal(null);
		menuPrincipal.mostrar();
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
