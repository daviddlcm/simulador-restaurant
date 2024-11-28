package org.example.models;

public class Mesa {
    private int id;
    private boolean ocupada; // true si está ocupada, false si está disponible

    public Mesa(int id) {
        this.id = id;
        this.ocupada = false;
    }

    public int getId() {
        return id;
    }

    public boolean isOcupada() {
        return ocupada;
    }

    public void setOcupada(boolean ocupada) {
        this.ocupada = ocupada;
    }

    @Override
    public String toString() {
        return "Mesa{id=" + id + ", ocupada=" + ocupada + "}";
    }
}

