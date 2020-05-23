package com.example.proyecto.Model;

import java.util.List;

public class Oferta {
    private int oferta_id;
    private String equipo;
    private String nombre;
    private String descripcion;
    private int numero_candidaturas;
    private int juego;
    private List<String> jugador;
    private int vacantes;


    public Oferta(int oferta_id, String equipo, String nombre, String descripcion, int numero_candidaturas, int juego, List<String> jugador, int vacantes) {
        this.oferta_id = oferta_id;
        this.equipo = equipo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.numero_candidaturas = numero_candidaturas;
        this.juego = juego;
        this.jugador = jugador;
        this.vacantes = vacantes;
    }

    public int getOferta_id() {
        return oferta_id;
    }

    public void setOferta_id(int oferta_id) {
        this.oferta_id = oferta_id;
    }

    public String getEquipo_id() {
        return equipo;
    }

    public void setEquipo_id(String equipo_id) {
        this.equipo = equipo_id;
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
        return juego;
    }

    public void setJuego_id(int juego_id) {
        this.juego = juego_id;
    }

    public int getVacantes() {
        return vacantes;
    }

    public void setVacantes(int vacantes) {
        this.vacantes = vacantes;
    }

    public String getEquipo() {
        return equipo;
    }

    public void setEquipo(String equipo) {
        this.equipo = equipo;
    }

    public int getJuego() {
        return juego;
    }

    public void setJuego(int juego) {
        this.juego = juego;
    }

    public List<String> getJugador() {
        return jugador;
    }

    public void setJugador(List<String> jugador) {
        this.jugador = jugador;
    }
}
