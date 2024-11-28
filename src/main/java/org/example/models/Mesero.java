package org.example.models;

public class Mesero {
    private int id;
    private boolean ocupado;

    public Mesero(int id) {
        this.id = id;
        this.ocupado = false;
    }

    public int getId() {
        return id;
    }

    public boolean isOcupado() {
        return ocupado;
    }

    public void setOcupado(boolean ocupado) {
        this.ocupado = ocupado;
    }

    @Override
    public String toString() {
        return "Mesero{id=" + id + ", ocupado=" + ocupado + "}";
    }
}
