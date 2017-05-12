/*
 * Copyright (c) 2017. Esta obra está sujeta a la licencia Reconocimiento 4.0 Internacional de Creative Commons.
 * Para ver una copia de esta licencia, visite http://creativecommons.org/licenses/by/4.0/.
 */

package es.uji.al341823.telefonia.gui.swing.dialogos;

import es.uji.al341823.telefonia.api.excepciones.FechaNoValidaExcepcion;
import es.uji.al341823.telefonia.clientes.Cliente;
import es.uji.al341823.telefonia.clientes.Particular;
import es.uji.al341823.telefonia.facturacion.Factura;
import es.uji.al341823.telefonia.gui.swing.controlador.Controlador;
import es.uji.al341823.telefonia.gui.swing.ventanas.VentanaPrincipal;
import es.uji.al341823.telefonia.llamadas.Llamada;

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
import java.awt.Dimension;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import static es.uji.al341823.telefonia.gui.swing.ActionCommands.BUSCAR_CLIENTES;
import static es.uji.al341823.telefonia.gui.swing.ActionCommands.BUSCAR_FACTURAS;
import static es.uji.al341823.telefonia.gui.swing.ActionCommands.BUSCAR_LLAMADAS;
import static es.uji.al341823.telefonia.gui.swing.ActionCommands.DIALOGO_BUSCAR;
import static es.uji.al341823.telefonia.gui.swing.ActionCommands.DIALOGO_CERRAR;

/**
 * @author Juanjo González (al341823)
 * @since 0.4
 */
public class DialogoBuscar extends JDialog {

	private final Controlador controlador;
	private final JSpinner spinnerFechaInicio = new JSpinner(new SpinnerDateModel());
	private final JSpinner spinnerFechaFinal = new JSpinner(new SpinnerDateModel());
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private final JTable tabla = new JTable();

	public DialogoBuscar(Window owner, Controlador controlador) {
		super(owner, ModalityType.APPLICATION_MODAL);
		this.controlador = controlador;
	}

	public void generar() {
		JPanel inputPanel = new JPanel();
		Border border = new CompoundBorder(new EmptyBorder(5, 5, 0, 5), new TitledBorder("Extraer"));
		inputPanel.setBorder(border);
		inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
		this.add(inputPanel, BorderLayout.NORTH);

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

		// ============================================================ //

		JScrollPane scrollPane = new JScrollPane();
		Border border1 = new CompoundBorder(new EmptyBorder(5, 5, 5, 5), scrollPane.getBorder());
		scrollPane.setBorder(border1);
		this.add(scrollPane);

		this.tabla.setModel(new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		});
		this.tabla.getTableHeader().setReorderingAllowed(false);
		this.tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.tabla.setAutoCreateRowSorter(true);
		scrollPane.setViewportView(this.tabla);

		// ============================================================ //

		JPanel buttonPanel = new JPanel();
		this.add(buttonPanel, BorderLayout.SOUTH);

		JButton btnCerrar = new JButton("Cerrar");
		btnCerrar.setActionCommand(DIALOGO_CERRAR);
		btnCerrar.addActionListener(new EscuchadorDialogoBuscar());
		buttonPanel.add(btnCerrar);

		// ============================================================ //
		// ============================================================ //

//		inputPanel.setPreferredSize(inputPanel.getPreferredSize());
//		buttonPanel.setPreferredSize(buttonPanel.getPreferredSize());

		this.setTitle("Buscar");
		this.setIconImage(VentanaPrincipal.getImage("find"));
		this.setPreferredSize(new Dimension(500, 500));
		this.setMinimumSize(new Dimension(450, 300));

		this.pack();

		this.setLocationRelativeTo(this.getOwner());
		this.setVisible(true);
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
					DefaultTableModel modelo = (DefaultTableModel) DialogoBuscar.this.tabla.getModel();
					modelo.setColumnCount(0);
					modelo.setRowCount(0);

					switch (DialogoBuscar.this.buttonGroup.getSelection().getActionCommand()) {
						case BUSCAR_CLIENTES:
							modelo.addColumn("NIF");
							modelo.addColumn("Nombre");
							modelo.addColumn("Apellido");
							modelo.addColumn("Dirección");
							modelo.addColumn("Email");
							modelo.addColumn("Fecha de alta");
							modelo.addColumn("Tarifa contratada");

							try {
								Collection<Cliente> clientes = DialogoBuscar.this.controlador.extraerClientes((Date) DialogoBuscar.this.spinnerFechaInicio.getValue(), (Date) DialogoBuscar.this.spinnerFechaFinal.getValue());
								for (Cliente cliente : clientes) {
									String apellidos = "";

									if (cliente instanceof Particular)
										apellidos = ((Particular) cliente).getApellidos();

									modelo.addRow(new Object[] {
											cliente.getNif(),
											cliente.getNombre(),
											apellidos,
											cliente.getDireccion(),
											cliente.getEmail(),
											cliente.getFecha(),
											cliente.getTarifa(),
											cliente.getTarifa()
									});
								}
							} catch (FechaNoValidaExcepcion fechaNoValidaExcepcion) {
								JOptionPane.showMessageDialog(getOwner(),
										"El intervalo de busqueda selecionado es incorrecto",
										"Error al buscar",
										JOptionPane.ERROR_MESSAGE);
							}
							break;

						case BUSCAR_LLAMADAS:
							modelo.addColumn("Numero de origen");
							modelo.addColumn("Numero de destino");
							modelo.addColumn("Fecha");
							modelo.addColumn("Duración");

							try {
								Collection<Llamada> llamadas = DialogoBuscar.this.controlador.extraerLlamadas((Date) DialogoBuscar.this.spinnerFechaInicio.getValue(), (Date) DialogoBuscar.this.spinnerFechaFinal.getValue());
								for (Llamada llamada : llamadas) {
									modelo.addRow(new Object[] {
											llamada.getNumeroOrigen(),
											llamada.getNumeroDestino(),
											llamada.getFecha(),
											llamada.getDuracionLlamada()
									});
								}
							} catch (FechaNoValidaExcepcion fechaNoValidaExcepcion) {
								JOptionPane.showMessageDialog(getOwner(),
										"El intervalo de busqueda selecionado es incorrecto",
										"Error al buscar",
										JOptionPane.ERROR_MESSAGE);
							}
							break;

						case BUSCAR_FACTURAS:
							modelo.addColumn("Codigo");
							modelo.addColumn("Tarifa");
							modelo.addColumn("Emisión");
							modelo.addColumn("Periodo");
							modelo.addColumn("Importe");

							try {
								Collection<Factura> facturas = DialogoBuscar.this.controlador.extraerFacturas((Date) DialogoBuscar.this.spinnerFechaInicio.getValue(), (Date) DialogoBuscar.this.spinnerFechaFinal.getValue());
								for (Factura factura : facturas) {
									modelo.addRow(new Object[] {
											factura.getCodigo(),
											factura.getTarifa(),
											factura.getFecha(),
											factura.getPeriodoFactuacion(),
											factura.getImporte()
									});
								}
							} catch (FechaNoValidaExcepcion fechaNoValidaExcepcion) {
								JOptionPane.showMessageDialog(getOwner(),
										"El intervalo de busqueda selecionado es incorrecto",
										"Error al buscar",
										JOptionPane.ERROR_MESSAGE);
							}
							break;
					}
					break;
				case DIALOGO_CERRAR:
					DialogoBuscar.this.dispose();
					break;
			}
		}
	}

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
