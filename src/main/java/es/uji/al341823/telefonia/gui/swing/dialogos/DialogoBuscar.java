/*
 * Copyright (c) 2017. Esta obra está sujeta a la licencia Reconocimiento 4.0 Internacional de Creative Commons.
 * Para ver una copia de esta licencia, visite http://creativecommons.org/licenses/by/4.0/.
 */

package es.uji.al341823.telefonia.gui.swing.dialogos;

import es.uji.al341823.telefonia.gui.swing.controlador.Controlador;
import es.uji.al341823.telefonia.gui.swing.ventanas.VentanaPrincipal;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static es.uji.al341823.telefonia.gui.swing.ActionCommands.DIALOGO_CERRAR;

/**
 * @author Juanjo González (al341823)
 * @since 0.4
 */
public class DialogoBuscar extends JDialog {

	private final Controlador controlador;

	public DialogoBuscar(Window owner, Controlador controlador) {
		super(owner, ModalityType.APPLICATION_MODAL);
		this.controlador = controlador;
	}

	public void generar() {
		JPanel inputPanel = new JPanel();
		this.add(inputPanel, BorderLayout.NORTH);

		JTextField txtBuscar = new JTextField();
		inputPanel.add(txtBuscar);

		JButton btnBuscar = new JButton();
		btnBuscar.setText("Buscar");
		inputPanel.add(btnBuscar);

		ButtonGroup group = new ButtonGroup();

		JRadioButton radioClientes = new JRadioButton();
		radioClientes.setText("Clientes");
		radioClientes.setSelected(true);
		inputPanel.add(radioClientes);
		group.add(radioClientes);

		JRadioButton radioLlamadas = new JRadioButton();
		radioLlamadas.setText("Llamadas");
		inputPanel.add(radioLlamadas);
		group.add(radioLlamadas);

		JRadioButton radioFacturas = new JRadioButton();
		radioFacturas.setText("Facturas");
		inputPanel.add(radioFacturas);
		group.add(radioFacturas);

		// ============================================================ //

		JPanel displayPanel = new JPanel();
		this.add(displayPanel, BorderLayout.CENTER);

		JScrollPane scrollPane = new JScrollPane();
		displayPanel.add(scrollPane);

		JTable tabla = new JTable();
		scrollPane.setViewportView(tabla);

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
		this.setMinimumSize(new Dimension(300, 200));

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
				case DIALOGO_CERRAR:
					DialogoBuscar.this.dispose();
					break;
			}
		}
	}
}
