/*
 * Copyright (c) 2017. Esta obra está sujeta a la licencia Reconocimiento 4.0 Internacional de Creative Commons.
 * Para ver una copia de esta licencia, visite http://creativecommons.org/licenses/by/4.0/.
 */

package es.uji.al341823.telefonia.facturacion.tarifas;

import es.uji.al341823.telefonia.llamadas.Llamada;

import java.io.Serializable;

/**
 * @author Juanjo González (al341823)
 * @author David Agost (al341819)
 * @since 0.1
 */
public abstract class Tarifa implements Serializable {


	private static final long serialVersionUID = -8903195202482262057L;
	/**
	 * Precio por minuto de la tarifa (€/min)
	 */
	private final float precio;

	/**
	 * Crea una tarifa con un precio determinado
	 *
	 * @param precio Precio de la tarifa
	 */
	Tarifa(float precio) {
		super();

		this.precio = precio;
	}

	/**
	 * Devuelve el precio por minuto de la tarifa (€/min)
	 *
	 * @return Precio por minuto
	 */
	public float getPrecio() {
		return this.precio;
	}

	/**
	 * Devuelve el precio total de la llamada pasada teniendo en cuenta la factura base y las extras
	 *
	 * @param llamada La llamada
	 *
	 * @return Coste de la llamada
	 */
	public float getCosteLlamada(Llamada llamada) {
		return (this.getPrecio() * llamada.getDuracionLlamada()) / 60f;
	}

	/**
	 * Devuelve toda la información de la tarifa
	 *
	 * @return Información de la tarifa
	 */
	@Override
	public String toString() {
		return String.format("Tarifa Base: %.2f €/min", this.getPrecio());
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if ((o == null) || (this.getClass() != o.getClass())) return false;

		Tarifa tarifa = (Tarifa) o;

		return Float.compare(tarifa.precio, this.precio) == 0;
	}

	@Override
	public int hashCode() {
		return ((this.precio == +0.0f) ? 0 : Float.floatToIntBits(this.precio));
	}
}
