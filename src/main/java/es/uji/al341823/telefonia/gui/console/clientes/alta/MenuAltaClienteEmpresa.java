/*
 * Copyright (c) 2017. Esta obra está sujeta a la licencia Reconocimiento 4.0 Internacional de Creative Commons.
 * Para ver una copia de esta licencia, visite http://creativecommons.org/licenses/by/4.0/.
 */

package es.uji.al341823.telefonia.gui.console.clientes.alta;

import es.uji.al341823.telefonia.api.TipoDato;
import es.uji.al341823.telefonia.api.excepciones.ObjetoYaExisteException;
import es.uji.al341823.telefonia.api.fabricas.FabricaClientes;
import es.uji.al341823.telefonia.api.fabricas.FabricaTarifas;
import es.uji.al341823.telefonia.api.fabricas.TipoTarifa;
import es.uji.al341823.telefonia.clientes.Direccion;
import es.uji.al341823.telefonia.gui.console.Menu;

import java.time.LocalDateTime;

/**
 * Clase del menu para dar de alta una empresa
 *
 * @author Juanjo González (al341823)
 * @since 0.2
 */
public class MenuAltaClienteEmpresa extends Menu {
	public MenuAltaClienteEmpresa(Menu padre) {
		super(padre);
	}

	@Override
	public void mostrar() {
		this.getAdministradorMenus().imprimeTitulo(this);

		System.out.println("Introduce los datos de la empresa:");

		System.out.println();

		String nombre = this.getAdministradorMenus().leerDato(" - Nombre: ", TipoDato.TEXTO);
		String nif = this.getAdministradorMenus().leerDato(" - NIF: ", TipoDato.NIF);
		Direccion direccion = this.getAdministradorMenus().leerDireccion(" - Dirección (CP, Provincia, Problación): ");
		String email = this.getAdministradorMenus().leerDato(" - E-mail: ", TipoDato.EMAIL);
		LocalDateTime fecha = this.getAdministradorMenus().leerFecha(" - Fecha de alta (AAAA-MM-DD hh:mm:ss | hoy): ");

		System.out.println();

		FabricaClientes fabricaClientes = new FabricaClientes();
		FabricaTarifas fabricaTarifas = new FabricaTarifas();

		try {
			this.getAdministradorDatos().addCliente(fabricaClientes.getEmpresa(nombre, nif, direccion, email, fecha, fabricaTarifas.getTarifaBase(TipoTarifa.Base.BASICA)));
			System.out.println("Cliente añadido con éxito");
		} catch (ObjetoYaExisteException e) {
			System.out.println("Ya existe un cliente con NIF '" + nif + "'");
		}

		this.getAdministradorMenus().esperarParaContinuar();
	}

	@Override
	public String getTitulo() {
		return "Empresa";
	}
}
