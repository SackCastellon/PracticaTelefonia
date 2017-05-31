/*
 * Copyright (c) 2017. Esta obra está sujeta a la licencia Reconocimiento 4.0 Internacional de Creative Commons.
 * Para ver una copia de esta licencia, visite http://creativecommons.org/licenses/by/4.0/.
 */

package es.uji.al341823.telefonia.datos;

import es.uji.al341823.telefonia.clientes.Cliente;
import es.uji.al341823.telefonia.facturacion.Factura;
import es.uji.al341823.telefonia.llamadas.Llamada;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Almacena todos los datos que se pueden guardar y cargar de la aplicación
 *
 * @author Juanjo González (al341823)
 * @since 0.2
 */
public class Datos implements Serializable {


	private static final long serialVersionUID = -5781118305793535170L;
	/** Almacena todos los clientes que hay en el momento */
	public final HashMap<String, Cliente> CLIENTES = new HashMap<>();

	/** Almacena todas las llamadas realizadas hasta el momento */
	public final HashMap<Integer, Llamada> LLAMADAS = new HashMap<>();

	/** Almacena todas las facturas emitidas hasta el momento */
	public final HashMap<Integer, Factura> FACTURAS = new HashMap<>();
}
