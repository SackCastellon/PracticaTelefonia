/*
 * Copyright (c) 2017. Esta obra está sujeta a la licencia Reconocimiento 4.0 Internacional de Creative Commons.
 * Para ver una copia de esta licencia, visite http://creativecommons.org/licenses/by/4.0/.
 */

package es.uji.al341823.telefonia.gui.swing.vista;

import es.uji.al341823.telefonia.api.AdministradorDatos;
import es.uji.al341823.telefonia.api.EnumTipoDato;
import es.uji.al341823.telefonia.api.fabricas.FabricaTarifas;
import es.uji.al341823.telefonia.api.fabricas.TipoTarifa;
import es.uji.al341823.telefonia.clientes.Cliente;
import es.uji.al341823.telefonia.facturacion.tarifas.Tarifa;
import es.uji.al341823.telefonia.gui.swing.InfoColumna;
import es.uji.al341823.telefonia.gui.swing.InfoTabla;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
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
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

/**
 * Created by Juanjo on 30/04/2017.
 */
public class DialogoEditar extends JDialog {

	private final InfoTabla infoTabla;
	private final AccionTabla accion;

	private final ArrayList<Boolean> camposValidos;

	private final JTable tabla;
	private JButton btnGuardar;
	private ArrayList<JTextComponent> inputs = new ArrayList<>(); // TODO

	DialogoEditar(Window owner, JTable tabla, InfoTabla infoTabla, AccionTabla accion) {
		super(owner, ModalityType.APPLICATION_MODAL);

		this.tabla = tabla;
		this.infoTabla = infoTabla;
		this.accion = accion;

		this.camposValidos = new ArrayList<>(Collections.nCopies(infoTabla.getColumnCount(), false));

		this.generar();

		if (accion == AccionTabla.NUEVO)
			this.setTitle("Nuevo cliente");
		else if (accion == AccionTabla.EDITAR)
			this.setTitle("Editar cliente");

		this.setResizable(false);

		this.pack();

		this.setLocationRelativeTo(owner);
		this.setVisible(true);
	}

	private void generar() {
		JPanel inputPanel = new JPanel();
		inputPanel.setLayout(new GridBagLayout());
		inputPanel.setBorder(new CompoundBorder(new EmptyBorder(5, 5, 0, 5), new TitledBorder("Información del cliente")));
		this.add(inputPanel, BorderLayout.CENTER);

		GridBagConstraints constraints = new GridBagConstraints();

		int row = this.tabla.getSelectedRow();

		for (int i = 0; i < this.infoTabla.getColumnCount(); i++) {
			InfoColumna infoColumna = this.infoTabla.getColumn(i);
			int col = this.tabla.getColumnModel().getColumnIndex(infoColumna.getNombre());

			constraints.insets = new Insets(3, 5, 3, 1);
			constraints.anchor = GridBagConstraints.LINE_END;
			constraints.fill = GridBagConstraints.NONE;
			constraints.gridx = 0;
			constraints.gridy = i;

			String text = infoColumna.getNombre();

			JLabel label = new JLabel(text + ":");
			inputPanel.add(label, constraints);


			constraints.insets = new Insets(3, 1, 3, 3);
			constraints.anchor = GridBagConstraints.CENTER;
			constraints.fill = GridBagConstraints.HORIZONTAL;
			constraints.gridx = 1;

			if (i >= (this.infoTabla.getColumnCount() - 2)) continue;

			JTextField textField = new JTextField();
			if (this.accion == AccionTabla.EDITAR) textField.setText((String) this.tabla.getValueAt(row, col));
			textField.setPreferredSize(new Dimension(250, textField.getPreferredSize().height));
			textField.setCaretPosition(0);
			textField.addFocusListener(new ValidadorDatos(this, infoColumna.getTipoDato(), 0));
			inputPanel.add(textField, constraints);
			inputs.add(textField);
		}

		constraints.gridy--;

		InfoColumna infoColumna = this.infoTabla.getColumn(constraints.gridy);
		int col = this.tabla.getColumnModel().getColumnIndex(infoColumna.getNombre());

		// Selector fecha
		JSpinner spinnerFecha = new JSpinner(new SpinnerDateModel());
		spinnerFecha.setEditor(new JSpinner.DateEditor(spinnerFecha, "yyyy-MM-dd HH:mm:ss"));
		if (this.accion == AccionTabla.NUEVO)
			spinnerFecha.setValue(new Date());
		else {
			LocalDateTime date = LocalDateTime.parse((String) this.tabla.getValueAt(row, col), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
			spinnerFecha.setValue(Date.from(date.atZone(ZoneId.systemDefault()).toInstant())); // FIXME
		}
		inputPanel.add(spinnerFecha, constraints);
		inputs.add(spinnerFecha); // TODO

		constraints.gridy++;

		// Selector tarifa basica
		JComboBox<Tarifa> comboBoxTarifas = new JComboBox<>();
		FabricaTarifas fabrica = new FabricaTarifas();
		comboBoxTarifas.addItem(fabrica.getTarifaBase(TipoTarifa.Base.BASICA));
		inputPanel.add(comboBoxTarifas, constraints);
		inputs.add(comboBoxTarifas); // TODO

		// ============================================================ //

		JPanel buttonPanel = new JPanel();
		this.add(buttonPanel, BorderLayout.SOUTH);

		this.btnGuardar = new JButton("Guardar");
		this.btnGuardar.setEnabled(false);
		this.btnGuardar.addActionListener(e -> this.devolverCliente());
		buttonPanel.add(this.btnGuardar);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(e -> this.dispose());
		buttonPanel.add(btnCancelar);

		buttonPanel.setPreferredSize(buttonPanel.getPreferredSize());
	}

	private void devolverCliente() {
		Cliente cliente; // TODO
	}

	private class ValidadorDatos implements FocusListener {
		private final DialogoEditar parent;
		private final EnumTipoDato tipoDato;
		private final int campo;

		private final Color colorValorIncorrecto = new Color(0xf2dede);

		ValidadorDatos(DialogoEditar parent, EnumTipoDato tipoDato, int campo) {
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
				textField.setBackground(colorValorIncorrecto);
				this.parent.camposValidos.set(this.campo, false);
			}

			boolean enabled = this.parent.camposValidos.stream().allMatch(Boolean::booleanValue);
			this.parent.btnGuardar.setEnabled(enabled);
		}
	}
}
