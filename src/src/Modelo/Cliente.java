package Modelo;

import java.util.Calendar;
import java.util.Date;

public class Cliente {
    //atributos
    private String nombre;
    private String apellido;
    private String correoElectronico;
    private String medioDePago;
    private int cantPersonas;
    private Date fechaInicio;
    private Date fechaFinal;

    //constructores


    public Cliente(String nombre, String apellido, String correoElectronico, String medioDePago, int cantPersonas){
        this.nombre = nombre;
        this.apellido = apellido;
        this.correoElectronico = correoElectronico;
        this.medioDePago = medioDePago;
        this.cantPersonas = cantPersonas;
        fechaInicio= new Date();
        fechaFinal= new Date(); // las fechas que elija el usuario aparte en otra funcion
    }

    public Cliente() {
        nombre = "";
        apellido = "";
        correoElectronico = "";
        medioDePago = "";
        cantPersonas = 0;
        fechaInicio= new Date();
        fechaFinal= new Date();
    }

    //getters


    public String getNombre() {
        return nombre;
    }

    public Date getFechaFinal() {
        return fechaFinal;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public int getCantPersonas() {
        return cantPersonas;
    }

    public String getMedioDePago() {
        return medioDePago;
    }

    public String getApellido() {
        return apellido;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    //setters

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public void setMedioDePago(String medioDePago) {
        this.medioDePago = medioDePago;
    }

    public void setCantPersonas(int cantPersonas) {
        this.cantPersonas = cantPersonas;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }


    //metodos


    @Override
    public String toString() {
        return "Cliente{" +
                "nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", correoElectronico='" + correoElectronico + '\'' +
                ", medioDePago='" + medioDePago + '\'' +
                ", cantPersonas=" + cantPersonas +
                ", fechaInicio=" + fechaInicio +
                ", fechaFinal=" + fechaFinal +
                '}';
    }

    @Override
    public int hashCode() {
        return 1;
    }

    @Override
    public boolean equals(Object obj) {
        boolean rta=true;
        if(obj!=null){
            if(obj instanceof Cliente){
                Cliente aux= (Cliente)obj;
                if(aux.nombre.equalsIgnoreCase(nombre)&& aux.cantPersonas==cantPersonas&& aux.apellido.equalsIgnoreCase(apellido)){
                    rta=true; //si el nombre, la cantidad de personas y el apellido concuerdan, efectivamente son iguales
                }
            }
        }

        return rta;
    }

    public void asignarFecha(int diainicio, int diaFinal, int anio){ //se puede hacer en interfaz para cada clase que use fechas
        fechaInicio.setDate(diainicio); //ya tendria que venir validado el año entre 1 y 31 o 30 segun el mes
        fechaInicio.setYear(anio); //tendriamos que validar que el año no sea menor a 2024, o hacer que sea solo del año actual de la compu
        fechaFinal.setDate(diaFinal);
        fechaFinal.setYear(anio);

    }


















}
