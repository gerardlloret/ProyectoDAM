package com.example.proyecto.Model;

public class Oferta {
    private int oferta_id;
    private int equipo_id;
    private String nombre;
    private String descripcion;
    private int numero_candidaturas;
    private int juego_id;
    private int vacantes;

    public Oferta(int oferta_id, int equipo_id, String nombre, String descripcion, int numero_candidaturas, int juego_id, int vacantes) {
        this.oferta_id = oferta_id;
        this.equipo_id = equipo_id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.numero_candidaturas = numero_candidaturas;
        this.juego_id = juego_id;
        this.vacantes = vacantes;
    }

    public int getOferta_id() {
        return oferta_id;
    }

    public void setOferta_id(int oferta_id) {
        this.oferta_id = oferta_id;
    }

    public int getEquipo_id() {
        return equipo_id;
    }

    public void setEquipo_id(int equipo_id) {
        this.equipo_id = equipo_id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getNumero_candidaturas() {
        return numero_candidaturas;
    }

    public void setNumero_candidaturas(int numero_candidaturas) {
        this.numero_candidaturas = numero_candidaturas;
    }

    public int getJuego_id() {
        return juego_id;
    }

    public void setJuego_id(int juego_id) {
        this.juego_id = juego_id;
    }

    public int getVacantes() {
        return vacantes;
    }

    public void setVacantes(int vacantes) {
        this.vacantes = vacantes;
    }
}
