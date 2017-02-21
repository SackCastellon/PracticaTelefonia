package es.uji.al341823.telefonia;

import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Created by al341819 on 21/02/17.
 */
public class Llamada {
    private String numDestino;
    private LocalDateTime fechaLlamada;
    private LocalTime horaLlamada;
    private int duracion;

    public Llamada(String numDestino, LocalDateTime fechaLlamada, LocalTime horaLlamada, int duracion){
        this.numDestino = numDestino;
        this.fechaLlamada = fechaLlamada;
        this.horaLlamada = horaLlamada;
        this.duracion = duracion;
    }
}