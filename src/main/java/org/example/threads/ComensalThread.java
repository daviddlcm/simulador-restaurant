package org.example.threads;

import javafx.application.Platform;
import org.example.models.Comensal;
import org.example.models.MonitorMesas;
import org.example.models.MonitorOrdenes;
import org.example.vistas.ClienteVista;

import java.util.List;

public class ComensalThread implements Runnable {
    private final Comensal comensal;
    private final MonitorMesas monitorMesas;
    private final MonitorOrdenes monitorOrdenes;
    private final List<ClienteVista> listaClientesVista;

    public ComensalThread(Comensal comensal, MonitorMesas monitorMesas, MonitorOrdenes monitorOrdenes, List<ClienteVista> listaClientes) {
        this.comensal = comensal;
        this.monitorMesas = monitorMesas;
        this.monitorOrdenes = monitorOrdenes;
        this.listaClientesVista = listaClientes;
    }

    @Override
    public void run() {
        try {
            // Intentar asignar una mesa al llegar al restaurante
            monitorMesas.asignarMesa(comensal);

            // Esperar hasta que el comensal esté "Comiendo"
            while (!comensal.getEstado().equals("Comiendo")) {
                Thread.sleep(500); // Espera activa
            }

            // Simular tiempo de comida
            System.out.println("Comensal " + comensal.getId() + " está comiendo...");
            Thread.sleep((int) (Math.random() * 5000) + 2000); // Entre 2 y 7 segundos

            // Cambiar estado a "Terminado" y liberar la mesa
            synchronized (monitorMesas) {
                monitorMesas.liberarMesa(comensal.getMesaId());

                // Actualizar la vista gráfica para eliminar al comensal
                ClienteVista clienteActual = listaClientesVista.stream()
                        .filter(v -> v.getId() == comensal.getId())
                        .findFirst()
                        .orElse(null);

                if (clienteActual != null) {
                    Platform.runLater(clienteActual::eliminarDeEscena);
                }

                comensal.setEstado("Terminado");
                System.out.println("Comensal " + comensal.getId() + " terminó y liberó la mesa " + comensal.getMesaId());
            }

            // Remover órdenes del comensal
            synchronized (monitorOrdenes) {
                monitorOrdenes.removerOrdenesDeComensal(comensal.getId());
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Comensal " + comensal.getId() + " interrumpido.");
        }
    }
}
