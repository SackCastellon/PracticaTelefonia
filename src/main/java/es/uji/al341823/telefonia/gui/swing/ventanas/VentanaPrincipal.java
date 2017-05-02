/*
 * Copyright (c) 2017. Esta obra está sujeta a la licencia Reconocimiento 4.0 Internacional de Creative Commons.
 * Para ver una copia de esta licencia, visite http://creativecommons.org/licenses/by/4.0/.
 */

package es.uji.al341823.telefonia.gui.swing.ventanas;

import es.uji.al341823.telefonia.gui.swing.controlador.Controlador;
import es.uji.al341823.telefonia.gui.swing.dialogos.DialogoEditar;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.net.URL;
import java.util.Objects;

/**
 * @author Juanjo González (al341823)
 * @since 0.4
 */
public class VentanaPrincipal {

	private static final String titulo = "Telefonía";

	private final JFrame frame;

	private final JPanel panelIzquierda = new JPanel();
	private final JTabbedPane tabbedPaneClientes = new JTabbedPane();
	//	private final ArrayList<InfoTabla> infoTablas = new ArrayList<>();
	private final JPanel panelBotonesClientes = new JPanel();

	private final JPanel panelDerecha = new JPanel();
	private final JPanel panelBotonesInfo = new JPanel();

	public final JTable tablaParticulares = new JTable();
	public final JTable tablaEmpresas = new JTable();


	private Controlador controlador;

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

		this.generarMenusContexto(); // Genera los menus contectuales

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
		itemNuevo.setIcon(this.getIcon("new"));
		itemNuevo.setMnemonic('N');
		itemNuevo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK)); // Ctrl + N
		itemNuevo.setActionCommand("new");
		itemNuevo.addActionListener(new EscuchadorVentanaPrincipal());
		menuArchivo.add(itemNuevo);

		// Item Cargar
		JMenuItem itemCargar = new JMenuItem("Abrir...");
		itemCargar.setIcon(this.getIcon("open"));
		itemCargar.setMnemonic('B');
		itemCargar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK)); // Ctrl + O
		itemCargar.setActionCommand("open");
		itemCargar.addActionListener(new EscuchadorVentanaPrincipal());
		menuArchivo.add(itemCargar);

		// Item Guardar
		JMenuItem itemGuardar = new JMenuItem("Guardar");
		itemGuardar.setIcon(this.getIcon("save"));
		itemGuardar.setMnemonic('G');
		itemGuardar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK)); // Ctrl + S
		itemGuardar.setActionCommand("save");
		itemGuardar.addActionListener(new EscuchadorVentanaPrincipal());
		menuArchivo.add(itemGuardar);

		// Item Guardar como
		JMenuItem itemGuardarComo = new JMenuItem("Guardar como...");
		itemGuardarComo.setMnemonic('O');
		itemGuardarComo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
				InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK)); // Ctrl + Shift + S
		itemGuardarComo.setActionCommand("save_as");
		itemGuardarComo.addActionListener(new EscuchadorVentanaPrincipal());
		menuArchivo.add(itemGuardarComo);

		// Separador
		menuArchivo.add(new JSeparator());

		// Item Salir
		JMenuItem itemSalir = new JMenuItem("Salir");
		itemSalir.setIcon(this.getIcon("exit"));
		itemSalir.setMnemonic('S');
		itemSalir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, InputEvent.ALT_MASK)); // Alt + F4
		itemSalir.setActionCommand("exit");
		itemSalir.addActionListener(new EscuchadorVentanaPrincipal());
		menuArchivo.add(itemSalir);

		// ============================================================ //

		// Menu Editar
		JMenu menuEditar = new JMenu("Editar");
		menuEditar.setMnemonic('E');
		menu.add(menuEditar);

		// Item Deshacer
		JMenuItem itemDeshacer = new JMenuItem("Deshacer");
		itemDeshacer.setIcon(this.getIcon("undo"));
		itemDeshacer.setMnemonic('D');
		itemDeshacer.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_MASK)); // Ctrl + Z
		itemDeshacer.addActionListener(new EscuchadorVentanaPrincipal());
		menuEditar.add(itemDeshacer);

		// Item Rehacer
		JMenuItem itemRehacer = new JMenuItem("Rehacer");
		itemRehacer.setIcon(this.getIcon("redo"));
		itemRehacer.setMnemonic('R');
		itemRehacer.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, InputEvent.CTRL_MASK)); // Ctrl + Y
		itemRehacer.addActionListener(new EscuchadorVentanaPrincipal());
		menuEditar.add(itemRehacer);

		// Separador
		menuEditar.add(new JSeparator());

		// Item Cortar
		JMenuItem itemCortar = new JMenuItem("Cortar");
		itemCortar.setIcon(this.getIcon("cut"));
		itemCortar.setMnemonic('T');
		itemCortar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_MASK)); // Ctrl + X
		itemCortar.addActionListener(new EscuchadorVentanaPrincipal());
		menuEditar.add(itemCortar);

		// Item Copiar
		JMenuItem itemCopiar = new JMenuItem("Copiar");
		itemCopiar.setIcon(this.getIcon("copy"));
		itemCopiar.setMnemonic('C');
		itemCopiar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK)); // Ctrl + C
		itemCopiar.addActionListener(new EscuchadorVentanaPrincipal());
		menuEditar.add(itemCopiar);

		// Item Pegar
		JMenuItem itemPegar = new JMenuItem("Pegar");
		itemPegar.setIcon(this.getIcon("paste"));
		itemPegar.setMnemonic('P');
		itemPegar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_MASK)); // Ctrl + V
		itemPegar.addActionListener(new EscuchadorVentanaPrincipal());
		menuEditar.add(itemPegar);

		// Item Borrar
		JMenuItem itemBorrar = new JMenuItem("Borrar");
		itemBorrar.setIcon(this.getIcon("delete"));
		itemBorrar.setMnemonic('B');
		itemBorrar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0)); // Supr
		itemBorrar.addActionListener(new EscuchadorVentanaPrincipal());
		menuEditar.add(itemBorrar);

		// Separador
		menuEditar.add(new JSeparator());

		// item Selecionar
		JMenuItem itemSeleccionar = new JMenuItem("Seleccionar todo");
		itemSeleccionar.setIcon(this.getIcon("select_all"));
		itemSeleccionar.setMnemonic('S');
		itemSeleccionar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK)); // Ctrl + A
		itemSeleccionar.addActionListener(new EscuchadorVentanaPrincipal());
		menuEditar.add(itemSeleccionar);

		// Separador
		menuEditar.add(new JSeparator());

		// Item Buscar
		JMenuItem itemBuscar = new JMenuItem("Buscar...");
		itemBuscar.setIcon(this.getIcon("find"));
		itemBuscar.setMnemonic('U');
		itemBuscar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_MASK)); // Ctrl + F
		itemBuscar.addActionListener(new EscuchadorVentanaPrincipal());
		menuEditar.add(itemBuscar);

		// Item Reemplazar
		JMenuItem itemReemplazar = new JMenuItem("Reemplazar...");
		itemReemplazar.setIcon(this.getIcon("find_replace"));
		itemReemplazar.setMnemonic('M');
		itemReemplazar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_MASK)); // Ctrl + R
		itemReemplazar.addActionListener(new EscuchadorVentanaPrincipal());
		menuEditar.add(itemReemplazar);

		// ============================================================ //

		// Menu Ver
		JMenu menuVer = new JMenu("Ver");
		menuVer.setMnemonic('V');
		menu.add(menuVer);

		// Submenú temas
		JMenu submenuTema = new JMenu("Tema");
		submenuTema.setMnemonic('T');
		submenuTema.setIcon(this.getIcon("theme"));
		submenuTema.addActionListener(new EscuchadorVentanaPrincipal());
		menuVer.add(submenuTema);

		ButtonGroup groupTema = new ButtonGroup();

		String temaActual = UIManager.getLookAndFeel().getClass().getName();

		// Items de cada uno de los temas disponibles
		for (UIManager.LookAndFeelInfo lookAndFeelInfo : UIManager.getInstalledLookAndFeels()) {
			String nombreTema = lookAndFeelInfo.getName();
			boolean activo = temaActual.equals(lookAndFeelInfo.getClassName());

			JRadioButtonMenuItem tema = new JRadioButtonMenuItem(nombreTema, activo);
			tema.addActionListener(e -> this.cambiarTema(lookAndFeelInfo.getClassName())); // TODO
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
		itemSobre.setIcon(this.getIcon("info"));
		itemSobre.setMnemonic('S');
		itemSobre.addActionListener(new EscuchadorVentanaPrincipal());
		menuAyuda.add(itemSobre);
	}

	private void generarPanelIzquierda() {
		this.panelIzquierda.setBorder(new EmptyBorder(5, 5, 0, 0));
		this.panelIzquierda.setLayout(new BoxLayout(this.panelIzquierda, BoxLayout.Y_AXIS));
		this.frame.add(this.panelIzquierda, BorderLayout.CENTER);

		// Panel de pestañas con clientes
		this.tabbedPaneClientes.setPreferredSize(new Dimension(550, 350));
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
		this.tablaParticulares.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		this.tablaParticulares.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

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
		this.tablaEmpresas.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		this.tablaEmpresas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

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
		btnNuevo.setActionCommand("new_client");
		btnNuevo.addActionListener(new EscuchadorBotonesTabla());
		this.panelBotonesClientes.add(btnNuevo);

		// Botón Editar
		JButton btnEditar = new JButton("Editar");
		btnEditar.setEnabled(false);
		btnEditar.setActionCommand("edit_client");
		btnEditar.addActionListener(new EscuchadorBotonesTabla());
		this.panelBotonesClientes.add(btnEditar);

		// Botón Borrar
		JButton btnBorrar = new JButton("Borrar");
//		btnBorrar.setEnabled(false);
		btnBorrar.setActionCommand("delete_client");
		btnBorrar.addActionListener(new EscuchadorBotonesTabla());
		this.panelBotonesClientes.add(btnBorrar);

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

		JPanel panelInfo = new JPanel();
		panelInfo.setLayout(new GridLayout(0, 1));
		panelInfo.setBorder(new EmptyBorder(5, 5, 5, 5));
		scroll.setViewportView(panelInfo);
	}

	private void generarBotonesInfo() {
		this.panelBotonesInfo.setBorder(new EmptyBorder(2, 2, 2, 2));
		this.panelDerecha.add(this.panelBotonesInfo);

		// Botón Ver Llamadas
		JButton btnVerLlamadas = new JButton("Ver llamadas");
		this.panelBotonesInfo.add(btnVerLlamadas);

		// Botón Ver Facturas
		JButton btnVerFacturas = new JButton("Ver facturas");
		this.panelBotonesInfo.add(btnVerFacturas);

		// Fija el tamaño del panel botones de acción
		this.panelBotonesInfo.setMaximumSize(this.panelBotonesInfo.getPreferredSize());
	}

	private void generarMenusContexto() {
	}

	private void generarVentana() {
		this.frame.setTitle(titulo);
		ImageIcon icon = this.getIcon("phone_ring");
		this.frame.setIconImage(icon.getImage());
		this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // FIXME
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

			this.panelBotonesClientes.setMaximumSize(this.panelBotonesClientes.getPreferredSize()); // TODO Mover a nuevo metodo
			this.panelBotonesInfo.setMaximumSize(this.panelBotonesInfo.getPreferredSize()); // TODO Mover a nuevo metodo

			this.frame.pack();
			this.frame.setLocationRelativeTo(null);
		} catch (IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException | ClassNotFoundException e) {
			System.err.println("No se pudo aplicar el LaF: " + className);
		}
	}

	private ImageIcon getIcon(String icon) {
		Class clazz = this.frame.getClass();
		String name = String.format("/icons/%s.png", icon);

		URL url = clazz.getResource(name);

		if (url == null) {
			System.err.println("Missing icon: " + icon);
			url = clazz.getResource("/icons/missing.png");
		}

		return new ImageIcon(url);
	}

	private class EscuchadorBotonesTabla implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String accion = e.getActionCommand();
			Window owner = VentanaPrincipal.this.frame;
			JTable tabla = VentanaPrincipal.this.tablaParticulares;

			if (VentanaPrincipal.this.tabbedPaneClientes.getSelectedIndex() == 1)
				tabla = VentanaPrincipal.this.tablaEmpresas;


			if ((Objects.equals(accion, "new_client")) || (Objects.equals(accion, "edit_client"))) {
				DialogoEditar dialogo = new DialogoEditar(owner, tabla, accion, VentanaPrincipal.this.controlador);
				dialogo.generar();
			} else if (Objects.equals(accion, "delete_client")) {

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
								"No se puedo eliminar el cliente seleccionado",
								"Error al borrar",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}

		}
	}

	private class EscuchadorVentanaPrincipal implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			switch (e.getActionCommand()) {
				case "new":
					this.nuevo();
					break;
				case "open":
					this.abrir();
					break;
				case "save":
					this.guardar(e);
					break;
				case "save_as":
					this.guardarComo();
					break;
				case "exit":
					this.salir();
					break;
			}
		}

		private void nuevo() {
			VentanaPrincipal.this.controlador.setFicheroDatos(null);
			VentanaPrincipal.this.controlador.limpiarDatos();
		}

		private void abrir() {
			JFileChooser chooser = new JFileChooser();
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

		private void guardar(ActionEvent e) {
			if (VentanaPrincipal.this.controlador.getFicheroDatos() == null)
				this.guardarComo();
			else
				VentanaPrincipal.this.controlador.guardarDatos();

		}

		private void guardarComo() {
			JFileChooser chooser = new JFileChooser();
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
}
