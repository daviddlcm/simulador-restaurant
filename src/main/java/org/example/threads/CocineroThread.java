package org.example.threads;

import org.example.models.Comensal;
import org.example.models.MonitorOrdenes;
import org.example.models.MonitorComidas;
import org.example.models.Orden;
import org.example.vistas.MeseroVista;

import java.util.List;

public class CocineroThread implements Runnable {
    private MonitorOrdenes monitorOrdenes;
    private MonitorComidas monitorComidas;
    private MeseroVista meseroVista;

    public CocineroThread(MonitorOrdenes monitorOrdenes, MonitorComidas monitorComidas, List<Comensal> comensales, MeseroVista mesero) {
        this.monitorOrdenes = monitorOrdenes;
        this.monitorComidas = monitorComidas;
        this.meseroVista = mesero;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Orden orden = monitorOrdenes.tomarOrden(); // Tomar una orden
                if (orden != null) {
                    System.out.println("Cocinero: Cocinando orden del comensal " + orden.getComensalId());
                    // Simular cocinado
                    Thread.sleep((int) (Math.random() * 5000) + 2000); // Tiempo de cocinado
                    monitorComidas.ponerComida(orden); // Poner comida lista en el monitor
                    System.out.println("Cocinero: Comida lista para el comensal " + orden.getComensalId());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
