package org.example.models;

public class Orden {
    private int id;
    private int comensalId;
    private String estado; // Estados posibles: "Pendiente", "En Proceso", "Listo"

    public Orden(int id, int comensalId) {
        this.id = id;
        this.comensalId = comensalId;
        this.estado = "Pendiente";
    }

    public int getId() {
        return id;
    }

    public int getComensalId() {
        return comensalId;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Orden{id=" + id + ", comensalId=" + comensalId + ", estado='" + estado + "'}";
    }
}

