package es.uji.al341823.telefonia.api;

import es.uji.al341823.telefonia.cliente.Cliente;
import es.uji.al341823.telefonia.cliente.Direccion;
import es.uji.al341823.telefonia.cliente.Llamada;
import es.uji.al341823.telefonia.cliente.Particular;
import es.uji.al341823.telefonia.facturacion.Factura;
import es.uji.al341823.telefonia.facturacion.Tarifa;
import es.uji.www.GeneradorDatosINE;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

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
	 *
	 * @return <code>true</code> si se ha dado de baja el cliente o <code>false</code> si el cliente no exitia
	 */
	public static boolean bajaCliente(String nif) {
		if (clientes.containsKey(nif)) {
			clientes.remove(nif);
			return true;
		}

		return false;
	}

	public static void cambiarTarifa(String nif, Tarifa nuevaTarifa) {
		if (clientes.containsKey(nif))
			clientes.get(nif).setTarifa(nuevaTarifa);
	}

	public static Cliente getCliente(String nif) {
		return clientes.get(nif);
	}

	public static Collection<Cliente> getClientes() {
		return clientes.values();
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

	public static void generarParticularesAleatorios(int cantidad) {
		GeneradorDatosINE gen = new GeneradorDatosINE();
		Random rand = new Random();

		for (int i = 0; i < cantidad; i++) {

			String nombre = gen.getNombre();
			String apellidos = gen.getApellido();
			String nif = gen.getNIF();

			String provincia = gen.getProvincia();
			Direccion direccion = new Direccion(rand.nextInt(100000), provincia, gen.getPoblacion(provincia));

			String email = nombre.toLowerCase().replace(' ', '_') + "@example.com";
			LocalDateTime fecha = LocalDateTime.of(2010 + rand.nextInt(10), 1 + rand.nextInt(12), 1 + rand.nextInt(28), rand.nextInt(24), rand.nextInt(60), rand.nextInt(60));
			Tarifa tarifa = new Tarifa(1);

			altaCliente(new Particular(nombre, apellidos, nif, direccion, email, fecha, tarifa));
		}
	}
}
