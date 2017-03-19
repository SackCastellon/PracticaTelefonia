package es.uji.al341823.telefonia.datos;

import es.uji.al341823.telefonia.clientes.Cliente;
import es.uji.al341823.telefonia.facturacion.Factura;

import java.io.Serializable;
import java.util.HashMap;

/**
 * @author Juanjo González (al341823)
 * @since 0.2
 */
public class Datos implements Serializable {

	private static final long serialVersionUID = -5886315231049338254L;

	/** Almacena todos los clientes que hay en el momento */
	public final HashMap<String, Cliente> CLIENTES = new HashMap<>();

	/** Almacena todas las facturas emitidas hasta el momento */
	public final HashMap<Integer, Factura> FACTURAS = new HashMap<>();
}
