/*
 * Copyright (c) 2017. Esta obra está sujeta a la licencia Reconocimiento 4.0 Internacional de Creative Commons.
 * Para ver una copia de esta licencia, visite http://creativecommons.org/licenses/by/4.0/.
 */

package es.uji.al341823.telefonia.gui.swing.dialogos;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.Component;
import java.awt.HeadlessException;
import java.awt.Image;

/**
 * @author Juanjo González (al341823)
 * @since 0.4
 */
public class DialogoFileChooser extends JFileChooser {
	private Image icon;

	public DialogoFileChooser() {
		super();
		this.setFileFilter(new FileNameExtensionFilter("Fichero de datos (*.data)", "data"));
	}

	@Override
	protected JDialog createDialog(Component parent) throws HeadlessException {
		JDialog dialog = super.createDialog(parent);
		dialog.setIconImage(this.icon);

		return dialog;
	}

	public void setIcon(Image icon) {
		this.icon = icon;
	}
}
