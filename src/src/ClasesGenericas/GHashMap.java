package ClasesGenericas;

import Interfaces.IOperacionesMap;
import Modelo.Alojamiento;
import Modelo.Cliente;
import Modelo.Reserva;

import java.util.HashMap;
import java.util.HashSet;

public class GHashMap <E,S>implements IOperacionesMap<E,S> {
    //atributo
    HashMap<E, S> nuevoHashMap; //Estos dos en archivos
    //constructor
    public GHashMap()
    {
        nuevoHashMap = new HashMap<>();
    }
    //metodos
    @Override
    public void agregar(E clave, S valor) {
        nuevoHashMap.put(clave,valor);
    }
    @Override
    public String listar() {
        String rta = "";
        rta += nuevoHashMap.toString();
        return rta;
    }

    @Override
    public void borrar(E clave, S valor) {

    }

    @Override
    public boolean buscarElemento(E clave, S valor) {
        return false;
    }
}
