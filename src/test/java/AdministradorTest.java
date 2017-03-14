import es.uji.al341823.telefonia.clientes.Cliente;
import es.uji.al341823.telefonia.clientes.DireccionPostal;
import es.uji.al341823.telefonia.clientes.Particular;
import es.uji.al341823.telefonia.facturacion.FacturaTelefonica;
import es.uji.al341823.telefonia.facturacion.TarifaTelefonica;
import es.uji.al341823.telefonia.llamadas.Llamada;
import es.uji.www.GeneradorDatosINE;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.Random;

import static es.uji.al341823.telefonia.api.Administrador.extraerConjunto;

/**
 * Created by al341819 on 7/03/17.
 */
public class AdministradorTest {
    private static Random rand;
    private static String nombre;
    private static String prov;
    private static DireccionPostal direccion;
    private static LocalDateTime fecha;
    private static LocalDateTime fechaInicio;
    private static LocalDateTime fechaFinal;
    GeneradorDatosINE generador = new GeneradorDatosINE();

    @BeforeClass
    public static void first() {

        rand = new Random();

        fechaInicio = LocalDateTime.of(2013, 1, 1, 1, 1);
        fechaFinal = LocalDateTime.of(2016, 12, 31, 4, 30);

    }

    @Test
    public void extraerConjuntoTest() {
        LinkedList<Cliente> clientes = new LinkedList<>();
        LinkedList<Cliente> clientesExtraidos = new LinkedList<>();
        LinkedList<Llamada> llamadas = new LinkedList<>();
        LinkedList<Llamada> llamadasExtraidas = new LinkedList<>();
        LinkedList<FacturaTelefonica> facturas = new LinkedList<>();
        LinkedList<FacturaTelefonica> facturasExtraidas = new LinkedList<>();

        for (int i = 0; i < 100; i++) {
            nombre = generador.getNombre();
            prov = generador.getProvincia();
            direccion = new DireccionPostal(12100, prov, generador.getPoblacion(prov));
            fecha = LocalDateTime.of(2010 + rand.nextInt(10), 1 + rand.nextInt(12), 1 + rand.nextInt(28), rand.nextInt(24), rand.nextInt(60));

            Cliente cliente = new Particular(nombre, generador.getApellido() + ' ' + generador.getApellido(), generador.getNIF(), direccion, nombre + "@uji.es", fecha, new TarifaTelefonica(rand.nextFloat()));
            clientes.add(cliente);

            if (cliente.getFecha().isAfter(fechaInicio) && cliente.getFecha().isBefore(fechaFinal))
                clientesExtraidos.add(cliente);

            Llamada llamada = new Llamada(Integer.toString(rand.nextInt(5)), Integer.toString(rand.nextInt(5)), fecha, rand.nextInt(5));
            llamadas.add(llamada);

            if (llamada.getFecha().isAfter(fechaInicio) && llamada.getFecha().isBefore(fechaFinal))
                llamadasExtraidas.add(llamada);

            FacturaTelefonica factura = new FacturaTelefonica(new TarifaTelefonica(rand.nextFloat()), LocalDateTime.of(2021, 12, 28, 2, 32), fecha, rand.nextInt());
            facturas.add(factura);

            if (factura.getFecha().isAfter(fechaInicio) && factura.getFecha().isBefore(fechaFinal))
                facturasExtraidas.add(factura);
        }
        Assert.assertEquals(extraerConjunto(clientes, fechaInicio, fechaFinal), clientesExtraidos);
        Assert.assertEquals(extraerConjunto(llamadas, fechaInicio, fechaFinal), llamadasExtraidas);
        Assert.assertEquals(extraerConjunto(facturas, fechaInicio, fechaFinal), facturasExtraidas);
    }
}
