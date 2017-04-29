package es.uji.al341823.telefonia.api;

/**
 * Created by al341819 on 25/04/17.
 */
public interface Vista {
	// Estos son los métodos que necesita conocer el Controlador.
	String getEntrada();

	// Estos son los métodos que necesita conocer el Modelo.
	void nuevaListaClientes();

	void nuevaListaLlamadas();

	void nuevaListaFacturas();
}
