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

    public void asignarFecha(int diainicio, int diaFinal, int mesInicial, int mesFinal, boolean anioInicio, boolean anioFinal){ //

        fechaInicio.setDate(diainicio); //ya tendria que venir validado el año entre 1 y 31 o 30 segun el mes
        fechaInicio.setMonth(mesInicial);
        if(anioInicio){
            fechaInicio.setYear(fechaInicio.getYear()+1901);// si es true quiere decir que el año elegido es el siguiente
        }else {
            fechaInicio.setYear(fechaInicio.getYear()+1900);
        }

        fechaFinal.setDate(diaFinal);
        fechaFinal.setMonth(mesFinal);
        if(anioFinal){
            fechaFinal.setYear(fechaFinal.getYear()+1901); // si es true quiere decir que el año elegido es el siguiente

        }else{
            fechaFinal.setYear(fechaFinal.getYear()+1900);
        }



    }

    public String mostrarFechaDeViaje(){
        String rta="no asignada";
        Calendar calendar = Calendar.getInstance();
        if(fechaInicio.getYear()!=calendar.get(Calendar.YEAR)-1900){ //si el año actual -1900 es distinto de lo que guarda el getYear de un Date(124 si es 2024 etc), significa que ya esta asignada una fecha, por lo que la muestra
            rta= "fechaInicio= "+fechaInicio.getDate()+" / "+fechaInicio.getMonth()+" / "+fechaInicio.getYear() +
                    " fechaFinal= "+fechaFinal.getDate()+" / "+fechaFinal.getMonth()+" / "+fechaFinal.getYear();
        }


        return rta;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", correoElectronico='" + correoElectronico + '\'' +
                ", medioDePago='" + medioDePago + '\'' +
                ", cantPersonas= " + cantPersonas + '\'' +
                mostrarFechaDeViaje()+
                 '}';
    }

    @Override
    public int hashCode() {
        return 1;
    }

    @Override
    public boolean equals(Object obj) {
        boolean rta=false;
        if(obj!=null){
            if(obj instanceof Cliente){
                Cliente aux= (Cliente)obj;
                if(aux.correoElectronico.equalsIgnoreCase(correoElectronico) && aux.nombre.equalsIgnoreCase(nombre)){
                    rta=true;
                }
            }
        }

        return rta;
    }




















}
