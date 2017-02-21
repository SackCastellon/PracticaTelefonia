package es.uji.al341823.telefonia;

import java.util.HashMap;

/**
 * Created by Juanjo on 21/2/2017.
 */
public class Telefonia {

	public static final HashMap<String, Particular> clientes = new HashMap<>(); //FIXME

	public static void altaCliente(Particular cliente) {
		if (clientes.containsKey(cliente.getNif())) {
			// No añadido
		} else {
			clientes.put(cliente.getNif(), cliente);
		}
	}

	public static void bajaCliente(String nif) {
		if (clientes.containsKey(nif)) {
			Particular eliminado = clientes.remove(nif);
		} else {
			// No eliminado
		}
	}

	public static void cambiarTarifa(String nif, Tarifa nuevaTarifa) {
		if (clientes.containsKey(nif)) {
			clientes.get(nif).setTarifa(nuevaTarifa);
		} else {
			// No cambiado
		}
	}
}
