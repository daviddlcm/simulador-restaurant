package org.example.Threads;

import org.example.models.Comensal;
import org.example.models.MonitorMesas;
import org.example.models.MonitorOrdenes;

public class ComensalThread implements Runnable {
    private final Comensal comensal;
    private final MonitorMesas monitorMesas;
    private final MonitorOrdenes monitorOrdenes;

    public ComensalThread(Comensal comensal, MonitorMesas monitorMesas, MonitorOrdenes monitorOrdenes) {
        this.comensal = comensal;
        this.monitorMesas = monitorMesas;
        this.monitorOrdenes = monitorOrdenes;
    }

    @Override
    public void run() {
        try {
            while (!comensal.getEstado().equals("Comiendo")) {
                Thread.sleep(500);
            }

            System.out.println("Comensal " + comensal.getId() + " está comiendo...");
            Thread.sleep((int) (Math.random() * 5000) + 2000);

            synchronized (monitorMesas) {
                comensal.setEstado("Terminado");
                monitorMesas.liberarMesa(comensal.getMesaId());
                System.out.println("Comensal " + comensal.getId() + " terminó y liberó la mesa " + comensal.getMesaId());
                System.out.println("Mesas ocupadas: "+monitorMesas.getTOTAL_MESAS());
            }

            synchronized (monitorOrdenes) {
                monitorOrdenes.removerOrdenesDeComensal(comensal.getId());
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Comensal " + comensal.getId() + " interrumpido.");
        }
    }
}