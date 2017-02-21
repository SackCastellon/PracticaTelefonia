package es.uji.al341823.telefonia;

import java.time.LocalDate;

/**
 * Created by al341819 on 21/02/17.
 */
public class Factura {

	/** Se utiliza para obtener el código unico de factura */
	private static int codigoUnico = 0;

	private final int codigo;
	private Tarifa tarifa;
	private LocalDate fechaEmision;
	private LocalDate periodoFactuación;
	private int importe;

	public Factura(Tarifa tarifa, LocalDate fechaEmision, LocalDate periodoFactuación, int importe) {
		this.codigo = codigoUnico++;
		this.tarifa = tarifa;
		this.fechaEmision = fechaEmision;
		this.periodoFactuación = periodoFactuación;
		this.importe = importe;
	}

	public int getCodigo() {
		return codigo;
	}

	public Tarifa getTarifa() {
		return tarifa;
	}

	public LocalDate getFechaEmision() {
		return fechaEmision;
	}

	public LocalDate getPeriodoFactuación() {
		return periodoFactuación;
	}

	public int getImporte() {
		return importe;
	}
}
