package es.uji.al341823.telefonia.api;

import es.uji.al341823.telefonia.facturacion.Factura;
import es.uji.al341823.telefonia.cliente.Llamada;
import es.uji.al341823.telefonia.facturacion.Tarifa;
import es.uji.al341823.telefonia.cliente.Cliente;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Clase que se encarga de administrar todas la operaciónes
 *
 * @author Juanjo González (al341823)
 * @author David Agost (al341819)
 * @since 0.1
 */
public class Administrador {

	/** Almacena todos los clientes que hay en el momento */
	private static final HashMap<String, Cliente> clientes = new HashMap<>();

	/**
	 * Da de alta un nuevo cliente siempre y cuado no esté dado ya de alta.<br>
	 * Se entiende que un cliente ya está dado alta cuando su NIF está almacenado en el sistema
	 *
	 * @param cliente Nuevo cliente a dar de alta
	 */
	public static void altaCliente(Cliente cliente) {
		if (!clientes.containsKey(cliente.getNif()))
			clientes.put(cliente.getNif(), cliente);
	}

	/**
	 * Da de baja un cliente siempre y cuando haya sido dado de alta previamente.<br>
	 * Se entiende que un cliente está dado de alta cuando su NIF está almacenado en el sistema
	 *
	 * @param nif NIF del cliente a das de baja
	 */
	public static void bajaCliente(String nif) {
		if (clientes.containsKey(nif))
			clientes.remove(nif);
	}

	public static void cambiarTarifa(String nif, Tarifa nuevaTarifa) {
		if (clientes.containsKey(nif))
			clientes.get(nif).setTarifa(nuevaTarifa);
	}

	public static Cliente getCliente(String nif) {
		return clientes.get(nif);
	}

	public static LinkedList<Cliente> getClientes() {
		return (LinkedList<Cliente>) clientes.values();
	}

	public static void altaLlamada(String nif, Llamada llamada) {
		if (clientes.containsKey(nif))
			clientes.get(nif).altaLlamada(llamada);
	}

	public static List<Llamada> getLlamadas(String nif) {
		if (!clientes.containsKey(nif))
			return null;

		return clientes.get(nif).getLlamadas();
	}

	public static Factura emitirFactura(String nif) {
		return null; //TODO
	}

	public static Factura getFactura(int codigo) {
		return null; //TODO
	}
}
