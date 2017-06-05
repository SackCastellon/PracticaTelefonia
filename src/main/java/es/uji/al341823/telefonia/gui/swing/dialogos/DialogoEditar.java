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
import es.uji.al341823.telefonia.gui.swing.controladores.Controlador;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
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
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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

	private final HashMap<String, JComponent> inputs = new HashMap<>();

	private JComboBox<Object> comboBoxTarifasExtra;
	private JButton btnGuardar;

	private String ELIMINAR;

	private DialogoEditar(Window owner, Cliente cliente, Class<? extends Cliente> tipoCliente) {
		super();

		this.dialog = new JDialog(owner, Dialog.ModalityType.APPLICATION_MODAL);
		this.cliente = cliente;
		this.tipoCliente = tipoCliente;

		if (cliente == null) {
			int size = (this.tipoCliente.equals(Particular.class) ? Particular.getIdDatos().size() : Empresa.getIdDatos().size());
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

		List<String> ids = this.tipoCliente.equals(Particular.class) ? Particular.getIdDatos() : Empresa.getIdDatos();

		for (int col = 0; col < ids.size(); col++) {
			constraints.insets = new Insets(3, 5, 3, 1);
			constraints.anchor = GridBagConstraints.LINE_END;
			constraints.fill = GridBagConstraints.NONE;
			constraints.gridx = 0;
			constraints.gridy++;

			// Etiquetas con el nombre del campo
			String id = ids.get(col);
			JLabel label = new JLabel(id + ":");
			inputPanel.add(label, constraints);


			constraints.insets = new Insets(3, 1, 3, 3);
			constraints.anchor = GridBagConstraints.CENTER;
			constraints.fill = GridBagConstraints.HORIZONTAL;
			constraints.gridx++;

			switch (id) {
				case Cliente.ID_FECHA:
					// Selector fecha
					JSpinner spinnerFecha = new JSpinner(new SpinnerDateModel());
					spinnerFecha.setEditor(new JSpinner.DateEditor(spinnerFecha, "yyyy-MM-dd HH:mm:ss"));

					if (this.cliente == null) spinnerFecha.setValue(new Date());
					else {
						LocalDateTime date = (LocalDateTime) this.cliente.getDatos().get(constraints.gridy).getValue();
						spinnerFecha.setValue(Date.from(date.atZone(ZoneId.systemDefault()).toInstant())); // FIXME
					}

					spinnerFecha.setEnabled(this.cliente == null);
					inputPanel.add(spinnerFecha, constraints);

					this.inputs.put(Cliente.ID_FECHA, spinnerFecha);
					break;

				case Cliente.ID_TARIFA:
					// Selector tarifa basica
					Controlador controlador = this.getControlador();
					LinkedList<Tarifa> tarifasCliente = new LinkedList<>();
					if (this.cliente != null) {
						Tarifa tarifaBase = controlador.getTarifa(this.cliente);

						while (tarifaBase instanceof TarifaExtra) {
							tarifasCliente.addFirst(tarifaBase);
							tarifaBase = ((TarifaExtra) tarifaBase).getTarifaBase();
						}
						tarifasCliente.addFirst(tarifaBase);
					}

					JComboBox<Tarifa> comboBoxTarifas = new JComboBox<>();
					ArrayList<Tarifa> tarifas = controlador.getTarifasBasicas();
					for (Tarifa tarifa : tarifas)
						comboBoxTarifas.addItem(tarifa);
					if (this.cliente != null) {
						comboBoxTarifas.setSelectedItem(tarifasCliente.removeFirst());
						comboBoxTarifas.setEnabled(false);
					}
					inputPanel.add(comboBoxTarifas, constraints);

					this.inputs.put(Cliente.ID_TARIFA, comboBoxTarifas);

					// Selector tarifa extra
					if (this.cliente != null) {
						constraints.gridy++;

						for (Tarifa tarifa : tarifasCliente) {
							JComboBox<Tarifa> comboBox = new JComboBox<>();
							comboBox.addItem(tarifa);
							comboBox.setSelectedIndex(0);
							comboBox.setEnabled(false);
							inputPanel.add(comboBox, constraints);
							constraints.gridy++;
						}

						this.comboBoxTarifasExtra = new JComboBox<>();
						ArrayList<Tarifa> tarifasExtra = controlador.getTarifasExtra(controlador.getTarifa(this.cliente));
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
					break;

				default:
					// Campos de texto para el dato
					JTextField textField = new JTextField();
					if (this.cliente != null) textField.setText(this.cliente.getDatos().get(col).getValue().toString());
					textField.setEditable(this.cliente == null);
					textField.setPreferredSize(new Dimension(250, textField.getPreferredSize().height));
					textField.setCaretPosition(0);
					textField.addFocusListener(new ValidadorDatos(this.tipoDatos.get(col), col));
					inputPanel.add(textField, constraints);

					if (id.equals(Cliente.ID_DIRECCION) && (this.cliente == null)) {
						String placeholder = "CP, Provincia, Población";
						textField.setText(placeholder);
						textField.setForeground(Color.GRAY);
						textField.addFocusListener(new FocusListener() {
							String text = textField.getText();

							@Override
							public void focusGained(FocusEvent e) {
								if (this.text.equals(placeholder)) {
									textField.setText(null);
									textField.setForeground(Color.BLACK);
								}
							}

							@Override
							public void focusLost(FocusEvent e) {
								if (this.text.isEmpty()) {
									textField.setText(placeholder);
									textField.setForeground(Color.GRAY);
								}
							}
						});
					}

					this.inputs.put(id, textField);
					break;
			}
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
			Controlador controlador = DialogoEditar.this.getControlador();

			if (DialogoEditar.this.cliente != null) {
				if (DialogoEditar.this.comboBoxTarifasExtra.getSelectedItem().equals(DialogoEditar.this.ELIMINAR))
					controlador.setTarifa(DialogoEditar.this.cliente, ((TarifaExtra) controlador.getTarifa(DialogoEditar.this.cliente)).getTarifaBase());
				else if (DialogoEditar.this.comboBoxTarifasExtra.getSelectedIndex() != 0)
					controlador.setTarifa(DialogoEditar.this.cliente, (Tarifa) DialogoEditar.this.comboBoxTarifasExtra.getSelectedItem());
			} else {
				HashMap<String, Object> datos = new HashMap<>();

				for (Map.Entry<String, JComponent> entry : DialogoEditar.this.inputs.entrySet()) {
					JComponent input = entry.getValue();
					Object dato = null;

					if (input instanceof JTextField)
						dato = ((JTextField) input).getText();
					else if (input instanceof JSpinner)
						dato = ((JSpinner) input).getValue();
					else if (input instanceof JComboBox)
						dato = ((JComboBox) input).getSelectedItem();

					datos.put(entry.getKey(), dato);
				}
				try {
					controlador.guardarCliente(datos);
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
