package Modelo;

import ClasesGenericas.GHashMap;
import Interfaces.IOperacionesMap;

import java.util.*;

public class BookingACD {

    //no tendriamos que estar usando object, es lo que hay que revisar
    //encima claro al tener dos de cada una no podemos tirar de 2 veces el mismo metodo
    //porque sino ya no seria generico
    //atributos

    GHashMap<Cliente, HashSet<Reserva>> hashMapCliente; //Estos dos en archivos
    GHashMap<Alojamiento, HashSet<Reserva>> hashMapAlojamiento;
    HashSet<Cliente> clienteHashSet; //Guardariamos un JSON
    HashSet<Alojamiento> alojamientoHashSet; //guardariamos un JSON
    HashSet<Reserva> reservaHashSet; //guardariamos en Archivo // no, tambien JSON

    //constructor
    public BookingACD() {
        hashMapCliente = new GHashMap<Cliente, HashSet<Reserva>>();
        hashMapAlojamiento = new GHashMap<Alojamiento, HashSet<Reserva>>();
        clienteHashSet = new HashSet<Cliente>();
        alojamientoHashSet = new HashSet<Alojamiento>();
        reservaHashSet = new HashSet<Reserva>();
    }

    //getters
    public HashSet<Cliente> getClienteHashSet() {

        return clienteHashSet;

    }

    public HashMap<Alojamiento, HashSet<Reserva>> getHashMapAlojamiento() {
        return hashMapAlojamiento;
    }

    public HashMap<Cliente, HashSet<Reserva>> getHashMapCliente() {
        return hashMapCliente;
    }

    public HashSet<Alojamiento> getAlojamientoHashSet() {
        return alojamientoHashSet;
    }

    public HashSet<Reserva> getReservaHashSet() {
        return reservaHashSet;
    }



    //metodos
    public void cargarHashMapAlojamiento(Alojamiento clave, Reserva valor){
        HashSet<Reserva> aux;

        if(hashMapAlojamiento.containsKey(clave)){
            aux = hashMapAlojamiento.get(clave);
        }else{
            aux = new HashSet<>();
            hashMapAlojamiento.put(clave, aux);
        }
        aux.add(valor);
    }

    @Override
    public void cargarHashMap(Cliente clave, Reserva valor) { //preguntar
        HashSet<Reserva> aux;

        if(hashMapCliente.containsKey(clave)){
           aux = hashMapCliente.get(clave);
       }else{
            aux = new HashSet<>();
            hashMapCliente.put(clave, aux);
       }
        aux.add(valor);


    }

    @Override
    public void listarHashMap(){

        for (Cliente cliente : hashMapCliente.keySet()) { //preguntar si hay que sacar interfaz o como implementar
            System.out.println("Cliente: " + cliente + " - Reservas: " + hashMapCliente.get(cliente));
        }



    }

    @Override
    public void cargarHashSet(Reserva valor) {
        reservaHashSet.add(valor);
    }

    @Override
    public String listarHashSets(int opcion){
       String rta="";
        switch (opcion){
            case 1:
                rta= clienteHashSet.toString();
                break;
            case 2:
                rta= alojamientoHashSet.toString();
                break;
            case 3:
                rta= reservaHashSet.toString();
                break;
            default:
                rta="ERROR, OPCION INVALIDA.";
                break;


        }
       

        return rta;
    }


    public boolean reservar(Cliente cliente, Alojamiento alojamiento){ //probar / revisar
        boolean reservada=false, enFecha=false, contieneAlojamiento=true;
        Iterator<Reserva> reservaIterator;
        HashSet<Reserva> reservas;
        Reserva reservaAux;
        if(hashMapCliente.containsKey(cliente)){
            reservas= hashMapCliente.get(cliente);
            reservaIterator= reservas.iterator();
            while(reservaIterator.hasNext()){
                reservaAux= reservaIterator.next(); //en teoria esta parte revisa si el cliente no tiene otra reserva en esa fecha
                if(!cliente.getFechaInicio().equals(reservaAux.getCliente().getFechaInicio()) && !cliente.getFechaFinal().equals(reservaAux.getCliente().getFechaFinal())){
                    enFecha=true;
                }
            }
        }

        if(enFecha){
        if(hashMapAlojamiento.containsKey(alojamiento)){ //aca revisariamos si el alojamiento esta disponible o si no esta ocupado o si tiene alguna reserva
            reservas = hashMapAlojamiento.get(alojamiento);
            reservaIterator = reservas.iterator();

                while (reservaIterator.hasNext()) {
                    reservaAux = reservaIterator.next();
                    if (alojamiento.isDisponibilidad() && reservaAux.getAlojamiento().isDisponibilidad() && (cliente.getFechaInicio().after(reservaAux.getCliente().getFechaFinal()) || !cliente.getFechaInicio().after(reservaAux.getCliente().getFechaInicio()) || !cliente.getFechaFinal().after(reservaAux.getCliente().getFechaInicio()))) {
                        enFecha = true;
                    }else{
                        enFecha=false;
                    }

                }
            }else{
            contieneAlojamiento=false;
        }

        }
        if(enFecha && !contieneAlojamiento){ //en el caso que ni el cliente ni el alojamiento tengan una reserva o que si lo tengan esten en una fecha que no interceda con otra reserva
            Reserva nuevaReserva = new Reserva(alojamiento, cliente, alojamiento.getPrecioXAlojar()*1.21); //precio del alojamiento +21% nuestro
            cargarHashMap(cliente, nuevaReserva);
            //en este punto antes de entrar a esta funcion en el menu hay que evaluar si el cliente y el alojamiento existen
            //asi de ultima los guardamos en los hashSet antes :)
            alojamiento.setDisponibilidad(false); //habra que utilizar enums
            cargarHashMapAlojamiento(alojamiento, nuevaReserva);
            reservaHashSet.add(nuevaReserva);
            reservada=true;

        }


        return reservada; //este boolean se puede cambiar por strings para despues ver porque no se puede reservar y devolver un mensaje
    }

    public String devolverAlojamientosDisponibles(){ //utiliza el hashSet de alojamientos, porque si esta reservado no esta disponible..
        String rta="";
        if(alojamientoHashSet!=null){
            Iterator<Alojamiento> alojamientoIterator = alojamientoHashSet.iterator();
            while(alojamientoIterator.hasNext()){
                Alojamiento aux=alojamientoIterator.next();
                if(aux.isDisponibilidad()){
                    rta+=aux.toString()+"\n";
                }
            }

        }

        return rta;
    }

    public String mostrarReservasDeCliente(Cliente cliente){ //deberia mostrar las reservas de un cliente
       String rta="cliente no encontrado/sin reservas";
        if(hashMapCliente.containsKey(cliente)){
            rta+=hashMapCliente.get(cliente)+"\n";
        }

        return rta;
    }
//preguntar como hacer para no repetir estas funciones
    public String mostrarReservasEnAlojamiento(Alojamiento alojamiento){
        String rta="Alojamiento no encontrado/sin reservas";
        if(hashMapAlojamiento.containsKey(alojamiento)){
            rta+=hashMapAlojamiento.get(alojamiento)+"\n";
        }

        return rta;
    }

    public String mostrarReservasAPuntoDeTerminar()//deberia mostrarse ni bien aparezca el programa
    {
        String rta="";
        Date fechaActualDelSistema= new Date();

        Iterator<Map.Entry<Cliente, HashSet<Reserva>>> entryIterator = hashMapCliente.entrySet().iterator();
        while(entryIterator.hasNext()){
            Map.Entry<Cliente,HashSet<Reserva>> reservaMapa = entryIterator.next();
            HashSet<Reserva> reservas = reservaMapa.getValue();
            Iterator<Reserva> reservaIterator = reservas.iterator();
            while(reservaIterator.hasNext()){
                Reserva reservaAux = reservaIterator.next();
                if(fechaActualDelSistema.getDay()==reservaAux.getCliente().getFechaFinal().getDay() && fechaActualDelSistema.getMonth()==reservaAux.getCliente().getFechaFinal().getMonth() && (fechaActualDelSistema.getYear()+1900)==(reservaAux.getCliente().getFechaFinal().getYear()+1900)){
                    rta+=reservaAux.toString()+"\n";
                }
            }
        }
        if(rta.equalsIgnoreCase("")){
            rta="No hay reservas que concluyan el dia de hoy..";
        }else{
            rta+="Recuerda agregar la valoracion de la estadia!";
        }

        return rta;
    }

    public void mostrarReservasYaTerminadas(){ //muestra las reservas que terminaron para que el admin diga uy hay que modificar esto loco y lo haga con la funcion de abajo
        String rta="Reservas terminadas: ";
        Date fechaActualDelSistema= new Date();

        Iterator<Map.Entry<Cliente, HashSet<Reserva>>> entryIterator = hashMapCliente.entrySet().iterator();
        while(entryIterator.hasNext()){
            Map.Entry<Cliente,HashSet<Reserva>> reservaMapa = entryIterator.next();
            HashSet<Reserva> reservas = reservaMapa.getValue();
            Iterator<Reserva> reservaIterator = reservas.iterator();
            while(reservaIterator.hasNext()){
                Reserva reservaAux = reservaIterator.next();
                if(fechaActualDelSistema.after(reservaAux.getCliente().getFechaFinal())){
                    rta+=reservaAux.toString()+"\n";
                }else{
                    rta+="Ninguna";
                }
            }
        }
    }
    




}

