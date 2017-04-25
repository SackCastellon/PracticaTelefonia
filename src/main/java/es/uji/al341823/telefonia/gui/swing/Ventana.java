/*
 * Copyright (c) 2017. Esta obra está sujeta a la licencia Reconocimiento 4.0 Internacional de Creative Commons.
 * Para ver una copia de esta licencia, visite http://creativecommons.org/licenses/by/4.0/.
 */

package es.uji.al341823.telefonia.gui.swing;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Juanjo on 25/04/2017.
 */
public class Ventana extends JFrame {

	public Ventana() {
		super("Telefonía");
	}

	public void ejecutar() {

		JButton btn = new JButton("HOLA");
		btn.setSize(100, 1000);


		JPanel panelClientes = new JPanel();
		panelClientes.add(btn);
		JScrollPane scrollPaneClientes = new JScrollPane(panelClientes);

		JPanel panelLlamadas = new JPanel();
		JScrollPane scrollPaneLlamadas = new JScrollPane(panelLlamadas);

		JPanel panelFacturas = new JPanel();
		JScrollPane scrollPaneFacturas = new JScrollPane(panelFacturas);

		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.setPreferredSize(new Dimension(600, 600));
		tabbedPane.addTab("Clientes", scrollPaneClientes);
		tabbedPane.addTab("Llamadas", scrollPaneLlamadas);
		tabbedPane.addTab("Facturas", scrollPaneFacturas);

		/* -------------------------------------- */

		Label label = new Label("Texto");

		JPanel informacion = new JPanel();
		informacion.add(label);
		informacion.setPreferredSize(new Dimension(200, 600));

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

		Container container = this.getContentPane();
		container.add(tabbedPane, BorderLayout.CENTER);
		container.add(informacion, BorderLayout.EAST);
		container.add(acciones, BorderLayout.SOUTH);

		this.setSize(800, 640);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
}
