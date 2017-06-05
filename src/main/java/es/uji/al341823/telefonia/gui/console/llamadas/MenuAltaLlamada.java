/*
 * Copyright (c) 2017. Esta obra está sujeta a la licencia Reconocimiento 4.0 Internacional de Creative Commons.
 * Para ver una copia de esta licencia, visite http://creativecommons.org/licenses/by/4.0/.
 */

package es.uji.al341823.telefonia.gui.console.llamadas;

import es.uji.al341823.telefonia.api.AdministradorDatos;
import es.uji.al341823.telefonia.api.TipoDato;
import es.uji.al341823.telefonia.api.excepciones.ObjetoNoExisteException;
import es.uji.al341823.telefonia.api.excepciones.ObjetoYaExisteException;
import es.uji.al341823.telefonia.gui.console.Menu;
import es.uji.al341823.telefonia.llamadas.Llamada;

import java.time.LocalDateTime;

/**
 * Clase del menu para dar de alta una empresa
 *
 * @author Juanjo González (al341823)
 * @since 0.2
 */
public class MenuAltaLlamada extends Menu {
	public MenuAltaLlamada(Menu padre) {
		super(padre);
	}

	@Override
	public void mostrar() {
		this.getAdministradorMenus().imprimeTitulo(this);

		String nif = this.getAdministradorMenus().leerNIF();

		System.out.println("Introduce los siguientes datos de la llamada:");

		String origen = this.getAdministradorMenus().leerDato(" - Numero de origen: ", TipoDato.TELEFONO);
		String destino = this.getAdministradorMenus().leerDato(" - Numero de destino: ", TipoDato.TELEFONO);
		LocalDateTime fecha = this.getAdministradorMenus().leerFecha(" - Fecha de la llamada (AAAA-MM-DD hh:mm:ss | hoy): ");
		int duracion = this.getAdministradorMenus().leerEntero(" - Duracion de la llamada en segundos: ");

		AdministradorDatos administradorDatos = this.getAdministradorDatos();
		Llamada llamada = new Llamada(administradorDatos.getNextCodigoLlamada(), origen, destino, fecha, duracion);

		try {
			System.out.println();
			administradorDatos.addLlamada(nif, llamada);
			System.out.println("Llamada añadida con éxito");
		} catch (ObjetoYaExisteException e) {
			System.err.println("Ya existe una llamada con el código '" + llamada.getCodigo() + "'");
		} catch (ObjetoNoExisteException e) {
			System.err.println("No existe ningún cliente con NIF '" + nif + "'");
		}

		this.getAdministradorMenus().esperarParaContinuar();
	}

	@Override
	public String getTitulo() {
		return "Alta llamada";
	}
}
