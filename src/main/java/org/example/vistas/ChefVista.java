package org.example.vistas;

import org.example.factory.RestaurantEntityFactory;

public class ChefVista {
    private final RestaurantEntityFactory restaurantEntityFactory;
    public ChefVista(RestaurantEntityFactory rF){
        this.restaurantEntityFactory = rF;
    }

    public void crearChef(double x, double y){
        this.restaurantEntityFactory.crearChef(x, y);
    }
}
