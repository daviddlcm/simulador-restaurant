package org.example.models;

import java.util.concurrent.LinkedBlockingQueue;

public class MonitorOrdenes {
    private final LinkedBlockingQueue<Orden> ordenes;

    public MonitorOrdenes() {
        ordenes = new LinkedBlockingQueue<>();
    }

    public void ponerOrden(Orden orden) throws InterruptedException {
        ordenes.put(orden); // Insertar orden
    }
    public synchronized void removerOrdenesDeComensal(int comensalId) {
        ordenes.removeIf(orden -> orden.getComensalId() == comensalId);
    }

    public Orden tomarOrden() throws InterruptedException {
        return ordenes.take(); // Extraer orden
    }
}