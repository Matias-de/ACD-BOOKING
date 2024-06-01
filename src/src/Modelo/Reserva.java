package Modelo;

import Enumeraciones.EstadoAlojamiento;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public class Reserva   {
    //atributos
    private Alojamiento alojamiento;
    private Cliente cliente;
    private double precioTotal;
    private UUID pin; //genera claves gigantes pero aleatorias //revisar otra forma o si se puede acortar

    //constructores

    public Reserva() {
        alojamiento= null;
        cliente= new Cliente();
        precioTotal=0;
        pin=UUID.randomUUID(); //la genera
    }

    public Reserva(Alojamiento alojamiento, Cliente cliente, double precioTotal) {
        this.alojamiento = alojamiento;
        this.cliente = cliente;
        this.precioTotal = precioTotal;
        pin=UUID.randomUUID();
    }

    //Getters

    public Cliente getCliente() {
        return cliente;
    }

    public Alojamiento getAlojamiento() {
        return alojamiento;
    }

    public double getPrecioTotal() {
        return precioTotal;
    }

    public UUID getPin() {
        return pin;
    }

    //Metodos

    public void verificarDisp(){
        Date fechaActual  = new Date();

        if(cliente.getFechaInicio().before(fechaActual) && cliente.getFechaFinal().after(fechaActual))
        {
            alojamiento.setEstado(EstadoAlojamiento.RESERVADO);
        }
        alojamiento.setEstado(EstadoAlojamiento.DISPONIBLE);
    }

    @Override
    public String toString() {
        return "Reserva{" +
                "alojamiento=" + alojamiento +
                ", cliente=" + cliente +
                ", precioTotal=" + precioTotal +
               // ", pin=" + pin +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        boolean sonIguales=false;
        if(obj!=null){
            if(obj instanceof Reserva){
                Reserva reserva=(Reserva)obj;
                if(reserva.pin==pin&&reserva.precioTotal==precioTotal){
                    sonIguales=true;
                }
            }
        }

        return sonIguales;
    }


    @Override
    public int hashCode() {
        return 1;
    }
    

}
