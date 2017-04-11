import es.uji.al341823.telefonia.api.AdministradorDatos;
import es.uji.al341823.telefonia.api.excepciones.FechaNoValidaExcepcion;
import es.uji.al341823.telefonia.clientes.Cliente;
import es.uji.al341823.telefonia.clientes.Direccion;
import es.uji.al341823.telefonia.clientes.Particular;
import es.uji.al341823.telefonia.facturacion.Factura;
import es.uji.al341823.telefonia.facturacion.tarifas.TarifaBasica;
import es.uji.al341823.telefonia.llamadas.Llamada;
import es.uji.www.GeneradorDatosINE;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.Random;


/**
 * Created by al341819 on 7/03/17.
 */
public class AdministradorDatosTest {
	private static Random rand;
	private static LocalDateTime fechaInicio;
	private static LocalDateTime fechaFinal;
	private static AdministradorDatos admin;
	private final GeneradorDatosINE generador = new GeneradorDatosINE();

	@BeforeClass
	public static void first() {

		rand = new Random();

		admin = new AdministradorDatos();

		fechaInicio = LocalDateTime.of(2013, 1, 1, 1, 1);
		fechaFinal = LocalDateTime.of(2016, 12, 31, 4, 30);

	}

	@Test
	public void extraerConjuntoTest() throws FechaNoValidaExcepcion {
		LinkedList<Cliente> clientes = new LinkedList<>();
		LinkedList<Cliente> clientesExtraidos = new LinkedList<>();
		LinkedList<Llamada> llamadas = new LinkedList<>();
		LinkedList<Llamada> llamadasExtraidas = new LinkedList<>();
		LinkedList<Factura> facturas = new LinkedList<>();
		LinkedList<Factura> facturasExtraidas = new LinkedList<>();

		// FIXME El generador de datos está roto 👎
		for (int i = 0; i < 100; i++) {
			String nombre = this.generador.getNombre();
			String prov = this.generador.getProvincia();

			Direccion direccion;
			while (true) {
				try {
					direccion = new Direccion(12100, prov, this.generador.getPoblacion(prov));
					break;
				} catch (Exception e) {
					prov = this.generador.getProvincia();
				}
			}

			LocalDateTime fecha = LocalDateTime.of(2010 + rand.nextInt(10), 1 + rand.nextInt(12), 1 + rand.nextInt(28), rand.nextInt(24), rand.nextInt(60));

			Cliente cliente = new Particular(nombre, this.generador.getApellido() + ' ' + this.generador.getApellido(), this.generador.getNIF(), direccion, nombre + "@uji.es", fecha, new TarifaBasica(rand.nextFloat()));
			clientes.add(cliente);

			if (cliente.getFecha().isAfter(fechaInicio) && cliente.getFecha().isBefore(fechaFinal))
				clientesExtraidos.add(cliente);

			Llamada llamada = new Llamada(Integer.toString(rand.nextInt(5)), Integer.toString(rand.nextInt(5)), fecha, rand.nextInt(5));
			llamadas.add(llamada);

			if (llamada.getFecha().isAfter(fechaInicio) && llamada.getFecha().isBefore(fechaFinal))
				llamadasExtraidas.add(llamada);

			Factura factura = new Factura(new TarifaBasica(rand.nextFloat()), fecha, LocalDateTime.of(2021, 12, 28, 2, 32), rand.nextInt());
			facturas.add(factura);

			if (factura.getFecha().isAfter(fechaInicio) && factura.getFecha().isBefore(fechaFinal))
				facturasExtraidas.add(factura);
		}

		Assert.assertEquals(AdministradorDatos.extraerConjunto(clientes, fechaInicio, fechaFinal), clientesExtraidos);
		Assert.assertEquals(AdministradorDatos.extraerConjunto(llamadas, fechaInicio, fechaFinal), llamadasExtraidas);
		Assert.assertEquals(AdministradorDatos.extraerConjunto(facturas, fechaInicio, fechaFinal), facturasExtraidas);
	}
}
