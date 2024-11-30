package org.example.threads;

import org.example.models.*;
import org.example.vistas.ClienteVista;
import org.example.vistas.MeseroVista;

import java.util.List;

public class MeseroThread implements Runnable {
    private MonitorOrdenes monitorOrdenes;
    private MonitorComidas monitorComidas;
    private List<Comensal> comensales;
    private final List<ClienteVista> listaClientesVista;
    private MeseroVista meseroVista ;
    private MonitorMesas monitorMesas;


    public MeseroThread(
            MonitorOrdenes monitorOrdenes,
            MonitorComidas monitorComidas,
            List<Comensal> comensales,
            List<ClienteVista> clienteVistaList,
            MeseroVista mesero,
            MonitorMesas monitorMesas) {
        this.monitorOrdenes = monitorOrdenes;
        this.monitorComidas = monitorComidas;
        this.comensales = comensales;
        this.listaClientesVista = clienteVistaList;
        this.meseroVista = mesero;
        this.monitorMesas = monitorMesas;
    }


    @Override
    public void run() {
        while (true) {
            try {
                // Esperar notificación de que hay comensales atendidos
                synchronized (monitorMesas.lockMesero) {
                    while (comensales.stream().noneMatch(c -> c.getEstado().equals("Atendido"))) {
                        monitorMesas.lockMesero.wait();
                    }
                }

                synchronized (comensales) {
                    Comensal comensalAtendido = comensales.stream()
                            .filter(c -> c.getEstado().equals("Atendido"))
                            .findFirst()
                            .orElse(null);

                    if (comensalAtendido != null) {
                        // Mover mesero a la mesa del comensal
                        this.meseroVista.moverMesero(comensalAtendido.getMesaId() - 1);

                        // Crear y registrar la orden
                        Orden nuevaOrden = new Orden(comensalAtendido.getMesaId(), comensalAtendido.getId());
                        monitorOrdenes.ponerOrden(nuevaOrden);
                        nuevaOrden.setEstado("EN PROCESO");

                        // Cambiar estado del comensal
                        comensalAtendido.setEstado("Esperando comida");

                        System.out.println("Mesero: Tomando orden del comensal " + comensalAtendido.getId());
                    }
                }

                // Revisar si hay comida lista para entregar
                Orden comidaLista = monitorComidas.tomarComida();
                if (comidaLista != null) {
                    comidaLista.setEstado("TERMINADO");
                    synchronized (comensales) {
                        Comensal comensal = comensales.stream()
                                .filter(c -> c.getId() == comidaLista.getComensalId() && c.getEstado().equals("Esperando comida"))
                                .findFirst()
                                .orElse(null);

                        if (comensal != null) {
                            comensal.setEstado("Comiendo");
                            System.out.println("Mesero: Entregando comida al comensal " + comensal.getId());
                        } else {
                            System.out.println("Mesero: Comida lista para un comensal inactivo, ignorando.");
                        }
                    }
                }

                Thread.sleep(1000); // Reducir carga en el procesador
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Mesero interrumpido.");
            }
        }
    }

}
