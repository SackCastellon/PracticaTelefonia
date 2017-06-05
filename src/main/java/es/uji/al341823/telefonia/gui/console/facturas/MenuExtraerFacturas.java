/*
 * Copyright (c) 2017. Esta obra está sujeta a la licencia Reconocimiento 4.0 Internacional de Creative Commons.
 * Para ver una copia de esta licencia, visite http://creativecommons.org/licenses/by/4.0/.
 */

package es.uji.al341823.telefonia.gui.console.facturas;

import es.uji.al341823.telefonia.api.excepciones.FechaNoValidaExcepcion;
import es.uji.al341823.telefonia.api.excepciones.ObjetoNoExisteException;
import es.uji.al341823.telefonia.facturacion.Factura;
import es.uji.al341823.telefonia.gui.console.Menu;

import java.time.LocalDateTime;
import java.util.Collection;

/**
 * @author Juanjo González (al341823)
 * @since 0.2
 */
public class MenuExtraerFacturas extends Menu {
	public MenuExtraerFacturas(Menu padre) {
		super(padre);
	}

	@Override
	public void mostrar() {
		this.getAdministradorMenus().imprimeTitulo(this);

		String nif = this.getAdministradorMenus().leerNIF();

		LocalDateTime inicio = this.getAdministradorMenus().leerFecha("Introcuce la fecha de inicio (AAAA-MM-DD hh:mm:ss | hoy): ");
		LocalDateTime fin = this.getAdministradorMenus().leerFecha("Introcuce la fecha de fin (AAAA-MM-DD hh:mm:ss | hoy): ");

		try {
			Collection<Factura> facturas = this.getAdministradorDatos().extraerConjunto(this.getAdministradorDatos().getFacturasCliente(nif), inicio, fin);

			System.out.println();
			System.out.println("Durante este periodo de tiempo se un total de " + facturas.size() + " factura(s) para este cliente");

			for (Factura factura : facturas)
				System.out.println(" - " + factura);

		} catch (FechaNoValidaExcepcion e) {
			System.out.println();
			System.out.println("El periodo de tiempo especificado no es valido");
		} catch (ObjetoNoExisteException e) {
			System.out.println();
			System.out.println("No existe ningún cliente con NIF '" + nif + "'");
		}

		this.getAdministradorMenus().esperarParaContinuar();
	}

	@Override
	public String getTitulo() {
		return "Ver faturas de un cliente emitidas entre dos fechas";
	}
}