/*
 * Copyright (c) 2017. Esta obra está sujeta a la licencia Reconocimiento 4.0 Internacional de Creative Commons.
 * Para ver una copia de esta licencia, visite http://creativecommons.org/licenses/by/4.0/.
 */

package es.uji.al341823.telefonia.gui.swing.dialogos;

import es.uji.al341823.telefonia.api.AdministradorSwing;
import es.uji.al341823.telefonia.api.excepciones.ObjetoNoExisteException;
import es.uji.al341823.telefonia.clientes.Cliente;
import es.uji.al341823.telefonia.facturacion.Factura;
import es.uji.al341823.telefonia.gui.swing.Vista;
import es.uji.al341823.telefonia.llamadas.Llamada;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.Dialog;
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
public class DialogoInfo extends Vista {

	private final JDialog dialog;

	private final JScrollPane scrollPane = new JScrollPane();

	private final Cliente cliente;
	private final String actionCommand;

	public DialogoInfo(Window owner, Cliente cliente, String actionCommand) {
		super();

		this.dialog = new JDialog(owner, Dialog.ModalityType.APPLICATION_MODAL);

		this.cliente = cliente;
		this.actionCommand = actionCommand;
	}

	@Override
	public void generarVista() {
		this.scrollPane.setBorder(new CompoundBorder(new EmptyBorder(5, 5, 0, 5), this.scrollPane.getBorder()));
		this.scrollPane.setPreferredSize(new Dimension(500, 250));
		this.dialog.add(this.scrollPane, BorderLayout.CENTER);

		this.actualizarVista();
		this.generarBotones();

		if (this.actionCommand.equals(INFO_VER_LLAMADAS)) {
			this.dialog.setTitle("Llamadas del cliente: " + this.cliente.getNif());
			this.dialog.setIconImage(AdministradorSwing.getImage("phone"));
		} else if (this.actionCommand.equals(INFO_VER_FACTURAS)) {
			this.dialog.setTitle("Facturas del cliente: " + this.cliente.getNif());
			this.dialog.setIconImage(AdministradorSwing.getImage("receipt"));
		}

		this.dialog.setMinimumSize(new Dimension(300, 200));

		this.dialog.pack();

		this.dialog.setLocationRelativeTo(this.dialog.getOwner());
		this.dialog.setVisible(true);
	}

	@Override
	public void actualizarVista() {

		if (this.actionCommand.equals(INFO_VER_LLAMADAS))
			this.generarTablaLlamadas();
		else
			this.generarTablaFacturas();
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
		modelo.addColumn("Código");
		modelo.addColumn("Nº origen");
		modelo.addColumn("Nº destino");
		modelo.addColumn("Fecha de llamada");
		modelo.addColumn("Duración de llamada");

		try {
			LinkedList<Llamada> llamadas = (LinkedList<Llamada>) this.getControlador().getLlamadas(this.cliente);

			for (Llamada llamada : llamadas) {
				modelo.addRow(new Object[] {
						llamada.getCodigo(),
						llamada.getNumeroOrigen(),
						llamada.getNumeroDestino(),
						llamada.getFecha().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
						llamada.getDuracionLlamada() + " segundos"
				});
			}

		} catch (ObjetoNoExisteException exception) {
			JOptionPane.showMessageDialog(this.dialog,
					"No se pudieron mostrar las llamadas del cliente especificado",
					"Error al mostrar llamadas",
					JOptionPane.ERROR_MESSAGE);
			this.dialog.dispose();
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
		tabla.getTableHeader().setReorderingAllowed(false);
		tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabla.setAutoCreateRowSorter(true);

		DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
		modelo.addColumn("Codigo");
		modelo.addColumn("Tarifa");
		modelo.addColumn("Emisión");
		modelo.addColumn("Periodo");
		modelo.addColumn("Importe");

		try {
			LinkedList<Factura> facturas = (LinkedList<Factura>) this.getControlador().getFacturas(this.cliente);

			for (Factura factura : facturas) {
				modelo.addRow(new Object[] {
						factura.getCodigo(),
						factura.getTarifa(),
						factura.getFecha().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
						factura.getPeriodoFactuacion() + " días",
						factura.getImporte() + "€"
				});
			}

		} catch (ObjetoNoExisteException exception) {
			JOptionPane.showMessageDialog(this.dialog,
					"No se pudieron mostrar las facturas del cliente especificado",
					"Error al mostrar facturas",
					JOptionPane.ERROR_MESSAGE);
			this.dialog.dispose();
		}

		this.scrollPane.setViewportView(tabla);
	}

	private void generarBotones() {
		JPanel buttonPanel = new JPanel();
		this.dialog.add(buttonPanel, BorderLayout.SOUTH);

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
	}

	private class EscuchadorDialogoInfo implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			switch (e.getActionCommand()) {
				case DIALOGO_EMITIR_FACTURA:
					try {
						DialogoInfo.this.getControlador().emitirFactura(DialogoInfo.this.cliente);
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(DialogoInfo.this.dialog,
								"No se pudo emitir una factura para el cliente especificado",
								"Error al emitir factura",
								JOptionPane.ERROR_MESSAGE);
					} finally {
						DialogoInfo.this.actualizarVista();
					}
					break;

				case DIALOGO_NUEVA_LLAMADA:
					Vista dialogo = new DialogoLlamada(DialogoInfo.this.dialog, DialogoInfo.this.cliente);
					dialogo.setControlador(DialogoInfo.this.getControlador());
					dialogo.generarVista();

					DialogoInfo.this.actualizarVista();
					break;

				case DIALOGO_CERRAR:
					DialogoInfo.this.dialog.dispose();
					break;
			}
		}
	}
}
