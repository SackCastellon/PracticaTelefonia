/*
 * Copyright (c) 2017. Esta obra está sujeta a la licencia Reconocimiento 4.0 Internacional de Creative Commons.
 * Para ver una copia de esta licencia, visite http://creativecommons.org/licenses/by/4.0/.
 */

package es.uji.al341823.telefonia.gui.swing.controlador;

import es.uji.al341823.telefonia.api.AdministradorDatos;
import es.uji.al341823.telefonia.api.EnumTipoDato;
import es.uji.al341823.telefonia.gui.swing.vista.DialogoEditar;

import javax.swing.JTextField;
import javax.swing.UIManager;
import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/**
 * Created by Juanjo on 01/05/2017.
 */
public class ValidadorDatos implements FocusListener {
	private final DialogoEditar parent;
	private final EnumTipoDato tipoDato;
	private final int campo;

	public ValidadorDatos(DialogoEditar parent, EnumTipoDato tipoDato, int campo) {
		super();
		this.parent = parent;
		this.tipoDato = tipoDato;
		this.campo = campo;
	}

	@Override
	public void focusGained(FocusEvent e) {
		JTextField textField = ((JTextField) e.getComponent());
		textField.setBackground(UIManager.getColor("TextField.background"));
		this.parent.camposValidos.set(this.campo, false);

		boolean enabled = this.parent.camposValidos.stream().allMatch(Boolean::booleanValue);
		this.parent.btnGuardar.setEnabled(enabled);
	}

	@Override
	public void focusLost(FocusEvent e) {
		JTextField textField = ((JTextField) e.getComponent());

		if (AdministradorDatos.esDatoValido(textField.getText(), this.tipoDato)) {
			this.parent.camposValidos.set(this.campo, true);
		} else {
			textField.setBackground(new Color(0xf2dede));
			this.parent.camposValidos.set(this.campo, false);
		}

		boolean enabled = this.parent.camposValidos.stream().allMatch(Boolean::booleanValue);
		this.parent.btnGuardar.setEnabled(enabled);
	}
}
