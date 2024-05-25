package Interfaces;

import java.util.Collection;
import java.util.HashSet;

public interface IOperaciones<E , W > {

    void cargarHashMap(E clave, W valor); // carga un hashmap
    void listarHashMap(); // muestra un hashmap

    void cargarHashSet(W valor); // carga un hashset
    void listarHashSet(); // muestra un hashset
}
