/*
 * Copyright (c) 2017. Esta obra está sujeta a la licencia Reconocimiento 4.0 Internacional de Creative Commons.
 * Para ver una copia de esta licencia, visite http://creativecommons.org/licenses/by/4.0/.
 */

package es.uji.al341823.telefonia.gui.swing.tablas;

import es.uji.al341823.telefonia.api.AdministradorDatos;
import es.uji.al341823.telefonia.api.IDatos;
import es.uji.al341823.telefonia.api.IFecha;
import es.uji.al341823.telefonia.api.excepciones.FechaNoValidaExcepcion;
import es.uji.al341823.telefonia.api.excepciones.ObjetoNoExisteException;
import es.uji.al341823.telefonia.clientes.Particular;
import es.uji.al341823.telefonia.facturacion.Factura;
import es.uji.al341823.telefonia.gui.swing.ActionCommands;
import es.uji.al341823.telefonia.llamadas.Llamada;

import javax.swing.table.AbstractTableModel;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Juanjo González (al341823)
 * @since 0.4
 */
public class ModeloTablaBusqueda extends AbstractTableModel {

	private final LinkedList<IFecha> datos = new LinkedList<>();
	private List<String> nombres;

	public ModeloTablaBusqueda(String actionCommand, String nif, LocalDateTime inicio, LocalDateTime fin) throws FechaNoValidaExcepcion {
		super();

		Collection<? extends IFecha> conjunto;

		try {
			conjunto = this.getConjuntos(actionCommand, nif);
		} catch (ObjetoNoExisteException e) {
			conjunto = new LinkedList<>();
		}

		Collection<? extends IFecha> subConjunto = AdministradorDatos.extraerConjunto(conjunto, inicio, fin);
		this.datos.addAll(subConjunto);
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
		IDatos obj = (IDatos) this.datos.get(rowIndex);
		Object value = obj.getDatos().get(columnIndex).getValue();

		if (value instanceof LocalDateTime)
			return ((LocalDateTime) value).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

		return value;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	private Collection<? extends IFecha> getConjuntos(String actionCommand, String nif) throws ObjetoNoExisteException {
		switch (actionCommand) {
			case ActionCommands.BUSCAR_CLIENTES:
				this.nombres = Particular.getNombreDatos();
				return AdministradorDatos.getClientes();

			case ActionCommands.BUSCAR_LLAMADAS:
				this.nombres = Llamada.getNombreDatos();
				return AdministradorDatos.getLlamadasCliente(nif);

			case ActionCommands.BUSCAR_FACTURAS:
				this.nombres = Factura.getNombreDatos();
				return AdministradorDatos.getFacturasCliente(nif);

			default:
				this.nombres = new LinkedList<>();
				return new LinkedList<>();
		}
	}
}
