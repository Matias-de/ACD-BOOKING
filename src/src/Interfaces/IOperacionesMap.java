package Interfaces;

public interface IOperacionesMap<E,W>{

    void agregar(E clave, W valor); // carga un hashmap
    String listar(); // muestra un hashmap
    void borrar(E clave, W valor); //borra un elemento
    boolean buscarElemento (E clave, W valor); // confirma si existe dicho elemento en el mapa

}
