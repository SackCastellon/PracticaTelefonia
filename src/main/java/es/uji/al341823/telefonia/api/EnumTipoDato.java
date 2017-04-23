/*
 * Copyright (c) 2017. Esta obra está sujeta a la licencia Reconocimiento 4.0 Internacional de Creative Commons.
 * Para ver una copia de esta licencia, visite http://creativecommons.org/licenses/by/4.0/.
 */

package es.uji.al341823.telefonia.api;

/**
 * Diferentes tipos de datos de entrada con sus diferentes formatos
 *
 * @author Juanjo González (al341823)
 * @since 0.2
 */
public enum EnumTipoDato {

	/** Formato sin restricciones */
	TEXTO(".+"),

	/** Formato del NIF: 8 digitos y una letra */
	NIF("[0-9]{8}[a-zA-Z]"),

	/** Formato del NIF de una empresa: una letra, 7 digitos y una letra */
	NIF_EMPRESA("[a-zA-Z][0-9]{7}[a-zA-Z]"),

	/**
	 * Formato de un numero de telefono. Por ejemplo:
	 * <ul>
	 * <li>964728000</li>
	 * <li>964 72 80 00</li>
	 * <li>96472 80 00</li>
	 * <li>+34964728000</li>
	 * <li>+34 964728000</li>
	 * <li>+349647 2800 0</li>
	 * <li>112</li>
	 * <li>1004</li>
	 * </ul>
	 */
	TELEFONO("((\\+)(\\d)*)?(\\s|\\d)*"),

	/** Formato de una dirección de correo electronico: alguien@ejemplo.es, alguien@ejemplo.com, ... */
	EMAIL("[a-zA-Z0-9]+@[a-zA-Z0-9]+(\\.[a-zA-Z0-9]+)?"),

	/**
	 * Formato de una fecha: {@code AAAA-MM-DD hh:mm:ss}<br>
	 * Tambien admite la cadena '{@code hoy}' para referirse al dia actual
	 */
	FECHA("([0-9]{4}-(0[1-9]|1[012])-([12][0-9]|3[01]) ([01][0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])|hoy)"),

	/**
	 * Formato de una dirección postal: Codigo postal de 5 digitos, Población, Provincia<br>
	 * Por ejemplo: 12594, Oropesa, Castellón
	 */
	DIRECCION("[0-9]{5}, (.*), (.*)"),

	/** Formato de un numero entero positivo */
	ENTERO("[0-9]+"),

	/** Formato de una ruta a un fichero (Windows/Linux) */
	FICHERO_O_NINGUNO("([a-zA-Z]:)?([\\\\/]?[a-zA-Z0-9_.-]+)+[\\\\/]?|^$");

	/**
	 * La regla de formato del tipo de dato
	 */
	private final String formato;

	/**
	 * Crea un tipo de dato
	 *
	 * @param formato La regla de formato correspondiente
	 */
	EnumTipoDato(String formato) {
		this.formato = formato;
	}

	/**
	 * Devuelve la regla de formato del tipo de dato
	 *
	 * @return La regla de formato
	 */
	String getFormato() {
		return this.formato;
	}
}
