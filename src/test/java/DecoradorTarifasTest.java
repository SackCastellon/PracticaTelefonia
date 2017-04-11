import es.uji.al341823.telefonia.api.fabricas.FabricaTarifas;
import es.uji.al341823.telefonia.api.fabricas.TipoTarifa;
import es.uji.al341823.telefonia.facturacion.tarifas.Tarifa;
import es.uji.al341823.telefonia.facturacion.tarifas.TarifaBasica;
import es.uji.al341823.telefonia.facturacion.tarifas.TarifaExtra;
import es.uji.al341823.telefonia.llamadas.Llamada;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Random;

import static com.sun.org.apache.xerces.internal.util.PropertyState.is;

/**
 * Created by al341819 on 11/04/17.
 */
public class DecoradorTarifasTest {

    private static FabricaTarifas fabricaTarifas;
    TarifaExtra tarifa;
    private static String numeroOrigen;
    private static String numeroDestino;
    private static LocalDateTime fechaLlamada1;
    private static LocalDateTime fechaLlamada2;
    private static LocalDateTime fechaLlamada3;
    private static int duracionLlamada;
    private static Llamada llamada1;
    private static Llamada llamada2;
    private static Llamada llamada3;


    @BeforeClass
    public static void first() {
        fabricaTarifas = new FabricaTarifas();
        Random rand = new Random();

        numeroOrigen = Integer.toString(rand.nextInt(5));
        numeroDestino = Integer.toString(rand.nextInt(5));
        fechaLlamada1 = LocalDateTime.of(2017, 4, 11, 2, 30);
        fechaLlamada2 = LocalDateTime.of(2017, 4, 11, 17, 30);
        fechaLlamada3 = LocalDateTime.of(2017, 4, 9, 2, 30);
        duracionLlamada = 20;

        llamada1 = new Llamada(numeroOrigen, numeroDestino, fechaLlamada1, duracionLlamada);
        llamada2 = new Llamada(numeroOrigen, numeroDestino, fechaLlamada2, duracionLlamada);
        llamada3 = new Llamada(numeroOrigen, numeroDestino, fechaLlamada3, duracionLlamada);

    }


    @Test
    public void getTarifaBaseTest() {
        tarifa = fabricaTarifas.getTarifaExtra(new TarifaBasica(0.15f), TipoTarifa.Extra.TARDES);
        Assert.assertEquals(tarifa.getCosteLlamada(llamada1), is(0.15f*20));
        Assert.assertEquals(tarifa.getCosteLlamada(llamada2), is(0.05f*20));
        Assert.assertEquals(tarifa.getCosteLlamada(llamada3), is(0.05f*20));

        tarifa = fabricaTarifas.getTarifaExtra(new TarifaBasica(0.15f), TipoTarifa.Extra.DOMINGOS);
        Assert.assertEquals(tarifa.getCosteLlamada(llamada1), is(0.15f*20));
        Assert.assertEquals(tarifa.getCosteLlamada(llamada2), is(0.05f*20));
        Assert.assertEquals(tarifa.getCosteLlamada(llamada3), is(0));
    }

}
