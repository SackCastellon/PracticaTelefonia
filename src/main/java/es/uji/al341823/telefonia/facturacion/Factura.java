/*
 * Copyright (c) 2017. Esta obra está sujeta a la licencia Reconocimiento 4.0 Internacional de Creative Commons.
 * Para ver una copia de esta licencia, visite http://creativecommons.org/licenses/by/4.0/.
 */

package es.uji.al341823.telefonia.facturacion;

import es.uji.al341823.telefonia.api.IDatos;
import es.uji.al341823.telefonia.api.IFecha;
import es.uji.al341823.telefonia.api.excepciones.FechaNoValidaExcepcion;
import es.uji.al341823.telefonia.facturacion.tarifas.Tarifa;
import javafx.util.Pair;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Juanjo González (al341823)
 * @author David Agost (al341819)
 * @since 0.1
 */
public class Factura implements IFecha, IDatos, Serializable {

	private static final long serialVersionUID = -7711276290283216630L;

	/** Codigo identificativo unico de la factura */
	private final int codigo;
	/** La tarifa usada para calcular la factura */
	private final Tarifa tarifa;
	/** Fecha y hora a la que se emitió la factura */
	private final LocalDateTime fechaEmision;
	/** Periodo de tiempo que comprende la factura en dias */
	private final long periodoFactuacion;
	/** Importe total de la factura calculado en euros (€) */
	private final float importe;

	/**
	 * Crea una tarifa a partir de los datos especificados
	 *
	 * @param tarifa             La tarifa que se aplica
	 * @param fechaUltimaEmision La fecha de emisión de la ultima fsctura
	 * @param fechaEmision       La decha de emisión de la nueva factura (normalmente el dia actual)
	 * @param duracionLlamadas   La duración total en segundos de las llamadas
	 *
	 * @throws FechaNoValidaExcepcion Si la fecha de emision es anterior a la fecha de la última emisión
	 */
	public Factura(int codigo, Tarifa tarifa, LocalDateTime fechaUltimaEmision, LocalDateTime fechaEmision, int duracionLlamadas) throws FechaNoValidaExcepcion {
		super();

		if (fechaEmision.isBefore(fechaUltimaEmision))
			throw new FechaNoValidaExcepcion();

		this.codigo = codigo;
		this.tarifa = tarifa;
		this.fechaEmision = fechaEmision;
		Duration periodo = Duration.between(fechaUltimaEmision, fechaEmision);
		this.periodoFactuacion = periodo.toDays();
		this.importe = tarifa.getPrecio() * duracionLlamadas;
	}

	public static List<String> getNombreDatos() {
		List<String> list = new LinkedList<>();

		list.add("Código");
		list.add("Tarifa");
		list.add("Fecha de emisión");
		list.add("Periodo de facturación");
		list.add("Importe");

		return list;
	}

	/**
	 * Devuelve el codigo identificativo unico de la factura
	 *
	 * @return Codigo identificativo
	 */
	public int getCodigo() {
		return this.codigo;
	}

	/**
	 * Devuelve la tarifa usada para calcular la factura
	 *
	 * @return La taifa de la factura
	 */
	public Tarifa getTarifa() {
		return this.tarifa;
	}

	/**
	 * Devuelve el periodo en días durante el cual se aplica la tarifa para calcuar el importe de la factura
	 *
	 * @return El periodo de facturación
	 */
	public long getPeriodoFactuacion() {
		return this.periodoFactuacion;
	}

	/**
	 * Devuelve el importe total de la factura calculado en euros (€)
	 *
	 * @return Importe de la factura
	 */
	public float getImporte() {
		return this.importe;
	}

	/**
	 * Devuelve el dia y la hora a la que se emitión la factura
	 *
	 * @return El dia y la hora de emisión de la factura
	 */
	@Override
	public LocalDateTime getFecha() {
		return this.fechaEmision;
	}

	@Override
	public List<Pair<String, Object>> getDatos() {
		List<Pair<String, Object>> list = new LinkedList<>();

		list.add(new Pair<>("Código", this.getCodigo()));
		list.add(new Pair<>("Tarifa", this.getTarifa()));
		list.add(new Pair<>("Fecha de emisión", this.getFecha()));
		list.add(new Pair<>("Periodo de facturación", this.getPeriodoFactuacion()));
		list.add(new Pair<>("Importe", this.getImporte()));

		return list;
	}

	/**
	 * Devuelve toda la información de la factura
	 *
	 * @return Información de la factura
	 */
	@Override
	public String toString() {
		// TODO Hacer que use "getDatos()"
		return "Factura{" +
				"cod=" + this.codigo +
				", tarifa=" + this.tarifa +
				", emision=" + this.fechaEmision +
				", periodo=" + this.periodoFactuacion +
				", importe=" + this.importe +
				'}';
	}
}
