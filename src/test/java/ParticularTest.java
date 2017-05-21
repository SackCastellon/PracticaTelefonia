/*
 * Copyright (c) 2017. Esta obra está sujeta a la licencia Reconocimiento 4.0 Internacional de Creative Commons.
 * Para ver una copia de esta licencia, visite http://creativecommons.org/licenses/by/4.0/.
 */

import es.uji.al341823.telefonia.api.AdministradorDatos;
import es.uji.al341823.telefonia.api.excepciones.FechaNoValidaExcepcion;
import es.uji.al341823.telefonia.api.excepciones.ObjetoNoExisteException;
import es.uji.al341823.telefonia.api.excepciones.ObjetoYaExisteException;
import es.uji.al341823.telefonia.clientes.Direccion;
import es.uji.al341823.telefonia.clientes.Particular;
import es.uji.al341823.telefonia.facturacion.Factura;
import es.uji.al341823.telefonia.facturacion.tarifas.Tarifa;
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
 * @author David Agost (al341819)
 * @since 0.1
 */
public class ParticularTest {
	private static Random rand;
	private static String nombre;
	private static String apellidos;
	private static String NIF;
	private static String email;
	private static LocalDateTime fecha;
	private static LocalDateTime fechaUltimaEmision;
	private static Tarifa tarifa;
	private static Direccion direccion;
	private static int duracionLlamadas;
	private static LinkedList<Integer> llamadas;
	private static LinkedList<Factura> facturas;
	private static Particular cliente;


	@BeforeClass
	public static void first() throws ObjetoYaExisteException {
		GeneradorDatosINE generador = new GeneradorDatosINE();
		rand = new Random();

		nombre = generador.getNombre();
		apellidos = generador.getApellido() + ' ' + generador.getApellido();
		NIF = generador.getNIF();
		email = nombre + "@uji.es";
		fecha = LocalDateTime.now();
		fechaUltimaEmision = LocalDateTime.of(2016, 12, 28, 2, 32);
		tarifa = new TarifaBasica(rand.nextFloat());

		String prov = generador.getProvincia();
		direccion = new Direccion(12100, prov, generador.getPoblacion(prov));

		duracionLlamadas = rand.nextInt();

		llamadas = new LinkedList<>();
		facturas = new LinkedList<>();

		cliente = new Particular(nombre, apellidos, NIF, direccion, email, fecha, tarifa);

		AdministradorDatos.addCliente(cliente);
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
		Tarifa tarifaNueva = new TarifaBasica(rand.nextFloat());
		cliente.setTarifa(tarifaNueva);
		tarifa = tarifaNueva;
		Assert.assertEquals(tarifaNueva, cliente.getTarifa());
	}

	@Test
	public void altaLlamadaTest() throws ObjetoYaExisteException, ObjetoNoExisteException {
		for (int i = 0; i < 100; i++) {
			Llamada llamada = new Llamada(Integer.toString(rand.nextInt(5)), Integer.toString(rand.nextInt(5)), fecha, rand.nextInt(5));
			llamadas.add(llamada.getCodigo());
			AdministradorDatos.addLlamada(cliente.getNif(),llamada);
		}
		Assert.assertEquals(llamadas.size(), cliente.getCodigosLlamadas().size());
	}

	@Test
	public void getLlamadasTest() throws ObjetoYaExisteException, ObjetoNoExisteException {
		for (int i = 0; i < 100; i++) {
			Llamada llamada = new Llamada(Integer.toString(rand.nextInt(5)), Integer.toString(rand.nextInt(5)), fecha, rand.nextInt(5));
			llamadas.add(llamada.getCodigo());
			AdministradorDatos.addLlamada(cliente.getNif(),llamada);
		}
		Assert.assertArrayEquals(llamadas.toArray(), cliente.getCodigosLlamadas().toArray());
	}

	@Test
	public void emitirFacturaTest() throws FechaNoValidaExcepcion, ObjetoNoExisteException {
		for (int i = 0; i < 100; i++) {
			Factura factura = new Factura(tarifa, fechaUltimaEmision, fecha, duracionLlamadas);
			facturas.add(factura);
			AdministradorDatos.addFactura(cliente.getNif());
		}
		Assert.assertEquals(facturas.size(), cliente.getCodigosFacturas().size());
	}

	@Test
	public void getApellidosTest() {
		Assert.assertEquals(apellidos, cliente.getApellidos());
	}

}
