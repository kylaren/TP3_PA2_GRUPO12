package com.example.tp3_pa2_grupo12;

public class Parqueo {
    private String nroMatricula;
    private String tiempo;

    public Parqueo(String nroMatricula, String tiempo) {
        this.nroMatricula = nroMatricula;
        this.tiempo = tiempo;
    }

    public String getNroMatricula() {
        return nroMatricula;
    }

    public String getTiempo() {
        return tiempo;
    }
}