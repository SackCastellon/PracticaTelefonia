import es.uji.al341823.telefonia.llamadas.Llamada;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Random;

/**
 * @author David Agost (al341819)
 * @since 0.1
 */
public class LlamadaTest {

	private static Random rand;
	private static String numeroOrigen;
	private static String numeroDestino;
	private static LocalDateTime fechaLlamada;
	private static int duracionLlamada;
	private static Llamada llamada;

	@BeforeClass
	public static void first() {
		rand = new Random();

		numeroOrigen = Integer.toString(rand.nextInt(5));
		numeroDestino = Integer.toString(rand.nextInt(5));
		fechaLlamada = LocalDateTime.now();
		duracionLlamada = rand.nextInt(5);

		llamada = new Llamada(numeroOrigen, numeroDestino, fechaLlamada, duracionLlamada);
	}

	@Test
	public void getNumeroOrigenTest() {
		Assert.assertEquals(numeroOrigen, llamada.getNumeroOrigen());
	}

	@Test
	public void getNumeroDestinoTest() {
		Assert.assertEquals(numeroDestino, llamada.getNumeroDestino());
	}

	@Test
	public void getDuracionLlamadaTest() {
		Assert.assertEquals(duracionLlamada, llamada.getDuracionLlamada());
	}

	@Test
	public void getFechaTest() {
		Assert.assertEquals(fechaLlamada, llamada.getFecha());
	}
}
