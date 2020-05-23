package com.example.proyecto.Model;

public class Juego {
    private int juego_id;
    private String nombre;
    private String genero;
    private String pegI;

    public Juego(int juego_id, String nombre, String genero, String pegI) {
        this.juego_id = juego_id;
        this.nombre = nombre;
        this.genero = genero;
        this.pegI = pegI;
    }

    public int getJuego_id() {
        return juego_id;
    }

    public void setJuego_id(int juego_id) {
        this.juego_id = juego_id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getPegI() {
        return pegI;
    }

    public void setPegI(String pegI) {
        this.pegI = pegI;
    }
}
