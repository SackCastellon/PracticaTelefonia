package es.uji.al341823.telefonia.clientes;

import es.uji.al341823.telefonia.IFecha;
import es.uji.al341823.telefonia.facturacion.FacturaTelefonica;
import es.uji.al341823.telefonia.facturacion.TarifaTelefonica;
import es.uji.al341823.telefonia.llamadas.Llamada;

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
	private final String nombre;
	/** NIF del cliente */
	private final String nif;
	/** Dirección del cliente */
	private final DireccionPostal direccion;
	/** Email del cliente */
	private final String email;
	/** Fecha en la que se dió de alta el cliente */
	private final LocalDateTime fechaAlta;
	/** Tarifa que tiene contratada el cliente */
	private TarifaTelefonica tarifa;

	/** El ultimo dia que se emitió una factura, se usa para calcular el preiodo de facturación */
	private LocalDateTime ultimaFacturacion;

	/** Lista de llamadas que realizó el cliente */
	private final LinkedList<Llamada> llamadas = new LinkedList<>();
	/** Lista de facturas correspondientes al cliente */
	private final LinkedList<FacturaTelefonica> facturas = new LinkedList<>();

	/**
	 * @param nombre    Nombre del cliente
	 * @param nif       NIF del cliente
	 * @param direccion Dirección del cliente
	 * @param email     Email del cliente
	 * @param fechaAlta Fecha de lata del cliente
	 * @param tarifa    Tarifa contratada por el cliente
	 */
	protected Cliente(String nombre, String nif, DireccionPostal direccion, String email, LocalDateTime fechaAlta, TarifaTelefonica tarifa) {
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
	public DireccionPostal getDireccion() {
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
	public TarifaTelefonica getTarifa() { return this.tarifa; }

	/**
	 * Establece la nueva tarifa que tendrá contrada el cliente
	 *
	 * @param tarifa Nueva tarifa
	 */
	public void setTarifa(TarifaTelefonica tarifa) { this.tarifa = tarifa; }

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
	public FacturaTelefonica emitirFactura() {
		LocalDateTime hoy = LocalDateTime.now();
		int duracionLlamadas = 0;

		for (Llamada llamada : llamadas) {
			if (llamada.getFecha().isBefore(hoy) && llamada.getFecha().isAfter(ultimaFacturacion))
				duracionLlamadas += llamada.getDuracionLlamada();
		}

		FacturaTelefonica factura = new FacturaTelefonica(tarifa, ultimaFacturacion, hoy, duracionLlamadas);

		ultimaFacturacion = hoy;

		facturas.add(factura);
		return factura;
	}

	/**
	 * Devuelve una copia de la lista de las facturas del cliente
	 *
	 * @return Lista de facturas
	 */
	public Collection<FacturaTelefonica> getFacturas() {
		return (Collection<FacturaTelefonica>) facturas.clone();
	}

	/**
	 * Devuelve un <code>String</code> con toda la información del cliente
	 *
	 * @return Información del cliente
	 */
	public String obtenerInformacion() {
		String info = "";

		info += " - Nombre: " + getNombre() + '\n';
		info += " - NIF: " + getNif() + '\n';
		info += " - Dirección: " + getDireccion().toString() + '\n';
		info += " - Email: " + getEmail() + '\n';
		info += " - Fecha alta: " + getFecha().toString() + '\n';
		info += " - Tarifa: " + getTarifa().getPrecio() + " €/min\n";

		return info;
	}
}
