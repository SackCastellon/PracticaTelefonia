package es.uji.al341823.telefonia.api.fabricas;

import es.uji.al341823.telefonia.facturacion.tarifas.Tarifa;
import es.uji.al341823.telefonia.facturacion.tarifas.TarifaBasica;
import es.uji.al341823.telefonia.facturacion.tarifas.TarifaDiasSemana;
import es.uji.al341823.telefonia.facturacion.tarifas.TarifaHoras;

/**
 * Created by al341819 on 4/04/17.
 */
public class FabricaTarifas {

    Tarifa tarifa;

    public Tarifa getTarifaBasica(TipoTarifa tipo){
        return tarifa = new TarifaBasica(0.15f);
    }

    public Tarifa getTarifaExtra(TipoTarifa tipo){
        switch(tipo){
            case TARDES:
                tarifa=new TarifaHoras(new TarifaBasica(0.15f),0.05f,16,20);
                break;
            case DOMINGOS:
                tarifa=new TarifaDiasSemana(new TarifaBasica(0.15f),0.0f,7);
                break;
        }
        return tarifa;
    }
}
