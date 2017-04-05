package es.uji.al341823.telefonia.gui.console.menu.facturas;

import es.uji.al341823.telefonia.api.AdministradorDatos;
import es.uji.al341823.telefonia.api.AdministradorMenus;
import es.uji.al341823.telefonia.api.excepciones.FechaNoValidaExcepcion;
import es.uji.al341823.telefonia.clientes.Cliente;
import es.uji.al341823.telefonia.facturacion.Factura;
import es.uji.al341823.telefonia.gui.console.menu.Menu;

import java.time.LocalDateTime;
import java.util.Collection;

/**
 * @author Juanjo González (al341823)
 * @since 0.2
 */
public class ExtraerFacturas extends Menu {
	public ExtraerFacturas(Menu padre) {
		super(padre);
	}

	@Override
	public void mostrar() {
		AdministradorMenus.imprimeTitulo(this);

		Cliente cliente = AdministradorMenus.leerClienteNIF();

		if (cliente == null) return;

		LocalDateTime inicio = AdministradorMenus.leerFecha("Introcuce la fecha de inicio (AAAA-MM-DD hh:mm:ss | hoy): ");
		LocalDateTime fin = AdministradorMenus.leerFecha("Introcuce la fecha de fin (AAAA-MM-DD hh:mm:ss | hoy): ");

		Collection<Factura> facturas;

		try {
			facturas = AdministradorDatos.extraerConjunto(cliente.getFacturas(), inicio, fin);
		} catch (FechaNoValidaExcepcion e) {
			System.out.println("El periodo de tiempo especificado no es valido");
			AdministradorMenus.esperarParaContinuar();
			return;
		}

		System.out.println();

		System.out.println("Durante este periodo de tiempo se un total de " + facturas.size() + " factura(s) para este cliente");

		for (Factura factura : facturas) {
			System.out.println(" - " + factura);
		}

		AdministradorMenus.esperarParaContinuar();

	}

	@Override
	public String getTitulo() {
		return "Ver faturas de un cliente emitidas entre dos fechas";
	}

	@Override
	public Menu[] getSubmenus() {
		return new Menu[0];
	}
}