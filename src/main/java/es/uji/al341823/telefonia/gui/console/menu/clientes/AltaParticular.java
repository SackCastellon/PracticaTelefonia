package es.uji.al341823.telefonia.gui.console.menu.clientes;

import es.uji.al341823.telefonia.api.AdministradorDatos;
import es.uji.al341823.telefonia.api.AdministradorMenus;
import es.uji.al341823.telefonia.api.EnumTipoDato;
import es.uji.al341823.telefonia.api.excepciones.ClienteYaExisteExcepcion;
import es.uji.al341823.telefonia.clientes.Direccion;
import es.uji.al341823.telefonia.facturacion.Tarifa;
import es.uji.al341823.telefonia.gui.console.menu.Menu;

import java.time.LocalDateTime;

/**
 * Clase del menu para dar de alta un particular
 *
 * @author Juanjo González (al341823)
 * @since 0.2
 */
public class AltaParticular extends Menu {
	public AltaParticular(Menu padre) {
		super(padre);
	}

	@Override
	public void mostrar() {
		AdministradorMenus.imprimeTitulo(this);

		System.out.println("Introduce los datos del particular:");

		System.out.println();

		String nombre = AdministradorMenus.leerTexto(" - Nombre: ", EnumTipoDato.TEXTO);
		String apellidos = AdministradorMenus.leerTexto(" - Apellidos: ", EnumTipoDato.TEXTO);
		String nif = AdministradorMenus.leerTexto(" - NIF: ", EnumTipoDato.NIF);
		Direccion direccion = AdministradorMenus.leerDireccion(" - Dirección: ");
		String email = AdministradorMenus.leerTexto(" - E-mail: ", EnumTipoDato.EMAIL);
		LocalDateTime fecha = AdministradorMenus.leerFecha(" - Fecha de alta: ");
		Tarifa tarifa = new Tarifa(AdministradorMenus.leerNumero(" - Tarifa: "));

		System.out.println();

		try {
			AdministradorDatos.altaParticular(nombre, apellidos, nif, direccion, email, fecha, tarifa);
			System.out.println("Cliente añadido con éxito");
		} catch (ClienteYaExisteExcepcion e) {
			System.out.println("Ya existe un cliente con NIF '" + nif + "'");
		}

		AdministradorMenus.esperarParaContinuar();
	}

	@Override
	public String getTitulo() {
		return "Prticular";
	}

	@Override
	public Menu[] getSubmenus() {
		return null;
	}
}
