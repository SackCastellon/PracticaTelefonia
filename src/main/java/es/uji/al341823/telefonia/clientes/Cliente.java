/*
 * Copyright (c) 2017. Esta obra está sujeta a la licencia Reconocimiento 4.0 Internacional de Creative Commons.
 * Para ver una copia de esta licencia, visite http://creativecommons.org/licenses/by/4.0/.
 */

package es.uji.al341823.telefonia.clientes;

import es.uji.al341823.telefonia.api.IDatos;
import es.uji.al341823.telefonia.api.IFecha;
import es.uji.al341823.telefonia.facturacion.tarifas.Tarifa;
import javafx.util.Pair;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * @author Juanjo González (al341823)
 * @author David Agost (al341819)
 * @since 0.1
 */
public abstract class Cliente implements IFecha, IDatos, Serializable {

	private static final long serialVersionUID = -6698454540590960908L;

	/** Nombre del cliente */
	private final String nombre;
	/** NIF del cliente */
	private final String nif;
	/** Dirección del cliente */
	private final Direccion direccion;
	/** Email del cliente */
	private final String email;
	/** Fecha en la que se dió de alta el cliente */
	private final LocalDateTime fechaAlta;

	/** Lista de llamadas que realizó el cliente */
	private final HashSet<Integer> codigosLlamadas = new HashSet<>();
	/** Conjunto de codigos de las facturas correspondientes al cliente */
	private final HashSet<Integer> codigosFacturas = new HashSet<>();

	/** Tarifa que tiene contratada el cliente */
	private Tarifa tarifa;
	/** El ultimo dia que se emitió una factura, se usa para calcular el preiodo de facturación */
	private LocalDateTime ultimaFacturacion;

	/**
	 * @param nombre    Nombre del cliente
	 * @param nif       NIF del cliente
	 * @param direccion Dirección del cliente
	 * @param email     Email del cliente
	 * @param fechaAlta Fecha de lata del cliente
	 * @param tarifa    Tarifa contratada por el cliente
	 */
	Cliente(String nombre, String nif, Direccion direccion, String email, LocalDateTime fechaAlta, Tarifa tarifa) {
		super();

		this.nombre = nombre;
		this.nif = nif;
		this.direccion = direccion;
		this.email = email;
		this.fechaAlta = fechaAlta;
		this.tarifa = tarifa;

		this.ultimaFacturacion = fechaAlta;
	}

	/**
	 * Devuelve el nombre del cliente
	 *
	 * @return Nombre del cliente
	 */
	public String getNombre() {
		return this.nombre;
	}

	/**
	 * Devuelve el NIF del cliente
	 *
	 * @return NIF del cliente
	 */
	public String getNif() {
		return this.nif;
	}

	/**
	 * Devuelve un objeto dirección con la información de la dirección del cliente
	 *
	 * @return Dirección del cliente
	 */
	public Direccion getDireccion() {
		return this.direccion;
	}

	/**
	 * Devuelve el email del cliente
	 *
	 * @return Email del cliente
	 */
	public String getEmail() {
		return this.email;
	}

	/**
	 * Devueve la fecha y la hora a la que el cliente se dió de alta
	 *
	 * @return Fecha de alta del cliente
	 */
	@Override
	public LocalDateTime getFecha() {
		return this.fechaAlta;
	}

	/**
	 * Devuelve la tarifa que tiene contratada el cliente en el momento actual
	 *
	 * @return Tarifa contratada
	 */
	public Tarifa getTarifa() {
		return this.tarifa;
	}

	/**
	 * Establece la nueva tarifa que tendrá contrada el cliente
	 *
	 * @param tarifa Nueva tarifa
	 */
	public void setTarifa(Tarifa tarifa) {
		this.tarifa = tarifa;
	}

	/**
	 * Da de alta una nueva llamada para el cliente
	 *
	 * @param codigoLlamada Código de la nueva llamada
	 */
	public void addLlamada(int codigoLlamada) {
		this.codigosLlamadas.add(codigoLlamada);
	}

	/**
	 * Devuelve una lista de las llamadas de el cliente
	 *
	 * @return Lista de llamadas
	 */
	public HashSet<Integer> getCodigosLlamadas() {
		return new HashSet<>(this.codigosLlamadas);
	}

	/**
	 * Añade una nueva factura para el cliente
	 *
	 * @param codigoFactura Código de la nueva factura
	 */
	public void addFactura(int codigoFactura) {
		this.codigosFacturas.add(codigoFactura);
	}

//	/**
//	 * Emite una nueva factura para el cliente teniendo en cuenta la tarifa contatada en el momento y las llamadas
//	 * realizadas
//	 *
//	 * @return La factura emitida
//	 */
//	public Factura generarFactura() throws FechaNoValidaExcepcion {
//		LocalDateTime hoy = LocalDateTime.now();
//
//		int duracionLlamadas = 0;
//
//		for (int codigoLlamada : this.codigosLlamadas) {
//			try {
//				Llamada llamada = AdministradorDatos.getLlamada(codigoLlamada);
//				LocalDateTime fecha = llamada.getFecha();
//				if (fecha.isBefore(hoy) && fecha.isAfter(this.ultimaFacturacion))
//					duracionLlamadas += llamada.getDuracionLlamada();
//			} catch (ObjetoNoExisteException e) {
//				System.err.println("Se intentó acceder a una llamada que no existe");
//			}
//		}
//
//		Factura factura = new Factura(this.tarifa, this.ultimaFacturacion, hoy, duracionLlamadas);
//
//		this.ultimaFacturacion = hoy;
//		this.codigosFacturas.add(factura.getCodigo());
//
//		return factura;
//	}

	public LocalDateTime getUltimaFacturacion() {
		return this.ultimaFacturacion;
	}

	public void updateUltimaFaturacion() {
		this.ultimaFacturacion = LocalDateTime.now();
	}

	/**
	 * Devuelve una copia de la lista de las codigosFacturas del cliente
	 *
	 * @return Lista de codigosFacturas - Vacia si no hay facturas
	 */
	public Set<Integer> getCodigosFacturas() {
		return this.codigosFacturas;
	}

	@Override
	public List<Pair<String, Object>> getDatos() {
		List<Pair<String, Object>> list = new LinkedList<>();

		list.add(new Pair<>("NIF", this.getNif()));
		list.add(new Pair<>("Nombre", this.getNombre()));
		list.add(new Pair<>("Dirección", this.getDireccion()));
		list.add(new Pair<>("Email", this.getEmail()));
		list.add(new Pair<>("Fecha de alta", this.getFecha()));
		list.add(new Pair<>("Tarifas", this.getTarifa()));

		return list;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if ((o == null) || (this.getClass() != o.getClass())) return false;

		Cliente cliente = (Cliente) o;

		return this.nif.equals(cliente.nif);
	}

	@Override
	public int hashCode() {
		return this.nif.hashCode();
	}
}
