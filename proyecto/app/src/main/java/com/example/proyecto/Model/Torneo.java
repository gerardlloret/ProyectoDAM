package com.example.proyecto.Model;

import java.util.Date;

public class Torneo {
    private int torneo_id;
    private String nombre;
    private Date fecha;
    private String ciudad;

    public Torneo(int torneo_id, String nombre, Date fecha, String ciudad) {
        this.torneo_id = torneo_id;
        this.nombre = nombre;
        this.fecha = fecha;
        this.ciudad = ciudad;
    }

    public int getTorneo_id() {
        return torneo_id;
    }

    public void setTorneo_id(int torneo_id) {
        this.torneo_id = torneo_id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }
}
