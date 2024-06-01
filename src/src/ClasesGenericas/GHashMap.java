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
    public void pasarMapaAArchivo(String nombreArchi)
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
    public boolean verificacionFechaCliente(Cliente clienteAAnalizar)
    {
        boolean flag = true;
        HashSet<Reserva> aux = nuevoHashMap.get(clienteAAnalizar);
        Reserva reservaAux = null;
        Iterator <Reserva> nuevoIterator = aux.iterator();
        while (nuevoIterator.hasNext())
        {
            reservaAux = nuevoIterator.next();
            if((clienteAAnalizar.getFechaInicio()).compareTo((reservaAux.getCliente()).getFechaInicio())!=0 &&
                    (clienteAAnalizar.getFechaFinal()).compareTo((reservaAux.getCliente().getFechaFinal()))!=0)
            {
                flag = false; //retornara false si las variables date son iguales
            }
        }
        return flag;
    }
    public boolean verificarFechaAlojamiento(Alojamiento alojamientoAAnalizar, Date fechaInicioCliente, Date fechaFinCliente)
    {
        boolean flag = true;
        HashSet<Reserva> auxSet = nuevoHashMap.get(alojamientoAAnalizar);
        Reserva auxReserva;
        Iterator<Reserva> nuevoIterator = auxSet.iterator();
        while (nuevoIterator.hasNext())
        {
            auxReserva = nuevoIterator.next();
            //hay q cambiar ese isDisponibilidad de nombre por que estamos programando en español
            //no,es como el get, te lo hace solo el ide.
             if(alojamientoAAnalizar.isDisponibilidad() && (auxReserva.getAlojamiento()).isDisponibilidad() && (fechaInicioCliente.after((auxReserva.getCliente()).getFechaFinal())) || !fechaInicioCliente.after((auxReserva.getCliente().getFechaInicio())) || !fechaFinCliente.after(auxReserva.getCliente().getFechaInicio()))
             {
                 flag = true;
             }
             else {
                 flag = false;
             }
        }
        return flag;
    }


}
