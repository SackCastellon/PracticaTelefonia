/*
 * Copyright (c) 2017. Esta obra está sujeta a la licencia Reconocimiento 4.0 Internacional de Creative Commons.
 * Para ver una copia de esta licencia, visite http://creativecommons.org/licenses/by/4.0/.
 */

package es.uji.al341823.telefonia.llamadas;

import es.uji.al341823.telefonia.api.AdministradorDatos;
import es.uji.al341823.telefonia.api.IDatos;
import es.uji.al341823.telefonia.api.IFecha;
import javafx.util.Pair;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Juanjo González (al341823)
 * @author David Agost (al341819)
 * @since 0.1
 */
public class Llamada implements IFecha, IDatos, Serializable {

	private static final long serialVersionUID = -5491839185929563564L;

	/** Codigo identificativo unico de la llamada */
	private final int codigo;
	/** Numero desde el que se ha realizado la llamada */
	private final String numeroOrigen;
	/** Numero al que se ha realizado la llamada */
	private final String numeroDestino;
	/** Fecha y hora a la que se realizó la llamada */
	private final LocalDateTime fechaLlamada;
	/** Duración de la llamada en segundos */
	private final int duracionLlamada;

	/**
	 * Crea una entrada de una llamada. Está entrada puede ser almacenada en un cliente posteriormente
	 *
	 * @param numeroOrigen    Número desde el que se llama
	 * @param numeroDestino   Número al que se llama
	 * @param fechaLlamada    Dia y hora de la llamada
	 * @param duracionLlamada Duración de la llamada en segundos
	 */
	public Llamada(String numeroOrigen, String numeroDestino, LocalDateTime fechaLlamada, int duracionLlamada) {
		super();

		this.codigo = AdministradorDatos.getNextCodigoLlamada();

		this.numeroOrigen = numeroOrigen;
		this.numeroDestino = numeroDestino;
		this.fechaLlamada = fechaLlamada;
		this.duracionLlamada = duracionLlamada;
	}

	public static List<String> getNombreDatos() {
		List<String> list = new LinkedList<>();

		list.add("Código");
		list.add("Número de origen");
		list.add("Número de destino");
		list.add("Duración de la llamada");
		list.add("Fecha de la llamada");

		return list;
	}

	/**
	 * Devuelve el codigo identificativo unico de la llamada
	 *
	 * @return Codigo identificativo
	 */
	public int getCodigo() {
		return this.codigo;
	}

	/**
	 * Devuelelve el numero desde el que se realizó la llamada
	 *
	 * @return Devuelve el numero de origen de la llamada
	 */
	public String getNumeroOrigen() {
		return this.numeroOrigen;
	}

	/**
	 * Devuelelve el numero a el que se realizó la llamada
	 *
	 * @return Devuelve el numero de destino de la llamada
	 */
	public String getNumeroDestino() {
		return this.numeroDestino;
	}

	/**
	 * Devuelve la duración total de la llamada medida en segundos
	 *
	 * @return Durecion de la llamada en segundos
	 */
	public int getDuracionLlamada() {
		return this.duracionLlamada;
	}

	/**
	 * Devuelve el dia y la hora a la que se realizó la llamada
	 *
	 * @return Dia y hora de la llamada
	 */
	@Override
	public LocalDateTime getFecha() {
		return this.fechaLlamada;
	}

	@Override
	public List<Pair<String, Object>> getDatos() {
		List<Pair<String, Object>> list = new LinkedList<>();

		list.add(new Pair<>("Código", this.getCodigo()));
		list.add(new Pair<>("Número de origen", this.getNumeroOrigen()));
		list.add(new Pair<>("Número de destino", this.getNumeroDestino()));
		list.add(new Pair<>("Duración de la llamada", this.getDuracionLlamada()));
		list.add(new Pair<>("Fecha de la llamada", this.getFecha()));

		return list;
	}

	/**
	 * Devuelve toda la información de la llamada
	 *
	 * @return Información de la llamada
	 */
	@Override
	public String toString() {
		// TODO Hacer que use "getDatos()"
		return "Llamada{" +
				"origen='" + this.numeroOrigen + '\'' +
				", destino='" + this.numeroDestino + '\'' +
				", fecha=" + this.fechaLlamada +
				", duracion=" + this.duracionLlamada +
				'}';
	}
}