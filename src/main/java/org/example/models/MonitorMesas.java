package org.example.models;
import javafx.application.Platform;
import org.example.vistas.ClienteVista;

import java.util.ArrayList;
import java.util.List;

public class MonitorMesas {
    private final int TOTAL_MESAS = 6;
    private final List<Boolean> mesas;
    public final Object lockMesero = new Object();

    private final double[][] POSICIONES_MESAS = {
            // Mesa izquierda primera linea
            {300, 50},
            // Mesa derecha segunda linea
            {400, 50},
            // Mesa izquierda segunda linea
            {300, 180},
            // Mesa derecha segunda linea
            {400, 180},
            // Penultima mesa abajo
            {300, 300},
            // Ultima mesa abajo
            {300, 400}
    };
    private final List<ClienteVista> listaClientesVista;

    public MonitorMesas(List<ClienteVista> listaClientesVista) {
        this.listaClientesVista = listaClientesVista;
        mesas = new ArrayList<>(TOTAL_MESAS);
        for (int i = 0; i < TOTAL_MESAS; i++) {
            mesas.add(false); // false = mesa libre
        }
    }

    public synchronized void asignarMesa(Comensal comensal) throws InterruptedException {
        while (!hayMesasDisponibles()) {
            wait(); // Bloquea al comensal hasta que se libere una mesa
        }

        // Busca y asigna la primera mesa libre
        for (int i = 0; i < TOTAL_MESAS; i++) {
            if (!mesas.get(i)) {
                mesas.set(i, true); // Marca la mesa como ocupada
                comensal.setMesaId(i + 1);
                comensal.setEstado("Atendido");
                // Actualiza la vista del comensal
                ClienteVista clienteActual = listaClientesVista.stream()
                        .filter(v -> v.getId() == comensal.getId())
                        .findFirst()
                        .orElse(null);

                if (clienteActual != null) {
                    double[] posMesa = getPosicionMesa(i);
                    Platform.runLater(() -> clienteActual.moverCliente(posMesa[0], posMesa[1]));
                }

                System.out.println("Comensal " + comensal.getId() + " asignado a mesa " + (i + 1));

                synchronized (lockMesero) {
                    lockMesero.notifyAll();
                }
                return; // Salir del método después de asignar la mesa
            }
        }
    }

    public boolean hayMesasDisponibles() {
        return mesas.stream().anyMatch(mesa -> !mesa); // Devuelve true si hay al menos una mesa libre
    }

    public synchronized void liberarMesa(int numeroMesa) {
        mesas.set(numeroMesa - 1, false); // liberar la mesa
        System.out.println("mesa "+numeroMesa+ " liberada.");
        notifyAll();
    }

    public double[] getPosicionMesa(int i) {
                   return this.POSICIONES_MESAS[i];
    }
}
