package org.example.factory;

import com.almasb.fxgl.entity.Entity;

import static com.almasb.fxgl.dsl.FXGLForKtKt.entityBuilder;

public class RestaurantEntityFactory {
    public RestaurantEntityFactory(){}
    public void crearFondo(){
        entityBuilder()
                .at(0, 0)
                .view("fondo.jpg")
                .zIndex(-1)
                .buildAndAttach();
    }

    public Entity crearMesero(double x, double y){
        return entityBuilder()
                .at(x, y)
                .viewWithBBox("waiter.png")
                .scale(0.05, 0.05)
                .zIndex(3)
                .buildAndAttach();
    }

    public Entity crearCliente(double x, double y){
        return entityBuilder()
                .at(x, y)
                .viewWithBBox("comensal.png")
                .buildAndAttach();
    }

    public Entity crearChef(double x, double y){
        return entityBuilder()
                .at(x, y)
                .viewWithBBox("Chef B Icon.png")
                .buildAndAttach();
    }

}
