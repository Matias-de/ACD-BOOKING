package Modelo;
import ClasesGenericas.GHashSet;
import ClasesGenericas.GHashMap;
import Enumeraciones.EstadoAlojamiento;

import java.io.*;
import java.util.*;

public class BookingACD {

    //no tendriamos que estar usando object, es lo que hay que revisar
    //encima claro al tener dos de cada una no podemos tirar de 2 veces el mismo metodo
    //porque sino ya no seria generico
    //atributos

    GHashMap<Cliente> hashMapCliente; //Estos dos en archivos
    GHashMap<Alojamiento> hashMapAlojamiento;
    GHashSet<Cliente> clienteHashSet; //Guardariamos un JSON
    GHashSet<Alojamiento> alojamientoHashSet; //guardariamos un JSON
    GHashSet<Reserva> reservaHashSet; //guardariamos en Archivo // no, tambien JSON

    //constructor
    public BookingACD() {
        hashMapCliente = new GHashMap<Cliente>();
        hashMapAlojamiento = new GHashMap<Alojamiento>();
        clienteHashSet = new GHashSet<Cliente>();
        alojamientoHashSet = new GHashSet<Alojamiento>();
        reservaHashSet = new GHashSet<Reserva>();
    }

    //getters sett
    public GHashSet<Cliente> getClienteHashSet() {
        return clienteHashSet;

    }
    //getters map
    public GHashMap<Alojamiento> getHashMapAlojamiento() {
        return hashMapAlojamiento;
    }

    public GHashMap<Cliente> getHashMapCliente() {
        return hashMapCliente;
    }

    public GHashSet<Alojamiento> getAlojamientoHashSet() {
        return alojamientoHashSet;
    }

    public GHashSet<Reserva> getReservaHashSet() {
        return reservaHashSet;
    }
    //metodos para conservacion de archivos
    public void guardarDatosEnArchi(String nombreArchiCliente, String nombreArchiAlojamiento)
    {
        hashMapAlojamiento.pasarMapaAArchivo(nombreArchiCliente);
        hashMapAlojamiento.pasarMapaAArchivo(nombreArchiAlojamiento);
    }
    public void pasarArchiAMapa(String nombreArchi)
    {
        ObjectInputStream objectInputStream = null;
        try {
            FileInputStream fileInputStream = new FileInputStream(nombreArchi);
            objectInputStream = new ObjectInputStream(fileInputStream);
            while (true)
            {
                Reserva nuevaReserva = (Reserva) objectInputStream.readObject();
                hashMapAlojamiento.agregar(nuevaReserva.getAlojamiento(),nuevaReserva);
                hashMapCliente.agregar(nuevaReserva.getCliente(),nuevaReserva);
            }
        }
        catch (EOFException e)
        {
            System.out.println("Fin del archivo.");
        }
        catch (FileNotFoundException e2)
        {
            e2.printStackTrace();
        }
        catch (IOException e3)
        {
            e3.printStackTrace();
        }
        catch (ClassNotFoundException e)
        {
            throw new RuntimeException(e);
        }
        finally
        {
            try {
                objectInputStream.close();
            } catch (IOException e4) {
                e4.printStackTrace();
            }
        }




    }


    //metodos
    public void agregarCliente(Cliente nuevoCliente)
    {
        clienteHashSet.agregar(nuevoCliente);
    }
    public void agregarAlojamiento(Alojamiento nuevoAlojamiento)
    {
        alojamientoHashSet.agregar(nuevoAlojamiento);
    }
    public void agregarReserva(Reserva nuevaReserva)
    {
        reservaHashSet.agregar(nuevaReserva);
    }
    public String mostrarSetReserva()
    {
        return reservaHashSet.toString();
    }

    public boolean reservar(Cliente cliente, Alojamiento alojamiento){ //probar / revisar
        boolean  contieneAlojamiento=true,reservada=false, enFecha=true; //pongo que sea igual a true ya que si no existe todavia ninguna reserva la creara

        if (hashMapCliente.buscarElemento(cliente)) // en el caso de que ya haya, ahi si comparo reservas
             {
                 enFecha = hashMapCliente.verificacionFechaCliente(cliente); //si recibe un false no continuara el programa de reservas
             }
            // y si no hay reservas a comparar que siga el programa para agregarla con la funcion de Agregar.



        if(enFecha) //si la fecha no es igual o no existe aun
        {
            if(hashMapAlojamiento.buscarElemento(alojamiento))//si el alojamiento existe en el mapa
                {
                    enFecha = hashMapAlojamiento.verificarFechaAlojamiento(alojamiento,(cliente.getFechaInicio()),(cliente.getFechaFinal())); //todo lo de abajo lo hace la funcion
                }
            else
            {
                contieneAlojamiento = false;
            }
        }



        if(enFecha && (!contieneAlojamiento)){ //en el caso que ni el cliente ni el alojamiento tengan una reserva o que si lo tengan esten en una fecha que no interceda con otra reserva
            Reserva nuevaReserva = new Reserva(alojamiento, cliente, alojamiento.getPrecioXAlojar()*1.21); //precio del alojamiento +21% nuestro
            hashMapCliente.agregar(cliente, nuevaReserva);
            //en este punto antes de entrar a esta funcion en el menu hay que evaluar si el cliente y el alojamiento existen
            //asi de ultima los guardamos en los hashSet antes :)
           alojamiento.setEstado(EstadoAlojamiento.RESERVADO); //habra que utilizar enums
            hashMapAlojamiento.agregar(alojamiento, nuevaReserva);
            agregarReserva(nuevaReserva);
            reservada=true;
        }


        return reservada; //este boolean se puede cambiar por strings para despues ver porque no se puede reservar y devolver un mensaje

        /*
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
            cargarHashMapAlojamiento(alojamiento, nuevaReserva);
            reservaHashSet.add(nuevaReserva);
            reservada=true;

        }


        return reservada; //este boolean se puede cambiar por strings para despues ver porque no se puede reservar y devolver un mensaje
    }

}
         */

    }

    public String devolverAlojamientosDisponibles(){ //utiliza el hashSet de alojamientos, porque si esta reservado no esta disponible..
        String rta="";
        if(alojamientoHashSet!=null){
            Iterator<Alojamiento> alojamientoIterator = alojamientoHashSet.iterator();
            while(alojamientoIterator.hasNext()){
                Alojamiento aux=alojamientoIterator.next();
                if(aux.getEstado().equals(EstadoAlojamiento.DISPONIBLE)){
                    rta+=aux.toString()+"\n";
                }
            }

        }

        return rta;
    }

    public String mostrarReservasDeCliente(Cliente cliente){ //deberia mostrar las reservas de un cliente
       String rta="cliente no encontrado/sin reservas";
        if(hashMapCliente.buscarElemento(cliente)){
            rta+=hashMapCliente.getReserva(cliente)+"\n";
        }

        return rta;
    }
//preguntar como hacer para no repetir estas funciones
    public String mostrarReservasEnAlojamiento(Alojamiento alojamiento){
        String rta="Alojamiento no encontrado/sin reservas";
        if(hashMapAlojamiento.buscarElemento(alojamiento)){
            rta+=hashMapAlojamiento.getReserva(alojamiento)+"\n";
        }

        return rta;
    }

    public String mostrarReservasAPuntoDeTerminar()//deberia mostrarse ni bien aparezca el programa
    {
        String rta="";
        Date fechaActualDelSistema= new Date();

        Iterator<Map.Entry<Cliente, HashSet<Reserva>>> entryIterator = hashMapCliente.entrySetIterator();
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

        Iterator<Map.Entry<Cliente, HashSet<Reserva>>> entryIterator = hashMapCliente.entrySetIterator();
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
    

    public Cliente buscarClientePorNombre(String nombre){
        Iterator<Cliente> iterator = clienteHashSet.iterator();
        Cliente cliente=null;
        while(iterator.hasNext()){
            Cliente aux = iterator.next();
            if(aux.getNombre().equals(nombre)){
                cliente=aux;
            }
        }
        return cliente;
    }

    public Alojamiento buscarAlojamientoPorNombre(String nombre){
        Iterator<Alojamiento> iterator = alojamientoHashSet.iterator();
        Alojamiento alojamiento=null;
        while(iterator.hasNext()){
            Alojamiento aux = iterator.next();
            if(aux.getNombre().equals(nombre)){
                alojamiento=aux;
            }
        }
        return alojamiento;
    }

}

