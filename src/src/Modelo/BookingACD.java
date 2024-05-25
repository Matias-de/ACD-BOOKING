package Modelo;

import Interfaces.IOperaciones;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

public class BookingACD implements IOperaciones<Object, Object>{
   //no tendriamos que estar usando object, es lo que hay que revisar
    //encima claro al tener dos de cada una no podemos tirar de 2 veces el mismo metodo
    //porque sino ya no seria generico
    //atributos
    HashMap <Cliente, HashSet<Reserva>> hashMapCliente; //Estos dos en archivos
    HashMap <Alojamiento, HashSet<Reserva>> hashMapAlojamiento;
    HashSet <Cliente> clienteHashSet; //Guardariamos un JSON
    HashSet <Alojamiento> alojamientoHashSet; //guardariamos un JSON
    HashSet <Reserva> reservaHashSet; //guardariamos en Archivo // no, tambien JSON

    //constructor
    public BookingACD()
    {
        hashMapCliente = new HashMap<Cliente,HashSet<Reserva>>();
        hashMapAlojamiento = new HashMap<Alojamiento, HashSet<Reserva>>();
        clienteHashSet = new HashSet<Cliente>();
        alojamientoHashSet = new HashSet<Alojamiento>();
        reservaHashSet = new HashSet<Reserva>();
    }

    //getters
    public HashSet<Cliente> getClienteHashSet() {
        return clienteHashSet;
    }

    public HashMap<Alojamiento, HashSet<Reserva>> getHashMapAlojamiento() {
        return hashMapAlojamiento;
    }

    public HashMap<Cliente, HashSet<Reserva>> getHashMapCliente() {
        return hashMapCliente;
    }

    public HashSet<Alojamiento> getAlojamientoHashSet() {
        return alojamientoHashSet;
    }

    public HashSet<Reserva> getReservaHashSet() {
        return reservaHashSet;
    }

    @Override
    public void cargarHashMap(Object clave, Object valor) {

    }

    @Override
    public void listarHashMap() {

    }

    @Override
    public void cargarHashSet(Object valor) {

    }

    @Override
    public void listarHashSet() {

    }


    //metodos


}

