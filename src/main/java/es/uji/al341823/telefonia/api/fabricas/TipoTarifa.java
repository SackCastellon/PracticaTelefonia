package es.uji.al341823.telefonia.api.fabricas;

/**
 * Created by al341819 on 4/04/17.
 */
public enum TipoTarifa {
    BASICA("Tarifa básica"),
    TARDES("Tarifa tardes"),
    DOMINGOS("Tarifa domingos");
    private String descripcion;

    private TipoTarifa(String descripcion) {
        this.descripcion=descripcion;
    }

    public static String opciones() {
        StringBuilder sb = new StringBuilder();
        for(TipoTarifa tipo: values())
            sb.append(tipo.ordinal() + ".- " + tipo.descripcion + "\n");
        return sb.toString();
    }

    public static TipoTarifa enteroATipo(int posicion) {
        return values()[posicion];
    }
}
