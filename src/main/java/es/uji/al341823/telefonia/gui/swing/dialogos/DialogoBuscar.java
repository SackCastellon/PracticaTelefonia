/*
 * Copyright (c) 2017. Esta obra está sujeta a la licencia Reconocimiento 4.0 Internacional de Creative Commons.
 * Para ver una copia de esta licencia, visite http://creativecommons.org/licenses/by/4.0/.
 */

package es.uji.al341823.telefonia.gui.swing.dialogos;

import es.uji.al341823.telefonia.api.AdministradorSwing;
import es.uji.al341823.telefonia.api.excepciones.FechaNoValidaExcepcion;
import es.uji.al341823.telefonia.gui.swing.Vista;
import es.uji.al341823.telefonia.gui.swing.tablas.ModeloTablaBusqueda;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerDateModel;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

	public DialogoBuscar(Window owner) {
		super();
		this.dialog = new JDialog(owner, Dialog.ModalityType.APPLICATION_MODAL);
	}

	@Override
	public void generarVista() {
		this.generarPanelBusqueda();
		this.generarPanelResultado();

//		inputPanel.setPreferredSize(inputPanel.getPreferredSize());
//		buttonPanel.setPreferredSize(buttonPanel.getPreferredSize());

		this.dialog.setTitle("Buscar");
		this.dialog.setIconImage(AdministradorSwing.getImage("find"));
		this.dialog.setPreferredSize(new Dimension(500, 500));
		this.dialog.setMinimumSize(new Dimension(450, 300));

		this.dialog.pack();

		this.dialog.setLocationRelativeTo(this.dialog.getOwner());
		this.dialog.setVisible(true);
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

		Date inicio = (Date) this.spinnerFechaInicio.getValue();
		Date fin = (Date) this.spinnerFechaFinal.getValue();

		LocalDateTime inicioLocal = LocalDateTime.ofInstant(inicio.toInstant(), ZoneId.systemDefault());
		LocalDateTime finLocal = LocalDateTime.ofInstant(fin.toInstant(), ZoneId.systemDefault());

		try {
			this.tabla.setModel(new ModeloTablaBusqueda(actionCommand, inicioLocal, finLocal));
		} catch (FechaNoValidaExcepcion fechaNoValidaExcepcion) {
			JOptionPane.showMessageDialog(this.dialog,
					"El intervalo de busqueda selecionado es incorrecto",
					"Error al buscar",
					JOptionPane.ERROR_MESSAGE);
		}
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
		inputPanel.add(radioClientes);
		this.buttonGroup.add(radioClientes);

		JRadioButton radioLlamadas = new JRadioButton();
		radioLlamadas.setText("Llamadas realizadas ...");
		radioLlamadas.setAlignmentX(CENTER_ALIGNMENT);
		radioLlamadas.setActionCommand(BUSCAR_LLAMADAS);
		inputPanel.add(radioLlamadas);
		this.buttonGroup.add(radioLlamadas);

		JRadioButton radioFacturas = new JRadioButton();
		radioFacturas.setText("Facturas emitidas ...");
		radioFacturas.setAlignmentX(CENTER_ALIGNMENT);
		radioFacturas.setActionCommand(BUSCAR_FACTURAS);
		inputPanel.add(radioFacturas);
		this.buttonGroup.add(radioFacturas);


		inputPanel.add(Box.createRigidArea(new Dimension(0, 5)));


		JPanel datePanel = new JPanel();
		datePanel.setLayout(new BoxLayout(datePanel, BoxLayout.X_AXIS));
		inputPanel.add(datePanel);

		datePanel.add(Box.createRigidArea(new Dimension(20, 0)));

		datePanel.add(new JLabel("... entre "));

		this.spinnerFechaInicio.setEditor(new JSpinner.DateEditor(this.spinnerFechaInicio, "yyyy-MM-dd HH:mm:ss"));
		this.spinnerFechaInicio.addChangeListener(new EcuchadorFechas());
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -1);
		Date result = cal.getTime();
		this.spinnerFechaInicio.setValue(result);
		datePanel.add(this.spinnerFechaInicio);

		datePanel.add(new JLabel(" y "));

		this.spinnerFechaFinal.setEditor(new JSpinner.DateEditor(this.spinnerFechaFinal, "yyyy-MM-dd HH:mm:ss"));
		this.spinnerFechaFinal.addChangeListener(new EcuchadorFechas());
		datePanel.add(this.spinnerFechaFinal);

		datePanel.add(Box.createRigidArea(new Dimension(20, 0)));

		inputPanel.add(Box.createRigidArea(new Dimension(0, 5)));

		JButton btnBuscar = new JButton();
		btnBuscar.setText("Buscar");
		btnBuscar.setActionCommand(DIALOGO_BUSCAR);
		btnBuscar.setAlignmentX(CENTER_ALIGNMENT);
		btnBuscar.addActionListener(new EscuchadorDialogoBuscar());
		inputPanel.add(btnBuscar);
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
	private class EcuchadorFechas implements ChangeListener {
		@Override
		public void stateChanged(ChangeEvent e) {
			SpinnerDateModel modeloInicio = (SpinnerDateModel) DialogoBuscar.this.spinnerFechaInicio.getModel();
			SpinnerDateModel modeloFin = (SpinnerDateModel) DialogoBuscar.this.spinnerFechaFinal.getModel();

			modeloInicio.setEnd((Date) modeloFin.getValue());
			modeloFin.setStart((Date) modeloInicio.getValue());
		}
	}
}
