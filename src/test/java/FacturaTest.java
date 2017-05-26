/*
 * Copyright (c) 2017. Esta obra está sujeta a la licencia Reconocimiento 4.0 Internacional de Creative Commons.
 * Para ver una copia de esta licencia, visite http://creativecommons.org/licenses/by/4.0/.
 */

import es.uji.al341823.telefonia.api.AdministradorDatos;
import es.uji.al341823.telefonia.api.excepciones.FechaNoValidaExcepcion;
import es.uji.al341823.telefonia.facturacion.Factura;
import es.uji.al341823.telefonia.facturacion.tarifas.Tarifa;
import es.uji.al341823.telefonia.facturacion.tarifas.TarifaBasica;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Random;

/**
 * @author David Agost (al341819)
 * @since 0.1
 */
public class FacturaTest {

	private static final AdministradorDatos admin = new AdministradorDatos();

	private static int codigo;
	private static Tarifa tarifa;
	private static LocalDateTime fechaEmision;
	private static long periodoFactuacion;
	private static float importe;
	private static Factura factura;

	@BeforeClass
	public static void first() throws FechaNoValidaExcepcion {
		Random rand = new Random();

		int codigoUnico = 0;
//		codigo = codigoUnico++;

		tarifa = new TarifaBasica(rand.nextFloat());

		fechaEmision = LocalDateTime.now();
		LocalDateTime fechaUltimaEmision = LocalDateTime.of(2016, 12, 28, 2, 32);
		periodoFactuacion = Duration.between(fechaUltimaEmision, fechaEmision).toDays();

		int duracionLlamadas = rand.nextInt();
		importe = tarifa.getPrecio() * duracionLlamadas;

		factura = new Factura(admin.getNextCodigoFactura(), tarifa, fechaUltimaEmision, fechaEmision, duracionLlamadas);
	}

	//@Test
	public void getCodigoTest() {
		Assert.assertEquals(codigo, factura.getCodigo());
	}

	@Test
	public void getTarifaTest() {
		Assert.assertEquals(tarifa, factura.getTarifa());
	}

	@Test
	public void getPeriodoFactuacionTest() {
		Assert.assertEquals(periodoFactuacion, factura.getPeriodoFactuacion());
	}

	@Test
	public void getImporteTest() {
		Assert.assertEquals(importe, factura.getImporte(), 0);
	}

	@Test
	public void getFecha() {
		Assert.assertEquals(fechaEmision, factura.getFecha());
	}
}
