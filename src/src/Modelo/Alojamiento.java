package Modelo;

import java.util.Date;

public abstract class Alojamiento {

    //atributos
    private double precioXAlojar;
    private int valoracion; //0 a 5 VALIDAR
    private String descripcion;
    private String nombre;
    private String direccion;
    private String zona;
    private String comentarios;
    private Date fechaOcupacion;
    private boolean disponibilidad;

    //constructores


    public Alojamiento(double precioXAlojar, int valoracion, String descripcion, String nombre, String direccion, String zona, String comentarios,  boolean disponibilidad) {
        this.precioXAlojar = precioXAlojar;
        this.valoracion = valoracion;
        this.descripcion = descripcion;
        this.nombre = nombre;
        this.direccion = direccion;
        this.zona = zona;
        this.comentarios = comentarios;
        fechaOcupacion= new Date();
        this.disponibilidad = disponibilidad;
    }

    public Alojamiento() {
        precioXAlojar = 0;
        valoracion = 0;
        descripcion = "";
        nombre="";
        direccion="";
        zona="";
        comentarios="";
        fechaOcupacion= new Date();
        disponibilidad = false;

    }

//getters
    public String getNombre() {
        return nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Date getFechaOcupacion() {
        return fechaOcupacion;
    }

    public double getPrecioXAlojar() {
        return precioXAlojar;
    }

    public int getValoracion() {
        return valoracion;
    }

    public String getComentarios() {
        return comentarios;
    }

    public String getZona() {
        return zona;
    }


}

