package es.uji.al341823.telefonia;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Juanjo on 21/2/2017.
 */
public class Telefonia {

	private static final HashMap<String, Empresa> clientes = new HashMap<>();

	public static boolean altaCliente(Empresa cliente) {
		if (clientes.containsKey(cliente.getNif()))
			return false;

		clientes.put(cliente.getNif(), cliente);
		return true;
	}

	public static Empresa bajaCliente(String nif) {
		if (!clientes.containsKey(nif))
			return null;

		return clientes.remove(nif);
	}

	public static Tarifa cambiarTarifa(String nif, Tarifa nuevaTarifa) {
		if (!clientes.containsKey(nif))
			return null;

		return clientes.get(nif).setTarifa(nuevaTarifa);
	}

	public static Empresa getCliente(String nif) {
		return clientes.get(nif);
	}

	public static Collection<Empresa> getClientes() {
		return clientes.values();
	}

	public static boolean altaLlamada(String nif, Llamada llamada) {
		if (!clientes.containsKey(nif))
			return false;

		clientes.get(nif).altaLlamada(llamada);
		return true;
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
