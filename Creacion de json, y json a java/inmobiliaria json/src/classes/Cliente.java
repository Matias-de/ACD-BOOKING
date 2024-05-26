package classes;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class Cliente {
    //atributos
    private String nombre;
    private String apellido;
    private String correoElectronico;
    private String medioDePago;
    private String contraseña;
    private int cantPersonas;
    private Date fechaInicio;
    private Date fechaFinal;


    //constructores

    public Cliente(String nombre, String apellido, String correoElectronico, String medioDePago, String contraseña, int cantPersonas){
        this.nombre = nombre;
        this.apellido = apellido;
        this.correoElectronico = correoElectronico;
        this.medioDePago = medioDePago;
        this.cantPersonas = cantPersonas;
        this.contraseña=contraseña;
        fechaInicio= new Date();
        fechaFinal= new Date(); // las fechas que elija el usuario aparte en otra funcion

    }

    public Cliente() {
        nombre = "";
        apellido = "";
        correoElectronico = "";
        medioDePago = "";
        contraseña="";
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
    public String getContraseña() {
        return contraseña;
    }

    //setters

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

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
        return "nombre='" + nombre + '\'' +
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

    //@Override
    /*public boolean equals(Object obj) {
        boolean rta=true;
        if(obj!=null){
            if(obj instanceof Cliente){
                Cliente aux= (Cliente)obj;
                if(aux.correoElectronico.equalsIgnoreCase(correoElectronico)&& aux.contraseña.equalsIgnoreCase(contraseña)){
                    rta=true; //si el email y la contraseña son iguales, devuelve true
                }
            }
        }

        return rta;
    }*/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return cantPersonas == cliente.cantPersonas && Objects.equals(nombre, cliente.nombre) && Objects.equals(apellido, cliente.apellido) && Objects.equals(correoElectronico, cliente.correoElectronico) && Objects.equals(medioDePago, cliente.medioDePago) && Objects.equals(contraseña, cliente.contraseña) && Objects.equals(fechaInicio, cliente.fechaInicio) && Objects.equals(fechaFinal, cliente.fechaFinal);
    }
}
