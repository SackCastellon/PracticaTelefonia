import es.uji.al341823.telefonia.api.Administrador;
import es.uji.al341823.telefonia.cliente.Cliente;
import es.uji.al341823.telefonia.cliente.Direccion;
import es.uji.al341823.telefonia.cliente.Particular;
import es.uji.al341823.telefonia.facturacion.Tarifa;
import es.uji.www.GeneradorDatosINE;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.Random;

/**
 * Created by Juanjo on 22/2/2017.
 */
public class AdministradorTest {

	private final static GeneradorDatosINE generador = new GeneradorDatosINE();
	private final static LinkedList<Cliente> clientes = new LinkedList<>();
	private final static Random rand = new Random();

	@Test
	public void altaClientes() {
		for (int i = 0; i < 100; i++) {

			String prov = generador.getProvincia();
			Direccion direccion = new Direccion(12100, prov, generador.getPoblacion(prov));
			String nombre = generador.getNombre();
			Cliente cliente = new Particular(nombre, generador.getApellido(), generador.getNIF(), direccion, nombre + "@uji.es", LocalDateTime.now(), new Tarifa(rand.nextInt(5)));

			clientes.add(cliente);
			Administrador.altaCliente(cliente);
		}

		Assert.assertEquals(clientes.size(), Administrador.getClientes().size());
	}

	@Test
	public void bajaClientes() {
		for (int i = 0; i < 20; i++) {
			int a = rand.nextInt(clientes.size());
			Administrador.bajaCliente(clientes.get(a).getNif());
			clientes.remove(a);
		}

		Assert.assertEquals(clientes.size(), Administrador.getClientes().size());
	}

	@Test
	public void bajaClienteNoValido() {
		Administrador.bajaCliente("NifNoValido");

		Assert.assertEquals(clientes.size(), Administrador.getClientes().size());
	}

}
