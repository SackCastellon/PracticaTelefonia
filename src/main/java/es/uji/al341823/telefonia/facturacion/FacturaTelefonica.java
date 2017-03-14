package es.uji.al341823.telefonia.facturacion;

import es.uji.al341823.telefonia.IFecha;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * @author Juanjo González (al341823)
 * @author David Agost (al341819)
 * @since 0.1
 */
public class FacturaTelefonica implements IFecha {

	/**
	 * Se utiliza para obtener el código unico de cada factura
	 */
	private static int codigoUnico = 0;

	/**
	 * Codigo identificativo unico de la factura
	 */
	private final int codigo;
	/**
	 * La tarifa usada para calcular la factura
	 */
	private final TarifaTelefonica tarifa;
	/**
	 * Fecha en la que se emitió la factura
	 */
	private final LocalDateTime fechaEmision;
	/**
	 * Periodo de tiempo que comprende la factura en dias
	 */
	private final long periodoFactuacion;
	/**
	 * Importe total de la factura calculado en euros (€)
	 */
	private final float importe;

	/**
	 * Crea una tarifa a partir de los datos especificados
	 *
	 * @param tarifa             La tarifa que se aplica
	 * @param fechaUltimaEmision La fecha de emisión de la ultima fsctura
	 * @param fechaEmision       La decha de emisión de la nueva factura (normalmente el dia actual)
	 * @param duracionLlamadas   La duración total en segundos de las llamadas
	 */
	public FacturaTelefonica(TarifaTelefonica tarifa, LocalDateTime fechaUltimaEmision, LocalDateTime fechaEmision, int duracionLlamadas) {
		this.codigo = codigoUnico++;
		this.tarifa = tarifa;
		this.fechaEmision = fechaEmision;
		this.periodoFactuacion = Duration.between(fechaUltimaEmision, fechaEmision).toDays();
		this.importe = tarifa.getPrecio() * duracionLlamadas;
	}

	/**
	 * Devuelve el codigo identificativo unico de la factura
	 *
	 * @return Codigo identificativo
	 */
	public int getCodigo() {
		return codigo;
	}

	/**
	 * Devuelve la tarifa usada para calcular la factura
	 *
	 * @return La taifa de la factura
	 */
	public TarifaTelefonica getTarifa() {
		return tarifa;
	}

	/**
	 * Devuelve el periodo en días durante el cual se aplica la tarifa para calcuar el importe de la factura
	 *
	 * @return El periodo de facturación
	 */
	public long getPeriodoFactuacion() {
		return periodoFactuacion;
	}

	/**
	 * Devuelve el importe total de la factura calculado en euros (€)
	 *
	 * @return Importe de la factura
	 */
	public float getImporte() {
		return importe;
	}

	/**
	 * Devuelve el dia y la hora a la que se emitión la factura
	 *
	 * @return El dia y la hora de emisión de la factura
	 */
	@Override
	public LocalDateTime getFecha() {
		return this.fechaEmision;
	}

	@Override
	public String toString() {
		return "FacturaTelefonica{" +
				"codigo=" + codigo +
				", tarifa=" + tarifa +
				", fechaEmision=" + fechaEmision +
				", periodoFactuacion=" + periodoFactuacion +
				", importe=" + importe +
				'}';
	}
}
