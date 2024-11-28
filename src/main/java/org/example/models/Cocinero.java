package org.example.models;

public class Cocinero {
    private int id;
    private boolean cocinando; // true si está cocinando, false si está en reposo

    public Cocinero(int id) {
        this.id = id;
        this.cocinando = false;
    }

    public int getId() {
        return id;
    }

    public boolean isCocinando() {
        return cocinando;
    }

    public void setCocinando(boolean cocinando) {
        this.cocinando = cocinando;
    }

    @Override
    public String toString() {
        return "Cocinero{id=" + id + ", cocinando=" + cocinando + "}";
    }
}