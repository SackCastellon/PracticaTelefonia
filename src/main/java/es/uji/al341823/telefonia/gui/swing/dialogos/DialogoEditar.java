/*
 * Copyright (c) 2017. Esta obra está sujeta a la licencia Reconocimiento 4.0 Internacional de Creative Commons.
 * Para ver una copia de esta licencia, visite http://creativecommons.org/licenses/by/4.0/.
 */

package es.uji.al341823.telefonia.gui.swing.dialogos;

import es.uji.al341823.telefonia.api.AdministradorDatos;
import es.uji.al341823.telefonia.api.TipoDato;
import es.uji.al341823.telefonia.api.excepciones.ClienteYaExisteExcepcion;
import es.uji.al341823.telefonia.facturacion.tarifas.Tarifa;
import es.uji.al341823.telefonia.gui.swing.controlador.Controlador;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.UIManager;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.JTextComponent;
import java.awt.BorderLayout;
import java.awt.Color;
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
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.Objects;

/**
 * Created by Juanjo on 30/04/2017.
 */
public class DialogoEditar extends JDialog {

	private final JTable tabla;
	private final String accion;
	private final Controlador controlador;

	private final ArrayList<Boolean> camposValidos;

	private final ArrayList<JTextComponent> inputs = new ArrayList<>(); // TODO
	private JSpinner spinnerFecha;
	private JComboBox<Tarifa> comboBoxTarifas;

	private JButton btnGuardar;

	private final LinkedList<TipoDato> tipoDatos = new LinkedList<>();

	public DialogoEditar(Window owner, JTable tabla, String accion, Controlador controlador) {
		super(owner, ModalityType.APPLICATION_MODAL);

		this.tabla = tabla;
		this.accion = accion;
		this.controlador = controlador;

		this.camposValidos = new ArrayList<>(Collections.nCopies(tabla.getColumnCount() - 2, false));

		if (tabla.getColumnCount() == 7) {
			this.tipoDatos.add(TipoDato.NIF);
			this.tipoDatos.add(TipoDato.TEXTO);
			this.tipoDatos.add(TipoDato.TEXTO);
			this.tipoDatos.add(TipoDato.DIRECCION);
			this.tipoDatos.add(TipoDato.EMAIL);
		} else {
			this.tipoDatos.add(TipoDato.NIF_EMPRESA);
			this.tipoDatos.add(TipoDato.TEXTO);
			this.tipoDatos.add(TipoDato.DIRECCION);
			this.tipoDatos.add(TipoDato.EMAIL);
		}
	}

	public void generar() {
		JPanel inputPanel = new JPanel();
		inputPanel.setLayout(new GridBagLayout());
		inputPanel.setBorder(new CompoundBorder(new EmptyBorder(5, 5, 0, 5), new TitledBorder("Información del cliente")));
		this.add(inputPanel, BorderLayout.CENTER);

		GridBagConstraints constraints = new GridBagConstraints();

		int row = this.tabla.getSelectedRow();

		for (int col = 0; col < this.tabla.getColumnCount(); col++) {
			constraints.insets = new Insets(3, 5, 3, 1);
			constraints.anchor = GridBagConstraints.LINE_END;
			constraints.fill = GridBagConstraints.NONE;
			constraints.gridx = 0;
			constraints.gridy = col;

			String text = this.tabla.getColumnName(col);

			JLabel label = new JLabel(text + ":");
			inputPanel.add(label, constraints);


			constraints.insets = new Insets(3, 1, 3, 3);
			constraints.anchor = GridBagConstraints.CENTER;
			constraints.fill = GridBagConstraints.HORIZONTAL;
			constraints.gridx = 1;

			if (col >= (this.tabla.getColumnCount() - 2)) continue;

			JTextField textField = new JTextField();
			if (Objects.equals(this.accion, "edit_client")) textField.setText((String) this.tabla.getValueAt(row, col));
			textField.setPreferredSize(new Dimension(250, textField.getPreferredSize().height));
			textField.setCaretPosition(0);
			textField.addFocusListener(new ValidadorDatos(this.tipoDatos.get(col), col));
			inputPanel.add(textField, constraints);
			this.inputs.add(textField);
		}

		constraints.gridy--;

		// Selector fecha
		this.spinnerFecha = new JSpinner(new SpinnerDateModel());
		this.spinnerFecha.setEditor(new JSpinner.DateEditor(this.spinnerFecha, "yyyy-MM-dd HH:mm:ss"));
		if (Objects.equals(this.accion, "new_client"))
			this.spinnerFecha.setValue(new Date());
		else {
			LocalDateTime date = LocalDateTime.parse((String) this.tabla.getValueAt(row, constraints.gridy), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
			this.spinnerFecha.setValue(Date.from(date.atZone(ZoneId.systemDefault()).toInstant())); // FIXME
		}
		inputPanel.add(this.spinnerFecha, constraints);

		constraints.gridy++;

		// Selector tarifa basica
		this.comboBoxTarifas = new JComboBox<>();
		ArrayList<Tarifa> tarifas = this.controlador.getTarifasBasicas();
		for (Tarifa tarifa : tarifas)
			this.comboBoxTarifas.addItem(tarifa);
		inputPanel.add(this.comboBoxTarifas, constraints);

		// ============================================================ //

		JPanel buttonPanel = new JPanel();
		this.add(buttonPanel, BorderLayout.SOUTH);

		this.btnGuardar = new JButton("Guardar");
		this.btnGuardar.setEnabled(false);
		this.btnGuardar.setActionCommand("save");
		this.btnGuardar.addActionListener(new EscuchadorDialogoEditar());
		buttonPanel.add(this.btnGuardar);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setActionCommand("cancel");
		btnCancelar.addActionListener(new EscuchadorDialogoEditar());
		buttonPanel.add(btnCancelar);

		buttonPanel.setPreferredSize(buttonPanel.getPreferredSize());


		// ============================================================ //
		// ============================================================ //


		if (Objects.equals(this.accion, "new_client"))
			this.setTitle("Nuevo cliente");
		else if (Objects.equals(this.accion, "edit_client"))
			this.setTitle("Editar cliente");

		this.setResizable(false);

		this.pack();

		this.setLocationRelativeTo(this.getOwner());
		this.setVisible(true);
	}

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
			DialogoEditar.this.camposValidos.set(this.campo, false);

			boolean enabled = DialogoEditar.this.camposValidos.stream().allMatch(Boolean::booleanValue);
			DialogoEditar.this.btnGuardar.setEnabled(enabled);
		}

		@Override
		public void focusLost(FocusEvent e) {
			JTextField textField = ((JTextField) e.getComponent());

			if (AdministradorDatos.esDatoValido(textField.getText(), this.tipoDato)) {
				DialogoEditar.this.camposValidos.set(this.campo, true);
			} else {
				textField.setBackground(this.colorValorIncorrecto);
				DialogoEditar.this.camposValidos.set(this.campo, false);
			}

			boolean enabled = DialogoEditar.this.camposValidos.stream().allMatch(Boolean::booleanValue);
			DialogoEditar.this.btnGuardar.setEnabled(enabled);
		}
	}

	private class EscuchadorDialogoEditar implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			switch (e.getActionCommand()) {
				case "save":
					String[] textos = DialogoEditar.this.inputs.stream().map(JTextComponent::getText).toArray(String[]::new);

					try {
						DialogoEditar.this.controlador.guardarCliente(textos,
								(Date) DialogoEditar.this.spinnerFecha.getValue(),
								(Tarifa) DialogoEditar.this.comboBoxTarifas.getSelectedItem());
					} catch (ClienteYaExisteExcepcion clienteYaExisteExcepcion) {
						JOptionPane.showMessageDialog(DialogoEditar.this.getOwner(),
								"No se puedo añadir el cliente especificado",
								"Error al añadir",
								JOptionPane.ERROR_MESSAGE);
					}
				case "cancel":
					DialogoEditar.this.dispose();
			}
		}
	}
}
