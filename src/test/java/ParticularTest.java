import es.uji.al341823.telefonia.cliente.Direccion;
import es.uji.al341823.telefonia.cliente.Llamada;
import es.uji.al341823.telefonia.cliente.Particular;
import es.uji.al341823.telefonia.facturacion.Tarifa;
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

	private static GeneradorDatosINE generador;
	private static Random rand;
	private static String nombre;
	private static String apellidos;
	private static String NIF;
	private static String prov;
	private static String email;
	private static LocalDateTime fecha;
	private static Tarifa tarifa;
	private static Direccion direccion;
	private static LinkedList<Llamada> llamadas;
	private static LinkedList<String> info;
	private static Particular cliente;


	@BeforeClass
	public static void first() {
		generador = new GeneradorDatosINE();
		rand = new Random();

		nombre = generador.getNombre();
		apellidos = generador.getApellido() + ' ' + generador.getApellido();
		NIF = generador.getNIF();
		prov = generador.getProvincia();
		email = nombre + "@uji.es";
		fecha = LocalDateTime.now();
		tarifa = new Tarifa(rand.nextInt(5));
		direccion = new Direccion(12100, prov, generador.getPoblacion(prov));

		llamadas = new LinkedList<>();
		info = new LinkedList<>();

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
		Tarifa tarifaNueva = new Tarifa(rand.nextInt(5));
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
