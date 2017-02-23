package es.uji.al341823.telefonia.cliente;

import es.uji.al341823.telefonia.IFecha;
import es.uji.al341823.telefonia.facturacion.Tarifa;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Juanjo González (al341823)
 * @author David Agost (al341819)
 * @since 0.1
 */
public class Cliente implements IFecha {

	private String nombre;
	private String nif;
	private Direccion direccion;
	private String email;
	private LocalDateTime fechaAlta;
	private Tarifa tarifa;

	private final LinkedList<Llamada> llamadas = new LinkedList<>();
	private final LinkedList<Llamada> facturas = new LinkedList<>();

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
