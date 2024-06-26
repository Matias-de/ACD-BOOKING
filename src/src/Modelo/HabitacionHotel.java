package Modelo;

import Enumeraciones.EstadoAlojamiento;

import java.io.Serializable;

public class HabitacionHotel extends Alojamiento implements Serializable {
    private String servicios;
    private String tipoHabitacion;
    private int numeroHabitacion;

    public HabitacionHotel(double precioXAlojar, String descripcion, String nombre, String direccion, String zona, String comentarios, EstadoAlojamiento estado, String servicios, String tipoHabitacion, int numeroHabitacion) {
        super(precioXAlojar, descripcion, nombre, direccion, zona, comentarios, estado);
        this.servicios = servicios;
        this.tipoHabitacion = tipoHabitacion;
        this.numeroHabitacion = numeroHabitacion;
    }

    public HabitacionHotel(String servicios, String tipoHabitacion, int numeroHabitacion) {
        this.servicios = servicios;
        this.tipoHabitacion = tipoHabitacion;
        this.numeroHabitacion = numeroHabitacion;
    }


    public HabitacionHotel()
    {
        super();
     servicios="";
     tipoHabitacion="";
     numeroHabitacion=0;

    }

    public String getServicios() {
        return servicios;
    }

    public void setServicios(String servicios) {
        this.servicios = servicios;
    }

    public String getTipoHabitacion() {
        return tipoHabitacion;
    }

    public void setTipoHabitacion(String tipoHabitacion) {
        this.tipoHabitacion = tipoHabitacion;
    }

    public int getNumeroHabitacion() {
        return numeroHabitacion;
    }

    public void setNumeroHabitacion(int numeroHabitacion) {
        this.numeroHabitacion = numeroHabitacion;
    }


    @Override
    public String toString() {
        return super.toString()+"Habitacion de hotel: " +
                "servicios='" + servicios + '\'' +
                ", tipoHabitacion='" + tipoHabitacion + '\'' +
                ", numeroHabitacion=" + numeroHabitacion +
                '}';
    }
    public String listar(){
        return super.listarAlojamiento()+"Habitacion de hotel: " +
                "servicios='" + servicios + '\'' +
                ", tipoHabitacion='" + tipoHabitacion + '\'' +
                ", numeroHabitacion=" + numeroHabitacion +
                '}';
    }

    @Override
    public boolean equals(Object obj)
    {
        boolean rta = super.equals(obj);
        if(obj!=null){

            if(obj instanceof HabitacionHotel)
            {
                HabitacionHotel aux=(HabitacionHotel) obj;
                if(aux.numeroHabitacion!=numeroHabitacion && !aux.tipoHabitacion.equalsIgnoreCase(tipoHabitacion))
                {
                    rta=false;
                }
            }
        }
        return rta;
    }
    @Override
    public int hashCode() {
        return 1;
    }
}
