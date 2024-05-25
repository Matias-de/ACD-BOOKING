package Interfaces;

import java.util.HashMap;
import java.util.HashSet;

public interface IOperaciones<E>{

    void cargarHashMap(HashMap<E,E> hashMap);//carga un hashmap
    void listarHashMap(HashMap<E,E> hashMap);//muestra un hashmap


    void cargarHashSet(HashSet<E> hashSet);//carga un hashset
    void listarHashSet(HashSet<E> hashSet);//muestra un hashset


}
