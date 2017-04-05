package es.uji.al341823.telefonia.api;

/**
 * Diferentes tipos de datos de entrada con sus diferentes formatos
 */
public enum EnumTipoDato {
	TEXTO(".+"),
	NIF("[0-9]{8}[A-Z]"),
	NIF_EMPRESA("[A-Z][0-9]{8}"),
	TELEFONO("(\\+)?(\\s|\\d)*"),
	EMAIL("[a-zA-Z0-9]+@[a-zA-Z0-9]+(\\.[a-zA-Z0-9]+)?"),
	FECHA("([0-9]{4}-(0[1-9]|1[012])-([12][0-9]|3[01]) ([01][0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])|hoy)"),
	DIRECCION("[0-9]{5}, (.*), (.*)"),
	NUMERO("[0-9]+(\\.[0-9]+)?"),
	ENTERO("[0-9]+"),
	FICHERO("([a-zA-Z]:)?([\\\\/]?[a-zA-Z0-9_.-]+)+[\\\\/]?");

	private final String formato;

	EnumTipoDato(String formato) {
		this.formato = formato;
	}

	String getFormato() {
		return this.formato;
	}
}
