/*
 * Copyright (c) 2017. Esta obra está sujeta a la licencia Reconocimiento 4.0 Internacional de Creative Commons.
 * Para ver una copia de esta licencia, visite http://creativecommons.org/licenses/by/4.0/.
 */

package es.uji.al341823.telefonia.gui.swing.dialogos;

import es.uji.al341823.telefonia.api.AdministradorDatos;
import es.uji.al341823.telefonia.api.SwingUtils;
import es.uji.al341823.telefonia.api.TipoDato;
import es.uji.al341823.telefonia.api.excepciones.FechaNoValidaExcepcion;
import es.uji.al341823.telefonia.gui.swing.Vista;
import es.uji.al341823.telefonia.gui.swing.tablas.ModeloTablaBusqueda;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

import static es.uji.al341823.telefonia.gui.swing.ActionCommands.BUSCAR_CLIENTES;
import static es.uji.al341823.telefonia.gui.swing.ActionCommands.BUSCAR_FACTURAS;
import static es.uji.al341823.telefonia.gui.swing.ActionCommands.BUSCAR_LLAMADAS;
import static es.uji.al341823.telefonia.gui.swing.ActionCommands.DIALOGO_BUSCAR;
import static es.uji.al341823.telefonia.gui.swing.ActionCommands.DIALOGO_CERRAR;
import static java.awt.Component.CENTER_ALIGNMENT;

/**
 * @author Juanjo González (al341823)
 * @since 0.4
 */
public class DialogoBuscar extends Vista {

	private final JDialog dialog;

	private final JSpinner spinnerFechaInicio = new JSpinner(new SpinnerDateModel());
	private final JSpinner spinnerFechaFinal = new JSpinner(new SpinnerDateModel());
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private final JTable tabla = new JTable();
	private final JTextField txtNif = new JTextField();
	private final JButton btnBuscar = new JButton();

	public DialogoBuscar(Window owner) {
		super();
		this.dialog = new JDialog(owner, Dialog.ModalityType.APPLICATION_MODAL);
	}

	@Override
	public void generarVista() {
		this.generarPanelBusqueda();
		this.generarPanelResultado();

		this.dialog.setTitle("Buscar");
		this.dialog.setIconImage(SwingUtils.getImage("find"));
		this.dialog.setPreferredSize(new Dimension(500, 500));
		this.dialog.setMinimumSize(new Dimension(450, 300));

		this.dialog.pack();

		this.dialog.setLocationRelativeTo(this.dialog.getOwner());
		this.dialog.setVisible(true);
	}

	private void generarPanelBusqueda() {
		JPanel inputPanel = new JPanel();
		Border border = new CompoundBorder(new EmptyBorder(5, 5, 0, 5), new TitledBorder("Extraer"));
		inputPanel.setBorder(border);
		inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
		this.dialog.add(inputPanel, BorderLayout.NORTH);

		JRadioButton radioClientes = new JRadioButton();
		radioClientes.setText("Clientes dados de alta ...");
		radioClientes.setSelected(true);
		radioClientes.setAlignmentX(CENTER_ALIGNMENT);
		radioClientes.setActionCommand(BUSCAR_CLIENTES);
		radioClientes.addActionListener(new EscuchadorRadio());
		inputPanel.add(radioClientes);
		this.buttonGroup.add(radioClientes);

		JRadioButton radioLlamadas = new JRadioButton();
		radioLlamadas.setText("Llamadas realizadas ...");
		radioLlamadas.setAlignmentX(CENTER_ALIGNMENT);
		radioLlamadas.setActionCommand(BUSCAR_LLAMADAS);
		radioLlamadas.addActionListener(new EscuchadorRadio());
		inputPanel.add(radioLlamadas);
		this.buttonGroup.add(radioLlamadas);

		JRadioButton radioFacturas = new JRadioButton();
		radioFacturas.setText("Facturas emitidas ...");
		radioFacturas.setAlignmentX(CENTER_ALIGNMENT);
		radioFacturas.setActionCommand(BUSCAR_FACTURAS);
		radioFacturas.addActionListener(new EscuchadorRadio());
		inputPanel.add(radioFacturas);
		this.buttonGroup.add(radioFacturas);


		inputPanel.add(Box.createRigidArea(new Dimension(0, 5)));


		JPanel panelNif = new JPanel();
		panelNif.setLayout(new BoxLayout(panelNif, BoxLayout.X_AXIS));
		inputPanel.add(panelNif);

		panelNif.add(Box.createRigidArea(new Dimension(50, 0)));

		panelNif.add(new JLabel("... por el cliente con NIF "));

		this.txtNif.setEnabled(false);
		this.txtNif.addFocusListener(new Validador());
		panelNif.add(this.txtNif);

		panelNif.add(Box.createRigidArea(new Dimension(50, 0)));


		inputPanel.add(Box.createRigidArea(new Dimension(0, 5)));


		JPanel datePanel = new JPanel();
		datePanel.setLayout(new BoxLayout(datePanel, BoxLayout.X_AXIS));
		inputPanel.add(datePanel);

		datePanel.add(Box.createRigidArea(new Dimension(20, 0)));

		datePanel.add(new JLabel("... entre "));

		this.spinnerFechaInicio.setEditor(new JSpinner.DateEditor(this.spinnerFechaInicio, "yyyy-MM-dd HH:mm:ss"));
		this.spinnerFechaInicio.addChangeListener(new EscuchadorFechas());
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -1);
		Date result = cal.getTime();
		this.spinnerFechaInicio.setValue(result);
		datePanel.add(this.spinnerFechaInicio);

		datePanel.add(new JLabel(" y "));

		this.spinnerFechaFinal.setEditor(new JSpinner.DateEditor(this.spinnerFechaFinal, "yyyy-MM-dd HH:mm:ss"));
		this.spinnerFechaFinal.addChangeListener(new EscuchadorFechas());
		datePanel.add(this.spinnerFechaFinal);

		datePanel.add(Box.createRigidArea(new Dimension(20, 0)));

		inputPanel.add(Box.createRigidArea(new Dimension(0, 5)));

		this.btnBuscar.setText("Buscar");
		this.btnBuscar.setActionCommand(DIALOGO_BUSCAR);
		this.btnBuscar.setAlignmentX(CENTER_ALIGNMENT);
		this.btnBuscar.addActionListener(new EscuchadorDialogoBuscar());
		inputPanel.add(this.btnBuscar);
	}

	private void generarPanelResultado() {
		JScrollPane scrollPane = new JScrollPane();
		Border border1 = new CompoundBorder(new EmptyBorder(5, 5, 5, 5), scrollPane.getBorder());
		scrollPane.setBorder(border1);
		this.dialog.add(scrollPane);

		this.tabla.getTableHeader().setReorderingAllowed(false);
		this.tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.tabla.setAutoCreateRowSorter(true);
		scrollPane.setViewportView(this.tabla);

		// ============================================================ //

		JPanel buttonPanel = new JPanel();
		this.dialog.add(buttonPanel, BorderLayout.SOUTH);

		JButton btnCerrar = new JButton("Cerrar");
		btnCerrar.setActionCommand(DIALOGO_CERRAR);
		btnCerrar.addActionListener(new EscuchadorDialogoBuscar());
		buttonPanel.add(btnCerrar);
	}

	@Override
	public void actualizarVista() {
		this.tabla.setModel(new DefaultTableModel());

		String actionCommand = this.buttonGroup.getSelection().getActionCommand();

		String nif = this.txtNif.getText();

		Date inicio = (Date) this.spinnerFechaInicio.getValue();
		Date fin = (Date) this.spinnerFechaFinal.getValue();

		LocalDateTime inicioLocal = LocalDateTime.ofInstant(inicio.toInstant(), ZoneId.systemDefault());
		LocalDateTime finLocal = LocalDateTime.ofInstant(fin.toInstant(), ZoneId.systemDefault());

		try {
			this.tabla.setModel(new ModeloTablaBusqueda(actionCommand, nif, inicioLocal, finLocal, this.getModelo()));
		} catch (FechaNoValidaExcepcion fechaNoValidaExcepcion) {
			JOptionPane.showMessageDialog(this.dialog,
					"El intervalo de busqueda selecionado es incorrecto",
					"Error al buscar",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * @author Juanjo González (al341823)
	 * @since 0.4
	 */
	private class EscuchadorDialogoBuscar implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			switch (e.getActionCommand()) {
				case DIALOGO_BUSCAR:
					DialogoBuscar.this.actualizarVista();
					break;
				case DIALOGO_CERRAR:
					DialogoBuscar.this.dialog.dispose();
					break;
			}
		}

	}

	/**
	 * Se encarga de ajustar los limites inferior y superioor de las fechas para que no se de un periodo imposible
	 *
	 * @author Juanjo González (al341823)
	 * @since 0.4
	 */
	private class EscuchadorFechas implements ChangeListener {
		@Override
		public void stateChanged(ChangeEvent e) {
			SpinnerDateModel modeloInicio = (SpinnerDateModel) DialogoBuscar.this.spinnerFechaInicio.getModel();
			SpinnerDateModel modeloFin = (SpinnerDateModel) DialogoBuscar.this.spinnerFechaFinal.getModel();

			modeloInicio.setEnd((Date) modeloFin.getValue());
			modeloFin.setStart((Date) modeloInicio.getValue());
		}
	}

	/**
	 * @author Juanjo González (al341823)
	 * @since 0.4
	 */
	private class EscuchadorRadio implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String actionCommand = e.getActionCommand();
			boolean enabled = !actionCommand.equals(BUSCAR_CLIENTES);

			if (enabled)
				DialogoBuscar.this.txtNif.setText(null);

			DialogoBuscar.this.txtNif.setEnabled(enabled);
			DialogoBuscar.this.txtNif.setBackground(UIManager.getColor("TextField.background"));
			DialogoBuscar.this.btnBuscar.setEnabled(!enabled);
		}
	}

	/**
	 * @author Juanjo González (al341823)
	 * @since 0.4
	 */
	private class Validador implements FocusListener {

		private final Color colorValorIncorrecto = new Color(0xea7070);

		@Override
		public void focusGained(FocusEvent e) {
			DialogoBuscar.this.txtNif.setBackground(UIManager.getColor("TextField.background"));
		}

		@Override
		public void focusLost(FocusEvent e) {

			if (AdministradorDatos.esDatoValido(DialogoBuscar.this.txtNif.getText(), TipoDato.NIF))
				DialogoBuscar.this.btnBuscar.setEnabled(true);
			else {
				DialogoBuscar.this.txtNif.setBackground(this.colorValorIncorrecto);
				DialogoBuscar.this.btnBuscar.setEnabled(false);
			}
		}
	}
}
