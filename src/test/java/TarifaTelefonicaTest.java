import es.uji.al341823.telefonia.facturacion.TarifaTelefonica;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Random;

/**
 * Created by al341819 on 3/03/17.
 */
public class TarifaTelefonicaTest {
    private static Random rand;
    private static float precio;
    private static TarifaTelefonica tarifa;

    @BeforeClass
    public static void first() {
        rand = new Random();
        precio = rand.nextFloat();

        tarifa = new TarifaTelefonica(precio);
    }

    @Test
    public void getPrecioTest(){ Assert.assertEquals(precio, tarifa.getPrecio()); }
}
