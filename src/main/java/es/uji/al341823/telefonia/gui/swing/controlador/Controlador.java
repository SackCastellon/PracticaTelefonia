/*
 * Copyright (c) 2017. Esta obra está sujeta a la licencia Reconocimiento 4.0 Internacional de Creative Commons.
 * Para ver una copia de esta licencia, visite http://creativecommons.org/licenses/by/4.0/.
 */

package es.uji.al341823.telefonia.gui.swing.controlador;

import es.uji.al341823.telefonia.api.AdministradorDatos;
import es.uji.al341823.telefonia.api.excepciones.ClienteNoExisteExcepcion;
import es.uji.al341823.telefonia.api.excepciones.ClienteYaExisteExcepcion;
import es.uji.al341823.telefonia.api.excepciones.FechaNoValidaExcepcion;
import es.uji.al341823.telefonia.api.fabricas.FabricaTarifas;
import es.uji.al341823.telefonia.api.fabricas.TipoTarifa;
import es.uji.al341823.telefonia.clientes.Cliente;
import es.uji.al341823.telefonia.clientes.Direccion;
import es.uji.al341823.telefonia.clientes.Empresa;
import es.uji.al341823.telefonia.clientes.Particular;
import es.uji.al341823.telefonia.facturacion.Factura;
import es.uji.al341823.telefonia.facturacion.tarifas.Tarifa;
import es.uji.al341823.telefonia.gui.swing.ventanas.VentanaPrincipal;
import es.uji.al341823.telefonia.llamadas.Llamada;

import javax.swing.table.DefaultTableModel;
import java.io.File;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;

/**
 * @author Juanjo González (al341823)
 * @since 0.4
 */
public class Controlador {

	private final VentanaPrincipal ventana;
	private File ficheroDatos;

	public Controlador(VentanaPrincipal ventana) {
		super();

		this.ventana = ventana;
	}

	public void guardarCliente(String[] textos, Date fecha, Tarifa tarifa) throws ClienteYaExisteExcepcion {
		LocalDateTime fechaLocal = LocalDateTime.ofInstant(fecha.toInstant(), ZoneId.systemDefault());

		if (textos.length == 5) {
			String[] split = textos[3].split(",", 3);
			Integer i = Integer.parseInt(split[0]);
			Direccion direccion = new Direccion(i, split[1], split[2]);

			AdministradorDatos.altaCliente(new Particular(textos[1], textos[2], textos[0], direccion, textos[4], fechaLocal, tarifa));
		} else {
			String[] split = textos[2].split(",", 3);
			Integer i = Integer.parseInt(split[0]);
			Direccion direccion = new Direccion(i, split[1], split[2]);

			AdministradorDatos.altaCliente(new Empresa(textos[1], textos[0], direccion, textos[3], fechaLocal, tarifa));
		}

		this.mostrarDatos();
	}

	public void borrarCliente(String nif) throws ClienteNoExisteExcepcion {
		AdministradorDatos.bajaCliente(nif);
		this.mostrarDatos();
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

	private ArrayList<Particular> getParticulares() {
		ArrayList<Particular> particulares = new ArrayList<>();

		for (Cliente cliente : AdministradorDatos.getClientes())
			if (cliente instanceof Particular)
				particulares.add((Particular) cliente);

		return particulares;
	}

	private ArrayList<Empresa> getEmpresas() {
		ArrayList<Empresa> empresas = new ArrayList<>();

		for (Cliente cliente : AdministradorDatos.getClientes())
			if (cliente instanceof Empresa)
				empresas.add((Empresa) cliente);

		return empresas;
	}

	public File getFicheroDatos() {
		return this.ficheroDatos;
	}

	public void setFicheroDatos(File ficheroDatos) {
		this.ficheroDatos = ficheroDatos;
	}

	public void limpiarDatos() {
		((DefaultTableModel) this.ventana.tablaParticulares.getModel()).setRowCount(0);
		((DefaultTableModel) this.ventana.tablaEmpresas.getModel()).setRowCount(0);
	}

	public void cargarDatos() {
		AdministradorDatos.cargarDatos(this.ficheroDatos.getAbsolutePath());
		this.mostrarDatos();
	}

	public void guardarDatos() {
		AdministradorDatos.guardarDatos(this.ficheroDatos.getAbsolutePath());
	}

	private void mostrarDatos() {
		this.limpiarDatos();

		DefaultTableModel modeloPart = (DefaultTableModel) this.ventana.tablaParticulares.getModel();
		for (Particular part : this.getParticulares()) {
			modeloPart.addRow(new String[] {
					part.getNif(),
					part.getNombre(),
					part.getApellidos(),
					part.getDireccion().toString(),
					part.getEmail(),
					part.getFecha().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
					part.getTarifa().toString()
			});
		}

		DefaultTableModel modeloEmpr = (DefaultTableModel) this.ventana.tablaEmpresas.getModel();
		for (Empresa empr : this.getEmpresas()) {
			modeloEmpr.addRow(new String[] {
					empr.getNif(),
					empr.getNombre(),
					empr.getDireccion().toString(),
					empr.getEmail(),
					empr.getFecha().toString().replace('T', ' '),
					empr.getTarifa().toString()
			});
		}
	}

	public void emitirFactura(String nif) throws ClienteNoExisteExcepcion, FechaNoValidaExcepcion {
		Cliente cliente = AdministradorDatos.getCliente(nif);
		AdministradorDatos.emitirFactura(cliente);
	}

	public void altaLlamada(String nif, Llamada llamada) throws ClienteNoExisteExcepcion {
		Cliente cliente = AdministradorDatos.getCliente(nif);
		cliente.altaLlamada(llamada);
	}

	public LinkedList<Llamada> getLlamadas(String nif) throws ClienteNoExisteExcepcion {
		Cliente cliente = AdministradorDatos.getCliente(nif);
		return cliente.getLlamadas();
	}

	public LinkedList<Factura> getFacturas(String nif) throws ClienteNoExisteExcepcion {
		Cliente cliente = AdministradorDatos.getCliente(nif);
		return cliente.getFacturas();
	}

	public void setTarifa(String nif, Tarifa tarifa) throws ClienteNoExisteExcepcion {
		AdministradorDatos.getCliente(nif).setTarifa(tarifa);
	}

	public Collection<Cliente> extraerClientes(Date inicio, Date fin) throws FechaNoValidaExcepcion {
		LinkedList<Cliente> clientes = AdministradorDatos.getClientes();
		LocalDateTime inicioLocal = LocalDateTime.ofInstant(inicio.toInstant(), ZoneId.systemDefault());
		LocalDateTime finLocal = LocalDateTime.ofInstant(fin.toInstant(), ZoneId.systemDefault());
		return AdministradorDatos.extraerConjunto(clientes, inicioLocal, finLocal);
	}

	public Collection<Llamada> extraerLlamadas(Date inicio, Date fin) throws FechaNoValidaExcepcion {
		LinkedList<Cliente> clientes = AdministradorDatos.getClientes();
		LinkedList<Llamada> llamadas = new LinkedList<>();

		for (Cliente cliente : clientes)
			llamadas.addAll(cliente.getLlamadas());

		LocalDateTime inicioLocal = LocalDateTime.ofInstant(inicio.toInstant(), ZoneId.systemDefault());
		LocalDateTime finLocal = LocalDateTime.ofInstant(fin.toInstant(), ZoneId.systemDefault());
		return AdministradorDatos.extraerConjunto(llamadas, inicioLocal, finLocal);
	}

	public Collection<Factura> extraerFacturas(Date inicio, Date fin) throws FechaNoValidaExcepcion {
		LinkedList<Cliente> clientes = AdministradorDatos.getClientes();
		LinkedList<Factura> facturas = new LinkedList<>();

		for (Cliente cliente : clientes)
			facturas.addAll(cliente.getFacturas());

		LocalDateTime inicioLocal = LocalDateTime.ofInstant(inicio.toInstant(), ZoneId.systemDefault());
		LocalDateTime finLocal = LocalDateTime.ofInstant(fin.toInstant(), ZoneId.systemDefault());
		return AdministradorDatos.extraerConjunto(facturas, inicioLocal, finLocal);
	}

	public Tarifa getTarifa(String nif) throws ClienteNoExisteExcepcion {
		return AdministradorDatos.getCliente(nif).getTarifa();
	}
}
