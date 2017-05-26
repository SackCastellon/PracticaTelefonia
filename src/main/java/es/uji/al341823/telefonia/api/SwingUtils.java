/*
 * Copyright (c) 2017. Esta obra está sujeta a la licencia Reconocimiento 4.0 Internacional de Creative Commons.
 * Para ver una copia de esta licencia, visite http://creativecommons.org/licenses/by/4.0/.
 */

package es.uji.al341823.telefonia.api;

import javax.swing.ImageIcon;
import java.awt.Image;
import java.net.URL;

/**
 * @author Juanjo González (al341823)
 * @since 0.4
 */
public class SwingUtils {
	public static ImageIcon getIcon(String icon) {
		String name = String.format("/icons/%s.png", icon);

		URL url = SwingUtils.class.getResource(name);

		if (url == null) {
			System.err.println("Missing icon: " + icon);
			url = SwingUtils.class.getResource("/icons/missing.png");
		}

		return new ImageIcon(url);
	}

	public static Image getImage(String image) {
		String name = String.format("/images/%s.png", image);

		URL url = SwingUtils.class.getResource(name);

		if (url == null) {
			System.err.println("Missing image: " + image);
			url = SwingUtils.class.getResource("/images/missing.png");
		}

		return new ImageIcon(url).getImage();
	}
}
