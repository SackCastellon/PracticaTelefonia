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

	/** Almacena todas las facturas emitidas hasta el momento */
	private static final HashMap<Integer, Factura> facturas = new HashMap<>();

	/**
	 * Da de alta un nuevo cliente siempre y cuado no esté dado ya de alta.<br>
	 * Se entiende que un cliente ya está dado alta cuando su NIF está almacenado en el sistema
	 *
	 * @param cliente Nuevo cliente a dar de alta
	 *
	 * @return <code>true</code> si se ha añadido el cliente o <code>false</code> si el cliente ya exitia
	 */
	public static boolean altaCliente(Cliente cliente) {
		if (exixteCliente(cliente.getNif()))
			return false;

		clientes.put(cliente.getNif(), cliente);
		return true;
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
		if (exixteCliente(nif)) {
			clientes.remove(nif);
			return true;
		}

		return false;
	}

	/**
	 * Cambia la tarifa del cliente con el NIF dado a la tarifa dada
	 *
	 * @param nif         NIF de cliente
	 * @param nuevaTarifa Nueva tarifa
	 */
	public static void cambiarTarifa(String nif, Tarifa nuevaTarifa) {
		if (exixteCliente(nif))
			clientes.get(nif).setTarifa(nuevaTarifa);
	}

	/**
	 * Devuelve el cliente cuyo NIF es igual al dado
	 *
	 * @param nif NIF del cliente
	 *
	 * @return El cliente con ese NIF
	 */
	public static Cliente getCliente(String nif) {
		return clientes.get(nif);
	}

	/**
	 * Devuelve una lista con todos los cliente actuales
	 *
	 * @return Lista de clientes
	 */
	public static Collection<Cliente> getClientes() {
		return clientes.values();
	}

	/**
	 * Da de alta una llamada para el cliente con el NIF especificado
	 *
	 * @param nif     NIF del cliente
	 * @param llamada La llamada
	 */
	public static void altaLlamada(String nif, Llamada llamada) {
		if (exixteCliente(nif))
			clientes.get(nif).altaLlamada(llamada);
	}

	/**
	 * Devuelve la lista dellamadas del cliente con el NIF especificado
	 *
	 * @param nif NIF del cliente
	 *
	 * @return Lista de llamadas
	 */
	public static Collection<Llamada> getLlamadas(String nif) {
		if (exixteCliente(nif))
			return clientes.get(nif).getLlamadas();

		return null;
	}

	/**
	 * Emite una factura para el cliente con el NIF especificado
	 *
	 * @param nif NIF del cliente
	 *
	 * @return La factura emitida
	 */
	public static Factura emitirFactura(String nif) {
		if (exixteCliente(nif)) {
			Factura factura = getCliente(nif).emitirFactura();
			facturas.put(factura.getCodigo(), factura);
			return factura;
		}

		return null;
	}

	/**
	 * Devuelve la factura correspondiente al codigo especificado
	 *
	 * @param codigo Codigo de factura
	 *
	 * @return La factura correspondiente
	 */
	public static Factura getFactura(int codigo) {
		return facturas.get(codigo);
	}

	/**
	 * Devuelve la lista de faturas del cliente correcpondiente con el NIF especificado
	 *
	 * @param nif NIF del cliente
	 *
	 * @return Lista de faturas
	 */
	public static Collection<Factura> getFacturas(String nif) {
		if (exixteCliente(nif))
			return getCliente(nif).getFacturas();

		return null;
	}

	/**
	 * Genera un cierto número de clientes particulare de forma aleatoria y los da de alta
	 *
	 * @param cantidad Cantidad de cliente a generar
	 */
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

	/**
	 * Comprueba si existe un cliente con el NIF especificado
	 *
	 * @param nif NIF a comprobar
	 *
	 * @return <code>true</code> si existe o <code>false</code> en caso contrario
	 */
	public static boolean exixteCliente(String nif) {
		return clientes.containsKey(nif);
	}
}
