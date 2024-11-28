package org.example.Threads;
import org.example.models.MonitorMesas;
import org.example.models.Comensal;

import java.util.List;

public class RecepcionistaThread implements Runnable {
    private final MonitorMesas monitorMesas;
    private final List<Comensal> comensales;

    public RecepcionistaThread(MonitorMesas monitorMesas, List<Comensal> comensales) {
        this.monitorMesas = monitorMesas;
        this.comensales = comensales;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (comensales) {
                // Buscar el primer comensal en estado "Esperando"
                Comensal comensalEsperando = comensales.stream()
                        .filter(c -> c.getEstado().equals("Esperando"))
                        .findFirst()
                        .orElse(null);

                if (comensalEsperando != null) {
                    // Intentar asignar una mesa al comensal esperando
                    int mesaAsignada = monitorMesas.asignarMesa();
                    if (mesaAsignada != -1) {
                        comensalEsperando.setMesaId(mesaAsignada);
                        comensalEsperando.setEstado("Atendido");
                        System.out.println("Recepcionista: Asignando mesa " + mesaAsignada + " al comensal " + comensalEsperando.getId());
                    }
                }
            }

            // Esperar antes de revisar nuevamente
            try {
                Thread.sleep(500); // Reduce la carga del procesador
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Recepcionista interrumpida.");
            }
        }
    }
}