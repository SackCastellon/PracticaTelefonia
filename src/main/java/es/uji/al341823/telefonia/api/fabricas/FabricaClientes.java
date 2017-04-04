package es.uji.al341823.telefonia.api.fabricas;

import es.uji.al341823.telefonia.clientes.Cliente;
import es.uji.al341823.telefonia.clientes.Direccion;
import es.uji.al341823.telefonia.clientes.Empresa;
import es.uji.al341823.telefonia.clientes.Particular;
import es.uji.al341823.telefonia.facturacion.tarifas.Tarifa;

import java.time.LocalDateTime;

/**
 * @author Juanjo González (al341823)
 * @author David Agost (al341819)
 * @since 0.3
 */
public class FabricaClientes {

	public Cliente getParticular(String nombre, String apellidos, String nif, Direccion direccion, String email, LocalDateTime fecha, Tarifa tarifa) {
		return new Particular(nombre, apellidos, nif, direccion, email, fecha, tarifa);
	}

	public Cliente getEmpresa(String nombre, String nif, Direccion direccion, String email, LocalDateTime fecha, Tarifa tarifa) {
		return new Empresa(nombre, nif, direccion, email, fecha, tarifa);
	}
}
