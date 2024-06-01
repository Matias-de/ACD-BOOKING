package ClasesGenericas;

import Interfaces.IOperacionesSet;

import java.util.HashSet;
import java.util.Iterator;

public class GHashSet<E> implements IOperacionesSet<E> {
    private HashSet<E> nuevoHashSet;
    public GHashSet()
    {
        nuevoHashSet = new HashSet<>();
    }

    @Override
    public String toString() {
        return "GHashSet{" +
                "nuevoHashSet=" + nuevoHashSet +"";
    }


    @Override
    public void agregar(E nuevoGenerico) {
        nuevoHashSet.add(nuevoGenerico);
    }

    public Iterator<E> iterator() {

        return nuevoHashSet.iterator();
    }

    @Override
    public boolean isEmpty(){
        return nuevoHashSet.isEmpty();
    }

}
