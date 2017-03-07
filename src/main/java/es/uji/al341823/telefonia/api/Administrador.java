package es.uji.al341823.telefonia.api;

import es.uji.al341823.telefonia.IFecha;
import es.uji.al341823.telefonia.clientes.Cliente;
import es.uji.al341823.telefonia.clientes.DireccionPostal;
import es.uji.al341823.telefonia.clientes.Particular;
import es.uji.al341823.telefonia.facturacion.FacturaTelefonica;
import es.uji.al341823.telefonia.facturacion.TarifaTelefonica;
import es.uji.al341823.telefonia.llamadas.Llamada;
import es.uji.www.GeneradorDatosINE;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

/**
 * Clase que se encarga de administrar todas la operaciónes
 *
 * @author Juanjo González (al341823)
 * @author David Agost (al341819)
 * @since 0.1
 */
public class Administrador {

	/**
	 * Almacena todos los clientes que hay en el momento
	 */
	private static final HashMap<String, Cliente> CLIENTES = new HashMap<>();

	/**
	 * Almacena todas las facturas emitidas hasta el momento
	 */
	private static final HashMap<Integer, FacturaTelefonica> FACTURAS = new HashMap<>();

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

		CLIENTES.put(cliente.getNif(), cliente);
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
			CLIENTES.remove(nif);
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
	public static void cambiarTarifa(String nif, TarifaTelefonica nuevaTarifa) {
		if (exixteCliente(nif))
			CLIENTES.get(nif).setTarifa(nuevaTarifa);
	}

	/**
	 * Devuelve el cliente cuyo NIF es igual al dado
	 *
	 * @param nif NIF del cliente
	 *
	 * @return El cliente con ese NIF
	 */
	public static Cliente getCliente(String nif) {
		return CLIENTES.get(nif);
	}

	/**
	 * Devuelve una lista con todos los cliente actuales
	 *
	 * @return Lista de clientes
	 */
	public static Collection<Cliente> getClientes() {
		return CLIENTES.values();
	}

	/**
	 * Da de alta una llamada para el cliente con el NIF especificado
	 *
	 * @param nif     NIF del cliente
	 * @param llamada La llamada
	 */
	public static void altaLlamada(String nif, Llamada llamada) {
		if (exixteCliente(nif))
			CLIENTES.get(nif).altaLlamada(llamada);
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
			return CLIENTES.get(nif).getLlamadas();

		return null;
	}

	/**
	 * Emite una factura para el cliente con el NIF especificado
	 *
	 * @param nif NIF del cliente
	 *
	 * @return La factura emitida
	 */
	public static FacturaTelefonica emitirFactura(String nif) {
		if (exixteCliente(nif)) {
			FacturaTelefonica factura = getCliente(nif).emitirFactura();
			FACTURAS.put(factura.getCodigo(), factura);
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
	public static FacturaTelefonica getFactura(int codigo) {
		return FACTURAS.get(codigo);
	}

	/**
	 * Devuelve la lista de faturas del cliente correcpondiente con el NIF especificado
	 *
	 * @param nif NIF del cliente
	 *
	 * @return Lista de faturas
	 */
	public static Collection<FacturaTelefonica> getFacturas(String nif) {
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
			String apellidos = gen.getApellido() + " " + gen.getApellido();
			String nif = gen.getNIF();

			String provincia = gen.getProvincia();
			DireccionPostal direccion = new DireccionPostal(rand.nextInt(100000), provincia, gen.getPoblacion(provincia));

			String email = nombre.toLowerCase().replace(' ', '_') + "@example.com";
			LocalDateTime fecha = LocalDateTime.of(2010 + rand.nextInt(10), 1 + rand.nextInt(12), 1 + rand.nextInt(28), rand.nextInt(24), rand.nextInt(60), rand.nextInt(60));
			TarifaTelefonica tarifa = new TarifaTelefonica(rand.nextFloat());

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
		return CLIENTES.containsKey(nif);
	}


	public static <T extends IFecha> Collection<T> extraerConjunto(Collection<T> conjunto, LocalDateTime inico, LocalDateTime fin) {
		Iterator<T> iterator = conjunto.iterator();

		while (iterator.hasNext()) {
			T next = iterator.next();
			if (next.getFecha().isBefore(inico) || next.getFecha().isAfter(fin))
				conjunto.remove(next);
		}
		return conjunto;
	}
}
