import es.uji.al341823.telefonia.api.Administrador;
import es.uji.al341823.telefonia.cliente.Cliente;
import es.uji.al341823.telefonia.cliente.Direccion;
import es.uji.al341823.telefonia.cliente.Particular;
import es.uji.al341823.telefonia.facturacion.Tarifa;
import es.uji.al341823.telefonia.cliente.Llamada;
import es.uji.www.GeneradorDatosINE;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.Random;

/**
 * Created by al341819 on 28/02/17.
 */
public class ParticularTest {

    private final static GeneradorDatosINE generador = new GeneradorDatosINE();
    private final static Random rand = new Random();
    String nombre = generador.getNombre();
    String apellidos = generador.getApellido() + ' ' + generador.getApellido();
    String NIF = generador.getNIF();
    String prov = generador.getProvincia();
    String email = nombre + "@uji.es";
    LocalDateTime fecha=LocalDateTime.now();
    Tarifa tarifa= new Tarifa(rand.nextInt(5));
    Direccion direccion = new Direccion(12100, prov, generador.getPoblacion(prov));
    private final static LinkedList<Llamada> llamadas = new LinkedList<>();
    private final static LinkedList<String> info = new LinkedList<>();


    Particular cliente = new Particular(nombre, apellidos, NIF, direccion, email, fecha, tarifa);

    @Test
    public void getNombreTest(){
        Assert.assertEquals(nombre,cliente.getNombre());
    }

    @Test
    public void getNifTest(){
        Assert.assertEquals(NIF,cliente.getNif());
    }

    @Test
    public void getDireccionTest(){
        Assert.assertEquals(direccion,cliente.getDireccion());
    }

    @Test
    public void getEmailTest(){
        Assert.assertEquals(email,cliente.getEmail());
    }

    @Test
    public void getFechaTest(){
        Assert.assertEquals(fecha,cliente.getFecha());
    }

    @Test
    public void getTarifaTest(){
        Assert.assertEquals(tarifa,cliente.getTarifa());
    }

    @Test
    public void setTarifaTest(){
        Tarifa tarifaNueva= new Tarifa(rand.nextInt(5));
        cliente.setTarifa(tarifaNueva);
        Assert.assertEquals(tarifaNueva,cliente.getTarifa());
    }

    @Test
    public void altaLlamadaTest(){
        for (int i = 0; i < 100; i++) {
            Llamada llamada= new Llamada(Integer.toString(rand.nextInt(5)),Integer.toString(rand.nextInt(5)),fecha,rand.nextInt(5));
            llamadas.add(llamada);
            cliente.altaLlamada(llamada);
        }
        Assert.assertEquals(llamadas.size(), cliente.getLlamadas().size());
    }

    @Test
    public void getLlamadasTest(){
        for (int i = 0; i < 100; i++) {
            Llamada llamada= new Llamada(Integer.toString(rand.nextInt(5)),Integer.toString(rand.nextInt(5)),fecha,rand.nextInt(5));
            llamadas.add(llamada);
            cliente.altaLlamada(llamada);
        }
        Assert.assertEquals(llamadas, cliente.getLlamadas());
    }

    @Test
    public void getInformacionTest(){
        info.add("Nombre: " + nombre);
        info.add("NIF: " + NIF);
        info.add("Dirección: " + direccion.toString());
        info.add("Email: " + email);
        info.add("Fecha: " + fecha.toString());
        info.add("Tarifa: " + tarifa.toString());
        info.add(1, "Apellidos: " + apellidos);
        Assert.assertEquals(info, cliente.getInformacion());
    }

    @Test
    public void getApellidosTest(){
        Assert.assertEquals(apellidos,cliente.getApellidos());
    }

}
