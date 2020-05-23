package com.example.proyecto.Model;

public class Equipo {
    private String nombre;
    private int vacantes;
    private String descripcion;
    private int numero_miembros;
    private String email;

    public Equipo(String nombre, int vacantes, String descripcion, int numero_miembros, String email) {
        this.nombre = nombre;
        this.vacantes = vacantes;
        this.descripcion = descripcion;
        this.numero_miembros = numero_miembros;
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getVacantes() {
        return vacantes;
    }

    public void setVacantes(int vacantes) {
        this.vacantes = vacantes;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getNumero_miembros() {
        return numero_miembros;
    }

    public void setNumero_miembros(int numero_miembros) {
        this.numero_miembros = numero_miembros;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
