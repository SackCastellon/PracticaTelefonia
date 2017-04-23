/*
 * Copyright (c) 2017. Esta obra está sujeta a la licencia Reconocimiento 4.0 Internacional de Creative Commons.
 * Para ver una copia de esta licencia, visite http://creativecommons.org/licenses/by/4.0/.
 */

import es.uji.al341823.telefonia.api.fabricas.FabricaTarifas;
import es.uji.al341823.telefonia.api.fabricas.TipoTarifa;
import es.uji.al341823.telefonia.facturacion.tarifas.TarifaBasica;
import es.uji.al341823.telefonia.facturacion.tarifas.TarifaExtra;
import es.uji.al341823.telefonia.llamadas.Llamada;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Random;

/**
 * Created by al341819 on 11/04/17.
 */
public class DecoradorTarifasTest {

	private static FabricaTarifas fabricaTarifas;
	private static Llamada llamada1;
	private static Llamada llamada2;
	private static Llamada llamada3;


	@BeforeClass
	public static void first() {
		fabricaTarifas = new FabricaTarifas();
		Random rand = new Random();

		String numeroOrigen = Integer.toString(rand.nextInt(5));
		String numeroDestino = Integer.toString(rand.nextInt(5));
		LocalDateTime fechaLlamada1 = LocalDateTime.of(2017, 4, 11, 2, 30);
		LocalDateTime fechaLlamada2 = LocalDateTime.of(2017, 4, 11, 17, 30);
		LocalDateTime fechaLlamada3 = LocalDateTime.of(2017, 4, 9, 2, 30);
		int duracionLlamada = 20;

		llamada1 = new Llamada(numeroOrigen, numeroDestino, fechaLlamada1, duracionLlamada);
		llamada2 = new Llamada(numeroOrigen, numeroDestino, fechaLlamada2, duracionLlamada);
		llamada3 = new Llamada(numeroOrigen, numeroDestino, fechaLlamada3, duracionLlamada);

	}


	@Test
	public void getTarifaBaseTest() {
		TarifaExtra tarifaTardes = fabricaTarifas.getTarifaExtra(new TarifaBasica(0.15f), TipoTarifa.Extra.TARDES);
		Assert.assertEquals(0.15f * 20, tarifaTardes.getCosteLlamada(llamada1), 0);
		Assert.assertEquals(0.05f * 20, tarifaTardes.getCosteLlamada(llamada2), 0);
		Assert.assertEquals(0.15f * 20, tarifaTardes.getCosteLlamada(llamada3), 0);

		TarifaExtra tarifaDomingos = fabricaTarifas.getTarifaExtra(new TarifaBasica(0.15f), TipoTarifa.Extra.DOMINGOS);
		Assert.assertEquals(0.15f * 20, tarifaDomingos.getCosteLlamada(llamada1), 0);
		Assert.assertEquals(0.15f * 20, tarifaDomingos.getCosteLlamada(llamada2), 0);
		Assert.assertEquals(0, tarifaDomingos.getCosteLlamada(llamada3), 0);
	}

}
