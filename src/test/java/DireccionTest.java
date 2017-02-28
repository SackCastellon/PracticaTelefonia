import es.uji.al341823.telefonia.cliente.Direccion;
import es.uji.www.GeneradorDatosINE;
import org.junit.Assert;
import org.junit.Test;

import java.util.Random;

/**
 * Created by al341819 on 28/02/17.
 */
public class DireccionTest {
    private final static GeneradorDatosINE generador = new GeneradorDatosINE();
    private final static Random rand = new Random();
    String prov = generador.getProvincia();
    String poblacion = generador.getPoblacion(prov);
    int codpostal = rand.nextInt(5);
    Direccion direccion = new Direccion(codpostal, prov, poblacion);

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
