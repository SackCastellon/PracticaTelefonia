/*
 * Copyright (c) 2017. Esta obra está sujeta a la licencia Reconocimiento 4.0 Internacional de Creative Commons.
 * Para ver una copia de esta licencia, visite http://creativecommons.org/licenses/by/4.0/.
 */

package es.uji.al341823.telefonia.gui.swing.dialogos;

import es.uji.al341823.telefonia.api.AdministradorDatos;
import es.uji.al341823.telefonia.api.TipoDato;
import es.uji.al341823.telefonia.gui.swing.ventanas.VentanaPrincipal;
import es.uji.al341823.telefonia.llamadas.Llamada;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import static es.uji.al341823.telefonia.gui.swing.ActionCommands.DIALOGO_CANCELAR;
import static es.uji.al341823.telefonia.gui.swing.ActionCommands.DIALOGO_GUARDAR;

/**
 * Created by Juanjo on 03/05/2017.
 */
public class DialogoLlamada extends JDialog {

	private final ArrayList<Boolean> camposValidos;

	private final JPanel inputPanel = new JPanel();

	private final JTextField txtNumOrigen = new JTextField();
	private final JTextField txtNumDestino = new JTextField();
	private final JSpinner spinnerFecha = new JSpinner(new SpinnerDateModel());
	private final JSpinner spinnerDuracion = new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));

	private final JButton btnGuardar = new JButton();

	private Llamada llamada;

	public DialogoLlamada(Window owner) {
		super(owner, ModalityType.APPLICATION_MODAL);

		this.camposValidos = new ArrayList<>(Collections.nCopies(2, false));
	}

	public void generar() {
		this.inputPanel.setLayout(new GridBagLayout());
		this.inputPanel.setBorder(new CompoundBorder(new EmptyBorder(5, 5, 0, 5), new TitledBorder("Información de la llamada")));
		this.add(this.inputPanel, BorderLayout.CENTER);

		this.generarCampos();

		// ============================================================ //

		JPanel buttonPanel = new JPanel();
		this.add(buttonPanel, BorderLayout.SOUTH);

		this.btnGuardar.setText("Guardar");
		this.btnGuardar.setEnabled(false);
		this.btnGuardar.setActionCommand(DIALOGO_GUARDAR);
		this.btnGuardar.addActionListener(new EscuchadorDialogoLlamada());
		buttonPanel.add(this.btnGuardar);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setActionCommand(DIALOGO_CANCELAR);
		btnCancelar.addActionListener(new EscuchadorDialogoLlamada());
		buttonPanel.add(btnCancelar);

		buttonPanel.setPreferredSize(buttonPanel.getPreferredSize());

		// ============================================================ //
		// ============================================================ //


		this.setTitle("Nueva llamada");
		this.setIconImage(VentanaPrincipal.getImage("phone_add"));

		this.setResizable(false);

		this.pack();

		this.setLocationRelativeTo(this.getOwner());
		this.setVisible(true);
	}

	private void generarCampos() {
		int i = 0;

		JLabel lblNumOrigen = new JLabel("Numero de origen:");
		this.txtNumOrigen.setPreferredSize(new Dimension(250, this.txtNumOrigen.getPreferredSize().height));
		this.txtNumOrigen.addFocusListener(new ValidadorDatos(TipoDato.TELEFONO, i));
		this.addLine(lblNumOrigen, this.txtNumOrigen, i);

		i++;

		JLabel lblNumDestino = new JLabel("Numero de destino:");
		this.txtNumDestino.setPreferredSize(new Dimension(250, this.txtNumDestino.getPreferredSize().height));
		this.txtNumDestino.addFocusListener(new ValidadorDatos(TipoDato.TELEFONO, i));
		this.addLine(lblNumDestino, this.txtNumDestino, i);

		i++;

		JLabel lblFecha = new JLabel("Fecha de la llamada:");
		this.spinnerFecha.setEditor(new JSpinner.DateEditor(this.spinnerFecha, "yyyy-MM-dd HH:mm:ss"));
		this.spinnerFecha.setValue(new Date());
		this.addLine(lblFecha, this.spinnerFecha, i);

		i++;

		JLabel lblDuracion = new JLabel("Duración de la llamada:");
		this.spinnerDuracion.setEditor(new JSpinner.NumberEditor(this.spinnerDuracion));
		this.addLine(lblDuracion, this.spinnerDuracion, i);
	}

	private void addLine(JLabel label, Component input, int i) {
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridy = i;

		constraints.insets = new Insets(3, 5, 3, 1);
		constraints.anchor = GridBagConstraints.LINE_END;
		constraints.fill = GridBagConstraints.NONE;
		constraints.gridx = 0;

		this.inputPanel.add(label, constraints);

		constraints.insets = new Insets(3, 1, 3, 3);
		constraints.anchor = GridBagConstraints.CENTER;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 1;

		this.inputPanel.add(input, constraints);
	}

	public Llamada getLlamada() {
		return this.llamada;
	}

	/**
	 * @author Juanjo González (al341823)
	 * @since 0.4
	 */
	private class ValidadorDatos implements FocusListener {
		private final TipoDato tipoDato;
		private final int campo;

		private final Color colorValorIncorrecto = new Color(0xf2dede);

		ValidadorDatos(TipoDato tipoDato, int campo) {
			super();
			this.tipoDato = tipoDato;
			this.campo = campo;
		}

		@Override
		public void focusGained(FocusEvent e) {
			JTextField textField = ((JTextField) e.getComponent());
			textField.setBackground(UIManager.getColor("TextField.background"));
			DialogoLlamada.this.camposValidos.set(this.campo, false);

			boolean enabled = DialogoLlamada.this.camposValidos.stream().allMatch(Boolean::booleanValue);
			DialogoLlamada.this.btnGuardar.setEnabled(enabled);
		}

		@Override
		public void focusLost(FocusEvent e) {
			JTextField textField = ((JTextField) e.getComponent());

			if (AdministradorDatos.esDatoValido(textField.getText(), this.tipoDato)) {
				DialogoLlamada.this.camposValidos.set(this.campo, true);
			} else {
				textField.setBackground(this.colorValorIncorrecto);
				DialogoLlamada.this.camposValidos.set(this.campo, false);
			}

			boolean enabled = DialogoLlamada.this.camposValidos.stream().allMatch(Boolean::booleanValue);
			DialogoLlamada.this.btnGuardar.setEnabled(enabled);
		}
	}

	private class EscuchadorDialogoLlamada implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			switch (e.getActionCommand()) {
				case DIALOGO_GUARDAR:
					String origen = DialogoLlamada.this.txtNumOrigen.getText();
					String destino = DialogoLlamada.this.txtNumDestino.getText();
					Date fecha = (Date) DialogoLlamada.this.spinnerFecha.getValue();
					LocalDateTime fechaLocal = LocalDateTime.ofInstant(fecha.toInstant(), ZoneId.systemDefault());
					int duracion = (int) DialogoLlamada.this.spinnerDuracion.getValue();

					DialogoLlamada.this.llamada = new Llamada(origen, destino, fechaLocal, duracion);

				case DIALOGO_CANCELAR:
					DialogoLlamada.this.dispose();
					break;
			}
		}
	}
}
