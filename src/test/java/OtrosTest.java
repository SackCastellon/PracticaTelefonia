/*
 * Copyright (c) 2017. Esta obra está sujeta a la licencia Reconocimiento 4.0 Internacional de Creative Commons.
 * Para ver una copia de esta licencia, visite http://creativecommons.org/licenses/by/4.0/.
 */

import org.junit.Test;

import javax.swing.*;

public class OtrosTest {

	@Test
	@SuppressWarnings("EmptyMethod")
	public void test() {
		System.out.println(System.getProperty("os.name"));
		System.out.println(UIManager.getLookAndFeel().getClass().getName());
		for (UIManager.LookAndFeelInfo lookAndFeelInfo : UIManager.getInstalledLookAndFeels()) {
			System.out.println(lookAndFeelInfo);
		}
	}
}
