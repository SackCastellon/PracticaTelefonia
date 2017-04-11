import es.uji.al341823.telefonia.clientes.Direccion;
import es.uji.www.GeneradorDatosINE;
import org.junit.Assert;
import org.junit.Test;

import java.util.Random;

/**
 * @author David Agost (al341819)
 * @since 0.1
 */
public class DireccionTest {

	private static final Random rand = new Random();
	private static final GeneradorDatosINE generador = new GeneradorDatosINE();

	private final int codpostal = rand.nextInt(99999);
	private final String prov = generador.getProvincia();
	private final String poblacion = generador.getPoblacion(this.prov);

	private final Direccion direccion = new Direccion(this.codpostal, this.prov, this.poblacion);

	@Test
	public void getCodigoPostalTest() {
		Assert.assertEquals(this.codpostal, this.direccion.getCodigoPostal());
	}

	@Test
	public void getProvinciaTest() {
		Assert.assertEquals(this.prov, this.direccion.getProvincia());
	}

	@Test
	public void getPoblacionTest() {
		Assert.assertEquals(this.poblacion, this.direccion.getPoblacion());
	}
}
