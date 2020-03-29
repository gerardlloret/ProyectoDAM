package com.example.proyecto.Model;

import java.util.Date;

public class Cuota {
    private String cuota_id;
    private int equipo_id;
    private double precio;
    private boolean renovacion_automatica;
    private Date fecha_inicio;
    private Date fecha_final;

    public Cuota(String cuota_id, int equipo_id, double precio, boolean renovacion_automatica, Date fecha_inicio, Date fecha_final) {
        this.cuota_id = cuota_id;
        this.equipo_id = equipo_id;
        this.precio = precio;
        this.renovacion_automatica = renovacion_automatica;
        this.fecha_inicio = fecha_inicio;
        this.fecha_final = fecha_final;
    }

    public String getCuota_id() {
        return cuota_id;
    }

    public void setCuota_id(String cuota_id) {
        this.cuota_id = cuota_id;
    }

    public int getEquipo_id() {
        return equipo_id;
    }

    public void setEquipo_id(int equipo_id) {
        this.equipo_id = equipo_id;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public boolean isRenovacion_automatica() {
        return renovacion_automatica;
    }

    public void setRenovacion_automatica(boolean renovacion_automatica) {
        this.renovacion_automatica = renovacion_automatica;
    }

    public Date getFecha_inicio() {
        return fecha_inicio;
    }

    public void setFecha_inicio(Date fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public Date getFecha_final() {
        return fecha_final;
    }

    public void setFecha_final(Date fecha_final) {
        this.fecha_final = fecha_final;
    }
}
