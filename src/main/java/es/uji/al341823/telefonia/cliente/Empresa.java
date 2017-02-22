package es.uji.al341823.telefonia.cliente;

import es.uji.al341823.telefonia.facturacion.Tarifa;

import java.time.LocalDateTime;

/**
 * @author Juanjo González (al341823)
 * @author David Agost (al341819)
 * @since 0.1
 */
public class Empresa extends Cliente {

	protected Empresa(String nombre, String nif, Direccion direccion, String email, LocalDateTime fechaAlta, Tarifa tarifa) {
		super(nombre, nif, direccion, email, fechaAlta, tarifa);
	}
}
