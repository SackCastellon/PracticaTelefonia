package es.uji.al341823.telefonia.api;

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

import java.io.Serializable;
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
public class Administrador implements Serializable {

	private static final long serialVersionUID = 4497473553243150487L;

	/**
	 * Almacena todos los clientes que hay en el momento
	 */
	private final HashMap<String, Cliente> CLIENTES = new HashMap<>();

	/**
	 * Almacena todas las facturas emitidas hasta el momento
	 */
	private final HashMap<Integer, Factura> FACTURAS = new HashMap<>();

	/**
	 * Da de alta un nuevo cliente siempre y cuado no esté dado ya de alta.<br>
	 * Se entiende que un cliente ya está dado alta cuando su NIF está almacenado en el sistema
	 *
	 * @param cliente Nuevo cliente a dar de alta
	 *
	 * @throws ClienteYaExisteExcepcion En caso de que el cliente ya existiese
	 */
	private void altaCliente(Cliente cliente) throws ClienteYaExisteExcepcion {
		if (this.exixteCliente(cliente.getNif()))
			throw new ClienteYaExisteExcepcion();

		this.CLIENTES.put(cliente.getNif(), cliente);
	}

	public void altaParticular(String nombre, String apellidos, String nif, Direccion direccion, String email, LocalDateTime fecha, Tarifa tarifa) throws ClienteYaExisteExcepcion {
		this.altaCliente(new Particular(nombre, apellidos, nif, direccion, email, fecha, tarifa)); // TODO
	}

	public void altaEmpresa(String nombre, String nif, Direccion direccion, String email, LocalDateTime fecha, Tarifa tarifa) throws ClienteYaExisteExcepcion {
		this.altaCliente(new Empresa(nombre, nif, direccion, email, fecha, tarifa)); // TODO
	}

	/**
	 * Da de baja un cliente siempre y cuando haya sido dado de alta previamente.<br>
	 * Se entiende que un cliente está dado de alta cuando su NIF está almacenado en el sistema
	 *
	 * @param nif NIF del cliente a das de baja
	 *
	 * @throws ClienteNoExisteExcepcion En caso de que el cliente no exista
	 */
	public void bajaCliente(String nif) throws ClienteNoExisteExcepcion {
		if (!this.exixteCliente(nif))
			throw new ClienteNoExisteExcepcion();

		this.CLIENTES.remove(nif);
	}

	/**
	 * Devuelve el cliente cuyo NIF es igual al dado
	 *
	 * @param nif NIF del cliente
	 *
	 * @return El cliente con ese NIF
	 */
	public Cliente getCliente(String nif) throws ClienteNoExisteExcepcion {
		if (!this.exixteCliente(nif))
			throw new ClienteNoExisteExcepcion();

		return this.CLIENTES.get(nif);
	}

	/**
	 * Devuelve una lista con todos los cliente actuales
	 *
	 * @return Lista de clientes
	 */
	public LinkedList<Cliente> getClientes() {
		return new LinkedList<>(this.CLIENTES.values());
	}

	/**
	 * Devuelve la factura correspondiente al codigo especificado
	 *
	 * @param codigo Codigo de factura
	 *
	 * @return La factura correspondiente
	 */
	public Factura getFactura(int codigo) throws FacturaNoExisteExcepcion {
		if (!this.FACTURAS.containsKey(codigo))
			throw new FacturaNoExisteExcepcion();

		return this.FACTURAS.get(codigo);
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
	public <T extends IFecha> Collection<T> extraerConjunto(Collection<T> conjunto, LocalDateTime inico, LocalDateTime fin) throws FechaNoValidaExcepcion {
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

	public boolean esDatoValido(String string, EnumTipoDato tipoDato) {
		return string.matches(tipoDato.getFormato());
	}

	/**
	 * Comprueba si existe un cliente con el NIF especificado
	 *
	 * @param nif NIF a comprobar
	 *
	 * @return <code>true</code> si existe o <code>false</code> en caso contrario
	 */
	private boolean exixteCliente(String nif) {
		return this.CLIENTES.containsKey(nif);
	}

//	/**
//	 * Genera un cierto número de clientes particulare de forma aleatoria y los da de alta
//	 *
//	 * @param cantidad Cantidad de cliente a generar
//	 */
//	@Deprecated
//	public void generarParticularesAleatorios(int cantidad) {
//		GeneradorDatosINE gen = new GeneradorDatosINE();
//		Random rand = new Random();
//
//		for (int i = 0; i < cantidad; i++) {
//
//			String nombre = gen.getNombre();
//			String apellidos = gen.getApellido() + " " + gen.getApellido();
//			String nif = gen.getNIF();
//
//			String provincia = gen.getProvincia();
//			Direccion direccion = new Direccion(rand.nextInt(100000), provincia, gen.getPoblacion(provincia));
//
//			String email = nombre.toLowerCase().replace(' ', '_') + "@example.com";
//			LocalDateTime fecha = LocalDateTime.of(2010 + rand.nextInt(10), 1 + rand.nextInt(12), 1 + rand.nextInt(28), rand.nextInt(24), rand.nextInt(60), rand.nextInt(60));
//			Tarifa tarifa = new Tarifa(rand.nextFloat());
//
//			altaCliente(new Particular(nombre, apellidos, nif, direccion, email, fecha, tarifa));
//		}
//	}
}
