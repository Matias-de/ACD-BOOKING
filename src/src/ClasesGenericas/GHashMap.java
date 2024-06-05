package ClasesGenericas;

import Interfaces.IOperacionesMap;
import Modelo.Alojamiento;
import Modelo.Cliente;
import Modelo.Reserva;

import java.io.*;
import java.util.*;

public class GHashMap <E>implements IOperacionesMap<E> {
    //atributo
    HashMap<E, HashSet<Reserva>> nuevoHashMap;
    //constructor
    public GHashMap()
    {
        nuevoHashMap = new HashMap<>();
    }
    //metodo para guardar mapa en archivo

    public void pasarMapaAArchivo(String nombreArchi) //es indiferente que mapa se quiera utilizar ya que las reservas contienen la info de todo
    {
        FileOutputStream fileOutputStream = null;
        ObjectOutputStream objectOutputStream = null;
        Iterator<Map.Entry<E,HashSet<Reserva>>> nuevoIteratorMap = nuevoHashMap.entrySet().iterator();
        try {
                fileOutputStream = new FileOutputStream(nombreArchi);
                objectOutputStream = new ObjectOutputStream(fileOutputStream);
            while (nuevoIteratorMap.hasNext())
            {
                Map.Entry<E,HashSet<Reserva>> mapaAux = nuevoIteratorMap.next();
                HashSet<Reserva> nuevoHashAux = mapaAux.getValue();
                for (Reserva hashAux : nuevoHashAux) {
                    objectOutputStream.writeObject(hashAux);
                }
            }
            }
            catch (FileNotFoundException e)
            {
                e.printStackTrace();

            }
            catch (IOException e2)
            {
                e2.printStackTrace();
            }
            finally
            {
                try {
                    objectOutputStream.close();
                }
                catch (IOException var12) {
                }
            }
    }

    //metodos

    @Override
    public String toString() {
        return "GHashMap{" +
                "nuevoHashMap=" + nuevoHashMap +
                '}';
    }
    @Override
    public String listar()
    {
        String rta = "";
        Iterator<Map.Entry<E,HashSet<Reserva>>> nuevoIteratorMap = nuevoHashMap.entrySet().iterator();
        while (nuevoIteratorMap.hasNext())
        {
            Map.Entry<E,HashSet<Reserva>> mapaAux = nuevoIteratorMap.next();
            HashSet<Reserva> nuevoHashAux = mapaAux.getValue();
            for (Reserva hashAux : nuevoHashAux)
            {
                rta += hashAux.toString();
            }
        }
        return rta;
    }
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
    public boolean estaVacio(){
        return nuevoHashMap.isEmpty();
    }

@Override
    public void borrar(E clave, Reserva valor) {
        HashSet<Reserva> reservas = nuevoHashMap.get(clave);
        if (reservas != null) {
            reservas.remove(valor);
            if (reservas.isEmpty()) {
                nuevoHashMap.remove(clave);
            }
        }
    }

    public HashSet<Reserva> getReserva(E nuevoGenerico) {
        return nuevoHashMap.get(nuevoGenerico);
    }

    @Override
    public boolean buscarElemento(E clave){
        return nuevoHashMap.containsKey(clave);
    }

    public Iterator<Map.Entry<E, HashSet<Reserva>>> entrySetIterator(){

        return nuevoHashMap.entrySet().iterator();
    }





}
