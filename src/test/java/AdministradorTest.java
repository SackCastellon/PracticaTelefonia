import es.uji.al341823.telefonia.api.Administrador;
import es.uji.al341823.telefonia.clientes.Cliente;
import es.uji.al341823.telefonia.clientes.DireccionPostal;
import es.uji.al341823.telefonia.clientes.Particular;
import es.uji.al341823.telefonia.facturacion.TarifaTelefonica;
import es.uji.www.GeneradorDatosINE;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.Random;

/**
 * Created by Juanjo on 22/2/2017.
 */
public class AdministradorTest {

	private static GeneradorDatosINE generador;
	private static LinkedList<Cliente> clientes;
	private static Random rand;

	@BeforeClass
	public static void first() {
		generador = new GeneradorDatosINE();
		clientes = new LinkedList<>();
		rand = new Random();
	}

	@Test
	public void altaClientes() {
		for (int i = 0; i < 100; i++) {

			String prov = generador.getProvincia();
			DireccionPostal direccion = new DireccionPostal(12100, prov, generador.getPoblacion(prov));
			String nombre = generador.getNombre();
			Cliente cliente = new Particular(nombre, generador.getApellido(), generador.getNIF(), direccion, nombre + "@uji.es", LocalDateTime.now(), new TarifaTelefonica(rand.nextFloat()));

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
