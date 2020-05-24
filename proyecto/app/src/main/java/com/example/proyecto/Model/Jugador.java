package com.example.proyecto.Model;

import java.util.Date;

public class Jugador {
    private String nombre;
    private String alias;
    private String password;
    private String imagen;
    private Date fecha_nacimiento;
    private String equipo_id;
    private String email;

    public Jugador(String nombre, String alias, String password, String imagen, Date fecha_nacimiento, String equipo_id, String email) {
        this.nombre = nombre;
        this.alias = alias;
        this.password = password;
        this.imagen = imagen;
        this.fecha_nacimiento = fecha_nacimiento;
        this.equipo_id = equipo_id;
        this.email = email;
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

    public String getEquipo_id() {
        return equipo_id;
    }

    public void setEquipo_id(String equipo_id) {
        this.equipo_id = equipo_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}
