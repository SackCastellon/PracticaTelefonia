/*
 * Copyright (c) 2017. Esta obra está sujeta a la licencia Reconocimiento 4.0 Internacional de Creative Commons.
 * Para ver una copia de esta licencia, visite http://creativecommons.org/licenses/by/4.0/.
 */

package es.uji.al341823.telefonia.gui.swing.ventanas;

import es.uji.al341823.telefonia.gui.swing.controlador.Controlador;
import es.uji.al341823.telefonia.gui.swing.dialogos.DialogoBuscar;
import es.uji.al341823.telefonia.gui.swing.dialogos.DialogoEditar;
import es.uji.al341823.telefonia.gui.swing.dialogos.DialogoInfo;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.net.URL;

import static es.uji.al341823.telefonia.gui.swing.ActionCommands.*;

/**
 * @author Juanjo González (al341823)
 * @since 0.4
 */
public class VentanaPrincipal {

	private static final String titulo = "Telefonía";

	private final JFrame frame;

	private final JPanel panelIzquierda = new JPanel();
	private final JTabbedPane tabbedPaneClientes = new JTabbedPane();
	private final JPanel panelBotonesClientes = new JPanel();

	private final JPanel panelDerecha = new JPanel();
	private final JPanel panelInfo = new JPanel();
	private final JPanel panelBotonesInfo = new JPanel();
	private final JButton btnVerLlamadas = new JButton();
	private final JButton btnVerFacturas = new JButton();

	public final JTable tablaParticulares = new JTable();
	public final JTable tablaEmpresas = new JTable();

	private Controlador controlador;

	private final JButton btnEditar = new JButton();
	private final JButton btnBorrar = new JButton();

	public VentanaPrincipal() {
		super();
		this.frame = new JFrame();
		this.frame.setLayout(new BorderLayout(5, 5));
	}

	public void setControlador(Controlador controlador) {
		this.controlador = controlador;
	}

	public void generar() {

		this.generarBarraMenu(); // Genera la barra de menú superior

		this.generarPanelIzquierda(); // Genera el panel de la iquierda
		this.generarPanelDerecha(); // Genera el panel de la derecha

		this.generarVentana(); // Ultimos ajustes antes de mostrar la ventana
	}

	private void generarBarraMenu() {

		// Barra de Menú
		JMenuBar menu = new JMenuBar();
		this.frame.setJMenuBar(menu);

		// ============================================================ //

		// Menu Archivo
		JMenu menuArchivo = new JMenu("Archivo");
		menuArchivo.setMnemonic('A');
		menu.add(menuArchivo);

		// Item Nuevo
		JMenuItem itemNuevo = new JMenuItem("Nuevo...");
		itemNuevo.setIcon(VentanaPrincipal.getIcon("new"));
		itemNuevo.setMnemonic('N');
		itemNuevo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK)); // Ctrl + N
		itemNuevo.setActionCommand(ARCHIVO_NUEVO);
		itemNuevo.addActionListener(new EscuchadorVentanaPrincipal());
		menuArchivo.add(itemNuevo);

		// Item Cargar
		JMenuItem itemCargar = new JMenuItem("Abrir...");
		itemCargar.setIcon(VentanaPrincipal.getIcon("open"));
		itemCargar.setMnemonic('B');
		itemCargar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK)); // Ctrl + O
		itemCargar.setActionCommand(ARCHIVO_ABRIR);
		itemCargar.addActionListener(new EscuchadorVentanaPrincipal());
		menuArchivo.add(itemCargar);

		// Item Guardar
		JMenuItem itemGuardar = new JMenuItem("Guardar");
		itemGuardar.setIcon(VentanaPrincipal.getIcon("save"));
		itemGuardar.setMnemonic('G');
		itemGuardar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK)); // Ctrl + S
		itemGuardar.setActionCommand(ARCHIVO_GUARDAR);
		itemGuardar.addActionListener(new EscuchadorVentanaPrincipal());
		menuArchivo.add(itemGuardar);

		// Item Guardar como
		JMenuItem itemGuardarComo = new JMenuItem("Guardar como...");
		itemGuardarComo.setMnemonic('O');
		itemGuardarComo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
				InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK)); // Ctrl + Shift + S
		itemGuardarComo.setActionCommand(ARCHIVO_GUARDAR_COMO);
		itemGuardarComo.addActionListener(new EscuchadorVentanaPrincipal());
		menuArchivo.add(itemGuardarComo);

		// Separador
		menuArchivo.add(new JSeparator());

		// Item Salir
		JMenuItem itemSalir = new JMenuItem("Salir");
		itemSalir.setIcon(VentanaPrincipal.getIcon("exit"));
		itemSalir.setMnemonic('S');
		itemSalir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, InputEvent.ALT_MASK)); // Alt + F4
		itemSalir.setActionCommand(ARCHIVO_SALIR);
		itemSalir.addActionListener(new EscuchadorVentanaPrincipal());
		menuArchivo.add(itemSalir);

		// ============================================================ //

		// Menu Editar
		JMenu menuEditar = new JMenu("Editar");
		menuEditar.setMnemonic('E');
		menu.add(menuEditar);

		// Item Buscar
		JMenuItem itemBuscar = new JMenuItem("Buscar");
		itemBuscar.setIcon(VentanaPrincipal.getIcon("find"));
		itemBuscar.setMnemonic('B');
		itemBuscar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_MASK)); // Ctrl + N
		itemBuscar.setActionCommand(EDITAR_BUSCAR);
		itemBuscar.addActionListener(new EscuchadorVentanaPrincipal());
		menuEditar.add(itemBuscar);

		// ============================================================ //

		// Menu Ver
		JMenu menuVer = new JMenu("Ver");
		menuVer.setMnemonic('V');
		menu.add(menuVer);

		// Submenú temas
		JMenu submenuTema = new JMenu("Tema");
		submenuTema.setMnemonic('T');
		submenuTema.setIcon(VentanaPrincipal.getIcon("theme"));
		submenuTema.addActionListener(new EscuchadorVentanaPrincipal());
		menuVer.add(submenuTema);

		ButtonGroup groupTema = new ButtonGroup();

		String temaActual = UIManager.getLookAndFeel().getClass().getName();

		// Items de cada uno de los temas disponibles
		for (UIManager.LookAndFeelInfo lookAndFeelInfo : UIManager.getInstalledLookAndFeels()) {
			String nombreTema = lookAndFeelInfo.getName();
			boolean activo = temaActual.equals(lookAndFeelInfo.getClassName());

			JRadioButtonMenuItem tema = new JRadioButtonMenuItem(nombreTema, activo);
			tema.addActionListener(e -> this.cambiarTema(lookAndFeelInfo.getClassName()));
			submenuTema.add(tema);
			groupTema.add(tema);
		}

		// Separador
		submenuTema.add(new JSeparator());

		// Panel para el aviso
		JPanel avisoTemaPanel = new JPanel();
		submenuTema.add(avisoTemaPanel);

		// Etiqueta aviso
		JLabel avisoTema = new JLabel("<html>Si cambia el tema se reestablcerán<br>el tamaño y la posición iniciales de<br>la ventana</html>");
		avisoTemaPanel.add(avisoTema);

		// ============================================================ //

		// Menu Ayuda
		JMenu menuAyuda = new JMenu("Ayuda");
		menuAyuda.setMnemonic('Y');
		menu.add(menuAyuda);

		// Item Sobre telefonía
		JMenuItem itemSobre = new JMenuItem("Sobre Telefonía...");
		itemSobre.setIcon(VentanaPrincipal.getIcon("info"));
		itemSobre.setMnemonic('S');
		itemSobre.setActionCommand(AYUDA_SOBRE);
		itemSobre.addActionListener(new EscuchadorVentanaPrincipal());
		menuAyuda.add(itemSobre);
	}

	private void generarPanelIzquierda() {
		this.panelIzquierda.setBorder(new EmptyBorder(5, 5, 0, 0));
		this.panelIzquierda.setLayout(new BoxLayout(this.panelIzquierda, BoxLayout.Y_AXIS));
		this.frame.add(this.panelIzquierda, BorderLayout.CENTER);

		// Panel de pestañas con clientes
		this.tabbedPaneClientes.setPreferredSize(new Dimension(550, 350));
		this.tabbedPaneClientes.addChangeListener(new EscuchadorTablas());
		this.panelIzquierda.add(this.tabbedPaneClientes);

		this.generarTablaParticulares();
		this.generarTablaEmpresas();
		this.generarBotonesTablas();
	}

	private void generarTablaParticulares() {
		int[] anchos = {80, 100, 125, 250, 125, 125, 150};
		String[] nombres = {"NIF", "Nombre", "Apellido", "Dirección", "Email", "Fecha de alta", "Tarifa contratada"};

		// Scroll pane
		JScrollPane scroll = new JScrollPane();
		this.tabbedPaneClientes.addTab("Particulares", scroll);

		// Tabla
		this.tablaParticulares.setModel(new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		});
		this.tablaParticulares.getTableHeader().setReorderingAllowed(false);
		this.tablaParticulares.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		this.tablaParticulares.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.tablaParticulares.getSelectionModel().addListSelectionListener(new EscuchadorTablas());

		// Establece el nombre de la columna
		DefaultTableModel modelo = (DefaultTableModel) this.tablaParticulares.getModel();
		for (String nombre : nombres) modelo.addColumn(nombre);

		// Establece el ancho de la columna
		TableColumnModel modeloColumna = this.tablaParticulares.getColumnModel();
		for (int i = 0; i < anchos.length; i++) {
			modeloColumna.getColumn(i).setPreferredWidth(anchos[i]);
		}

		scroll.setViewportView(this.tablaParticulares);
	}

	private void generarTablaEmpresas() {
		int[] anchos = {80, 100, 250, 125, 125, 150};
		String[] nombres = {"NIF", "Nombre", "Dirección", "Email", "Fecha de alta", "Tarifa contratada"};

		// Scroll pane
		JScrollPane scroll = new JScrollPane();
		this.tabbedPaneClientes.addTab("Empresas", scroll);

		// Tabla
		this.tablaEmpresas.setModel(new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		});
		this.tablaEmpresas.getTableHeader().setReorderingAllowed(false);
		this.tablaEmpresas.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		this.tablaEmpresas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.tablaEmpresas.getSelectionModel().addListSelectionListener(new EscuchadorTablas());

		// Establece el nombre de la columna
		DefaultTableModel modelo = (DefaultTableModel) this.tablaEmpresas.getModel();
		for (String nombre : nombres) modelo.addColumn(nombre);

		// Establece el ancho de la columna
		TableColumnModel modeloColumna = this.tablaEmpresas.getColumnModel();
		for (int i = 0; i < anchos.length; i++) {
			modeloColumna.getColumn(i).setPreferredWidth(anchos[i]);
		}

		scroll.setViewportView(this.tablaEmpresas);
	}

	private void generarBotonesTablas() {
		this.panelBotonesClientes.setBorder(new EmptyBorder(2, 2, 2, 2));
		this.panelIzquierda.add(this.panelBotonesClientes);

		// Botón Nuevo
		JButton btnNuevo = new JButton("Nuevo");
		btnNuevo.setActionCommand(TABLA_NUEVO);
		btnNuevo.addActionListener(new EscuchadorBotonesTabla());
		this.panelBotonesClientes.add(btnNuevo);

		// Botón Editar
		this.btnEditar.setText("Editar");
		this.btnEditar.setEnabled(false);
		this.btnEditar.setActionCommand(TABLA_EDITAR_TARIFA);
		this.btnEditar.addActionListener(new EscuchadorBotonesTabla());
		this.panelBotonesClientes.add(this.btnEditar);

		// Botón Borrar
		this.btnBorrar.setText("Borrar");
		this.btnBorrar.setEnabled(false);
		this.btnBorrar.setActionCommand(TABLA_BORRAR);
		this.btnBorrar.addActionListener(new EscuchadorBotonesTabla());
		this.panelBotonesClientes.add(this.btnBorrar);

		// Fija el tamaño del panel botones de acción
		this.panelBotonesClientes.setMaximumSize(this.panelBotonesClientes.getPreferredSize());
	}

	private void generarPanelDerecha() {
		this.panelDerecha.setBorder(new EmptyBorder(5, 0, 0, 5));
		this.panelDerecha.setLayout(new BoxLayout(this.panelDerecha, BoxLayout.Y_AXIS));
		this.frame.add(this.panelDerecha, BorderLayout.EAST);

		this.generarPanelInfo();
		this.generarBotonesInfo();
	}

	private void generarPanelInfo() {
		JScrollPane scroll = new JScrollPane();
		scroll.setBorder(new TitledBorder("Información detallada"));
		scroll.setPreferredSize(new Dimension(250, 350));
		scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		this.panelDerecha.add(scroll);

		this.panelInfo.setLayout(new GridBagLayout());
		this.panelInfo.setBorder(new EmptyBorder(2, 0, 2, 0));
		scroll.setViewportView(this.panelInfo);
	}

	private void actualizarPanelInfo() {
		this.panelInfo.removeAll();

		JScrollPane scrollPane = (JScrollPane) this.tabbedPaneClientes.getSelectedComponent();
		JTable tabla = (JTable) scrollPane.getViewport().getView();

		if (tabla != null) {

			int row = tabla.getSelectedRow();

			boolean enabled = row != -1;

			this.btnVerLlamadas.setEnabled(enabled);
			this.btnVerFacturas.setEnabled(enabled);

			if (enabled) {

				GridBagConstraints constraints = new GridBagConstraints();
				constraints.anchor = GridBagConstraints.CENTER;
				constraints.fill = GridBagConstraints.HORIZONTAL;

				for (int col = 0; col < tabla.getColumnCount(); col++) {
					constraints.insets = new Insets(3, 0, 1, 0);
					constraints.gridx = 0;
					constraints.gridy = col * 2;

					JLabel label = new JLabel();
					label.setText(tabla.getColumnName(col) + ":");
					this.panelInfo.add(label, constraints);

					constraints.insets = new Insets(1, 0, 3, 0);
					constraints.gridy++;

					JTextField textField = new JTextField();
					textField.setEditable(false);
					textField.setText((String) tabla.getValueAt(row, col));
					textField.setPreferredSize(new Dimension(200, textField.getPreferredSize().height));
					this.panelInfo.add(textField, constraints);
				}
			}
		}

		this.panelInfo.updateUI();
	}

	private void generarBotonesInfo() {
		this.panelBotonesInfo.setBorder(new EmptyBorder(2, 2, 2, 2));
		this.panelDerecha.add(this.panelBotonesInfo);

		// Botón Ver Llamadas
		this.btnVerLlamadas.setText("Ver llamadas");
		this.btnVerLlamadas.setEnabled(false);
		this.btnVerLlamadas.setActionCommand(INFO_VER_LLAMADAS);
		this.btnVerLlamadas.addActionListener(new EscuchadorBotonesInfo());
		this.panelBotonesInfo.add(this.btnVerLlamadas);

		// Botón Ver Facturas
		this.btnVerFacturas.setText("Ver facturas");
		this.btnVerFacturas.setEnabled(false);
		this.btnVerFacturas.setActionCommand(INFO_VER_FACTURAS);
		this.btnVerFacturas.addActionListener(new EscuchadorBotonesInfo());
		this.panelBotonesInfo.add(this.btnVerFacturas);

		// Fija el tamaño del panel botones de acción
		this.panelBotonesInfo.setMaximumSize(this.panelBotonesInfo.getPreferredSize());
	}

	private void generarVentana() {
		this.frame.setTitle(titulo);
		this.frame.setIconImage(VentanaPrincipal.getImage("phone_ring"));
		this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.frame.setResizable(true);

		this.frame.setMinimumSize(new Dimension(650, 400));

		this.frame.pack();

		this.frame.setLocationRelativeTo(null);
		this.frame.setVisible(true);
	}

	private void cambiarTema(String className) {
		try {
			UIManager.setLookAndFeel(className);
			SwingUtilities.updateComponentTreeUI(this.frame);

			this.panelBotonesClientes.setMaximumSize(this.panelBotonesClientes.getPreferredSize());
			this.panelBotonesInfo.setMaximumSize(this.panelBotonesInfo.getPreferredSize());
			this.actualizarPanelInfo();

			this.frame.pack();
			this.frame.setLocationRelativeTo(null);
		} catch (IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException | ClassNotFoundException e) {
			System.err.println("No se pudo aplicar el LaF: " + className);
		}
	}

	public static ImageIcon getIcon(String icon) {
		String name = String.format("/icons/%s.png", icon);

		URL url = VentanaPrincipal.class.getResource(name);

		if (url == null) {
			System.err.println("Missing icon: " + icon);
			url = VentanaPrincipal.class.getResource("/icons/missing.png");
		}

		return new ImageIcon(url);
	}

	public static Image getImage(String image) {
		String name = String.format("/images/%s.png", image);

		URL url = VentanaPrincipal.class.getResource(name);

		if (url == null) {
			System.err.println("Missing image: " + image);
			url = VentanaPrincipal.class.getResource("/images/missing.png");
		}

		return new ImageIcon(url).getImage();
	}

	/**
	 * @author Juanjo González (al341823)
	 * @author David Agost (al341819)
	 * @since 0.4
	 */
	private class EscuchadorVentanaPrincipal implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			switch (e.getActionCommand()) {
				case ARCHIVO_NUEVO:
					this.nuevo();
					break;
				case ARCHIVO_ABRIR:
					this.abrir();
					break;
				case ARCHIVO_GUARDAR:
					this.guardar();
					break;
				case ARCHIVO_GUARDAR_COMO:
					this.guardarComo();
					break;
				case ARCHIVO_SALIR:
					this.salir();
					break;

				case EDITAR_BUSCAR:
					this.buscar();
					break;

				case AYUDA_SOBRE:
					this.sobre();
					break;
			}
		}

		private void nuevo() {
			VentanaPrincipal.this.controlador.setFicheroDatos(null);
			VentanaPrincipal.this.controlador.limpiarDatos();
		}

		private void abrir() {
			JFileChooser chooser = new JFileChooser() {
				@Override
				protected JDialog createDialog(Component parent) throws HeadlessException {
					JDialog dialog = super.createDialog(parent);
					dialog.setIconImage(VentanaPrincipal.getImage("open"));
					return dialog;
				}
			};
			chooser.setDialogTitle("Abrir...");
			chooser.setFileFilter(new FicherosData());
			chooser.setSelectedFile(VentanaPrincipal.this.controlador.getFicheroDatos());

			int option = chooser.showOpenDialog(VentanaPrincipal.this.frame);

			if (option == JFileChooser.APPROVE_OPTION) {
				File file = chooser.getSelectedFile();

				if (!file.getName().endsWith(".data"))
					file = new File(file.getParent(), file.getName() + ".data");

				VentanaPrincipal.this.controlador.setFicheroDatos(file);
				VentanaPrincipal.this.controlador.cargarDatos();
			}
		}

		private void guardar() {
			if (VentanaPrincipal.this.controlador.getFicheroDatos() == null)
				this.guardarComo();
			else
				VentanaPrincipal.this.controlador.guardarDatos();

		}

		private void guardarComo() {
			JFileChooser chooser = new JFileChooser() {
				@Override
				protected JDialog createDialog(Component parent) throws HeadlessException {
					JDialog dialog = super.createDialog(parent);
					dialog.setIconImage(VentanaPrincipal.getImage("save"));
					return dialog;
				}
			};
			chooser.setDialogTitle("Guardar como...");
			chooser.setFileFilter(new FicherosData());
			chooser.setSelectedFile(VentanaPrincipal.this.controlador.getFicheroDatos());

			int option = chooser.showSaveDialog(VentanaPrincipal.this.frame);

			if (option == JFileChooser.APPROVE_OPTION) {
				File file = chooser.getSelectedFile();

				if (!file.getName().endsWith(".data"))
					file = new File(file.getParent(), file.getName() + ".data");

				VentanaPrincipal.this.controlador.setFicheroDatos(file);
				VentanaPrincipal.this.controlador.guardarDatos();
			}
		}

		private void salir() {
			int option = JOptionPane.showConfirmDialog(VentanaPrincipal.this.frame,
					"Está seguro de que desea salir de programa?",
					"Salir de programa",
					JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE);

			if (option == JOptionPane.YES_OPTION) {
				VentanaPrincipal.this.frame.dispose();
				System.exit(0);
			}
		}

		private void buscar() {
			DialogoBuscar dialogo = new DialogoBuscar(frame, controlador);
			dialogo.generar();
		}

		private void sobre() {
			String message = "<html><center><h1>Telefonía</h1>" +
					"Proyecto en lenguaje Java desarrollado para la asignatura de Programación Avanzada en la UJI<br>" +
					"<br><hr><br>" +
					"Copyright (c) 2017 Juan José González y David Agost<br>" +
					"Esta obra está sujeta a la licencia Reconocimiento 4.0 Internacional de Creative Commons<br>" +
					"Para ver una copia de esta licencia, visite http://creativecommons.org/licenses/by/4.0/" +
					"</center></html>";

			JOptionPane.showMessageDialog(VentanaPrincipal.this.frame,
					message,
					"Sobre Telefonía",
					JOptionPane.PLAIN_MESSAGE);
		}

		private class FicherosData extends FileFilter {
			@Override
			public boolean accept(File file) {
				if (file.isDirectory())
					return true;

				String filename = file.getName().toLowerCase();
				return filename.endsWith(".data");
			}

			@Override
			public String getDescription() {
				return "Fichero de datos (*.data)";
			}
		}
	}

	/**
	 * @author Juanjo González (al341823)
	 * @since 0.4
	 */
	private class EscuchadorTablas implements ListSelectionListener, ChangeListener {

		@Override
		public void valueChanged(ListSelectionEvent e) {
			if (e.getValueIsAdjusting()) {
				this.actualizarBotones();
				VentanaPrincipal.this.actualizarPanelInfo();
			}
		}

		@Override
		public void stateChanged(ChangeEvent e) {
			this.actualizarBotones();
			VentanaPrincipal.this.actualizarPanelInfo();
		}

		private void actualizarBotones() {
			JScrollPane scrollPane = (JScrollPane) VentanaPrincipal.this.tabbedPaneClientes.getSelectedComponent();
			JTable tabla = (JTable) scrollPane.getViewport().getView();

			if (tabla == null) return;

			boolean enabled = tabla.getSelectedRow() != -1;

			VentanaPrincipal.this.btnEditar.setEnabled(enabled);
			VentanaPrincipal.this.btnBorrar.setEnabled(enabled);
		}

	}

	/**
	 * @author Juanjo González (al341823)
	 * @since 0.4
	 */
	private class EscuchadorBotonesTabla implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String accion = e.getActionCommand();
			Window owner = VentanaPrincipal.this.frame;
			JScrollPane scrollPane = (JScrollPane) VentanaPrincipal.this.tabbedPaneClientes.getSelectedComponent();
			JTable tabla = (JTable) scrollPane.getViewport().getView();

			if (accion.equals(TABLA_NUEVO) || accion.equals(TABLA_EDITAR_TARIFA)) {
				DialogoEditar dialogo = new DialogoEditar(owner, tabla, accion, VentanaPrincipal.this.controlador);
				dialogo.generar();
			} else if (accion.equals(TABLA_BORRAR)) {

				int response = JOptionPane.showConfirmDialog(owner,
						"Está seguro de que desea borrar el cliente?",
						"Borrar cliente",
						JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE);

				if (response == JOptionPane.YES_OPTION) {
					try {
						int row = tabla.getSelectedRow();
						String nif = (String) tabla.getValueAt(row, 0);
						VentanaPrincipal.this.controlador.borrarCliente(nif);
					} catch (Exception excepcion) {
						JOptionPane.showMessageDialog(owner,
								"No se pudo eliminar el cliente seleccionado",
								"Error al borrar",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}

		}

	}

	/**
	 * @author Juanjo González (al341823)
	 * @since 0.4
	 */
	private class EscuchadorBotonesInfo implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				JScrollPane scrollPane = (JScrollPane) VentanaPrincipal.this.tabbedPaneClientes.getSelectedComponent();
				JTable tabla = (JTable) scrollPane.getViewport().getView();
				int row = tabla.getSelectedRow();
				String nif = (String) tabla.getValueAt(row, 0);

				DialogoInfo dialogo = new DialogoInfo(frame, nif, e.getActionCommand(), controlador);
				dialogo.generar();

			} catch (Exception exception) {
				if (e.getActionCommand().equals(INFO_VER_LLAMADAS))
					JOptionPane.showMessageDialog(frame,
							"No se pudieron mostrar las llamadas del cliente seleccionado",
							"Error al mostrar llamadas",
							JOptionPane.ERROR_MESSAGE);
				else
					JOptionPane.showMessageDialog(frame,
							"No se pudieron mostrar las facturas del cliente seleccionado",
							"Error al mostrar facturas",
							JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}
