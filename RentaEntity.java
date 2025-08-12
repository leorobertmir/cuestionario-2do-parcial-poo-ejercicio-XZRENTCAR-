package com.rentas.xzrentcar;

import java.util.Date;

public class RentaEntity {

    private String dni;
    private String nombres;
    private String modelo;
    private String marca;
    private Date feNacimiento;
    private String tieneTc;

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public Date getFeNacimiento() {
        return feNacimiento;
    }

    public void setFeNacimiento(Date feNacimiento) {
        this.feNacimiento = feNacimiento;
    }

    public String getTieneTc() {
        return tieneTc;
    }

    public void setTieneTc(String tieneTc) {
        this.tieneTc = tieneTc;
    }

}