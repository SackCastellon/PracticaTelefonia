package es.uji.al341823.telefonia.api;

import es.uji.al341823.telefonia.api.excepciones.ClienteNoExisteExcepcion;
import es.uji.al341823.telefonia.api.excepciones.ClienteYaExisteExcepcion;
import es.uji.al341823.telefonia.api.excepciones.FacturaNoExisteExcepcion;
import es.uji.al341823.telefonia.api.excepciones.FechaNoValidaExcepcion;
import es.uji.al341823.telefonia.clientes.Cliente;
import es.uji.al341823.telefonia.clientes.Direccion;
import es.uji.al341823.telefonia.clientes.Empresa;
import es.uji.al341823.telefonia.clientes.Particular;
import es.uji.al341823.telefonia.datos.Datos;
import es.uji.al341823.telefonia.facturacion.Factura;
import es.uji.al341823.telefonia.facturacion.tarifas.Tarifa;

import java.io.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.LinkedList;

/**
 * Clase que se encarga de administrar todas la operaciónes
 *
 * @author Juanjo González (al341823)
 * @author David Agost (al341819)
 * @since 0.1
 */
public class AdministradorDatos {

	private static Datos datos = new Datos();

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

		datos.CLIENTES.put(cliente.getNif(), cliente);
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

		datos.CLIENTES.remove(nif);
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

		return datos.CLIENTES.get(nif);
	}

	/**
	 * Devuelve una lista con todos los cliente actuales
	 *
	 * @return Lista de clientes
	 */
	public static LinkedList<Cliente> getClientes() {
		return new LinkedList<>(datos.CLIENTES.values());
	}

	/**
	 * Eite una factura para el cliente especificado
	 *
	 * @param cliente El cliende del cual se emitirá la factura
	 *
	 * @return La factura emitida, <code>null</code> si no se pudo emitir
	 */
	public static Factura emitirFactura(Cliente cliente) {
		Factura factura = cliente.emitirFactura();
		datos.FACTURAS.put(factura.getCodigo(), factura);

		return factura;
	}

	/**
	 * Devuelve la factura correspondiente al codigo especificado
	 *
	 * @param codigo Codigo de factura
	 *
	 * @return La factura correspondiente
	 */
	public static Factura getFactura(int codigo) throws FacturaNoExisteExcepcion {
		if (!datos.FACTURAS.containsKey(codigo))
			throw new FacturaNoExisteExcepcion();

		return datos.FACTURAS.get(codigo);
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

	/**
	 * Comprueba si un dato es valido, comprovando que el coincide con el patron del tipo de dato
	 *
	 * @param dato     El dato
	 * @param tipoDato El tipo de dato
	 *
	 * @return <code>true</code> si coincide con el patron de tipo de dato, <code>false</code> en caso contrario
	 */
	public static boolean esDatoValido(String dato, EnumTipoDato tipoDato) {
		return dato.matches(tipoDato.getFormato());
	}

	/**
	 * Comprueba si existe un cliente con el NIF especificado
	 *
	 * @param nif NIF a comprobar
	 *
	 * @return <code>true</code> si existe o <code>false</code> en caso contrario
	 */
	private static boolean exixteCliente(String nif) {
		return datos.CLIENTES.containsKey(nif);
	}

	/**
	 * Carga los datos (clientes y facturas) desde el fichero especificado
	 *
	 * @param nombreFichero El fichero de datos
	 */
	public static void cargarDatos(String nombreFichero) {
		try {
			File fichero = new File(nombreFichero);

			FileInputStream fin = new FileInputStream(fichero);
			ObjectInputStream oin = new ObjectInputStream(fin);
			datos = (Datos) oin.readObject();

			System.out.println("Datos cargados con éxito");

			AdministradorMenus.esperarParaContinuar();
		} catch (FileNotFoundException e) {
			System.out.println("Error al cargar datos: No se encontro el fichero de datos");
		} catch (IOException e) {
			System.out.println("Error al cargar datos: No se pudo leer el fichero de datos");
		} catch (ClassNotFoundException e) {
			System.out.println("Error al cargar datos: No se encontro la clase");
		}
	}

	/**
	 * Guarda los datos (clientes y facturas) en el fichero especificado
	 *
	 * @param nombreFichero El nombre del fichero de datos
	 */
	public static void guardarDatos(String nombreFichero) {
		try {
			File fichero = new File(nombreFichero);

			System.out.println();

			if (fichero.createNewFile())
				System.out.println("El fichero de datos no existia y se ha creado\n");

			FileOutputStream fin = new FileOutputStream(fichero);
			ObjectOutputStream oin = new ObjectOutputStream(fin);
			oin.writeObject(datos);

			System.out.println("Datos guardados con éxito");

			AdministradorMenus.esperarParaContinuar();
		} catch (FileNotFoundException e) {
			System.out.println("Error al guardar datos: No se encontro el fichero de datos");
		} catch (IOException e) {
			System.out.println("Error al guardar datos: No se pudo escribir en el fichero de datos");
		}
	}
}
