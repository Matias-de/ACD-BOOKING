package Modelo;

import Interfaces.IOperaciones;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

public class BookingACD implements IOperaciones<Cliente, Reserva> {
    //no tendriamos que estar usando object, es lo que hay que revisar
    //encima claro al tener dos de cada una no podemos tirar de 2 veces el mismo metodo
    //porque sino ya no seria generico
    //atributos
    HashMap<Cliente, HashSet<Reserva>> hashMapCliente; //Estos dos en archivos
    HashMap<Alojamiento, HashSet<Reserva>> hashMapAlojamiento;
    HashSet<Cliente> clienteHashSet; //Guardariamos un JSON
    HashSet<Alojamiento> alojamientoHashSet; //guardariamos un JSON
    HashSet<Reserva> reservaHashSet; //guardariamos en Archivo // no, tambien JSON

    //constructor
    public BookingACD() {
        hashMapCliente = new HashMap<Cliente, HashSet<Reserva>>();
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
    public void cargarHashMap(Cliente clave, Reserva valor) {
        HashSet<Reserva> aux;

        if(hashMapCliente.containsKey(clave)){
           aux = hashMapCliente.get(clave);
       }else{
            aux = new HashSet<>();
            hashMapCliente.put(clave, aux);
       }
        aux.add(valor);


    }

    @Override
    public void listarHashMap(){
        for (Cliente cliente : hashMapCliente.keySet()) {
            System.out.println("Cliente: " + cliente + " - Reservas: " + hashMapCliente.get(cliente));
        }
    }

    @Override
    public void cargarHashSet(Reserva valor) {

    }

    @Override
    public void listarHashSet() {

    }
    //metodos






}

