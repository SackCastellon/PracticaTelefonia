/*
 * Copyright (c) 2017. Esta obra está sujeta a la licencia Reconocimiento 4.0 Internacional de Creative Commons.
 * Para ver una copia de esta licencia, visite http://creativecommons.org/licenses/by/4.0/.
 */

package es.uji.al341823.telefonia.gui.swing;

import es.uji.al341823.telefonia.api.EnumTipoDato;
import es.uji.al341823.telefonia.api.fabricas.FabricaTarifas;
import es.uji.al341823.telefonia.api.fabricas.TipoTarifa;
import es.uji.al341823.telefonia.facturacion.tarifas.Tarifa;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Window;
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

	private final Accion accion;

	public final ArrayList<Boolean> camposValidos = new ArrayList<>(Collections.nCopies(5, false));

	private final JTable tabla;
	public JButton btnGuardar;
	private final EnumTipoDato[] tiposDato;

	public DialogoEditar(Window owner, JTable table, Accion accion) {
		super(owner, ModalityType.APPLICATION_MODAL);

		this.tabla = table;
		this.accion = accion;

		this.tiposDato = new EnumTipoDato[] {EnumTipoDato.NIF, EnumTipoDato.TEXTO, EnumTipoDato.TEXTO, EnumTipoDato.DIRECCION, EnumTipoDato.EMAIL}; // FIXME

		this.generar();

		if (accion == Accion.NUEVO)
			this.setTitle("Nuevo cliente");
		else if (accion == Accion.EDITAR)
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

		for (int i = 0; i < this.tabla.getColumnCount(); i++) {
			constraints.insets = new Insets(3, 5, 3, 1);
			constraints.anchor = GridBagConstraints.LINE_END;
			constraints.fill = GridBagConstraints.NONE;
			constraints.gridx = 0;
			constraints.gridy = i;

			String text = this.tabla.getColumnName(i);

			JLabel label = new JLabel(text + ":");
			inputPanel.add(label, constraints);


			constraints.insets = new Insets(3, 1, 3, 3);
			constraints.anchor = GridBagConstraints.CENTER;
			constraints.fill = GridBagConstraints.HORIZONTAL;
			constraints.gridx = 1;

			if (i >= (this.tabla.getColumnCount() - 2)) continue;

			JTextField textField = new JTextField();
			if (this.accion == Accion.EDITAR) textField.setText((String) this.tabla.getValueAt(row, i));
			textField.setPreferredSize(new Dimension(250, textField.getPreferredSize().height));
			textField.setCaretPosition(0);
			textField.addFocusListener(new ValidadorDatos(this, tiposDato[i], 0));
			inputPanel.add(textField, constraints);
		}

		constraints.gridy--;

		// Selector fecha
		JSpinner spinnerFecha = new JSpinner(new SpinnerDateModel());
		spinnerFecha.setEditor(new JSpinner.DateEditor(spinnerFecha, "yyyy-MM-dd HH:mm:ss"));
		if (this.accion == Accion.NUEVO)
			spinnerFecha.setValue(new Date());
		else {
			LocalDateTime date = LocalDateTime.parse((String) this.tabla.getValueAt(row, constraints.gridy), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
			spinnerFecha.setValue(Date.from(date.atZone(ZoneId.systemDefault()).toInstant())); // FIXME
		}
		inputPanel.add(spinnerFecha, constraints);

		constraints.gridy++;

		// Selector tarifa basica
		JComboBox<Tarifa> comboBoxTarifas = new JComboBox<>();
		FabricaTarifas fabrica = new FabricaTarifas();
		comboBoxTarifas.addItem(fabrica.getTarifaBase(TipoTarifa.Base.BASICA));
		inputPanel.add(comboBoxTarifas, constraints);

		// ============================================================ //

		JPanel buttonPanel = new JPanel();
		this.add(buttonPanel, BorderLayout.SOUTH);

		this.btnGuardar = new JButton("Guardar");
		this.btnGuardar.setEnabled(false);
		this.btnGuardar.addActionListener(e -> { // TODO
		});
		buttonPanel.add(this.btnGuardar);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(e -> this.dispose());
		buttonPanel.add(btnCancelar);

		buttonPanel.setPreferredSize(buttonPanel.getPreferredSize());
	}

	public enum Accion {NUEVO, EDITAR}
}
