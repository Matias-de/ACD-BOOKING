package Modelo;

import java.util.UUID;

public class Reserva{
    //atributos
    private Alojamiento alojamiento;
    private Cliente cliente;
    private double precioTotal;
    private int pin; //buscar como aumentarse y no repetirse (archivos quizas?)

    //constructores

    public Reserva() {
        alojamiento= null;
        cliente= new Cliente();
        precioTotal=0;
        pin=0;
    }

    public Reserva(Alojamiento alojamiento, Cliente cliente, double precioTotal) {
        this.alojamiento = alojamiento;
        this.cliente = cliente;
        this.precioTotal = precioTotal;
        pin=0;
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

    public int getPin() {
        return pin;
    }

    


    //hacer funcion que copie las fechas que tiene guardado cliente en alojamiento
}
