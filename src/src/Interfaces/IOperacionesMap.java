package Interfaces;

import Modelo.Reserva;

import java.util.HashSet;

public interface IOperacionesMap<E>{

    void agregar(E clave, Reserva valor); // carga un hashmap
    String listar(); // muestra un hashmap
    void borrar(E clave); //borra un elemento
    boolean buscarElemento (E clave); // confirma si existe dicho elemento en el mapa
    HashSet<Reserva> getReserva(E generico);

}
