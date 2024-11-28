package org.example.Threads;
import javafx.application.Platform;
import org.example.models.MonitorMesas;
import org.example.models.Comensal;
import org.example.vistas.ClienteVista;

import java.util.List;

public class RecepcionistaThread implements Runnable {
    private final MonitorMesas monitorMesas;
    private final List<Comensal> comensales;
    private final List<ClienteVista> listaClientesVista;

    public RecepcionistaThread(MonitorMesas monitorMesas, List<Comensal> comensales,  List<ClienteVista> clienteVistaList) {
        this.monitorMesas = monitorMesas;
        this.comensales = comensales;
        this.listaClientesVista = clienteVistaList;
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
                        // Actualizar la vista del cliente correspondiente
                        ClienteVista clienteActual = this.listaClientesVista.stream()
                                .filter(vista -> vista.getId() == comensalEsperando.getId())
                                .findFirst()
                                .orElse(null);

                        comensalEsperando.setEstado("Atendido");
                        comensalEsperando.setMesaId(mesaAsignada);

                        // Consigue posicion de la mesa asignada
                        double[] posMesa = monitorMesas.getPosicionMesa(comensalEsperando.getMesaId()-1);

                        Platform.runLater(() -> clienteActual.moverCliente(posMesa[0], posMesa[1]));

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
