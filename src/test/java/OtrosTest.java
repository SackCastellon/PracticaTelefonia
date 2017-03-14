import org.junit.Test;

/**
 * Created by juanjo on 3/7/17.
 */
public class OtrosTest {
	@Test
	public void test() {
		String s = "2312-12-23 26:23:51";
		System.out.println(s.matches("[0-9]{5}, (.*), (.*)"));
	}
}
