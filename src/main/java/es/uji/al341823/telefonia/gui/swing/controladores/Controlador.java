/*
 * Copyright (c) 2017. Esta obra está sujeta a la licencia Reconocimiento 4.0 Internacional de Creative Commons.
 * Para ver una copia de esta licencia, visite http://creativecommons.org/licenses/by/4.0/.
 */

package es.uji.al341823.telefonia.gui.swing.controladores;

import es.uji.al341823.telefonia.api.AdministradorDatos;
import es.uji.al341823.telefonia.api.excepciones.FechaNoValidaExcepcion;
import es.uji.al341823.telefonia.api.excepciones.ObjetoNoExisteException;
import es.uji.al341823.telefonia.api.excepciones.ObjetoYaExisteException;
import es.uji.al341823.telefonia.api.fabricas.FabricaTarifas;
import es.uji.al341823.telefonia.api.fabricas.TipoTarifa;
import es.uji.al341823.telefonia.clientes.Cliente;
import es.uji.al341823.telefonia.clientes.Direccion;
import es.uji.al341823.telefonia.clientes.Empresa;
import es.uji.al341823.telefonia.clientes.Particular;
import es.uji.al341823.telefonia.facturacion.Factura;
import es.uji.al341823.telefonia.facturacion.tarifas.Tarifa;
import es.uji.al341823.telefonia.gui.swing.Vista;
import es.uji.al341823.telefonia.llamadas.Llamada;

import java.io.File;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 * @author Juanjo González (al341823)
 * @since 0.4
 */
public class Controlador {
	private Vista vista;

	public Vista getVista() {
		return this.vista;
	}

	public void setVista(Vista vista) {
		this.vista = vista;
	}

	public void nuevosDatos() {
		AdministradorDatos.setFicheroDatos(null);
		AdministradorDatos.limpiarDatos();

		this.vista.actualizarVista();
	}

	public File getFicheroDatos() {
		return AdministradorDatos.getFicheroDatos();
	}

	public void setFicheroDatos(File file) {
		AdministradorDatos.setFicheroDatos(file);
	}

	public void cargarDatos() {
		AdministradorDatos.cargarDatos();

		this.vista.actualizarVista();
	}

	public void guardarDatos() {
		AdministradorDatos.guardarDatos();
	}

	public ArrayList<Tarifa> getTarifasBasicas() {
		ArrayList<Tarifa> tarifas = new ArrayList<>();

		FabricaTarifas fabrica = new FabricaTarifas();
		for (TipoTarifa.Base base : TipoTarifa.Base.values())
			tarifas.add(fabrica.getTarifaBase(base));

		return tarifas;
	}

	public ArrayList<Tarifa> getTarifasExtra(Tarifa tarifaBase) {
		ArrayList<Tarifa> tarifas = new ArrayList<>();

		FabricaTarifas fabrica = new FabricaTarifas();
		for (TipoTarifa.Extra extra : TipoTarifa.Extra.values())
			tarifas.add(fabrica.getTarifaExtra(tarifaBase, extra));

		return tarifas;
	}

	public Tarifa getTarifa(Cliente cliente) {
		return cliente.getTarifa();
	}

	public void setTarifa(Cliente cliente, Tarifa tarifa) {
		cliente.setTarifa(tarifa);

		this.vista.actualizarVista();
	}

	public void guardarCliente(String[] textos, Date fecha, Tarifa tarifa) throws ObjetoYaExisteException {
		LocalDateTime fechaLocal = LocalDateTime.ofInstant(fecha.toInstant(), ZoneId.systemDefault());

		if (textos.length == 5) {
			String[] split = textos[3].split(",", 3);
			Integer i = Integer.parseInt(split[0]);
			Direccion direccion = new Direccion(i, split[1], split[2]);

			AdministradorDatos.addCliente(new Particular(textos[1], textos[2], textos[0], direccion, textos[4], fechaLocal, tarifa));
		} else {
			String[] split = textos[2].split(",", 3);
			Integer i = Integer.parseInt(split[0]);
			Direccion direccion = new Direccion(i, split[1], split[2]);

			AdministradorDatos.addCliente(new Empresa(textos[1], textos[0], direccion, textos[3], fechaLocal, tarifa));
		}

		this.vista.actualizarVista();
	}

	public void borrarCliente(Cliente cliente) throws ObjetoNoExisteException {
		AdministradorDatos.removeCliente(cliente.getNif());

		this.vista.actualizarVista();
	}

	public void altaLlamada(Cliente cliente, Llamada llamada) throws ObjetoNoExisteException, ObjetoYaExisteException {
		AdministradorDatos.addLlamada(cliente.getNif(), llamada);
	}

	public Collection<Llamada> getLlamadas(Cliente cliente) throws ObjetoNoExisteException {
		return AdministradorDatos.getLlamadasCliente(cliente.getNif());
	}

	public void emitirFactura(Cliente cliente) throws ObjetoNoExisteException, FechaNoValidaExcepcion {
		AdministradorDatos.addFactura(cliente.getNif());
	}

	public Collection<Factura> getFacturas(Cliente cliente) throws ObjetoNoExisteException {
		return AdministradorDatos.getFacturasCliente(cliente.getNif());
	}

}
