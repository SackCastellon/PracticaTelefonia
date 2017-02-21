package es.uji.al341823.telefonia;

import java.time.LocalDateTime;

/**
 * Created by al341819 on 21/02/17.
 */
public class Llamada {

	/** Numero desde el que se ha realizado la llamada */
	private String numeroOrigen;
	/** Numero al que se ha realizado la llamada */
	private String numeroDestino;
	/** Fecha y hora a la que se realizó la llamada */
	private LocalDateTime fechaLlamada;
	/** Duración de la llamada en segundos */
	private int duracionLlamada;

	public Llamada(String numeroOrigen, String numeroDestino, LocalDateTime fechaLlamada, int duracionLlamada) {
		this.numeroOrigen = numeroOrigen;
		this.numeroDestino = numeroDestino;
		this.fechaLlamada = fechaLlamada;
		this.duracionLlamada = duracionLlamada;
	}

	public String getNumeroOrigen() {
		return numeroOrigen;
	}

	public String getNumeroDestino() {
		return numeroDestino;
	}

	public LocalDateTime getFechaLlamada() {
		return fechaLlamada;
	}

	public int getDuracionLlamada() {
		return duracionLlamada;
	}
}