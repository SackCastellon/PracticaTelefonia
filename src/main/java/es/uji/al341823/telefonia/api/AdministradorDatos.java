/*
 * Copyright (c) 2017. Esta obra está sujeta a la licencia Reconocimiento 4.0 Internacional de Creative Commons.
 * Para ver una copia de esta licencia, visite http://creativecommons.org/licenses/by/4.0/.
 */

package es.uji.al341823.telefonia.api;

import es.uji.al341823.telefonia.api.excepciones.FechaNoValidaExcepcion;
import es.uji.al341823.telefonia.api.excepciones.ObjetoNoExisteException;
import es.uji.al341823.telefonia.api.excepciones.ObjetoYaExisteException;
import es.uji.al341823.telefonia.clientes.Cliente;
import es.uji.al341823.telefonia.datos.Datos;
import es.uji.al341823.telefonia.facturacion.Factura;
import es.uji.al341823.telefonia.gui.swing.Vista;
import es.uji.al341823.telefonia.llamadas.Llamada;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Set;

/**
 * Clase que se encarga de administrar todas la operaciónes
 *
 * @author Juanjo González (al341823)
 * @author David Agost (al341819)
 * @since 0.1
 */
public class AdministradorDatos {

	private Datos datos = new Datos();
	private File ficheroDatos;

	private Vista vista;

	public Vista getVista() {
		return this.vista;
	}

	public void setVista(Vista vista) {
		this.vista = vista;
	}

	/**
	 * Da de alta un nuevo cliente siempre y cuado no esté dado ya de alta.<br>
	 * Se entiende que un cliente ya está dado alta cuando su NIF está almacenado en el sistema
	 *
	 * @param cliente Nuevo cliente a dar de alta
	 *
	 * @throws ObjetoYaExisteException En caso de que el cliente ya existiese
	 */
	public void addCliente(Cliente cliente) throws ObjetoYaExisteException {
		if (this.exixteCliente(cliente.getNif()))
			throw new ObjetoYaExisteException("Cliente");

		this.datos.CLIENTES.put(cliente.getNif(), cliente);

		if (this.vista != null) this.vista.actualizarVista();
	}

	/**
	 * Da de baja un cliente siempre y cuando haya sido dado de alta previamente.<br>
	 * Se entiende que un cliente está dado de alta cuando su NIF está almacenado en el sistema
	 *
	 * @param nif NIF del cliente a das de baja
	 *
	 * @throws ObjetoNoExisteException En caso de que el cliente no exista
	 */
	public void removeCliente(String nif) throws ObjetoNoExisteException {
		if (!this.exixteCliente(nif))
			throw new ObjetoNoExisteException("Cliente");

		this.datos.CLIENTES.remove(nif);

		if (this.vista != null) this.vista.actualizarVista();
	}

	/**
	 * Devuelve el cliente cuyo NIF es igual al dado
	 *
	 * @param nif NIF del cliente
	 *
	 * @return El cliente con ese NIF
	 *
	 * @throws ObjetoNoExisteException En caso de que el NIF no corresponda a ningún cliente
	 */
	public Cliente getCliente(String nif) throws ObjetoNoExisteException {
		if (!this.exixteCliente(nif))
			throw new ObjetoNoExisteException("Cliente");

		return this.datos.CLIENTES.get(nif);
	}

	/**
	 * Devuelve una lista con todos los cliente actuales
	 *
	 * @return Lista de clientes
	 */
	public Collection<Cliente> getClientes() {
		return this.datos.CLIENTES.values();
	}

	public int getNextCodigoLlamada() {
		Set<Integer> set = this.datos.LLAMADAS.keySet();
		if (!set.isEmpty())
			return Collections.max(set) + 1;
		return 0;
	}

	public void addLlamada(String nif, Llamada llamada) throws ObjetoYaExisteException, ObjetoNoExisteException {
		if (this.datos.LLAMADAS.containsKey(llamada.getCodigo()))
			throw new ObjetoYaExisteException("Llamada");

		Cliente cliente = this.getCliente(nif);
		cliente.addLlamada(llamada.getCodigo());

		this.datos.LLAMADAS.put(llamada.getCodigo(), llamada);

		if (this.vista != null) this.vista.actualizarVista();
	}

	public Llamada getLlamada(int codigo) throws ObjetoNoExisteException {
		Llamada llamada = this.datos.LLAMADAS.get(codigo);

		if (llamada == null)
			throw new ObjetoNoExisteException("Llamada");

		return llamada;
	}

	/**
	 * Devuelve una lista con todas las facturas
	 *
	 * @return Lista de facturas
	 */
	public Collection<Llamada> getLlamadas() {
		return this.datos.LLAMADAS.values();
	}

	public Collection<Llamada> getLlamadasCliente(String nif) throws ObjetoNoExisteException {
		Cliente cliente = this.getCliente(nif);
		Collection<Llamada> llamadas = new LinkedList<>();

		for (Integer codigo : cliente.getCodigosLlamadas())
			llamadas.add(this.getLlamada(codigo));

		return llamadas;
	}

	public int getNextCodigoFactura() {
		Set<Integer> set = this.datos.FACTURAS.keySet();
		if (!set.isEmpty())
			return Collections.max(set) + 1;
		return 0;
	}

	/**
	 * Emite una factura para el cliente especificado
	 *
	 * @param nif NIF del cliende del cual se emitirá la factura
	 *
	 * @return La factura emitida, {@code null} si no se pudo emitir
	 */
	public Factura addFactura(String nif) throws ObjetoNoExisteException, FechaNoValidaExcepcion {
		Cliente cliente = this.getCliente(nif);

		LocalDateTime ultimaFacturacion = cliente.getUltimaFacturacion();
		cliente.updateUltimaFaturacion();
		LocalDateTime hoy = cliente.getUltimaFacturacion();

		int duracionLlamadas = 0;

		for (int codigoLlamada : cliente.getCodigosLlamadas()) {
			try {
				Llamada llamada = this.getLlamada(codigoLlamada);
				LocalDateTime fecha = llamada.getFecha();
				if (fecha.isBefore(hoy) && fecha.isAfter(ultimaFacturacion))
					duracionLlamadas += llamada.getDuracionLlamada();
			} catch (ObjetoNoExisteException e) {
				System.err.println("Se intentó acceder a una llamada que no existe");
			}
		}

		Factura factura = new Factura(this.getNextCodigoFactura(), cliente.getTarifa(), ultimaFacturacion, hoy, duracionLlamadas);

		cliente.addFactura(factura.getCodigo());

		this.datos.FACTURAS.put(factura.getCodigo(), factura);

		if (this.vista != null) this.vista.actualizarVista();

		return factura;
	}

	/**
	 * Devuelve la factura correspondiente al codigo especificado
	 *
	 * @param codigo Codigo de factura
	 *
	 * @return La factura correspondiente
	 *
	 * @throws ObjetoNoExisteException Si el codigo no corresponde a ninguna factura
	 */
	public Factura getFactura(int codigo) throws ObjetoNoExisteException {
		Factura factura = this.datos.FACTURAS.get(codigo);

		if (factura == null)
			throw new ObjetoNoExisteException("Factura");

		return factura;
	}

	/**
	 * Devuelve una lista con todas las facturas
	 *
	 * @return Lista de facturas
	 */
	public Collection<Factura> getFacturas() {
		return this.datos.FACTURAS.values();
	}

	public Collection<Factura> getFacturasCliente(String nif) throws ObjetoNoExisteException {
		Cliente cliente = this.getCliente(nif);
		Collection<Factura> facturas = new LinkedList<>();

		for (Integer codigo : cliente.getCodigosFacturas())
			facturas.add(this.getFactura(codigo));

		return facturas;
	}

	/**
	 * Extrae, a partir de un conjunto de elementos, otro conjunto de elementos cuya fecha
	 * esta comprendida <b>exclusivamente</b> entre las fechas especificadas
	 *
	 * @param conjunto Conjunto de elemetos del cual se extraen los elementos
	 * @param inico    Fecha de incio del periodo
	 * @param fin      Fecha de fin del periodo
	 * @param <T>      E tipo del elementos que implementa la {@code interface IFecha}
	 *
	 * @return El conjunto que se ha extraido del conjunto original
	 *
	 * @throws FechaNoValidaExcepcion Si inicio es posterior a fin
	 * @see IFecha#getFecha()
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

	/**
	 * Comprueba si existe un cliente con el NIF especificado
	 *
	 * @param nif NIF a comprobar
	 *
	 * @return {@code true} si existe o {@code false} en caso contrario
	 */
	private boolean exixteCliente(String nif) {
		return this.datos.CLIENTES.containsKey(nif);
	}

	/**
	 * Devuelve el fichero de datos desde donde se cargan y a donde se guardan los datos
	 *
	 * @return El fichero de datos
	 */
	public File getFicheroDatos() {
		return this.ficheroDatos;
	}

	/**
	 * Establece el fichero de datos desde donde se cargan y a donde se guardan los datos
	 *
	 * @param file El fichero de datos
	 */
	public void setFicheroDatos(File file) {
		if (file != null) {
			String name = file.getName();

			if (!name.endsWith(".data"))
				file = new File(file.getParent(), name + ".data");
		}

		this.ficheroDatos = file;
	}

	/**
	 * Carga los datos de clientes, llamadas y facturas
	 */
	public void cargarDatos() {
		try {
			FileInputStream fis = new FileInputStream(this.ficheroDatos);
			ObjectInputStream ois = new ObjectInputStream(fis);
			this.datos = (Datos) ois.readObject();

			System.out.println("Datos cargados con éxito desde: " + this.ficheroDatos.getAbsolutePath());
		} catch (FileNotFoundException e) {
			System.out.println("Error al cargar datos: No se encontro el fichero de datos");
		} catch (IOException e) {
			System.out.println("Error al cargar datos: No se pudo leer el fichero de datos");
		} catch (ClassNotFoundException e) {
			System.out.println("Error al cargar datos: No se encontro la clase");
		}

		if (this.vista != null) this.vista.actualizarVista();
	}

	/**
	 * Guarda los datos de clientes, llamadas y facturas
	 */
	public void guardarDatos() {
		try {
			if (this.ficheroDatos.createNewFile())
				System.out.println("El fichero de datos no existia y se ha creado\n");

			FileOutputStream fos = new FileOutputStream(this.ficheroDatos);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(this.datos);

			System.out.println("Datos guardados con éxito en: " + this.ficheroDatos.getAbsolutePath());
		} catch (FileNotFoundException e) {
			System.out.println("Error al guardar datos: No se encontro el fichero de datos");
		} catch (IOException e) {
			System.out.println("Error al guardar datos: No se pudo escribir en el fichero de datos");
		}
	}

	/**
	 * Borra todos los datos existentes
	 */
	public void limpiarDatos() { //FIXME Problema con los codigos
		this.datos = new Datos();

		if (this.vista != null) this.vista.actualizarVista();
	}

	/**
	 * Comprueba si un dato es valido, comprovando que el coincide con el patron del tipo de dato
	 *
	 * @param dato     El dato
	 * @param tipoDato El tipo de dato
	 *
	 * @return {@code true} si coincide con el patron de tipo de dato, {@code false} en caso contrario
	 */
	public static boolean esDatoValido(String dato, TipoDato tipoDato) {
		return dato.matches(tipoDato.getFormato());
	}
}
