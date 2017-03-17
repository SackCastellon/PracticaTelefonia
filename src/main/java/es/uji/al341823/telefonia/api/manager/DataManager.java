package es.uji.al341823.telefonia.api.manager;

import es.uji.al341823.telefonia.IFecha;
import es.uji.al341823.telefonia.api.excepciones.ClienteNoExisteExcepcion;
import es.uji.al341823.telefonia.api.excepciones.ClienteYaExisteExcepcion;
import es.uji.al341823.telefonia.api.excepciones.FacturaNoExisteExcepcion;
import es.uji.al341823.telefonia.api.excepciones.FechaNoValidaExcepcion;
import es.uji.al341823.telefonia.clientes.Cliente;
import es.uji.al341823.telefonia.clientes.Direccion;
import es.uji.al341823.telefonia.clientes.Empresa;
import es.uji.al341823.telefonia.clientes.Particular;
import es.uji.al341823.telefonia.facturacion.Factura;
import es.uji.al341823.telefonia.facturacion.Tarifa;

import java.io.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Clase que se encarga de administrar todas la operaciónes
 *
 * @author Juanjo González (al341823)
 * @author David Agost (al341819)
 * @since 0.1
 */
public class DataManager implements Serializable {

	private static final long serialVersionUID = 4497473553243150487L;

	private static final File ficheroClientes = new File("Telefonia.data");
	private static final File ficheroFacturas = new File("Facturas.data");

	/**
	 * Almacena todos los clientes que hay en el momento
	 */
	private static final HashMap<String, Cliente> CLIENTES = new HashMap<>();

	/**
	 * Almacena todas las facturas emitidas hasta el momento
	 */
	private static final HashMap<Integer, Factura> FACTURAS = new HashMap<>();

	/**
	 * Da de alta un nuevo cliente siempre y cuado no esté dado ya de alta.<br>
	 * Se entiende que un cliente ya está dado alta cuando su NIF está almacenado en el sistema
	 *
	 * @param cliente Nuevo cliente a dar de alta
	 *
	 * @throws ClienteYaExisteExcepcion En caso de que el cliente ya existiese
	 */
	private static void altaCliente(Cliente cliente) throws ClienteYaExisteExcepcion {
		if (exixteCliente(cliente.getNif()))
			throw new ClienteYaExisteExcepcion();

		CLIENTES.put(cliente.getNif(), cliente);
	}

	public static void altaParticular(String nombre, String apellidos, String nif, Direccion direccion, String email, LocalDateTime fecha, Tarifa tarifa) throws ClienteYaExisteExcepcion {
		altaCliente(new Particular(nombre, apellidos, nif, direccion, email, fecha, tarifa)); // TODO
	}

	public static void altaEmpresa(String nombre, String nif, Direccion direccion, String email, LocalDateTime fecha, Tarifa tarifa) throws ClienteYaExisteExcepcion {
		altaCliente(new Empresa(nombre, nif, direccion, email, fecha, tarifa)); // TODO
	}

	/**
	 * Da de baja un cliente siempre y cuando haya sido dado de alta previamente.<br>
	 * Se entiende que un cliente está dado de alta cuando su NIF está almacenado en el sistema
	 *
	 * @param nif NIF del cliente a das de baja
	 *
	 * @throws ClienteNoExisteExcepcion En caso de que el cliente no exista
	 */
	public static void bajaCliente(String nif) throws ClienteNoExisteExcepcion {
		if (!exixteCliente(nif))
			throw new ClienteNoExisteExcepcion();

		CLIENTES.remove(nif);
	}

	/**
	 * Devuelve el cliente cuyo NIF es igual al dado
	 *
	 * @param nif NIF del cliente
	 *
	 * @return El cliente con ese NIF
	 */
	public static Cliente getCliente(String nif) throws ClienteNoExisteExcepcion {
		if (!exixteCliente(nif))
			throw new ClienteNoExisteExcepcion();

		return CLIENTES.get(nif);
	}

	/**
	 * Devuelve una lista con todos los cliente actuales
	 *
	 * @return Lista de clientes
	 */
	public static LinkedList<Cliente> getClientes() {
		return new LinkedList<>(CLIENTES.values());
	}

	/**
	 * Devuelve la factura correspondiente al codigo especificado
	 *
	 * @param codigo Codigo de factura
	 *
	 * @return La factura correspondiente
	 */
	public static Factura getFactura(int codigo) throws FacturaNoExisteExcepcion {
		if (!FACTURAS.containsKey(codigo))
			throw new FacturaNoExisteExcepcion();

		return FACTURAS.get(codigo);
	}

	/**
	 * Extrae, a partir de un conjunto de elementos que implemetan la <code>interface IFecha</code>, otro conjunto de
	 * elementos cuyo valor <code>IFecha.getFecha()</code> esta comprendido enre las fechas especificadas
	 *
	 * @param conjunto Conjunto de elemetos del cual se extraen los elementos
	 * @param inico    Fecha de incio del periodo
	 * @param fin      Fecha de fin del periodo
	 * @param <T>      E tipo del elementos que implementa la <code>interface IFecha</code>
	 *
	 * @return El conjunto que se ha extraido del conjunto original
	 */
	public static <T extends IFecha> Collection<T> extraerConjunto(Collection<T> conjunto, LocalDateTime inico, LocalDateTime fin) throws FechaNoValidaExcepcion {
		if (inico.isAfter(fin))
			throw new FechaNoValidaExcepcion();

		Collection<T> extraccion = new LinkedList<>();

		for (T elem : conjunto) {
			LocalDateTime fecha = elem.getFecha();
			if (fecha.isAfter(inico) && fecha.isBefore(fin))
				extraccion.add(elem);
		}

		return extraccion;
	}

	public static boolean esDatoValido(String string, EnumTipoDato tipoDato) {
		return string.matches(tipoDato.getFormato());
	}

	/**
	 * Comprueba si existe un cliente con el NIF especificado
	 *
	 * @param nif NIF a comprobar
	 *
	 * @return <code>true</code> si existe o <code>false</code> en caso contrario
	 */
	private static boolean exixteCliente(String nif) {
		return CLIENTES.containsKey(nif);
	}

	@SuppressWarnings("unchecked")
	public static void cargarDatos() {
		try {
			FileInputStream in = new FileInputStream(ficheroClientes);
			ObjectInputStream obj = new ObjectInputStream(in);
			CLIENTES.putAll((HashMap<String, Cliente>) obj.readObject());
			obj.close();
		} catch (Exception e) {
			System.err.println("ERROR: No se pudieron cargar los datos");
			e.printStackTrace();
		}
	}

	@SuppressWarnings("ResultOfMethodCallIgnored")
	public static void guardarDatos() {
		try {
			ficheroClientes.createNewFile();

			FileOutputStream out = new FileOutputStream(ficheroClientes);
			ObjectOutputStream obj = new ObjectOutputStream(out);
			obj.writeObject(CLIENTES);
			obj.close();
		} catch (Exception e) {
			System.err.println("ERROR: No se pudieron guardar los datos");
			e.printStackTrace();
		}
	}
}
