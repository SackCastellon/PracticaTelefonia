package es.uji.al341823.telefonia.gui.console.menu.clientes.alta;

import es.uji.al341823.telefonia.api.AdministradorDatos;
import es.uji.al341823.telefonia.api.AdministradorMenus;
import es.uji.al341823.telefonia.api.EnumTipoDato;
import es.uji.al341823.telefonia.api.excepciones.ClienteYaExisteExcepcion;
import es.uji.al341823.telefonia.api.fabricas.FabricaClientes;
import es.uji.al341823.telefonia.api.fabricas.FabricaTarifas;
import es.uji.al341823.telefonia.api.fabricas.TipoTarifa;
import es.uji.al341823.telefonia.clientes.Direccion;
import es.uji.al341823.telefonia.gui.console.menu.Menu;

import java.time.LocalDateTime;

/**
 * Clase del menu para dar de alta un particular
 *
 * @author Juanjo González (al341823)
 * @since 0.2
 */
public class AltaClienteParticular extends Menu {
	public AltaClienteParticular(Menu padre) {
		super(padre);
	}

	@Override
	public void mostrar() {
		AdministradorMenus.imprimeTitulo(this);

		System.out.println("Introduce los datos del particular:");

		System.out.println();

		String nombre = AdministradorMenus.leerDato(" - Nombre: ", EnumTipoDato.TEXTO);
		String apellidos = AdministradorMenus.leerDato(" - Apellidos: ", EnumTipoDato.TEXTO);
		String nif = AdministradorMenus.leerDato(" - NIF: ", EnumTipoDato.NIF);
		Direccion direccion = AdministradorMenus.leerDireccion(" - Dirección (CP, Provincia, Problación): ");
		String email = AdministradorMenus.leerDato(" - E-mail: ", EnumTipoDato.EMAIL);
		LocalDateTime fecha = AdministradorMenus.leerFecha(" - Fecha de alta (AAAA-MM-DD hh:mm:ss | hoy): ");

		System.out.println();

		FabricaClientes fabricaClientes = new FabricaClientes();
		FabricaTarifas fabricaTarifas = new FabricaTarifas();

		try {
			AdministradorDatos.altaCliente(fabricaClientes.getParticular(nombre, apellidos, nif, direccion, email, fecha, fabricaTarifas.getTarifaBase(TipoTarifa.Base.BASICA)));
			System.out.println("Cliente añadido con éxito");
		} catch (ClienteYaExisteExcepcion e) {
			System.out.println("Ya existe un cliente con NIF '" + nif + "'");
		}

		AdministradorMenus.esperarParaContinuar();
	}

	@Override
	public String getTitulo() {
		return "Particular";
	}

	@Override
	public Menu[] getSubmenus() {
		return new Menu[0];
	}
}
