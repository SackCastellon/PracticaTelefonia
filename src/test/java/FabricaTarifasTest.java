/*
 * Copyright (c) 2017. Esta obra está sujeta a la licencia Reconocimiento 4.0 Internacional de Creative Commons.
 * Para ver una copia de esta licencia, visite http://creativecommons.org/licenses/by/4.0/.
 */

import es.uji.al341823.telefonia.api.fabricas.FabricaTarifas;
import es.uji.al341823.telefonia.api.fabricas.TipoTarifa;
import es.uji.al341823.telefonia.facturacion.tarifas.TarifaBasica;
import es.uji.al341823.telefonia.facturacion.tarifas.TarifaDiasSemana;
import es.uji.al341823.telefonia.facturacion.tarifas.TarifaHoras;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Created by al341819 on 4/04/17.
 */
public class FabricaTarifasTest {

	private static FabricaTarifas fabricaTarifas;

	@BeforeClass
	public static void first() {
		fabricaTarifas = new FabricaTarifas();
	}

	@Test
	public void getTarifaBaseTest() {
		Assert.assertEquals(new TarifaBasica(0.15f), fabricaTarifas.getTarifaBase(TipoTarifa.Base.BASICA));
	}

	@Test
	public void getTarifaExtraTardesTest() {
		Assert.assertEquals(new TarifaHoras(new TarifaBasica(0.15f), 0.05f, 16, 20), fabricaTarifas.getTarifaExtra(new TarifaBasica(0.15f), TipoTarifa.Extra.TARDES));
	}

	@Test
	public void getTarifaExtraDomingosTest() {
		Assert.assertEquals(new TarifaDiasSemana(new TarifaBasica(0.15f), 0, 7), fabricaTarifas.getTarifaExtra(new TarifaBasica(0.15f), TipoTarifa.Extra.DOMINGOS));
	}
}
