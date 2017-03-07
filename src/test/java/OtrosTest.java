import org.junit.Test;

/**
 * Created by juanjo on 3/7/17.
 */
public class OtrosTest {
	@Test
	public void test() {
		String s = "+34 682 20 12 12";
		System.out.println(s.matches("(\\+)?(\\s|\\d)*"));
	}
}
