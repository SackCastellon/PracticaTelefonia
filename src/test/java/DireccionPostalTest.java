import es.uji.al341823.telefonia.clientes.DireccionPostal;
import es.uji.www.GeneradorDatosINE;
import org.junit.Assert;
import org.junit.Test;

import java.util.Random;

/**
 * @author David Agost (al341819)
 * @since 0.1
 */
public class DireccionPostalTest {
    private final static GeneradorDatosINE generador = new GeneradorDatosINE();
    private final static Random rand = new Random();
    private String prov = generador.getProvincia();
    private String poblacion = generador.getPoblacion(prov);
    private int codpostal = rand.nextInt(5);
    private DireccionPostal direccion = new DireccionPostal(codpostal, prov, poblacion);

    @Test
    public void getCodigoPostalTest(){
        Assert.assertEquals(codpostal,direccion.getCodigoPostal());
    }

    @Test
    public void getProvinciaTest(){
        Assert.assertEquals(prov,direccion.getProvincia());
    }

    @Test
    public void getPoblacionTest(){
        Assert.assertEquals(poblacion,direccion.getPoblacion());
    }
}
