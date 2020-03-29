package com.example.proyecto.Model;

public class Juego {
    private int juego_id;
    private String nombre;
    private String genero;
    private int pegI;

    public Juego(int juego_id, String nombre, String genero, int pegI) {
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

    public int getPegI() {
        return pegI;
    }

    public void setPegI(int pegI) {
        this.pegI = pegI;
    }
}
