package com.example.proyecto.Model;

import java.util.Date;

public class Jugador {
    private int jugador_id;
    private String nombre;
    private String alias;
    private String password;
    private Date fecha_nacimiento;
    private int equipo_id;
    private String email;

    public Jugador(int jugador_id, String nombre, String alias, String password, Date fecha_nacimiento, int equipo_id, String email) {
        this.jugador_id = jugador_id;
        this.nombre = nombre;
        this.alias = alias;
        this.password = password;
        this.fecha_nacimiento = fecha_nacimiento;
        this.equipo_id = equipo_id;
        this.email = email;
    }

    public int getJugador_id() {
        return jugador_id;
    }

    public void setJugador_id(int jugador_id) {
        this.jugador_id = jugador_id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(Date fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public int getEquipo_id() {
        return equipo_id;
    }

    public void setEquipo_id(int equipo_id) {
        this.equipo_id = equipo_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
