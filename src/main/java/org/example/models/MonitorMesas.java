package org.example.models;

import java.util.ArrayList;
import java.util.List;

public class MonitorMesas {
    private final int TOTAL_MESAS = 6;
    private final List<Boolean> mesas;

    public MonitorMesas() {
        mesas = new ArrayList<>(TOTAL_MESAS);
        for (int i = 0; i < TOTAL_MESAS; i++) {
            mesas.add(false); // false = mesa libre
        }
    }

    public int getTOTAL_MESAS() {
        int count = 0;
        for (int i = 0; i < TOTAL_MESAS; i++) {
            if(mesas.get(i)==true){
                count++;
            }
        }
        return count;
    }

    public synchronized int asignarMesa() {
        for (int i = 0; i < TOTAL_MESAS; i++) {
            if (!mesas.get(i)) {
                mesas.set(i, true); // asignar la mesa
                return i + 1; // Mesa asignada
            }
        }
        return -1; // No hay mesas disponibles
    }

    public synchronized void liberarMesa(int numeroMesa) {
        mesas.set(numeroMesa - 1, false); // liberar la mesa
        System.out.println("mesa "+numeroMesa+ " liberada.");
        notifyAll();
    }
}
