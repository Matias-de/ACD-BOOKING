package ClasesGenericas;

import Interfaces.IOperacionesMap;
import Modelo.Reserva;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

public class GHashMap <E>implements IOperacionesMap<E> {
    //atributo
    HashMap<E, HashSet<Reserva>> nuevoHashMap;
    //constructor
    public GHashMap()
    {
        nuevoHashMap = new HashMap<>();
    }
    //metodos
    @Override
    public void agregar(E clave, Reserva valor) {
        HashSet<Reserva> aux ;

        if (nuevoHashMap.containsKey(clave))
        {
            aux = nuevoHashMap.get(clave);
        }
        else {
            aux = new HashSet<Reserva>();
            nuevoHashMap.put(clave, aux);
        }
        aux.add(valor);
    }
    @Override
    public String listar() {
        return nuevoHashMap.toString();
    }

    @Override
    public void borrar(E clave) {
        nuevoHashMap.remove(clave);

    }
    public HashSet<Reserva> getReserva(E nuevoGenerico)
    {
        HashSet<Reserva> aux =  nuevoHashMap.get(nuevoGenerico);
        return aux;
    }
    @Override
    public boolean buscarElemento(E clave) {
        return nuevoHashMap.containsKey(clave);
    }

    public Iterator<Map.Entry<E, HashSet<Reserva>>> entrySetIterator(){
        nuevoHashMap.entrySet().iterator();
        return null;
    }

}
