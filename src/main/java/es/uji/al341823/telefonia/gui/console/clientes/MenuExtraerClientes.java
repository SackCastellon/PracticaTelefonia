/*
 * Copyright (c) 2017. Esta obra está sujeta a la licencia Reconocimiento 4.0 Internacional de Creative Commons.
 * Para ver una copia de esta licencia, visite http://creativecommons.org/licenses/by/4.0/.
 */

package es.uji.al341823.telefonia.gui.console.clientes;

import es.uji.al341823.telefonia.api.excepciones.FechaNoValidaExcepcion;
import es.uji.al341823.telefonia.clientes.Cliente;
import es.uji.al341823.telefonia.gui.console.Menu;

import java.time.LocalDateTime;
import java.util.Collection;

/**
 * @author Juanjo González (al341823)
 * @since 0.2
 */
public class MenuExtraerClientes extends Menu {
	public MenuExtraerClientes(Menu padre) {
		super(padre);
	}

	@Override
	public void mostrar() {
		this.getAdministradorMenus().imprimeTitulo(this);

		LocalDateTime inicio = this.getAdministradorMenus().leerFecha("Introcuce la fecha de inicio (AAAA-MM-DD hh:mm:ss | hoy): ");
		LocalDateTime fin = this.getAdministradorMenus().leerFecha("Introcuce la fecha de fin (AAAA-MM-DD hh:mm:ss | hoy): ");

		Collection<Cliente> clientes;

		try {
			clientes = this.getAdministradorDatos().extraerConjunto(this.getAdministradorDatos().getClientes(), inicio, fin);
		} catch (FechaNoValidaExcepcion e) {
			System.out.println("El periodo de tiempo especificado no es valido");
			this.getAdministradorMenus().esperarParaContinuar();
			return;
		}

		System.out.println();

		System.out.println("Durante este periodo de tiempo se direon de alta un total de " + clientes.size() + " cliente(s)");

		for (Cliente cliente : clientes) {
			System.out.println(" - " + cliente);
		}

		this.getAdministradorMenus().esperarParaContinuar();

	}

	@Override
	public String getTitulo() {
		return "Ver clientes dados de alta entre dos fechas";
	}
}
