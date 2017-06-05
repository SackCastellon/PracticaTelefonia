/*
 * Copyright (c) 2017. Esta obra está sujeta a la licencia Reconocimiento 4.0 Internacional de Creative Commons.
 * Para ver una copia de esta licencia, visite http://creativecommons.org/licenses/by/4.0/.
 */

package es.uji.al341823.telefonia.gui.swing.tablas;

import es.uji.al341823.telefonia.api.AdministradorDatos;
import es.uji.al341823.telefonia.clientes.Cliente;
import es.uji.al341823.telefonia.clientes.Empresa;
import es.uji.al341823.telefonia.clientes.Particular;

import javax.swing.table.AbstractTableModel;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Juanjo González (al341823)
 * @since 0.4
 */
public class ModeloTablaClientes extends AbstractTableModel {

	private final LinkedList<Cliente> datos = new LinkedList<>();

	private final Class<? extends Cliente> tipoCliente;
	private final AdministradorDatos admin;
	private final List<String> nombres;

	public ModeloTablaClientes(Class<? extends Cliente> tipoCliente, AdministradorDatos admin) {
		super();

		this.tipoCliente = tipoCliente;
		this.admin = admin;

		if (this.tipoCliente.equals(Particular.class))
			this.nombres = Particular.getIdDatos();
		else if (this.tipoCliente.equals(Empresa.class))
			this.nombres = Empresa.getIdDatos();
		else
			this.nombres = new LinkedList<>();

		this.actualizarDatos();
	}

	@Override
	public int getRowCount() {
		return this.datos.size();
	}

	@Override
	public int getColumnCount() {
		return this.nombres.size();
	}

	@Override
	public String getColumnName(int column) {
		return this.nombres.get(column);
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Cliente cliente = this.getClienteAt(rowIndex);
		Object value = cliente.getDatos().get(columnIndex).getValue();

		if (value instanceof LocalDateTime)
			return ((LocalDateTime) value).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

		return value;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	public Cliente getClienteAt(int row) {
		return this.datos.get(row);
	}

	public void actualizarDatos() {
		this.datos.clear();

		for (Cliente cliente : this.admin.getClientes())
			if (this.tipoCliente.isInstance(cliente))
				this.datos.add(cliente);
	}

	public Class<? extends Cliente> getTipoCliente() {
		return this.tipoCliente;
	}
}
