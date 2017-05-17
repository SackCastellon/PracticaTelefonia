/*
 * Copyright (c) 2017. Esta obra está sujeta a la licencia Reconocimiento 4.0 Internacional de Creative Commons.
 * Para ver una copia de esta licencia, visite http://creativecommons.org/licenses/by/4.0/.
 */

package es.uji.al341823.telefonia.gui.swing.ventanas;

import es.uji.al341823.telefonia.api.AdministradorSwing;
import es.uji.al341823.telefonia.clientes.Cliente;
import es.uji.al341823.telefonia.clientes.Empresa;
import es.uji.al341823.telefonia.clientes.Particular;
import es.uji.al341823.telefonia.gui.swing.Vista;
import es.uji.al341823.telefonia.gui.swing.controladores.Controlador;
import es.uji.al341823.telefonia.gui.swing.dialogos.DialogoBuscar;
import es.uji.al341823.telefonia.gui.swing.dialogos.DialogoEditar;
import es.uji.al341823.telefonia.gui.swing.dialogos.DialogoFileChooser;
import es.uji.al341823.telefonia.gui.swing.dialogos.DialogoInfo;
import es.uji.al341823.telefonia.gui.swing.tablas.ModeloTablaClientes;
import javafx.util.Pair;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;

import static es.uji.al341823.telefonia.gui.swing.ActionCommands.*;

/**
 * @author Juanjo González (al341823)
 * @since 0.4
 */
public class VentanaPrincipal extends Vista {

	private final JFrame frame;

	private final JPanel panelInfo = new JPanel();
	private final JTabbedPane tabbedPaneClientes = new JTabbedPane();

	private final HashSet<JComponent> componentsToAdjustSize = new HashSet<>();
	private final HashSet<JButton> botonesInfoCliente = new HashSet<>();
	private final HashSet<JTable> tablas = new HashSet<>();

	public VentanaPrincipal() {
		super();
		this.frame = new JFrame();
	}

	@Override
	public void generarVista() {
		this.generarBarraMenu();
		this.generarPanelIzquierda();
		this.generarPanelDerecha();
		this.generarMenusContexto();

		this.frame.setTitle("Telefonía");
		this.frame.setIconImage(AdministradorSwing.getImage("phone_ring"));
		this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.frame.setMinimumSize(new Dimension(650, 400));
		this.frame.setResizable(true);

		this.frame.pack();

		this.frame.setLocationRelativeTo(null);
		this.frame.setVisible(true);
	}

	@Override
	public void actualizarVista() {
		for (JTable tabla : this.tablas) {
			ModeloTablaClientes modelo = (ModeloTablaClientes) tabla.getModel();
			modelo.actualizarDatos();
			modelo.fireTableDataChanged();
		}
	}

	public void actualizarInfo() {
		// Limpia el panel de información
		this.panelInfo.removeAll();

		// Obtiene el elemento seleccionado del panel de pestañas
		Component component = this.tabbedPaneClientes.getSelectedComponent();

		boolean isRowSelected = false;

		if ((component != null) && (component instanceof JScrollPane)) {
			JScrollPane scrollPane = (JScrollPane) component;

			// Obtiene la tabla seleccionada
			JTable tabla = (JTable) scrollPane.getViewport().getView();

			if (tabla != null) {

				// Fila seleccionada
				int row = tabla.getSelectedRow();

				// Comprueva si hay alguna fila seleccionada
				isRowSelected = row != -1;

				if (isRowSelected) {

					row = tabla.convertRowIndexToModel(row);

					ModeloTablaClientes model = (ModeloTablaClientes) tabla.getModel();
					Cliente cliente = model.getClienteAt(row);

					GridBagConstraints constraints = new GridBagConstraints();
					constraints.anchor = GridBagConstraints.CENTER;
					constraints.fill = GridBagConstraints.HORIZONTAL;
					constraints.gridx = 0;
					constraints.gridy = -1;

					// Muestra toda la información de la tabla en el panel de información
					for (Pair<String, Object> pair : cliente.getDatos()) {
						constraints.insets = new Insets(3, 0, 1, 0);
						constraints.gridy++;

						// Etiqueta con el nombre del dato
						JLabel label = new JLabel();
						label.setText(pair.getKey() + ":");
						this.panelInfo.add(label, constraints);

						constraints.insets = new Insets(1, 0, 3, 0);
						constraints.gridy++;

						// Cuadro de texto con el valor del dato
						JTextField textField = new JTextField();
						textField.setEditable(false);

						Object value = pair.getValue();
						if (value instanceof LocalDateTime)
							textField.setText(((LocalDateTime) value).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
						else
							textField.setText(value.toString());

						textField.setPreferredSize(new Dimension(200, textField.getPreferredSize().height));
						this.panelInfo.add(textField, constraints);
					}
				}
			}
		}

		for (JButton btn : this.botonesInfoCliente)
			btn.setEnabled(isRowSelected);

		this.panelInfo.updateUI();
	}

	/**
	 * Genera la barra de menú superior
	 */
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
		itemNuevo.setIcon(AdministradorSwing.getIcon("new"));
		itemNuevo.setMnemonic('N');
		itemNuevo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK)); // Ctrl + N
		itemNuevo.setActionCommand(ARCHIVO_NUEVO);
		itemNuevo.addActionListener(new EscuchadorVentanaPrincipal());
		menuArchivo.add(itemNuevo);

		// Item Cargar
		JMenuItem itemCargar = new JMenuItem("Abrir...");
		itemCargar.setIcon(AdministradorSwing.getIcon("open"));
		itemCargar.setMnemonic('B');
		itemCargar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK)); // Ctrl + O
		itemCargar.setActionCommand(ARCHIVO_ABRIR);
		itemCargar.addActionListener(new EscuchadorVentanaPrincipal());
		menuArchivo.add(itemCargar);

		// Item Guardar
		JMenuItem itemGuardar = new JMenuItem("Guardar");
		itemGuardar.setIcon(AdministradorSwing.getIcon("save"));
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
		itemSalir.setIcon(AdministradorSwing.getIcon("exit"));
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
		JMenuItem itemBuscar = new JMenuItem("Buscar...");
		itemBuscar.setIcon(AdministradorSwing.getIcon("find"));
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
		submenuTema.setIcon(AdministradorSwing.getIcon("theme"));
		submenuTema.addActionListener(new EscuchadorVentanaPrincipal());
		menuVer.add(submenuTema);

		ButtonGroup groupTema = new ButtonGroup();

		String temaActual = UIManager.getLookAndFeel().getClass().getName();

		// Items de cada uno de los temas disponibles
		for (UIManager.LookAndFeelInfo lookAndFeelInfo : UIManager.getInstalledLookAndFeels()) {
			String nombreTema = lookAndFeelInfo.getName();
			boolean activo = temaActual.equals(lookAndFeelInfo.getClassName());

			JRadioButtonMenuItem btnTema = new JRadioButtonMenuItem(nombreTema, activo);
			btnTema.addActionListener(e -> this.setTema(lookAndFeelInfo.getClassName()));
			submenuTema.add(btnTema);
			groupTema.add(btnTema);
		}

		// Separador
		submenuTema.add(new JSeparator());

		// Panel para el aviso
		JPanel avisoTemaPanel = new JPanel();
		submenuTema.add(avisoTemaPanel);

		// Etiqueta aviso
		JLabel avisoTema = new JLabel("<html>Si cambia el tema se reestablecerán<br>el tamaño y la posición iniciales de<br>la ventana</html>");
		avisoTemaPanel.add(avisoTema);

		// ============================================================ //

		// Menu Ayuda
		JMenu menuAyuda = new JMenu("Ayuda");
		menuAyuda.setMnemonic('Y');
		menu.add(menuAyuda);

		// Item Sobre telefonía
		JMenuItem itemSobre = new JMenuItem("Sobre Telefonía...");
		itemSobre.setIcon(AdministradorSwing.getIcon("info"));
		itemSobre.setMnemonic('S');
		itemSobre.setActionCommand(AYUDA_SOBRE);
		itemSobre.addActionListener(new EscuchadorVentanaPrincipal());
		menuAyuda.add(itemSobre);
	}

	/**
	 * Genera el panel de la iquierda
	 */
	private void generarPanelIzquierda() {
		// Panel con titulo
		JPanel panelIzquierda = new JPanel();
		panelIzquierda.setBorder(new EmptyBorder(5, 5, 0, 4));
		panelIzquierda.setLayout(new BoxLayout(panelIzquierda, BoxLayout.Y_AXIS));
		this.frame.add(panelIzquierda, BorderLayout.CENTER);

		// Panel de pestañas para particulares y empresas
		this.tabbedPaneClientes.setPreferredSize(new Dimension(550, 350));
		this.tabbedPaneClientes.addChangeListener(new EscuchadorTablas());
		panelIzquierda.add(this.tabbedPaneClientes);

		int[] anchoParticular = {80, 100, 125, 250, 125, 125, 150}; //TODO Intentar buscar otra forma de establecer los tamaños
		this.generarTabla(this.tabbedPaneClientes, "Particulares", Particular.class, anchoParticular);

		int[] anchoEmpresa = {80, 100, 250, 125, 125, 150}; //TODO Intentar buscar otra forma de establecer los tamaños
		this.generarTabla(this.tabbedPaneClientes, "Empresas", Empresa.class, anchoEmpresa);

		this.generarBotonesTablas(panelIzquierda);
	}

	/**
	 * Genera una tabla a partir de la información suministrada y la añade a una nueva pestaña
	 *
	 * @param nombre        Nombre de la pestaña
	 * @param claseCliente  La clase de los clientes
	 * @param anchoColumnas El ancho para cada columna de la tabla
	 */
	private void generarTabla(JTabbedPane parent, String nombre, Class<? extends Cliente> claseCliente, int[] anchoColumnas) {
		// Panel con scroll
		JScrollPane scroll = new JScrollPane();
		parent.addTab(nombre, scroll);

		// Configuración de la Tabla de Particulares
		JTable tabla = new JTable();
		tabla.setModel(new ModeloTablaClientes(claseCliente));
		tabla.setAutoCreateRowSorter(true);
		tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabla.getTableHeader().setReorderingAllowed(true);
		tabla.getSelectionModel().addListSelectionListener(new EscuchadorTablas());

		// Establece el ancho de la columna
		TableColumnModel modeloColumna = tabla.getColumnModel();
		for (int i = 0; i < anchoColumnas.length; i++)
			modeloColumna.getColumn(i).setPreferredWidth(anchoColumnas[i]);

		// Establece la tabla como contenido del panel con scroll
		scroll.setViewportView(tabla);

		this.tablas.add(tabla);
	}

	/**
	 * Genera el panel con los botones relativos a las tablas de clientes
	 *
	 * @param parent El componente padre del panel de los botones
	 */
	private void generarBotonesTablas(JPanel parent) {
		// Panel con los botones
		JPanel panelBotonesClientes = new JPanel();
		parent.add(panelBotonesClientes);

		this.componentsToAdjustSize.add(panelBotonesClientes);

		// Botón Nuevo
		JButton btnNuevo = new JButton("Nuevo");
		btnNuevo.setActionCommand(TABLA_NUEVO);
		btnNuevo.addActionListener(new EscuchadorBotonesTabla());
		panelBotonesClientes.add(btnNuevo);

		// Botón Editar
		JButton btnEditar = new JButton();
		btnEditar.setText("Editar");
		btnEditar.setEnabled(false);
		btnEditar.setActionCommand(TABLA_EDITAR_TARIFA);
		btnEditar.addActionListener(new EscuchadorBotonesTabla());
		panelBotonesClientes.add(btnEditar);

		this.botonesInfoCliente.add(btnEditar);

		// Botón Borrar
		JButton btnBorrar = new JButton();
		btnBorrar.setText("Borrar");
		btnBorrar.setEnabled(false);
		btnBorrar.setActionCommand(TABLA_BORRAR);
		btnBorrar.addActionListener(new EscuchadorBotonesTabla());
		panelBotonesClientes.add(btnBorrar);

		this.botonesInfoCliente.add(btnBorrar);
	}

	/**
	 * Genera el panel de la derecha
	 */
	private void generarPanelDerecha() {
		// Panel con titulo
		JPanel panelDerecha = new JPanel();
		panelDerecha.setBorder(new EmptyBorder(5, 4, 0, 5));
		panelDerecha.setLayout(new BoxLayout(panelDerecha, BoxLayout.Y_AXIS));
		this.frame.add(panelDerecha, BorderLayout.EAST);

		this.generarPanelInfo(panelDerecha);
		this.generarBotonesInfo(panelDerecha);
	}

	/**
	 * Genera el panel qeu mostrará la información del cliente seleccionado
	 *
	 * @param parent El componente padre del panel de información
	 */
	private void generarPanelInfo(JPanel parent) {
		// Panel con scroll
		JScrollPane scroll = new JScrollPane();
		scroll.setBorder(new TitledBorder("Información detallada"));
		scroll.setPreferredSize(new Dimension(250, 350));
		scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		parent.add(scroll);

		// Panel con la información del cliente selecionado
		this.panelInfo.setLayout(new GridBagLayout());
		this.panelInfo.setBorder(new EmptyBorder(3, 3, 3, 3));
		scroll.setViewportView(this.panelInfo);
	}

	/**
	 * Genera los botones del panel de información de cliente
	 *
	 * @param parent El componente padre del panel de los botones
	 */
	private void generarBotonesInfo(JPanel parent) {
		// Panel con los botones
		JPanel panelBotonesInfo = new JPanel();
		parent.add(panelBotonesInfo);

		this.componentsToAdjustSize.add(panelBotonesInfo);

		// Botón Ver Llamadas
		JButton btnVerLlamadas = new JButton();
		btnVerLlamadas.setText("Ver llamadas");
		btnVerLlamadas.setEnabled(false);
		btnVerLlamadas.setActionCommand(INFO_VER_LLAMADAS);
		btnVerLlamadas.addActionListener(new EscuchadorBotonesInfo());
		panelBotonesInfo.add(btnVerLlamadas);

		this.botonesInfoCliente.add(btnVerLlamadas);

		// Botón Ver Facturas
		JButton btnVerFacturas = new JButton();
		btnVerFacturas.setText("Ver facturas");
		btnVerFacturas.setEnabled(false);
		btnVerFacturas.setActionCommand(INFO_VER_FACTURAS);
		btnVerFacturas.addActionListener(new EscuchadorBotonesInfo());
		panelBotonesInfo.add(btnVerFacturas);

		this.botonesInfoCliente.add(btnVerFacturas);
	}

	private void generarMenusContexto() {
		for (JTable tabla : this.tablas)
			this.generarMenuContextoTabla(tabla);
	}

	private void generarMenuContextoTabla(JTable tabla) {
		// Lista de columnas ocultas
		ArrayList<TableColumn> hiddenColumns = new ArrayList<>();
		// Columna que se va a ocultar
		TableColumn[] columnToHide = new TableColumn[1];

		// Menú contextual para las columnas
		JPopupMenu popupTabla = new JPopupMenu();

		// Opción para ocultar la columna selecionada
		JMenuItem itemHideColumn = new JMenuItem();
		itemHideColumn.addActionListener(e -> {
			TableColumnModel columnModel = tabla.getColumnModel();
			columnModel.removeColumn(columnToHide[0]);
			hiddenColumns.add(columnToHide[0]);
		});
		popupTabla.add(itemHideColumn);

		// Opción para mostrar columnas ocultas
		JMenu menuShowColumn = new JMenu("Mostrar columna");
		popupTabla.add(menuShowColumn);

		// Escuchador ratón para mostrar el menú contextual
		JTableHeader tableHeader = tabla.getTableHeader();
		tableHeader.addMouseListener(new EscuchadorCabeceraTabla(tabla, itemHideColumn, columnToHide, menuShowColumn, hiddenColumns, popupTabla));
	}

	private void setTema(String className) {
		try {
			UIManager.setLookAndFeel(className);
			SwingUtilities.updateComponentTreeUI(this.frame);

			for (JComponent component : this.componentsToAdjustSize)
				component.setMaximumSize(component.getPreferredSize());

			this.frame.pack();
			this.frame.setLocationRelativeTo(null);
		} catch (IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException | ClassNotFoundException e) {
			JOptionPane.showMessageDialog(this.frame,
					"No se pudo aplicar el tema seleccionado",
					"Error al cambiar el tema",
					JOptionPane.ERROR_MESSAGE);
		} finally {
			this.actualizarVista();
		}
	}

	/**
	 * @author Juanjo González (al341823)
	 * @author David Agost (al341819)
	 * @since 0.4
	 */
	private class EscuchadorVentanaPrincipal implements ActionListener {

		private final Controlador controlador = VentanaPrincipal.this.getControlador();

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

		/**
		 * Borra los datos existentes, loo nuevos datos son independientes de los borrados
		 */
		private void nuevo() {
			this.controlador.nuevosDatos();
		}

		/**
		 * Carga los datos del fichero que se seleccione
		 */
		private void abrir() {
			DialogoFileChooser chooser = new DialogoFileChooser();
			chooser.setDialogTitle("Abrir...");
			chooser.setIcon(AdministradorSwing.getImage("open"));
			chooser.setSelectedFile(this.controlador.getFicheroDatos());

			int option = chooser.showOpenDialog(VentanaPrincipal.this.frame);

			if (option == JFileChooser.APPROVE_OPTION) {
				File file = chooser.getSelectedFile();

				this.controlador.setFicheroDatos(file);
				this.controlador.cargarDatos();
			}
		}

		/**
		 * Guarda los datos en el fichero de datos existente, si no hay ninguno se pide que se seleccione uno
		 */
		private void guardar() {
			if (this.controlador.getFicheroDatos() == null)
				this.guardarComo();
			else
				this.controlador.guardarDatos();
		}

		/**
		 * Guarda los datos en el fichero que se seleccione
		 */
		private void guardarComo() {
			DialogoFileChooser chooser = new DialogoFileChooser();
			chooser.setDialogTitle("Guardar como...");
			chooser.setIcon(AdministradorSwing.getImage("save"));
			chooser.setSelectedFile(this.controlador.getFicheroDatos());

			int option = chooser.showSaveDialog(VentanaPrincipal.this.frame);

			if (option == JFileChooser.APPROVE_OPTION) {
				File file = chooser.getSelectedFile();

				this.controlador.setFicheroDatos(file);
				this.controlador.guardarDatos();
			}
		}

		/**
		 * Sale del programa
		 */
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
			Vista dialogo = new DialogoBuscar(VentanaPrincipal.this.frame);
			dialogo.setControlador(this.controlador);
			dialogo.generarVista();
		}

		private void sobre() {
			String message = "<html><center><h1>Telefonía</h1>" +
					"Proyecto en lenguaje Java desarrollado para la asignatura de Programación Avanzada en la UJI<br>" +
					"<br><hr><br>" +
					"Copyright © 2017 Juan José González y David Agost<br>" +
					"Esta obra está sujeta a la licencia Reconocimiento 4.0 Internacional de Creative Commons<br>" +
					"Para ver una copia de esta licencia, visite http://creativecommons.org/licenses/by/4.0/" +
					"</center></html>";

			JOptionPane.showMessageDialog(VentanaPrincipal.this.frame,
					message,
					"Sobre Telefonía",
					JOptionPane.PLAIN_MESSAGE);
		}
	}

	/**
	 * @author Juanjo González (al341823)
	 * @since 0.4
	 */
	private class EscuchadorTablas implements ListSelectionListener, ChangeListener {

		@Override
		public void valueChanged(ListSelectionEvent e) {
			if (!e.getValueIsAdjusting())
				VentanaPrincipal.this.actualizarInfo();
		}

		@Override
		public void stateChanged(ChangeEvent e) {
			VentanaPrincipal.this.actualizarInfo();
		}
	}

	/**
	 * @author Juanjo González (al341823)
	 * @since 0.4
	 */
	private class EscuchadorBotonesTabla implements ActionListener {

		private final Controlador controlador = VentanaPrincipal.this.getControlador();

		@Override
		public void actionPerformed(ActionEvent e) {
			String accion = e.getActionCommand();
			Window owner = VentanaPrincipal.this.frame;
			JScrollPane scrollPane = (JScrollPane) VentanaPrincipal.this.tabbedPaneClientes.getSelectedComponent();
			JTable tabla = (JTable) scrollPane.getViewport().getView();
			ModeloTablaClientes modelo = (ModeloTablaClientes) tabla.getModel();

			switch (accion) {
				case TABLA_NUEVO: {
					Vista dialogo = new DialogoEditar(owner, modelo.getTipoCliente());
					dialogo.setControlador(this.controlador);
					dialogo.generarVista();
					break;
				}
				case TABLA_EDITAR_TARIFA: {
					int row = tabla.convertRowIndexToModel(tabla.getSelectedRow());

					Vista dialogo = new DialogoEditar(owner, modelo.getClienteAt(row));
					dialogo.setControlador(this.controlador);
					dialogo.generarVista();
					break;
				}
				case TABLA_BORRAR:

					int response = JOptionPane.showConfirmDialog(owner,
							"Está seguro de que desea borrar el cliente?",
							"Borrar cliente",
							JOptionPane.YES_NO_OPTION,
							JOptionPane.QUESTION_MESSAGE);

					if (response == JOptionPane.YES_OPTION) {
						try {
							int row = tabla.convertRowIndexToModel(tabla.getSelectedRow());
							Cliente cliente = modelo.getClienteAt(row);
							this.controlador.borrarCliente(cliente);
						} catch (Exception excepcion) {
							JOptionPane.showMessageDialog(owner,
									"No se pudo eliminar el cliente seleccionado",
									"Error al borrar",
									JOptionPane.ERROR_MESSAGE);
						}
					}
					break;
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
				ModeloTablaClientes modelo = (ModeloTablaClientes) tabla.getModel();
				int row = tabla.convertRowIndexToModel(tabla.getSelectedRow());
				Cliente cliente = modelo.getClienteAt(row);

				Vista dialogo = new DialogoInfo(VentanaPrincipal.this.frame, cliente, e.getActionCommand());
				dialogo.setControlador(VentanaPrincipal.this.getControlador());
				dialogo.generarVista();

			} catch (Exception exception) {
				if (e.getActionCommand().equals(INFO_VER_LLAMADAS))
					JOptionPane.showMessageDialog(VentanaPrincipal.this.frame,
							"No se pudieron mostrar las llamadas del cliente seleccionado",
							"Error al mostrar llamadas",
							JOptionPane.ERROR_MESSAGE);
				else
					JOptionPane.showMessageDialog(VentanaPrincipal.this.frame,
							"No se pudieron mostrar las facturas del cliente seleccionado",
							"Error al mostrar facturas",
							JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	/**
	 * @author Juanjo González (al341823)
	 * @since 0.4
	 */
	private class EscuchadorCabeceraTabla extends MouseAdapter {

		private JTable tabla;
		private JMenuItem itemHideColumn;
		private TableColumn[] columnToHide;
		private JMenu menuShowColumn;
		private ArrayList<TableColumn> hiddenColumns;
		private JPopupMenu popupTabla;

		private EscuchadorCabeceraTabla(JTable tabla, JMenuItem itemHideColumn, TableColumn[] columnToHide, JMenu menuShowColumn, ArrayList<TableColumn> hiddenColumns, JPopupMenu popupTabla) {
			super();
			this.tabla = tabla;
			this.itemHideColumn = itemHideColumn;
			this.columnToHide = columnToHide;
			this.menuShowColumn = menuShowColumn;
			this.hiddenColumns = hiddenColumns;
			this.popupTabla = popupTabla;
		}

		@Override
		public void mousePressed(MouseEvent event) {
			this.showPopup(event);
		}

		@Override
		public void mouseReleased(MouseEvent event) {
			this.showPopup(event);
		}

		private void showPopup(MouseEvent event) {
			// Detecta si es un click derecho
			if (!event.isPopupTrigger()) return;

			// Columna sobre la que se hizo click
			int col = this.tabla.columnAtPoint(event.getPoint());

			// Actualiza la opción de ocultar columna
			this.itemHideColumn.setText("Ocultar columna: " + this.tabla.getColumnName(col));

			TableColumnModel columnModel = this.tabla.getColumnModel();
			this.columnToHide[0] = columnModel.getColumn(col);

			this.itemHideColumn.setEnabled(columnModel.getColumnCount() > 1);
			this.menuShowColumn.setEnabled(!this.hiddenColumns.isEmpty());

			// Actualiza la opción de mostrar columnas ocultas
			this.menuShowColumn.removeAll();
			for (TableColumn hiddenColumn : this.hiddenColumns) {
				JMenuItem item = new JMenuItem();
				item.setText(hiddenColumn.getHeaderValue().toString());
				item.addActionListener(e -> {
					this.tabla.getColumnModel().addColumn(hiddenColumn);
					this.hiddenColumns.remove(hiddenColumn);
				});

				this.menuShowColumn.add(item);
			}

			this.menuShowColumn.add(new JSeparator());

			// Actualiza la opción mostrar todas la columnas
			JMenuItem mostrarTodas = new JMenuItem("Mostrar todas");
			mostrarTodas.addActionListener(e -> {
				for (TableColumn hiddenColumn : this.hiddenColumns) {
					this.tabla.getColumnModel().addColumn(hiddenColumn);
				}
				this.hiddenColumns.clear();
			});
			this.menuShowColumn.add(mostrarTodas);

			SwingUtilities.updateComponentTreeUI(this.popupTabla);
			this.popupTabla.show(event.getComponent(), event.getX(), event.getY());
			this.popupTabla.setVisible(true);
		}

	}
}
