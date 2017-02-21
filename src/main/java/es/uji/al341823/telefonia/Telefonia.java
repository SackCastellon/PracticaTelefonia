package es.uji.al341823.telefonia;

import es.uji.www.GeneradorDatosINE;

/**
 * Created by Juanjo on 21/2/2017.
 */
public class Telefonia {
    public static void main(String[] args) {
        GeneradorDatosINE generador = new GeneradorDatosINE();

        for (int i = 0; i < 1000; i++) {
            System.out.println(generador.getNIF());
        }
    }
}
