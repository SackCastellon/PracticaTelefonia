package es.uji.al341823.telefonia.cliente;

import es.uji.al341823.telefonia.IFecha;
import es.uji.al341823.telefonia.facturacion.Factura;
import es.uji.al341823.telefonia.facturacion.Tarifa;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.LinkedList;

/**
 * @author Juanjo González (al341823)
 * @author David Agost (al341819)
 * @since 0.1
 */
public class Cliente implements IFecha {

	/** Nombre del cliente */
	private String nombre;
	/** NIF del cliente */
	private final String nif;
	/** Dirección del cliente */
	private Direccion direccion;
	/** Email del cliente */
	private String email;
	/** Fecha en la que se dió de alta el cliente */
	private LocalDateTime fechaAlta;
	/** Tarifa que tiene contratada el cliente */
	private Tarifa tarifa;

	/** El ultimo dia que se emitió una factura, se usa para calcular el preiodo de facturación */
	private LocalDateTime ultimaFacturacion;

	/** Lista de llamadas que realizó el cliente */
	private final LinkedList<Llamada> llamadas = new LinkedList<>();
	/** Lista de facturas correspondientes al cliente */
	private final LinkedList<Factura> facturas = new LinkedList<>();

	/**
	 * @param nombre    Nombre del cliente
	 * @param nif       NIF del cliente
	 * @param direccion Dirección del cliente
	 * @param email     Email del cliente
	 * @param fechaAlta Fecha de lata del cliente
	 * @param tarifa    Tarifa contratada por el cliente
	 */
	protected Cliente(String nombre, String nif, Direccion direccion, String email, LocalDateTime fechaAlta, Tarifa tarifa) {
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
	public Tarifa getTarifa() { return this.tarifa; }

	/**
	 * Establece la nueva tarifa que tendrá contrada el cliente
	 *
	 * @param tarifa Nueva tarifa
	 */
	public void setTarifa(Tarifa tarifa) { this.tarifa = tarifa; }

	/**
	 * Da de alta una nueva llamada para el cliente
	 *
	 * @param llamada Nueva llamada
	 */
	public void altaLlamada(Llamada llamada) {
		llamadas.add(llamada);
	}

	/**
	 * Devuelve una lista de las llamadas de el cliente
	 *
	 * @return Lista de llamadas
	 */
	public Collection<Llamada> getLlamadas() {
		return (Collection<Llamada>) llamadas.clone();
	}

	/**
	 * Emite una nueva factura para el cliente teniendo en cuenta la tarifa contatada en el momento y las llamadas realizadas
	 *
	 * @return La factura emitida
	 */
	public Factura emitirFactura() {
		LocalDateTime hoy = LocalDateTime.now();
		int duracionLlamadas = 0;

		for (Llamada llamada : llamadas) {
			if (llamada.getFecha().isAfter(hoy))
				duracionLlamadas += llamada.getDuracionLlamada();
		}

		Factura factura = new Factura(tarifa, ultimaFacturacion, hoy, duracionLlamadas);

		facturas.add(factura);
		return factura;
	}

	/**
	 * Devuelve una copia de la lista de las facturas del cliente
	 *
	 * @return Lista de facturas
	 */
	public Collection<Factura> getFacturas() {
		return (Collection<Factura>) facturas.clone();
	}

	/**
	 * Devuelve un <code>String</code> con toda la información del cliente
	 *
	 * @return Información del cliente
	 */
	public String getInformacion() {
		String info = "";

		info += " - Nombre: " + getNombre() + '\n';
		info += " - NIF: " + getNif() + '\n';
		info += " - Dirección: " + getDireccion().toString() + '\n';
		info += " - Email: " + getEmail() + '\n';
		info += " - Fecha alta: " + getFecha().toString() + '\n';
		info += " - Tarifa: " + getTarifa().toString() + '\n';

		return info.toString();
	}
}
