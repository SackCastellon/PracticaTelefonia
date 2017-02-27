package es.uji.al341823.telefonia.cliente;

import es.uji.al341823.telefonia.IFecha;
import es.uji.al341823.telefonia.facturacion.Factura;
import es.uji.al341823.telefonia.facturacion.Tarifa;

import java.time.LocalDateTime;
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
	private String nif;
	/** Dirección del cliente */
	private Direccion direccion;
	/** Email del cliente */
	private String email;
	/** Fecha en la que se dió de alta el cliente */
	private LocalDateTime fechaAlta;
	/** Tarifa que tiene contratada el cliente */
	private Tarifa tarifa;

	/** Lista de llamadas que realizó el cliente */
	private final LinkedList<Llamada> llamadas = new LinkedList<>();
	/** Lista de facturas correspondientes al cliente */
	private final LinkedList<Factura> facturas = new LinkedList<>();

	protected Cliente(String nombre, String nif, Direccion direccion, String email, LocalDateTime fechaAlta, Tarifa tarifa) {
		this.nombre = nombre;
		this.nif = nif;
		this.direccion = direccion;
		this.email = email;
		this.fechaAlta = fechaAlta;
		this.tarifa = tarifa;
	}

	public String getNombre() {
		return this.nombre;
	}

	public String getNif() {
		return this.nif;
	}

	public Direccion getDireccion() {
		return this.direccion;
	}

	public String getEmail() {
		return this.email;
	}

	@Override
	public LocalDateTime getFecha() {
		return this.fechaAlta;
	}

	public Tarifa getTarifa() { return this.tarifa; }

	public void setTarifa(Tarifa tarifa) { this.tarifa = tarifa; }

	public void altaLlamada(Llamada llamada) {
		llamadas.add(llamada);
	}

	public LinkedList<Llamada> getLlamadas() {
		return (LinkedList<Llamada>) llamadas.clone();
	}

	public void emitirFactura(int periodoFacturacion) {
		float importe = 0; //TODO
		Factura factura = new Factura(tarifa, LocalDateTime.now(), periodoFacturacion, importe);
	}

	public LinkedList<String> getInformacion() {
		LinkedList<String> info = new LinkedList<>();

		info.add("Nombre: " + getNombre());
		info.add("NIF: " + getNif());
		info.add("Dirección: " + getDireccion().toString());
		info.add("Email: " + getEmail());
		info.add("Fecha: " + getFecha().toString());
		info.add("Tarifa: " + getTarifa().toString());

		return info;
	}
}
