/*
 * Copyright (c) 2017. Esta obra está sujeta a la licencia Reconocimiento 4.0 Internacional de Creative Commons.
 * Para ver una copia de esta licencia, visite http://creativecommons.org/licenses/by/4.0/.
 */

package es.uji.al341823.telefonia.gui.console.menu.llamadas;

import es.uji.al341823.telefonia.api.AdministradorDatos;
import es.uji.al341823.telefonia.api.AdministradorMenus;
import es.uji.al341823.telefonia.api.excepciones.FechaNoValidaExcepcion;
import es.uji.al341823.telefonia.clientes.Cliente;
import es.uji.al341823.telefonia.gui.console.menu.Menu;
import es.uji.al341823.telefonia.llamadas.Llamada;

import java.time.LocalDateTime;
import java.util.Collection;

/**
 * @author Juanjo González (al341823)
 * @since 0.2
 */
public class ExtraerLlamadas extends Menu {
	public ExtraerLlamadas(Menu padre) {
		super(padre);
	}

	@Override
	public void mostrar() {
		AdministradorMenus.imprimeTitulo(this);

		Cliente cliente = AdministradorMenus.leerClienteNIF();

		if (cliente == null) return;

		LocalDateTime inicio = AdministradorMenus.leerFecha("Introcuce la fecha de inicio (AAAA-MM-DD hh:mm:ss | hoy): ");
		LocalDateTime fin = AdministradorMenus.leerFecha("Introcuce la fecha de fin (AAAA-MM-DD hh:mm:ss | hoy): ");

		Collection<Llamada> llamadas;

		try {
			llamadas = AdministradorDatos.extraerConjunto(cliente.getLlamadas(), inicio, fin);
		} catch (FechaNoValidaExcepcion e) {
			System.out.println("El periodo de tiempo especificado no es valido");
			AdministradorMenus.esperarParaContinuar();
			return;
		}

		System.out.println();

		System.out.println("Durante este periodo de tiempo este cliente realizó un total de " + llamadas.size() + " llamada(s)");

		for (Llamada llamada : llamadas) {
			System.out.println(" - " + llamada);
		}

		AdministradorMenus.esperarParaContinuar();

	}

	@Override
	public String getTitulo() {
		return "Ver llamadas de un cliente emitidas entre dos fechas";
	}
}