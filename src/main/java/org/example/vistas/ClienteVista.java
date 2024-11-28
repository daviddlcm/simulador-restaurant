package org.example.vistas;

import com.almasb.fxgl.entity.Entity;
import org.example.factory.RestaurantEntityFactory;

public class ClienteVista {
    private int id;
    private final RestaurantEntityFactory restaurantEntityFactory;
    private Entity clienteEntidad;

    public ClienteVista(RestaurantEntityFactory rF, int id){
        this.id = id;
        this.restaurantEntityFactory = rF;
    }

    public void crearCliente(double x, double y){
        this.clienteEntidad = this.restaurantEntityFactory.crearCliente(x, y);
    }

    public void moverCliente(double x, double y){
        this.clienteEntidad.setPosition(x, y);
    }

    public int getId() {
        return id;
    }

    public double getPositionX(){
        return this.clienteEntidad.getX();
    }
    public double getPositionY(){
        return this.clienteEntidad.getY();
    }

    public void eliminarDeEscena(){
        this.clienteEntidad.removeFromWorld();
    }
}
