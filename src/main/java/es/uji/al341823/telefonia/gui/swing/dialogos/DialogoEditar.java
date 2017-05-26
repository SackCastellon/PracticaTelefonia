/*
 * Copyright (c) 2017. Esta obra está sujeta a la licencia Reconocimiento 4.0 Internacional de Creative Commons.
 * Para ver una copia de esta licencia, visite http://creativecommons.org/licenses/by/4.0/.
 */

package es.uji.al341823.telefonia.gui.swing.dialogos;

import es.uji.al341823.telefonia.api.AdministradorDatos;
import es.uji.al341823.telefonia.api.SwingUtils;
import es.uji.al341823.telefonia.api.TipoDato;
import es.uji.al341823.telefonia.api.excepciones.ObjetoYaExisteException;
import es.uji.al341823.telefonia.clientes.Cliente;
import es.uji.al341823.telefonia.clientes.Empresa;
import es.uji.al341823.telefonia.clientes.Particular;
import es.uji.al341823.telefonia.facturacion.tarifas.Tarifa;
import es.uji.al341823.telefonia.facturacion.tarifas.TarifaExtra;
import es.uji.al341823.telefonia.gui.swing.Vista;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.UIManager;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.JTextComponent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
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
import java.util.LinkedList;
import java.util.List;

import static es.uji.al341823.telefonia.gui.swing.ActionCommands.DIALOGO_CANCELAR;
import static es.uji.al341823.telefonia.gui.swing.ActionCommands.DIALOGO_GUARDAR;

/**
 * @author Juanjo González (al341823)
 * @since 0.4
 */
public class DialogoEditar extends Vista {

	private final JDialog dialog;

	private final Cliente cliente;
	private final Class<? extends Cliente> tipoCliente;

	private final LinkedList<TipoDato> tipoDatos = new LinkedList<>();
	private final ArrayList<Boolean> camposValidos;

	private final ArrayList<JTextComponent> inputs = new ArrayList<>();

	private JSpinner spinnerFecha;
	private JComboBox<Tarifa> comboBoxTarifas;
	private JComboBox<Object> comboBoxTarifasExtra;
	private JButton btnGuardar;
	private String ELIMINAR;

	private DialogoEditar(Window owner, Cliente cliente, Class<? extends Cliente> tipoCliente) {
		super();

		this.dialog = new JDialog(owner, Dialog.ModalityType.APPLICATION_MODAL);
		this.cliente = cliente;
		this.tipoCliente = tipoCliente;

		if (cliente == null) {
			int size = (this.tipoCliente.equals(Particular.class) ? Particular.getNombreDatos().size() : Empresa.getNombreDatos().size());
			this.camposValidos = new ArrayList<>(Collections.nCopies(size - 2, false));
		} else
			this.camposValidos = new ArrayList<>();

		if (tipoCliente.equals(Particular.class)) {
			this.tipoDatos.add(TipoDato.NIF);
			this.tipoDatos.add(TipoDato.TEXTO);
			this.tipoDatos.add(TipoDato.TEXTO);
			this.tipoDatos.add(TipoDato.DIRECCION);
			this.tipoDatos.add(TipoDato.EMAIL);
		} else {
			this.tipoDatos.add(TipoDato.NIF);
			this.tipoDatos.add(TipoDato.TEXTO);
			this.tipoDatos.add(TipoDato.DIRECCION);
			this.tipoDatos.add(TipoDato.EMAIL);
		}
	}

	public DialogoEditar(Window owner, Class<? extends Cliente> tipoCliente) {
		this(owner, null, tipoCliente);
	}

	public DialogoEditar(Window owner, Cliente cliente) {
		this(owner, cliente, cliente.getClass());
	}

	@Override
	public void generarVista() {
		this.generarCampos();
		this.generarBotones();

		if (this.cliente == null) {
			this.dialog.setTitle("Nuevo cliente");
			this.dialog.setIconImage(SwingUtils.getImage("add"));
		} else {
			this.dialog.setTitle("Editar tarifa");
			this.dialog.setIconImage(SwingUtils.getImage("edit"));
		}

		this.dialog.setResizable(false);

		this.dialog.pack();

		this.dialog.setLocationRelativeTo(this.dialog.getOwner());
		this.dialog.setVisible(true);
	}

	@Override
	public void actualizarVista() {

	}

	private void generarCampos() {
		// Panel com campos de datos
		JPanel inputPanel = new JPanel();
		inputPanel.setLayout(new GridBagLayout());
		inputPanel.setBorder(new CompoundBorder(new EmptyBorder(5, 5, 0, 5), new TitledBorder("Información del cliente")));
		this.dialog.add(inputPanel, BorderLayout.CENTER);

		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridy = -1;

		List<String> nombres = this.tipoCliente.equals(Particular.class) ? Particular.getNombreDatos() : Empresa.getNombreDatos();

		for (int col = 0; col < nombres.size(); col++) {
			constraints.insets = new Insets(3, 5, 3, 1);
			constraints.anchor = GridBagConstraints.LINE_END;
			constraints.fill = GridBagConstraints.NONE;
			constraints.gridx = 0;
			constraints.gridy++;

			// Etiquetas con el nombre del campo
			JLabel label = new JLabel(nombres.get(col) + ":");
			inputPanel.add(label, constraints);


			constraints.insets = new Insets(3, 1, 3, 3);
			constraints.anchor = GridBagConstraints.CENTER;
			constraints.fill = GridBagConstraints.HORIZONTAL;
			constraints.gridx++;

			if (col >= (nombres.size() - 2)) continue;

			// Campos de texto para el dato
			JTextField textField = new JTextField();
			if (this.cliente != null) textField.setText(this.cliente.getDatos().get(col).getValue().toString());
			textField.setEditable(this.cliente == null);
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
		if (this.cliente == null)
			this.spinnerFecha.setValue(new Date());
		else {
			LocalDateTime date = (LocalDateTime) this.cliente.getDatos().get(constraints.gridy).getValue();
			this.spinnerFecha.setValue(Date.from(date.atZone(ZoneId.systemDefault()).toInstant())); // FIXME
		}
		this.spinnerFecha.setEnabled(this.cliente == null);
		inputPanel.add(this.spinnerFecha, constraints);

		constraints.gridy++;

		LinkedList<Tarifa> tarifasCliente = new LinkedList<>();
		if (this.cliente != null) {
			Tarifa tarifaBase = this.cliente.getTarifa();

			while (tarifaBase instanceof TarifaExtra) {
				tarifasCliente.addFirst(tarifaBase);
				tarifaBase = ((TarifaExtra) tarifaBase).getTarifaBase();
			}

			tarifasCliente.addFirst(tarifaBase);
		}

		// Selector tarifa basica
		this.comboBoxTarifas = new JComboBox<>();
		ArrayList<Tarifa> tarifas = this.getControlador().getTarifasBasicas();
		for (Tarifa tarifa : tarifas)
			this.comboBoxTarifas.addItem(tarifa);
		if (this.cliente != null) {
			this.comboBoxTarifas.setSelectedItem(tarifasCliente.removeFirst());
			this.comboBoxTarifas.setEnabled(false);
		}
		inputPanel.add(this.comboBoxTarifas, constraints);

		constraints.gridy++;

		// Selector tarifa extra
		if (this.cliente != null) {
			for (Tarifa tarifa : tarifasCliente) {
				JComboBox<Tarifa> comboBox = new JComboBox<>();
				comboBox.addItem(tarifa);
				comboBox.setSelectedIndex(0);
				comboBox.setEnabled(false);
				inputPanel.add(comboBox, constraints);
				constraints.gridy++;
			}

			this.comboBoxTarifasExtra = new JComboBox<>();
			ArrayList<Tarifa> tarifasExtra = this.getControlador().getTarifasExtra(this.cliente.getTarifa());
			tarifasExtra.removeIf(tarifasCliente::contains);
			this.comboBoxTarifasExtra.addItem("Selecciona una nueva tarifa extra...");

			if (!tarifasCliente.isEmpty()) {
				this.ELIMINAR = "Eliminar: \"" + tarifasCliente.getLast() + "\"";
				this.comboBoxTarifasExtra.addItem(this.ELIMINAR);
			}

			for (Tarifa tarifa : tarifasExtra)
				this.comboBoxTarifasExtra.addItem(tarifa);

			this.comboBoxTarifasExtra.addItemListener(e -> {
				boolean isValid = this.comboBoxTarifasExtra.getSelectedIndex() != 0;
				this.btnGuardar.setEnabled(isValid);
			});

			inputPanel.add(this.comboBoxTarifasExtra, constraints);
		}
	}

	private void generarBotones() {
		JPanel buttonPanel = new JPanel();
		this.dialog.add(buttonPanel, BorderLayout.SOUTH);

		this.btnGuardar = new JButton("Guardar");
		this.btnGuardar.setEnabled(false);
		this.btnGuardar.setActionCommand(DIALOGO_GUARDAR);
		this.btnGuardar.addActionListener(new EscuchadorDialogoEditar());
		buttonPanel.add(this.btnGuardar);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setActionCommand(DIALOGO_CANCELAR);
		btnCancelar.addActionListener(new EscuchadorDialogoEditar());
		buttonPanel.add(btnCancelar);

		buttonPanel.setPreferredSize(buttonPanel.getPreferredSize());


		if (this.cliente != null) {// TODO
			Tarifa tarifa = this.getControlador().getTarifa(this.cliente);
			this.comboBoxTarifas.setSelectedItem(tarifa);
		}
	}

	/**
	 * @author Juanjo González (al341823)
	 * @since 0.4
	 */
	private class ValidadorDatos implements FocusListener {
		private final TipoDato tipoDato;
		private final int campo;

		private final Color colorValorIncorrecto = new Color(0xea7070);

		ValidadorDatos(TipoDato tipoDato, int campo) {
			super();
			this.tipoDato = tipoDato;
			this.campo = campo;
		}

		@Override
		public void focusGained(FocusEvent e) {
			if (DialogoEditar.this.cliente != null) return;

			JTextField textField = ((JTextField) e.getComponent());
			textField.setBackground(UIManager.getColor("TextField.background"));
			DialogoEditar.this.camposValidos.set(this.campo, false);

			boolean enabled = DialogoEditar.this.camposValidos.stream().allMatch(Boolean::booleanValue);
			DialogoEditar.this.btnGuardar.setEnabled(enabled);
		}

		@Override
		public void focusLost(FocusEvent e) {
			if (DialogoEditar.this.cliente != null) return;

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

	/**
	 * @author Juanjo González (al341823)
	 * @since 0.4
	 */
	private class EscuchadorDialogoEditar implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			switch (e.getActionCommand()) {
				case DIALOGO_GUARDAR:
					this.guardar();
				case DIALOGO_CANCELAR:
					DialogoEditar.this.dialog.dispose();
			}
		}

		// FIXME Revisar
		private void guardar() {
			if (DialogoEditar.this.cliente != null) {
				if (DialogoEditar.this.comboBoxTarifasExtra.getSelectedItem().equals(DialogoEditar.this.ELIMINAR))
					DialogoEditar.this.getControlador().setTarifa(DialogoEditar.this.cliente, ((TarifaExtra) DialogoEditar.this.cliente.getTarifa()).getTarifaBase());
				else if (DialogoEditar.this.comboBoxTarifasExtra.getSelectedIndex() != 0)
					DialogoEditar.this.getControlador().setTarifa(DialogoEditar.this.cliente, (Tarifa) DialogoEditar.this.comboBoxTarifasExtra.getSelectedItem());
			} else {
				String[] textos = DialogoEditar.this.inputs.stream().map(JTextComponent::getText).toArray(String[]::new);

				try {
					DialogoEditar.this.getControlador().guardarCliente(textos,
							(Date) DialogoEditar.this.spinnerFecha.getValue(),
							(Tarifa) DialogoEditar.this.comboBoxTarifas.getSelectedItem());
				} catch (ObjetoYaExisteException exception) {
					JOptionPane.showMessageDialog(DialogoEditar.this.dialog.getOwner(),
							"No se pudo añadir el cliente especificado",
							"Error al añadir",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}
}
