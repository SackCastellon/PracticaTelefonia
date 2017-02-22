package es.uji.al341823.telefonia;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Juanjo on 21/2/2017.
 */
public class Empresa implements IFecha {

	private String nombre;
	private String nif;
	private Direccion direccion;
	private String email;
	private LocalDateTime fechaAlta;
	private Tarifa tarifa;

	private final LinkedList<Llamada> llamadas = new LinkedList<>();

	public Empresa(String nombre, String nif, Direccion direccion, String email, LocalDateTime fechaAlta, Tarifa tarifa) {
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

	public Tarifa setTarifa(Tarifa tarifa) {
		Tarifa antiguaTarifa = this.tarifa;
		this.tarifa = tarifa;
		return antiguaTarifa;
	}

	public Tarifa getTarifa() {
		return this.tarifa;
	}

	public void altaLlamada(Llamada llamada) {
		llamadas.add(llamada);
	}

	public List<Llamada> getLlamadas() {
		return (List<Llamada>) llamadas.clone();
	}
}
