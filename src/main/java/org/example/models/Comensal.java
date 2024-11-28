package org.example.models;

public class Comensal {
    private int id;
    private int mesaId;
    private String estado; // Estados posibles: "Esperando", "Atendido", "Comiendo", "Terminado"

    public Comensal(int id) {
        this.id = id;
        this.mesaId = -1; // Sin mesa al inicio
        this.estado = "Esperando";
    }

    public int getId() {
        return id;
    }

    public int getMesaId() {
        return mesaId;
    }

    public void setMesaId(int mesaId) {
        this.mesaId = mesaId;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Comensal{id=" + id + ", mesaId=" + mesaId + ", estado='" + estado + "'}";
    }
}