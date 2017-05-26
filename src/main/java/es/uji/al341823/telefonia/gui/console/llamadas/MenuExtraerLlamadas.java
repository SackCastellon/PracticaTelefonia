/*
 * Copyright (c) 2017. Esta obra está sujeta a la licencia Reconocimiento 4.0 Internacional de Creative Commons.
 * Para ver una copia de esta licencia, visite http://creativecommons.org/licenses/by/4.0/.
 */

package es.uji.al341823.telefonia.gui.console.llamadas;

import es.uji.al341823.telefonia.api.AdministradorDatos;
import es.uji.al341823.telefonia.api.AdministradorMenus;
import es.uji.al341823.telefonia.api.excepciones.FechaNoValidaExcepcion;
import es.uji.al341823.telefonia.api.excepciones.ObjetoNoExisteException;
import es.uji.al341823.telefonia.gui.console.Menu;
import es.uji.al341823.telefonia.llamadas.Llamada;

import java.time.LocalDateTime;
import java.util.Collection;

/**
 * @author Juanjo González (al341823)
 * @since 0.2
 */
public class MenuExtraerLlamadas extends Menu {
	public MenuExtraerLlamadas(Menu padre) {
		super(padre);
	}

	@Override
	public void mostrar() {
		AdministradorMenus.imprimeTitulo(this);

		String nif = AdministradorMenus.leerNIF();

		LocalDateTime inicio = AdministradorMenus.leerFecha("Introcuce la fecha de inicio (AAAA-MM-DD hh:mm:ss | hoy): ");
		LocalDateTime fin = AdministradorMenus.leerFecha("Introcuce la fecha de fin (AAAA-MM-DD hh:mm:ss | hoy): ");

		try {
			System.out.println();
			Collection<Llamada> llamadas = getAdministradorDatos().extraerConjunto(getAdministradorDatos().getLlamadasCliente(nif), inicio, fin);

			System.out.println("Durante este periodo de tiempo se un total de " + llamadas.size() + " llamada(s) para este cliente");

			for (Llamada llamada : llamadas)
				System.out.println(" - " + llamada);

		} catch (FechaNoValidaExcepcion e) {
			System.out.println("El periodo de tiempo especificado no es valido");
		} catch (ObjetoNoExisteException e) {
			System.out.println("No existe ningún cliente con NIF '" + nif + "'");
		}

		AdministradorMenus.esperarParaContinuar();
	}

	@Override
	public String getTitulo() {
		return "Ver llamadas de un cliente emitidas entre dos fechas";
	}
}