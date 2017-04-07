package es.uji.al341823.telefonia.facturacion.tarifas;

import es.uji.al341823.telefonia.api.IDescripcion;
import es.uji.al341823.telefonia.llamadas.Llamada;

import java.io.Serializable;

/**
 * @author Juanjo González (al341823)
 * @author David Agost (al341819)
 * @since 0.1
 */
public abstract class Tarifa implements Serializable, IDescripcion {

	private static final long serialVersionUID = 2027121994743403669L;

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
	float getCosteLlamada(Llamada llamada) {
		return this.precio * llamada.getDuracionLlamada();
	}

	/**
	 * Devuelve toda la información de la tarifa
	 *
	 * @return Información de la tarifa
	 */
	@Override
	public String toString() {
		return "Tarifa{" +
				"precio=" + this.precio +
				'}';
	}
}
