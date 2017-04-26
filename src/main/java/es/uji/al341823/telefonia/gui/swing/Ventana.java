/*
 * Copyright (c) 2017. Esta obra está sujeta a la licencia Reconocimiento 4.0 Internacional de Creative Commons.
 * Para ver una copia de esta licencia, visite http://creativecommons.org/licenses/by/4.0/.
 */

package es.uji.al341823.telefonia.gui.swing;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

/**
 * Created by Juanjo on 25/04/2017.
 */
public class Ventana extends JFrame {

	public Ventana() {
		super("Telefonía");
	}

	public void ejecutar() {

		this.setSize(800, 640);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);

		Container container = this.getContentPane();

		/* ---------- Menu ---------- */

		JMenuBar menu = new JMenuBar();

		// Menu Archivo

		JMenu menuArchivo = new JMenu("Archivo");
		menuArchivo.setMnemonic('A');

		JMenuItem itemNuevo = new JMenuItem("Nuevo...");
		itemNuevo.setIcon(new ImageIcon(this.getClass().getResource("/icons/new.png")));
		itemNuevo.setMnemonic('N');
		itemNuevo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK)); // Ctrl + N
		itemNuevo.addActionListener(new Escuchador());
		menuArchivo.add(itemNuevo);

		JMenuItem itemCargar = new JMenuItem("Cargar...");
		itemCargar.setIcon(new ImageIcon(this.getClass().getResource("/icons/open.png")));
		itemCargar.setMnemonic('C');
		itemCargar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK)); // Ctrl + O
		itemCargar.addActionListener(new Escuchador());
		menuArchivo.add(itemCargar);

		JMenuItem itemGuardar = new JMenuItem("Guardar");
		itemGuardar.setIcon(new ImageIcon(this.getClass().getResource("/icons/save.png")));
		itemGuardar.setMnemonic('G');
		itemGuardar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK)); // Ctrl + S
		itemGuardar.addActionListener(new Escuchador());
		menuArchivo.add(itemGuardar);

		JMenuItem itemGuardarComo = new JMenuItem("Guardar como...");
		itemGuardarComo.setMnemonic('O');
		itemGuardarComo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
				InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK)); // Ctrl + Shift + S
		itemGuardarComo.addActionListener(new Escuchador());
		menuArchivo.add(itemGuardarComo);

		menuArchivo.add(new JSeparator());

		JMenuItem itemSalir = new JMenuItem("Salir");
		itemSalir.setIcon(new ImageIcon(this.getClass().getResource("/icons/exit.png")));
		itemSalir.setMnemonic('S');
		itemSalir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, InputEvent.ALT_MASK)); // Alt + F4
		itemSalir.addActionListener(e -> this.dispose());
		menuArchivo.add(itemSalir);

		menu.add(menuArchivo);

		// Menu Editar

		JMenu menuEditar = new JMenu("Editar");
		menuEditar.setMnemonic('E');

		JMenuItem itemCortar = new JMenuItem("Cortar");
		itemCortar.setIcon(new ImageIcon(this.getClass().getResource("/icons/cut.png")));
		itemCortar.setMnemonic('T');
		itemCortar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_MASK)); // Ctrl + X
		itemCortar.addActionListener(new Escuchador());
		menuEditar.add(itemCortar);

		JMenuItem itemCopiar = new JMenuItem("Copiar");
		itemCopiar.setIcon(new ImageIcon(this.getClass().getResource("/icons/copy.png")));
		itemCopiar.setMnemonic('C');
		itemCopiar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK)); // Ctrl + C
		itemCopiar.addActionListener(new Escuchador());
		menuEditar.add(itemCopiar);

		JMenuItem itemPegar = new JMenuItem("Pegar");
		itemPegar.setIcon(new ImageIcon(this.getClass().getResource("/icons/paste.png")));
		itemPegar.setMnemonic('P');
		itemPegar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_MASK)); // Ctrl + V
		itemPegar.addActionListener(new Escuchador());
		menuEditar.add(itemPegar);

		JMenuItem itemBorrar = new JMenuItem("Borrar");
		itemBorrar.setIcon(new ImageIcon(this.getClass().getResource("/icons/delete.png")));
		itemBorrar.setMnemonic('B');
		itemBorrar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0)); // Supr
		itemBorrar.addActionListener(new Escuchador());
		menuEditar.add(itemBorrar);

		menu.add(menuEditar);

		// Menu Ver

		JMenu menuVer = new JMenu("Ver");
		menuVer.setMnemonic('V');

		menu.add(menuVer);

		// Menu Ayuda

		JMenu menuAyuda = new JMenu("Ayuda");
		menuVer.setMnemonic('A');

		JMenuItem itemSobre = new JMenuItem("Sobre Telefonía...");
		itemSobre.setIcon(new ImageIcon(this.getClass().getResource("/icons/info.png")));
		itemSobre.setMnemonic('S');
		itemSobre.addActionListener(new Escuchador());
		menuAyuda.add(itemSobre);

		menu.add(menuAyuda);

		this.setJMenuBar(menu);

		/* ======================================== */

		/* ---------- Panel de pestañas ---------- */

		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.setBorder(new EmptyBorder(30, 10, 10, 10));
		tabbedPane.setPreferredSize(new Dimension(600, 600));

		container.add(tabbedPane, BorderLayout.CENTER);

		/* ----- Clientes ----- */

		JTable tablaClientes = new JTable();
		JScrollPane panelClientes = new JScrollPane(tablaClientes);
		panelClientes.setRequestFocusEnabled(false);

		tabbedPane.addTab("Clientes", panelClientes);

		/* ----- Llamadas ----- */

		JTable tablaLlamadas = new JTable();
		JScrollPane panelLlamadas = new JScrollPane(tablaLlamadas);

		tabbedPane.addTab("Llamadas", panelLlamadas);

		/* ----- Facturas ----- */

		JTable tablaFacturas = new JTable();
		JScrollPane panelFacturas = new JScrollPane(tablaFacturas);

		tabbedPane.addTab("Facturas", panelFacturas);

		/* ======================================== */


		/* -------------------------------------- */

		Label label = new Label("Texto");

		JPanel informacion = new JPanel();
		informacion.add(label);
		informacion.setPreferredSize(new Dimension(200, 600));
		informacion.setBorder(new CompoundBorder(new EmptyBorder(10, 10, 10, 10), BorderFactory.createTitledBorder("title")));

		/* -------------------------------------- */

		JButton nuevo = new JButton("Añadir");
		JButton borrar = new JButton("Borrar");
		JButton editar = new JButton("Editar");

		JPanel acciones = new JPanel();
		acciones.add(nuevo);
		acciones.add(borrar);
		acciones.add(editar);
		acciones.setPreferredSize(new Dimension(800, 40));

		/* -------------------------------------- */

		container.add(informacion, BorderLayout.EAST);
		container.add(acciones, BorderLayout.SOUTH);

	}
}
