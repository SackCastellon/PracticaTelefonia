import es.uji.al341823.telefonia.api.fabricas.FabricaClientes;
import es.uji.al341823.telefonia.clientes.Direccion;
import es.uji.al341823.telefonia.clientes.Empresa;
import es.uji.al341823.telefonia.clientes.Particular;
import es.uji.al341823.telefonia.facturacion.tarifas.Tarifa;
import es.uji.al341823.telefonia.facturacion.tarifas.TarifaBasica;
import es.uji.www.GeneradorDatosINE;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Random;

/**
 * Created by al341819 on 4/04/17.
 */
public class FabricaClientesTest {

    private static Random rand;
    private static FabricaClientes fabricaClientes;
    private static String nombre;
    private static String apellidos;
    private static String NIF;
    private static Direccion direccion;
    private static String email;
    private static LocalDateTime fecha;
    private static Tarifa tarifa;
    private static Particular particular;
    private static Empresa empresa;

    @BeforeClass
    public static void first() {
        GeneradorDatosINE generador = new GeneradorDatosINE();
        rand = new Random();

        fabricaClientes=new FabricaClientes();

        nombre = generador.getNombre();
        apellidos = generador.getApellido() + ' ' + generador.getApellido();
        NIF = generador.getNIF();

        String prov = generador.getProvincia();
        direccion = new Direccion(12100, prov, generador.getPoblacion(prov));

        email = nombre + "@uji.es";
        fecha = LocalDateTime.now();
        tarifa = new TarifaBasica(rand.nextFloat());

        particular = new Particular(nombre, apellidos, NIF, direccion, email, fecha, tarifa);

        empresa = new Empresa(nombre, NIF, direccion, email, fecha, tarifa);
    }

    @Test
    public void getParticularTest(){ Assert.assertEquals(particular, fabricaClientes.getParticular(nombre, apellidos, NIF, direccion, email, fecha, tarifa)); }

    @Test
    public void getEmpresaTest(){ Assert.assertEquals(empresa, fabricaClientes.getEmpresa(nombre, NIF, direccion, email, fecha, tarifa)); }


}
