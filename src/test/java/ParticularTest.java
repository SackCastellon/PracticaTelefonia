import es.uji.al341823.telefonia.clientes.DireccionPostal;
import es.uji.al341823.telefonia.llamadas.Llamada;
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
 * Created by al341819 on 28/02/17.
 */
public class ParticularTest {

	private static Random rand;
	private static String nombre;
	private static String apellidos;
	private static String NIF;
	private static String email;
	private static LocalDateTime fecha;
	private static TarifaTelefonica tarifa;
	private static DireccionPostal direccion;
	private static LinkedList<Llamada> llamadas;
	private static Particular cliente;


	@BeforeClass
	public static void first() {
		GeneradorDatosINE generador = new GeneradorDatosINE();
		rand = new Random();

		nombre = generador.getNombre();
		apellidos = generador.getApellido() + ' ' + generador.getApellido();
		NIF = generador.getNIF();
		String prov = generador.getProvincia();
		email = nombre + "@uji.es";
		fecha = LocalDateTime.now();
		tarifa = new TarifaTelefonica(rand.nextInt(5));
		direccion = new DireccionPostal(12100, prov, generador.getPoblacion(prov));

		llamadas = new LinkedList<>();
		LinkedList<String> info = new LinkedList<>();

		cliente = new Particular(nombre, apellidos, NIF, direccion, email, fecha, tarifa);
	}

	@Test
	public void getNombreTest() {
		Assert.assertEquals(nombre, cliente.getNombre());
	}

	@Test
	public void getNifTest() {
		Assert.assertEquals(NIF, cliente.getNif());
	}

	@Test
	public void getDireccionTest() {
		Assert.assertEquals(direccion, cliente.getDireccion());
	}

	@Test
	public void getEmailTest() {
		Assert.assertEquals(email, cliente.getEmail());
	}

	@Test
	public void getFechaTest() {
		Assert.assertEquals(fecha, cliente.getFecha());
	}

	@Test
	public void getTarifaTest() {
		Assert.assertEquals(tarifa, cliente.getTarifa());
	}

	@Test
	public void setTarifaTest() {
		TarifaTelefonica tarifaNueva = new TarifaTelefonica(rand.nextInt(5));
		cliente.setTarifa(tarifaNueva);
		tarifa = tarifaNueva;
		Assert.assertEquals(tarifaNueva, cliente.getTarifa());
	}

	@Test
	public void altaLlamadaTest() {
		for (int i = 0; i < 100; i++) {
			Llamada llamada = new Llamada(Integer.toString(rand.nextInt(5)), Integer.toString(rand.nextInt(5)), fecha, rand.nextInt(5));
			llamadas.add(llamada);
			cliente.altaLlamada(llamada);
		}
		Assert.assertEquals(llamadas.size(), cliente.getLlamadas().size());
	}

	@Test
	public void getLlamadasTest() {
		for (int i = 0; i < 100; i++) {
			Llamada llamada = new Llamada(Integer.toString(rand.nextInt(5)), Integer.toString(rand.nextInt(5)), fecha, rand.nextInt(5));
			llamadas.add(llamada);
			cliente.altaLlamada(llamada);
		}
		Assert.assertArrayEquals(llamadas.toArray(), cliente.getLlamadas().toArray());
	}

	// Este test no es necesario
	/*
	@Test
	public void getInformacionTest() {
		info.add("Nombre: " + nombre);
		info.add("NIF: " + NIF);
		info.add("Dirección: " + direccion.toString());
		info.add("Email: " + email);
		info.add("Fecha: " + fecha.toString());
		info.add("Tarifa: " + tarifa.toString());
		info.add(1, "Apellidos: " + apellidos);
		Assert.assertEquals(info, cliente.getInformacion());
	}*/

	@Test
	public void getApellidosTest() {
		Assert.assertEquals(apellidos, cliente.getApellidos());
	}

}
