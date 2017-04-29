/*
 * Copyright (c) 2017. Esta obra está sujeta a la licencia Reconocimiento 4.0 Internacional de Creative Commons.
 * Para ver una copia de esta licencia, visite http://creativecommons.org/licenses/by/4.0/.
 */

package es.uji.al341823.telefonia.gui.swing;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.net.URL;

/**
 * @author Juanjo González (al341823)
 * @since 0.4
 */
public class Ventana {

	private static final String titulo = "Telefonía";

	private final JFrame frame;
	private JPanel panelBotonesClientes;
	private JPanel panelBotonesInfo;

	public Ventana() {
		super();
		this.frame = new JFrame();
	}

	public void ejecutar() {
		this.generarMenu(); // Genera la barra de menú superior
		this.generarContenido(); // Genera el contenido de la ventana
		this.generarVentana(); // Ultimos ajustes antes de mostrar la ventana
	}

	private void generarMenu() {

		JMenuBar menu = new JMenuBar();
		this.frame.setJMenuBar(menu);

		// Menu Archivo

		JMenu menuArchivo = new JMenu("Archivo");
		menuArchivo.setMnemonic('A');
		menu.add(menuArchivo);

		JMenuItem itemNuevo = new JMenuItem("Nuevo...");
		itemNuevo.setIcon(this.getIcon("new"));
		itemNuevo.setMnemonic('N');
		itemNuevo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK)); // Ctrl + N
		itemNuevo.addActionListener(new Escuchador());
		menuArchivo.add(itemNuevo);

		JMenuItem itemCargar = new JMenuItem("Cargar...");
		itemCargar.setIcon(this.getIcon("open"));
		itemCargar.setMnemonic('C');
		itemCargar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK)); // Ctrl + O
		itemCargar.addActionListener(new Escuchador());
		menuArchivo.add(itemCargar);

		JMenuItem itemGuardar = new JMenuItem("Guardar");
		itemGuardar.setIcon(this.getIcon("save"));
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
		itemSalir.setIcon(this.getIcon("exit"));
		itemSalir.setMnemonic('S');
		itemSalir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, InputEvent.ALT_MASK)); // Alt + F4
		itemSalir.addActionListener(e -> this.frame.dispose()); // TODO
		menuArchivo.add(itemSalir);

		// Menu Editar

		JMenu menuEditar = new JMenu("Editar");
		menuEditar.setMnemonic('E');
		menu.add(menuEditar);

		JMenuItem itemDeshacer = new JMenuItem("Deshacer");
		itemDeshacer.setIcon(this.getIcon("undo"));
		itemDeshacer.setMnemonic('D');
		itemDeshacer.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_MASK)); // Ctrl + Z
		itemDeshacer.addActionListener(new Escuchador());
		menuEditar.add(itemDeshacer);

		JMenuItem itemRehacer = new JMenuItem("Rehacer");
		itemRehacer.setIcon(this.getIcon("redo"));
		itemRehacer.setMnemonic('R');
		itemRehacer.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, InputEvent.CTRL_MASK)); // Ctrl + Y
		itemRehacer.addActionListener(new Escuchador());
		menuEditar.add(itemRehacer);

		menuEditar.add(new JSeparator());

		JMenuItem itemCortar = new JMenuItem("Cortar");
		itemCortar.setIcon(this.getIcon("cut"));
		itemCortar.setMnemonic('T');
		itemCortar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_MASK)); // Ctrl + X
		itemCortar.addActionListener(new Escuchador());
		menuEditar.add(itemCortar);

		JMenuItem itemCopiar = new JMenuItem("Copiar");
		itemCopiar.setIcon(this.getIcon("copy"));
		itemCopiar.setMnemonic('C');
		itemCopiar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK)); // Ctrl + C
		itemCopiar.addActionListener(new Escuchador());
		menuEditar.add(itemCopiar);

		JMenuItem itemPegar = new JMenuItem("Pegar");
		itemPegar.setIcon(this.getIcon("paste"));
		itemPegar.setMnemonic('P');
		itemPegar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_MASK)); // Ctrl + V
		itemPegar.addActionListener(new Escuchador());
		menuEditar.add(itemPegar);

		JMenuItem itemBorrar = new JMenuItem("Borrar");
		itemBorrar.setIcon(this.getIcon("delete"));
		itemBorrar.setMnemonic('B');
		itemBorrar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0)); // Supr
		itemBorrar.addActionListener(new Escuchador());
		menuEditar.add(itemBorrar);

		menuEditar.add(new JSeparator());

		JMenuItem itemSeleccionar = new JMenuItem("Seleccionar todo");
		itemSeleccionar.setIcon(this.getIcon("select_all"));
		itemSeleccionar.setMnemonic('S');
		itemSeleccionar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK)); // Ctrl + A
		itemSeleccionar.addActionListener(new Escuchador());
		menuEditar.add(itemSeleccionar);

		menuEditar.add(new JSeparator());

		JMenuItem itemBuscar = new JMenuItem("Buscar...");
		itemBuscar.setIcon(this.getIcon("find"));
		itemBuscar.setMnemonic('U');
		itemBuscar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_MASK)); // Ctrl + F
		itemBuscar.addActionListener(new Escuchador());
		menuEditar.add(itemBuscar);

		JMenuItem itemReemplazar = new JMenuItem("Reemplazar...");
		itemReemplazar.setIcon(this.getIcon("find_replace"));
		itemReemplazar.setMnemonic('M');
		itemReemplazar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_MASK)); // Ctrl + R
		itemReemplazar.addActionListener(new Escuchador());
		menuEditar.add(itemReemplazar);

		// Menu Ver

		JMenu menuVer = new JMenu("Ver");
		menuVer.setMnemonic('V');
		menu.add(menuVer);

		JMenu submenuTema = new JMenu("Tema");
		submenuTema.setMnemonic('T');
		submenuTema.setIcon(this.getIcon("theme"));
		submenuTema.addActionListener(new Escuchador());
		menuVer.add(submenuTema);

		ButtonGroup groupTema = new ButtonGroup();

		String temaActual = UIManager.getLookAndFeel().getClass().getName();

		for (UIManager.LookAndFeelInfo lookAndFeelInfo : UIManager.getInstalledLookAndFeels()) {
			String nombreTema = lookAndFeelInfo.getName();
			boolean activo = temaActual.equals(lookAndFeelInfo.getClassName());

			JRadioButtonMenuItem tema = new JRadioButtonMenuItem(nombreTema, activo);
			tema.addActionListener(e -> this.cambiarTema(lookAndFeelInfo.getClassName())); // TODO
			submenuTema.add(tema);
			groupTema.add(tema);
		}

		submenuTema.add(new JSeparator());

		// Panel para el aviso

		JPanel avisoTemaPanel = new JPanel();
		submenuTema.add(avisoTemaPanel);

		// Etiqueta aviso

		JLabel avisoTema = new JLabel("<html>Si cambia el tema se reestablcerán<br>el tamaño y la posición iniciales de<br>la ventana</html>");
		avisoTemaPanel.add(avisoTema);

		// Menu Ayuda

		JMenu menuAyuda = new JMenu("Ayuda");
		menuAyuda.setMnemonic('Y');
		menu.add(menuAyuda);

		JMenuItem itemSobre = new JMenuItem("Sobre Telefonía...");
		itemSobre.setIcon(this.getIcon("info"));
		itemSobre.setMnemonic('S');
		itemSobre.addActionListener(new Escuchador());
		menuAyuda.add(itemSobre);
	}

	private void generarContenido() {
		Container content = this.frame.getContentPane();
		content.setLayout(new BorderLayout(5, 5));

		// ============================================================ //
		// ============================================================ //

		// Panel Izquierda

		JPanel panelClientes = new JPanel();
		panelClientes.setBorder(new EmptyBorder(5, 5, 0, 0));
		panelClientes.setLayout(new BoxLayout(panelClientes, BoxLayout.Y_AXIS));
		content.add(panelClientes, BorderLayout.CENTER);

		// ============================================================ //

		// Panel de pestañas con clientes

		JTabbedPane tabbedPaneClientes = new JTabbedPane();
		tabbedPaneClientes.setPreferredSize(new Dimension(550, 350));
		panelClientes.add(tabbedPaneClientes);

		// Panel scroll clientes particulares

		JScrollPane scrollParticulares = new JScrollPane();
		tabbedPaneClientes.addTab("Particulares", scrollParticulares);

		// Tabla clientes particulares

		JTable tableParticulares = new JTable(new DefaultTableModel());
		tableParticulares.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tableParticulares.getTableHeader().setReorderingAllowed(false);
		DefaultTableModel modeloParticulares = (DefaultTableModel) tableParticulares.getModel();

		// Cabeceras columnas tabla particulares

		modeloParticulares.addColumn("NIF");
		modeloParticulares.addColumn("Nombre");
		modeloParticulares.addColumn("Apellidos");
		modeloParticulares.addColumn("Dirección");
		modeloParticulares.addColumn("Email");
		modeloParticulares.addColumn("Fecha de alta");
		modeloParticulares.addColumn("Tarifa contratada actual");

		// TODO Solo para test
		modeloParticulares.addRow(new String[] {"12345678A", "Juan José", "González Abril", "12100, Grao de Castellón, Castellón de la Plana", "juanjo@uji.es", "29/04/2017 16:44:27", "Tarifa Basica"});

		// Tamaños columnas tabla particulares

		TableColumnModel columnModelParticulares = tableParticulares.getColumnModel();
		columnModelParticulares.getColumn(0).setPreferredWidth(80);
		columnModelParticulares.getColumn(1).setPreferredWidth(100);
		columnModelParticulares.getColumn(2).setPreferredWidth(125);
		columnModelParticulares.getColumn(3).setPreferredWidth(250);
		columnModelParticulares.getColumn(4).setPreferredWidth(125);
		columnModelParticulares.getColumn(5).setPreferredWidth(125);
		columnModelParticulares.getColumn(6).setPreferredWidth(150);
		scrollParticulares.setViewportView(tableParticulares);

		// ============================================================ //

		// Panel scroll clientes empresa

		JScrollPane scrollEmpresas = new JScrollPane();
		tabbedPaneClientes.addTab("Empresas", scrollEmpresas);

		// Tabla clientes empresa

		JTable tableEmpresas = new JTable(new DefaultTableModel());
		tableEmpresas.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tableEmpresas.getTableHeader().setReorderingAllowed(false);
		DefaultTableModel modeloEmpresas = (DefaultTableModel) tableEmpresas.getModel();

		// Cabeceras columnas tabla Empresas

		modeloEmpresas.addColumn("NIF");
		modeloEmpresas.addColumn("Nombre");
		modeloEmpresas.addColumn("Dirección");
		modeloEmpresas.addColumn("Email");
		modeloEmpresas.addColumn("Fecha de alta");
		modeloEmpresas.addColumn("Tarifa contratada actual");

		// TODO Solo para test
		modeloEmpresas.addRow(new String[] {"12345678A", "Microsoft", "12100, Grao de Castellón, Castellón de la Plana", "microsoft@htomail.com", "29/04/2017 16:44:27", "Tarifa Basica - Domingos gratis"});

		// Tamaños columnas tabla Empresas

		TableColumnModel columnModelEmpresas = tableEmpresas.getColumnModel();
		columnModelEmpresas.getColumn(0).setPreferredWidth(80);
		columnModelEmpresas.getColumn(1).setPreferredWidth(100);
		columnModelEmpresas.getColumn(2).setPreferredWidth(250);
		columnModelEmpresas.getColumn(3).setPreferredWidth(125);
		columnModelEmpresas.getColumn(4).setPreferredWidth(125);
		columnModelEmpresas.getColumn(5).setPreferredWidth(150);
		scrollEmpresas.setViewportView(tableEmpresas);

		// ============================================================ //

		// Panel botones de acción panel izquierdo (clientes)

		this.panelBotonesClientes = new JPanel();
		this.panelBotonesClientes.setBorder(new EmptyBorder(2, 2, 2, 2));
		panelClientes.add(this.panelBotonesClientes);

		// Botones de acción clientes

		JButton btnNuevo = new JButton("Nuevo");
		this.panelBotonesClientes.add(btnNuevo);
		JButton btnEditar = new JButton("Editar");
		this.panelBotonesClientes.add(btnEditar);
		JButton btnBorrar = new JButton("Borrar");
		this.panelBotonesClientes.add(btnBorrar);

		// Fija el tamaño del panel botones de acción
		this.panelBotonesClientes.setMaximumSize(this.panelBotonesClientes.getPreferredSize());

		// ============================================================ //
		// ============================================================ //

		// Panel Derecha

		JPanel panelInfo = new JPanel();
		panelInfo.setBorder(new EmptyBorder(5, 0, 0, 5));
		panelInfo.setLayout(new BoxLayout(panelInfo, BoxLayout.Y_AXIS));
		content.add(panelInfo, BorderLayout.EAST);

		// ============================================================ //

		// Panel información

		JPanel panelInfoClientes = new JPanel();
		panelInfoClientes.setBorder(new TitledBorder("Información detallada"));
		panelInfoClientes.setPreferredSize(new Dimension(250, 350));
		panelInfo.add(panelInfoClientes);

		// ============================================================ //

		// Panel botones de acción panel derecho (información)

		this.panelBotonesInfo = new JPanel();
		panelInfo.add(this.panelBotonesInfo);

		// Botones de acción información

		JButton btnVerLlamadas = new JButton("Ver llamadas");
		this.panelBotonesInfo.add(btnVerLlamadas);
		JButton btnVerFacturas = new JButton("Ver facturas");
		this.panelBotonesInfo.add(btnVerFacturas);

		// Fija el tamaño del panel botones de acción
		this.panelBotonesInfo.setMaximumSize(this.panelBotonesInfo.getPreferredSize());
	}

	private void generarVentana() {
		this.frame.setTitle(titulo);
		ImageIcon icon = this.getIcon("phone_ring");
		this.frame.setIconImage(icon.getImage());
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
}
