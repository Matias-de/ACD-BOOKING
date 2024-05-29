package ClasesGenericas;

import Interfaces.IOperacionesSet;

import java.util.HashSet;

public class GHashSet<E> implements IOperacionesSet<E> {
    private HashSet<E> nuevoHashSet;
    public GHashSet()
    {
        nuevoHashSet = new HashSet<>();
    }

    @Override
    public void agregar(E nuevoGenerico) {
        nuevoHashSet.add(nuevoGenerico);
    }
}
