package es.uji.al341823.telefonia.api.fabricas;

import es.uji.al341823.telefonia.clientes.Cliente;
import es.uji.al341823.telefonia.clientes.Direccion;
import es.uji.al341823.telefonia.clientes.Empresa;
import es.uji.al341823.telefonia.clientes.Particular;
import es.uji.al341823.telefonia.facturacion.tarifas.Tarifa;

import java.time.LocalDateTime;

/**
 * Clase <i>Factory</i> para los clientes:
 * <ul>
 * <li>Clientes Particulares</li>
 * <li>Clientes Empresas</li>
 * </ul>
 *
 * @author Juanjo González (al341823)
 * @author David Agost (al341819)
 * @since 0.3
 */
public class FabricaClientes {

	/**
	 * Crea un cliente particular con los datos especificados
	 *
	 * @param nombre     Nombre del particular
	 * @param apellidos  Apellidos del particular
	 * @param nif        NIF del particular
	 * @param direccion  Dirección del particular
	 * @param email      Email del particular
	 * @param fecha      Fecha de alta del particular
	 * @param tarifaBase Tarifa base del particular
	 *
	 * @return El cliente con los datos especificados
	 */
	public Cliente getParticular(String nombre, String apellidos, String nif, Direccion direccion, String email, LocalDateTime fecha, Tarifa tarifaBase) {
		return new Particular(nombre, apellidos, nif, direccion, email, fecha, tarifaBase);
	}

	/**
	 * Crea un cliente empresa con los datos especificados
	 *
	 * @param nombre     Nombre de la empresa
	 * @param nif        NIF de la empresa
	 * @param direccion  Dirección de la empresa
	 * @param email      Email de la empresa
	 * @param fecha      Fecha de alta de la empresa
	 * @param tarifaBase Tarifa base de la empresa
	 *
	 * @return El cliente con los datos especificados
	 */
	public Cliente getEmpresa(String nombre, String nif, Direccion direccion, String email, LocalDateTime fecha, Tarifa tarifaBase) {
		return new Empresa(nombre, nif, direccion, email, fecha, tarifaBase);
	}
}
