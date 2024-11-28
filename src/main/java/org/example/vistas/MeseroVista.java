package org.example.vistas;

import com.almasb.fxgl.entity.Entity;
import org.example.factory.RestaurantEntityFactory;

public class MeseroVista {
    private final RestaurantEntityFactory restaurantEntityFactory;
    private Entity meseroEntidad;

    private final double[][] POSICIONES_MESAS = {
            // Mesa izquierda primera linea
            {-200, -400},
            // Mesa derecha segunda linea
            {0, -400},
            // Mesa izquierda segunda linea
            {-200, -250},
            // Mesa derecha segunda linea
            {0, -250},
            // Penultima mesa abajo
            {-200, 120},
            // Ultima mesa abajo
            {-200, 10}
    };

    public MeseroVista(RestaurantEntityFactory rF){
        this.restaurantEntityFactory = rF;
    }

    public void crearMesero(double x, double y){
        this.meseroEntidad = this.restaurantEntityFactory.crearMesero(x, y);
    }

    public void moverMesero(int idMesa){
        double[] posiciones =  this.POSICIONES_MESAS[idMesa];
        this.meseroEntidad.setPosition(posiciones[0], posiciones[1]);
    }

    public void moverMeseroConPosiciones(double x, double y){
        this.meseroEntidad.setPosition(x, y);
    }
}