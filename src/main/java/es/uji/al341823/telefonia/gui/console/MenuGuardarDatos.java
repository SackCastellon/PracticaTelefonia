/*
 * Copyright (c) 2017. Esta obra está sujeta a la licencia Reconocimiento 4.0 Internacional de Creative Commons.
 * Para ver una copia de esta licencia, visite http://creativecommons.org/licenses/by/4.0/.
 */

package es.uji.al341823.telefonia.gui.console;

import es.uji.al341823.telefonia.api.AdministradorDatos;
import es.uji.al341823.telefonia.api.AdministradorMenus;
import es.uji.al341823.telefonia.api.TipoDato;

/**
 * Clase del menu para guardar los datos desde un fichero
 *
 * @author Juanjo González (al341823)
 * @since 0.2
 */
public class MenuGuardarDatos extends Menu {

	@Override
	public void mostrar() {
		AdministradorMenus.imprimeTitulo(this);

		String ruta = AdministradorMenus.leerDato("Introduce la ruta al fichero de datos: ", TipoDato.FICHERO_O_NINGUNO);

		System.out.println();

		if (ruta.isEmpty())
			System.out.println("Se canceló el guardado de datos");
		else
			AdministradorDatos.guardarDatos(ruta);

		AdministradorMenus.esperarParaContinuar();
	}

	@Override
	public String getTitulo() {
		return "Guardar datos en fichero";
	}
}