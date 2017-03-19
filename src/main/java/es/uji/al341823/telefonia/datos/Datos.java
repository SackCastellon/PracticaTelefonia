package es.uji.al341823.telefonia.datos;

import es.uji.al341823.telefonia.clientes.Cliente;
import es.uji.al341823.telefonia.facturacion.Factura;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by Juanjo on 18/03/2017.
 */
public class Datos implements Serializable {

	/** Almacena todos los clientes que hay en el momento */
	public final HashMap<String, Cliente> CLIENTES = new HashMap<>();

	/** Almacena todas las facturas emitidas hasta el momento */
	public final HashMap<Integer, Factura> FACTURAS = new HashMap<>();
}
