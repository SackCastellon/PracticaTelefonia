/*
 * Copyright (c) 2017. Esta obra está sujeta a la licencia Reconocimiento 4.0 Internacional de Creative Commons.
 * Para ver una copia de esta licencia, visite http://creativecommons.org/licenses/by/4.0/.
 */

import es.uji.al341823.telefonia.facturacion.tarifas.Tarifa;
import es.uji.al341823.telefonia.facturacion.tarifas.TarifaBasica;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Random;

/**
 * @author David Agost (al341819)
 * @since 0.1
 */
public class TarifaTest {
	private static float precio;
	private static Tarifa tarifa;

	@BeforeClass
	public static void first() {
		Random rand = new Random();
		precio = rand.nextFloat();

		tarifa = new TarifaBasica(precio);
	}

	@Test
	public void getPrecioTest() {
		Assert.assertEquals(precio, tarifa.getPrecio(), 0);
	}
}
