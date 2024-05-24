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



    //hacer funcion que copie las fechas que tiene guardado cliente en alojamiento
}
