package Modelo;

import java.util.HashMap;
import java.util.HashSet;

public class BookingACD{
    //atributos
    HashMap <Cliente, HashSet<Reserva>> hashMapCliente;
    HashMap <Alojamiento, HashSet<Reserva>> hashMapAlojamiento;

    HashSet <Cliente> clienteHashSet; //Guardariamos un JSON
    HashSet <Alojamiento> alojamientoHashSet; //guardariamos un JSON
    HashSet <Reserva> reservaHashSet; //guardariamos en Archivo

    //constructor
    public BookingACD()
    {
        hashMapCliente = new HashMap<Cliente,HashSet<Reserva>>();
        hashMapAlojamiento = new HashMap<Alojamiento, HashSet<Reserva>>();
        clienteHashSet = new HashSet<Cliente>();
        alojamientoHashSet = new HashSet<Alojamiento>();
        reservaHashSet = new HashSet<Reserva>();
    }

    public void agregarCliente(Cliente nuevoCliente)
    {
        clienteHashSet.add(nuevoCliente);
    }
    public void agregarAlojamiento(Alojamiento nuevoAlojamiento)
    {
        alojamientoHashSet.add(nuevoAlojamiento);
    }
    public void cargarReserva(Reserva nuevaReserva)
    {
        reservaHashSet.add(nuevaReserva);
    }


}
