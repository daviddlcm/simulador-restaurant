package org.example.Threads;

import org.example.models.MonitorOrdenes;
import org.example.models.MonitorComidas;
import org.example.models.Comensal;
import org.example.models.Orden;

import java.util.List;

public class MeseroThread implements Runnable {
    private MonitorOrdenes monitorOrdenes;
    private MonitorComidas monitorComidas;
    private List<Comensal> comensales;

    public MeseroThread(MonitorOrdenes monitorOrdenes, MonitorComidas monitorComidas, List<Comensal> comensales) {
        this.monitorOrdenes = monitorOrdenes;
        this.monitorComidas = monitorComidas;
        this.comensales = comensales;
    }

    @Override
    public void run() {
        while (true) {
            try {
                synchronized (comensales) {
                    Comensal comensalAtendido = comensales.stream()
                            .filter(c -> c.getEstado().equals("Atendido"))
                            .findFirst()
                            .orElse(null);

                    if (comensalAtendido != null) {
                        Orden nuevaOrden = new Orden(comensalAtendido.getMesaId(), comensalAtendido.getId());
                        monitorOrdenes.ponerOrden(nuevaOrden);
                        comensalAtendido.setEstado("Esperando comida");
                        System.out.println("Mesero: Tomando orden del comensal " + comensalAtendido.getId());
                    }
                }

                // Entregar comida lista
                Orden comidaLista = monitorComidas.tomarComida();
                if (comidaLista != null) {
                    synchronized (comensales) {
                        Comensal comensal = comensales.stream()
                                .filter(c -> c.getId() == comidaLista.getComensalId() && c.getEstado().equals("Esperando comida"))
                                .findFirst()
                                .orElse(null);

                        if (comensal != null) {
                            comensal.setEstado("Comiendo");
                            //System.out.println("comensal completo: "+comensal.toString());
                            System.out.println("Mesero: Entregando comida al comensal " + comensal.getId());
                        }else{
                            System.out.println("Mesero: Comida lista para un comensal inactivo, ignorando.");
                        }
                    }
                }

                Thread.sleep(1000); // Reducir carga en el procesador
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}