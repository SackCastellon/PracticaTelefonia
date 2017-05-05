/*
 * Copyright (c) 2017. Esta obra está sujeta a la licencia Reconocimiento 4.0 Internacional de Creative Commons.
 * Para ver una copia de esta licencia, visite http://creativecommons.org/licenses/by/4.0/.
 */

package es.uji.al341823.telefonia.gui.swing.dialogos;

import es.uji.al341823.telefonia.api.excepciones.ClienteNoExisteExcepcion;
import es.uji.al341823.telefonia.facturacion.Factura;
import es.uji.al341823.telefonia.gui.swing.controlador.Controlador;
import es.uji.al341823.telefonia.gui.swing.ventanas.VentanaPrincipal;
import es.uji.al341823.telefonia.llamadas.Llamada;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;

import static es.uji.al341823.telefonia.gui.swing.ActionCommands.DIALOGO_CERRAR;
import static es.uji.al341823.telefonia.gui.swing.ActionCommands.DIALOGO_EMITIR_FACTURA;
import static es.uji.al341823.telefonia.gui.swing.ActionCommands.DIALOGO_NUEVA_LLAMADA;
import static es.uji.al341823.telefonia.gui.swing.ActionCommands.INFO_VER_FACTURAS;
import static es.uji.al341823.telefonia.gui.swing.ActionCommands.INFO_VER_LLAMADAS;

/**
 * @author Juanjo González (al341823)
 * @since 0.4
 */
public class DialogoInfo extends JDialog {

	private final String nif;
	private final String actionCommand;
	private final Controlador controlador;
	private final JScrollPane scrollPane = new JScrollPane();

	public DialogoInfo(Window owner, String nif, String actionCommand, Controlador controlador) {
		super(owner, ModalityType.APPLICATION_MODAL);

		this.nif = nif;
		this.actionCommand = actionCommand;
		this.controlador = controlador;
	}

	public void generar() {
		this.setMinimumSize(new Dimension(300, 200));
		this.scrollPane.setPreferredSize(new Dimension(500, 250));
		this.add(this.scrollPane, BorderLayout.CENTER);

		if (this.actionCommand.equals(INFO_VER_LLAMADAS))
			this.generarTablaLlamadas();
		else
			this.generarTablaFacturas();


		// ============================================================ //

		JPanel buttonPanel = new JPanel();
		this.add(buttonPanel, BorderLayout.SOUTH);

		if (this.actionCommand.equals(INFO_VER_FACTURAS)) {
			JButton btnEmitirFactura = new JButton("Emitir factura");
			btnEmitirFactura.setActionCommand(DIALOGO_EMITIR_FACTURA);
			btnEmitirFactura.addActionListener(new EscuchadorDialogoInfo());
			buttonPanel.add(btnEmitirFactura);
		} else {
			JButton btnNuevaLlamada = new JButton("Añadir llamada");
			btnNuevaLlamada.setActionCommand(DIALOGO_NUEVA_LLAMADA);
			btnNuevaLlamada.addActionListener(new EscuchadorDialogoInfo());
			buttonPanel.add(btnNuevaLlamada);
		}

		JButton btnCerrar = new JButton("Cerrar");
		btnCerrar.setActionCommand(DIALOGO_CERRAR);
		btnCerrar.addActionListener(new EscuchadorDialogoInfo());
		buttonPanel.add(btnCerrar);

		buttonPanel.setPreferredSize(buttonPanel.getPreferredSize());

		// ============================================================ //
		// ============================================================ //

		if (this.actionCommand.equals(INFO_VER_LLAMADAS)) {
			this.setTitle("Llamadas del cliente: " + this.nif);
			this.setIconImage(VentanaPrincipal.getImage("phone"));
		} else if (this.actionCommand.equals(INFO_VER_FACTURAS)) {
			this.setTitle("Facturas del cliente: " + this.nif);
			this.setIconImage(VentanaPrincipal.getImage("receipt"));
		}

		this.setMinimumSize(new Dimension(300, 200));

		this.pack();

		this.setLocationRelativeTo(this.getOwner());
		this.setVisible(true);
	}

	private void generarTablaLlamadas() {
		JTable tabla = new JTable();
		tabla.setModel(new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		});
		tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
		modelo.addColumn("Nº origen");
		modelo.addColumn("Nº destino");
		modelo.addColumn("Fecha de llamada");
		modelo.addColumn("Duración de llamada");

		try {
			LinkedList<Llamada> llamadas = this.controlador.getLlamadas(this.nif);

			for (Llamada llamada : llamadas) {
				modelo.addRow(new Object[] {
						llamada.getNumeroOrigen(),
						llamada.getNumeroDestino(),
						llamada.getFecha().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
						llamada.getDuracionLlamada() + " segundos"
				});
			}

		} catch (ClienteNoExisteExcepcion clienteNoExisteExcepcion) {
			JOptionPane.showMessageDialog(this,
					"No se pudieron mostrar las llamadas del cliente especificado",
					"Error al mostrar llamadas",
					JOptionPane.ERROR_MESSAGE);
			this.dispose();
		}

		this.scrollPane.setViewportView(tabla);
	}

	private void generarTablaFacturas() {
		JTable tabla = new JTable();
		tabla.setModel(new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		});
		tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
		modelo.addColumn("Codigo");
		modelo.addColumn("Tarifa");
		modelo.addColumn("Emisión");
		modelo.addColumn("Periodo");
		modelo.addColumn("Importe");

		try {
			LinkedList<Factura> facturas = this.controlador.getFacturas(this.nif);

			for (Factura factura : facturas) {
				modelo.addRow(new Object[] {
						factura.getCodigo(),
						factura.getTarifa(),
						factura.getFecha().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
						factura.getPeriodoFactuacion() + " días",
						factura.getImporte() + "€"
				});
			}

		} catch (ClienteNoExisteExcepcion clienteNoExisteExcepcion) {
			JOptionPane.showMessageDialog(this,
					"No se pudieron mostrar las facturas del cliente especificado",
					"Error al mostrar facturas",
					JOptionPane.ERROR_MESSAGE);
			this.dispose();
		}

		this.scrollPane.setViewportView(tabla);
	}

	private class EscuchadorDialogoInfo implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			switch (e.getActionCommand()) {
				case DIALOGO_EMITIR_FACTURA:
					try {
						DialogoInfo.this.controlador.emitirFactura(DialogoInfo.this.nif);
					} catch (ClienteNoExisteExcepcion clienteNoExisteExcepcion) {
						JOptionPane.showMessageDialog(DialogoInfo.this,
								"No se pudo emitir una factura para el cliente especificado",
								"Error al emitir factura",
								JOptionPane.ERROR_MESSAGE);
					} finally {
						DialogoInfo.this.generarTablaFacturas();
					}
					break;

				case DIALOGO_NUEVA_LLAMADA:
					DialogoLlamada dialogo = new DialogoLlamada(DialogoInfo.this);
					dialogo.generar();
					try {
						Llamada llamada = dialogo.getLlamada();
						DialogoInfo.this.controlador.altaLlamada(DialogoInfo.this.nif, llamada);
					} catch (ClienteNoExisteExcepcion clienteNoExisteExcepcion) {
						JOptionPane.showMessageDialog(DialogoInfo.this,
								"No se pudo añadir la llamada al cliente especificado",
								"Error al añadir llamada",
								JOptionPane.ERROR_MESSAGE);
					} finally {
						DialogoInfo.this.generarTablaLlamadas();
					}
					break;

				case DIALOGO_CERRAR:
					DialogoInfo.this.dispose();
					break;
			}
		}
	}
}
