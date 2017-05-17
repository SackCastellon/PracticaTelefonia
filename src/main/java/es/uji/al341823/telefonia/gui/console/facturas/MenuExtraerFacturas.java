/*
 * Copyright (c) 2017. Esta obra está sujeta a la licencia Reconocimiento 4.0 Internacional de Creative Commons.
 * Para ver una copia de esta licencia, visite http://creativecommons.org/licenses/by/4.0/.
 */

package es.uji.al341823.telefonia.gui.console.facturas;

import es.uji.al341823.telefonia.api.AdministradorDatos;
import es.uji.al341823.telefonia.api.AdministradorMenus;
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
		AdministradorMenus.imprimeTitulo(this);

		String nif = AdministradorMenus.leerNIF();

		LocalDateTime inicio = AdministradorMenus.leerFecha("Introcuce la fecha de inicio (AAAA-MM-DD hh:mm:ss | hoy): ");
		LocalDateTime fin = AdministradorMenus.leerFecha("Introcuce la fecha de fin (AAAA-MM-DD hh:mm:ss | hoy): ");

		try {
			Collection<Factura> facturas = AdministradorDatos.extraerConjunto(AdministradorDatos.getFacturasCliente(nif), inicio, fin);

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

		AdministradorMenus.esperarParaContinuar();
	}

	@Override
	public String getTitulo() {
		return "Ver faturas de un cliente emitidas entre dos fechas";
	}
}