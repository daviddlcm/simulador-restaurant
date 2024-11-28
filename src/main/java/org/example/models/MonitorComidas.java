package org.example.models;

import java.util.concurrent.LinkedBlockingQueue;

public class MonitorComidas {
    private final LinkedBlockingQueue<Orden> comidas;

    public MonitorComidas() {
        comidas = new LinkedBlockingQueue<>();
    }

    public void ponerComida(Orden comida) throws InterruptedException {
        comidas.put(comida); // Insertar comida lista
    }

    public Orden tomarComida() throws InterruptedException {
        return comidas.take(); // Extraer comida lista
    }
}