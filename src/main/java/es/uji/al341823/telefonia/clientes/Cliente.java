package es.uji.al341823.telefonia.clientes;

import es.uji.al341823.telefonia.api.IFecha;
import es.uji.al341823.telefonia.api.excepciones.FechaNoValidaExcepcion;
import es.uji.al341823.telefonia.facturacion.Factura;
import es.uji.al341823.telefonia.facturacion.tarifas.Tarifa;
import es.uji.al341823.telefonia.llamadas.Llamada;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.LinkedList;

/**
 * @author Juanjo González (al341823)
 * @author David Agost (al341819)
 * @since 0.1
 */
public abstract class Cliente implements IFecha, Serializable {

	private static final long serialVersionUID = -6698454540590960908L;

	/**
	 * Nombre del cliente
	 */
	private final String nombre;
	/**
	 * NIF del cliente
	 */
	private final String nif;
	/**
	 * Dirección del cliente
	 */
	private final Direccion direccion;
	/**
	 * Email del cliente
	 */
	private final String email;
	/**
	 * Fecha en la que se dió de alta el cliente
	 */
	private final LocalDateTime fechaAlta;
	/**
	 * Lista de llamadas que realizó el cliente
	 */
	private final LinkedList<Llamada> llamadas = new LinkedList<>();
	/**
	 * Lista de facturas correspondientes al cliente
	 */
	private final LinkedList<Factura> facturas = new LinkedList<>();
	/**
	 * Tarifa que tiene contratada el cliente
	 */
	private Tarifa tarifa;
	/**
	 * El ultimo dia que se emitió una factura, se usa para calcular el preiodo de facturación
	 */
	private LocalDateTime ultimaFacturacion;

	/**
	 * @param nombre    Nombre del cliente
	 * @param nif       NIF del cliente
	 * @param direccion Dirección del cliente
	 * @param email     Email del cliente
	 * @param fechaAlta Fecha de lata del cliente
	 * @param tarifa    Tarifa contratada por el cliente
	 */
	Cliente(String nombre, String nif, Direccion direccion, String email, LocalDateTime fechaAlta, Tarifa tarifa) {
		super();

		this.nombre = nombre;
		this.nif = nif;
		this.direccion = direccion;
		this.email = email;
		this.fechaAlta = fechaAlta;
		this.tarifa = tarifa;

		this.ultimaFacturacion = fechaAlta;
	}

	/**
	 * Devuelve el nombre del cliente
	 *
	 * @return Nombre del cliente
	 */
	public String getNombre() {
		return this.nombre;
	}

	/**
	 * Devuelve el NIF del cliente
	 *
	 * @return NIF del cliente
	 */
	public String getNif() {
		return this.nif;
	}

	/**
	 * Devuelve un objeto dirección con la información de la dirección del cliente
	 *
	 * @return Dirección del cliente
	 */
	public Direccion getDireccion() {
		return this.direccion;
	}

	/**
	 * Devuelve el email del cliente
	 *
	 * @return Email del cliente
	 */
	public String getEmail() {
		return this.email;
	}

	/**
	 * Devueve la fecha y la hora a la que el cliente se dió de alta
	 *
	 * @return Fecha de alta del cliente
	 */
	@Override
	public LocalDateTime getFecha() {
		return this.fechaAlta;
	}

	/**
	 * Devuelve la tarifa que tiene contratada el cliente en el momento actual
	 *
	 * @return Tarifa contratada
	 */
	public Tarifa getTarifa() {
		return this.tarifa;
	}

	/**
	 * Establece la nueva tarifa que tendrá contrada el cliente
	 *
	 * @param tarifa Nueva tarifa
	 */
	public void setTarifa(Tarifa tarifa) {
		this.tarifa = tarifa;
	}

	/**
	 * Da de alta una nueva llamada para el cliente
	 *
	 * @param llamada Nueva llamada
	 */
	public void altaLlamada(Llamada llamada) {
		this.llamadas.add(llamada);
	}

	/**
	 * Devuelve una lista de las llamadas de el cliente
	 *
	 * @return Lista de llamadas
	 */
	public LinkedList<Llamada> getLlamadas() {
		return new LinkedList<>(this.llamadas);
	}

	/**
	 * Emite una nueva factura para el cliente teniendo en cuenta la tarifa contatada en el momento y las llamadas
	 * realizadas
	 *
	 * @return La factura emitida
	 */
	public Factura emitirFactura() {
		LocalDateTime hoy = LocalDateTime.now();
		int duracionLlamadas = 0;

		for (Llamada llamada : this.llamadas) {
			LocalDateTime fecha = llamada.getFecha();
			if (fecha.isBefore(hoy) && fecha.isAfter(this.ultimaFacturacion))
				duracionLlamadas += llamada.getDuracionLlamada();
		}

		Factura factura;
		try {
			factura = new Factura(this.tarifa, this.ultimaFacturacion, hoy, duracionLlamadas);
		} catch (FechaNoValidaExcepcion e) {
			return null;
		}

		this.ultimaFacturacion = hoy;

		this.facturas.add(factura);
		return factura;
	}

	/**
	 * Devuelve una copia de la lista de las facturas del cliente
	 *
	 * @return Lista de facturas
	 */
	public LinkedList<Factura> getFacturas() {
		return new LinkedList<>(this.facturas);
	}

	/**
	 * Devuelve un <code>String</code> con toda la información del cliente
	 *
	 * @return Información del cliente
	 */
	@Override
	public String toString() {
		return "Cliente{" +
				"nombre='" + this.nombre + '\'' +
				", nif='" + this.nif + '\'' +
				", dir=" + this.direccion +
				", email='" + this.email + '\'' +
				", alta=" + this.fechaAlta +
				", tarifa=" + this.tarifa +
				'}';
	}
}
