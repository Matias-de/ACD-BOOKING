package Modelo;
import ClasesGenericas.GHashSet;
import ClasesGenericas.GHashMap;
import Enumeraciones.EstadoAlojamiento;
import Utils.JsonUtiles;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.util.*;

public class BookingACD  {

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
    public void guardarDatosEnArchi(String nombreArchiCliente, String nombreArchiAlojamiento) {
        hashMapAlojamiento.pasarMapaAArchivo(nombreArchiCliente);
        hashMapAlojamiento.pasarMapaAArchivo(nombreArchiAlojamiento);
    }

    public void pasarArchiAMapa(String nombreArchi) {
        ObjectInputStream objectInputStream = null;
        try {
            FileInputStream fileInputStream = new FileInputStream(nombreArchi);
            objectInputStream = new ObjectInputStream(fileInputStream);
            while (true) {
                Reserva nuevaReserva = (Reserva) objectInputStream.readObject();
                hashMapAlojamiento.agregar(nuevaReserva.getAlojamiento(), nuevaReserva);
                hashMapCliente.agregar(nuevaReserva.getCliente(), nuevaReserva);
            }
        } catch (EOFException e) {
            System.out.println("Fin del archivo.");
        } catch (FileNotFoundException e2) {
            e2.printStackTrace();
        } catch (IOException e3) {
            e3.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                objectInputStream.close();
            } catch (IOException e4) {
                e4.printStackTrace();
            }
        }


    }


    //metodos
    public void agregarCliente(Cliente nuevoCliente) {
        clienteHashSet.agregar(nuevoCliente);
    }

    public void agregarAlojamiento(Alojamiento nuevoAlojamiento) {
        alojamientoHashSet.agregar(nuevoAlojamiento);
    }

    public void agregarReserva(Reserva nuevaReserva) {
        reservaHashSet.agregar(nuevaReserva);
    }

    public String mostrarSetReserva() {
        return reservaHashSet.toString();
    }

    public boolean reservar(Cliente cliente, Alojamiento alojamiento) { //probar / revisar
        boolean contieneAlojamiento = true, reservada = false, enFecha = true; //pongo que sea igual a true ya que si no existe todavia ninguna reserva la creara

        if (hashMapCliente.buscarElemento(cliente)) // en el caso de que ya haya, ahi si comparo reservas
        {
            enFecha = hashMapCliente.verificacionFechaCliente(cliente); //si recibe un false no continuara el programa de reservas
        }
        // y si no hay reservas a comparar que siga el programa para agregarla con la funcion de Agregar.


        if (enFecha) //si la fecha no es igual o no existe aun
        {
            if (hashMapAlojamiento.buscarElemento(alojamiento))//si el alojamiento existe en el mapa
            {
                enFecha = hashMapAlojamiento.verificarFechaAlojamiento(alojamiento, (cliente.getFechaInicio()), (cliente.getFechaFinal())); //todo lo de abajo lo hace la funcion
            } else {
                contieneAlojamiento = false;
            }
        }


        if (enFecha && (!contieneAlojamiento)) { //en el caso que ni el cliente ni el alojamiento tengan una reserva o que si lo tengan esten en una fecha que no interceda con otra reserva
            Reserva nuevaReserva = new Reserva(alojamiento, cliente, alojamiento.getPrecioXAlojar() * 1.21); //precio del alojamiento +21% nuestro
            hashMapCliente.agregar(cliente, nuevaReserva);
            //en este punto antes de entrar a esta funcion en el menu hay que evaluar si el cliente y el alojamiento existen
            //asi de ultima los guardamos en los hashSet antes :)
            alojamiento.setEstado(EstadoAlojamiento.RESERVADO); //habra que utilizar enums
            hashMapAlojamiento.agregar(alojamiento, nuevaReserva);
            agregarReserva(nuevaReserva);
            reservada = true;
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

    public String devolverAlojamientosDisponibles() { //utiliza el hashSet de alojamientos, porque si esta reservado no esta disponible..
        String rta = "";
        if (alojamientoHashSet != null) {
            Iterator<Alojamiento> alojamientoIterator = alojamientoHashSet.iterator();
            while (alojamientoIterator.hasNext()) {
                Alojamiento aux = alojamientoIterator.next();
                if (aux.getEstado().equals(EstadoAlojamiento.DISPONIBLE)) {
                    rta += aux.toString() + "\n";
                }
            }

        }

        return rta;
    }

    public String mostrarReservasDeCliente(Cliente cliente) { //deberia mostrar las reservas de un cliente
        String rta = "cliente no encontrado/sin reservas";
        if (hashMapCliente.buscarElemento(cliente)) {
            rta += hashMapCliente.getReserva(cliente) + "\n";
        }

        return rta;
    }

    //preguntar como hacer para no repetir estas funciones // hay que hacerlo en el la clase map y usar el tipo de dato generico
    public String mostrarReservasEnAlojamiento(Alojamiento alojamiento) {
        String rta = "Alojamiento no encontrado/sin reservas";
        if (hashMapAlojamiento.buscarElemento(alojamiento)) {
            rta += hashMapAlojamiento.getReserva(alojamiento) + "\n";
        }

        return rta;
    }

    public String mostrarReservasAPuntoDeTerminar()//deberia mostrarse ni bien aparezca el programa
    {
        String rta = "";
        Date fechaActualDelSistema = new Date();

        Iterator<Map.Entry<Cliente, HashSet<Reserva>>> entryIterator = hashMapCliente.entrySetIterator();
        while (entryIterator.hasNext()) {
            Map.Entry<Cliente, HashSet<Reserva>> reservaMapa = entryIterator.next();
            HashSet<Reserva> reservas = reservaMapa.getValue();
            Iterator<Reserva> reservaIterator = reservas.iterator();
            while (reservaIterator.hasNext()) {
                Reserva reservaAux = reservaIterator.next();
                if (fechaActualDelSistema.getDay() == reservaAux.getCliente().getFechaFinal().getDay() && fechaActualDelSistema.getMonth() == reservaAux.getCliente().getFechaFinal().getMonth() && (fechaActualDelSistema.getYear() + 1900) == (reservaAux.getCliente().getFechaFinal().getYear() + 1900)) {
                    rta += reservaAux.toString() + "\n";
                }
            }
        }
        if (rta.equalsIgnoreCase("")) {
            rta = "No hay reservas que concluyan el dia de hoy..";
        } else {
            rta += "Recuerda agregar la valoracion de la estadia!";
        }

        return rta;
    }

    public void mostrarReservasYaTerminadas() { //muestra las reservas que terminaron para que el admin diga uy hay que modificar esto loco y lo haga con la funcion de abajo
        String rta = "Reservas terminadas: ";
        Date fechaActualDelSistema = new Date();

        Iterator<Map.Entry<Cliente, HashSet<Reserva>>> entryIterator = hashMapCliente.entrySetIterator();
        while (entryIterator.hasNext()) {
            Map.Entry<Cliente, HashSet<Reserva>> reservaMapa = entryIterator.next();
            HashSet<Reserva> reservas = reservaMapa.getValue();
            Iterator<Reserva> reservaIterator = reservas.iterator();
            while (reservaIterator.hasNext()) {
                Reserva reservaAux = reservaIterator.next();
                if (fechaActualDelSistema.after(reservaAux.getCliente().getFechaFinal())) {
                    rta += reservaAux.toString() + "\n";
                } else {
                    rta += "Ninguna";
                }
            }
        }
    }


    public Cliente buscarClientePorNombre(String nombre) {
        Iterator<Cliente> iterator = clienteHashSet.iterator();
        Cliente cliente = null;
        while (iterator.hasNext()) {
            Cliente aux = iterator.next();
            if (aux.getNombre().equals(nombre)) {
                cliente = aux;
            }
        }
        return cliente;
    }

    public Alojamiento buscarAlojamientoPorNombre(String nombre) {
        Iterator<Alojamiento> iterator = alojamientoHashSet.iterator();
        Alojamiento alojamiento = null;
        while (iterator.hasNext()) {
            Alojamiento aux = iterator.next();
            if (aux.getNombre().equals(nombre)) {
                alojamiento = aux;
            }
        }
        return alojamiento;
    }
///JSON

//CREACION DEL JSON DE CLIENTE A PARTIR DE UN HASHSET
public void jsonCliente() {


    JSONArray ja= new JSONArray();
    Iterator<Cliente> iterator = clienteHashSet.iterator();//iterator de hashSetcliente

    while (iterator.hasNext()) {//recorremos hashset
        JSONObject jo = new JSONObject();
        Cliente cliente = iterator.next();

        ///clave valor de cliente y hacemos el put en jo atributo x atributo

        try {
            jo.put("nombre",cliente.getNombre());
            jo.put("apellido",cliente.getApellido());
            jo.put("correoElectronico",cliente.getCorreoElectronico());
            jo.put("medioDePago",cliente.getMedioDePago());
            jo.put("cantPersonas",cliente.getCantPersonas());
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        ja.put(jo);
    }
    ///guardamos el jsonobject en un json Array para recorrerlo despuescon el nombre de "clientes"
    JsonUtiles.grabar(ja, "clientes");///
    System.out.println(ja.toString());

}
    ///CREACION DEL JSON ALOJAMIENTO A PARTIR DE UN HASH SET
    public void jsonAlojamiento() {
        //creacion de mi array para luego guardar los jsonobject
        JSONArray jaDepto = new JSONArray();
        JSONArray jaHabHotel = new JSONArray();
        Iterator<Alojamiento> iterator = alojamientoHashSet.iterator();//iterator de mi hashset de alojamiento
        alojamientoHashSet.toString();
        while (iterator.hasNext()) {
            Alojamiento alojamiento = iterator.next();
            alojamiento.toString();
            JSONObject jo = new JSONObject();///creacion de mi json object
            try {
                jo.put("PrecioXAlojar", alojamiento.getPrecioXAlojar());
                jo.put("valoracion", alojamiento.getValoracion());
                jo.put("cantReservas", alojamiento.getCantReservas());
                jo.put("descripcion", alojamiento.getDescripcion());
                jo.put("nombre", alojamiento.getNombre());
                jo.put("direccion", alojamiento.getDireccion());
                jo.put("zona", alojamiento.getZona());
                jo.put("comentarios", alojamiento.getComentarios());
                jo.put("estado", alojamiento.getEstado());
                ///COMO COMPARTEN ATRIBUTOS, EL INSTANCE OF LO HAGO DESPUES DE TERMINAR LOS ATRIBUTOS QUE COMPARTEN ASI MODULARIZAR Y NO GASTAR CODIGO.
                ///DEBO HGACER EL INSTANCE OF PORQUE HAY METODOS PROPIOS DE DEPARTAMENTO Y HOTEL
                if (alojamiento instanceof Departamento) {
                    jo.put("numeroPiso", ((Departamento) alojamiento).getNumeroPiso());
                    jo.put("tamañoDepartamento", ((Departamento) alojamiento).getTamañoDepartamento());
                    jo.put("servicioExtra", ((Departamento) alojamiento).getServicioExtra());
                    jaDepto.put(jo);

                }
                else if (alojamiento instanceof HabitacionHotel)
                {
                    jo.put("servicios", ((HabitacionHotel) alojamiento).getServicios());
                    jo.put("tipoHabitacion", ((HabitacionHotel) alojamiento).getTipoHabitacion());
                    jo.put("numeroHabitacion", ((HabitacionHotel) alojamiento).getNumeroHabitacion());
                    jaHabHotel.put(jo);
                }

            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
        JSONObject joAlojamientos = new JSONObject();//creo este jsonobject para guardarles mis jsonarray y asi tener un objeto que contenga arreglos de objetos
        try {
            joAlojamientos.put("Departamento", jaDepto);
            joAlojamientos.put("HabitacionesHotel",jaHabHotel);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        JsonUtiles.grabar(joAlojamientos, "alojamientos");//lo grabo.

        // System.out.println(joAlojamientos);
        System.out.println((JsonUtiles.leer("alojamientos")));
    }
    ///CREACION DEL JSON DE RESERVAS A PARTIR DE UN HASH SET
    public void jsonReservas(){


        JSONArray jaReservas = new JSONArray();//ESTE SERA MIJSON ARRAY DE RESERVAS
        Iterator <Reserva> iterator = reservaHashSet.iterator();///MI ITERATOR DE HASHSET RESERVA
        while(iterator.hasNext())
        {
            ///CREAMOS T VARIABLES DE CLIENTE Y ALOJAMIENTO CON SU RSPECTIVO CONTENIDO BASADO EN SU RESERVA HACIENDO EL GET, PARA MODULARIZAR
            Reserva reserva = iterator.next();
            Cliente cliente = reserva.getCliente();
            Alojamiento alojamiento=reserva.getAlojamiento();
            ////CREAMOS UN JSON OBJECT POR CADA HASHSET PARA QUE APAREZCA UN STRING ANTES DEL OBJETO EN JSON Y QUE QUEDE MAS MODULARIZADO Y MASORGANIZADO
            JSONObject joReserva= new JSONObject();
            JSONObject joCliente= new JSONObject();
            JSONObject joAloj = new JSONObject();
            {
                try {
                    //PRIMERO HAGO EL PUT DE LOS ATRIBUTOS DE RESERVA PARA QUE SEA LO PRIMERO QUE SE VEA EN EL JSON
                    joReserva.put("preciototal",reserva.getPrecioTotal());
                    joReserva.put("pin",reserva.getPin());

                    ///ATRIBUTOS DE ALOJAMIENTO
                    joAloj.put("PrecioXAlojar", alojamiento.getPrecioXAlojar());
                    joAloj.put("valoracion", alojamiento.getValoracion());
                    joAloj.put("cantReservas", alojamiento.getCantReservas());
                    joAloj.put("descripcion", alojamiento.getDescripcion());
                    joAloj.put("nombre", alojamiento.getNombre());
                    joAloj.put("direccion", alojamiento.getDireccion());
                    joAloj.put("zona", alojamiento.getZona());
                    joAloj.put("comentarios", alojamiento.getComentarios());
                    joAloj.put("estado", alojamiento.getEstado());

                    if (alojamiento instanceof Departamento) {
                        joAloj.put("tipo","Departamento");
                        joAloj.put("numeroPiso", ((Departamento) alojamiento).getNumeroPiso());
                        joAloj.put("tamañoDepartamento", ((Departamento) alojamiento).getTamañoDepartamento());
                        joAloj.put("servicioExtra", ((Departamento) alojamiento).getServicioExtra());

                    }
                    else if (alojamiento instanceof HabitacionHotel)
                    {
                        joAloj.put("tipo","HabitacionHotel");
                        joAloj.put("servicios", ((HabitacionHotel) alojamiento).getServicios());
                        joAloj.put("tipoHabitacion", ((HabitacionHotel) alojamiento).getTipoHabitacion());
                        joAloj.put("numeroHabitacion", ((HabitacionHotel) alojamiento).getNumeroHabitacion());

                    }

                    ///REALIZO EL PUT DE ALOJAMIENTO EN MI JSON OBJECT DE RESERVA
                    joReserva.put("alojamiento",joAloj);
                    ///ATRIBUTOS DE CLIENTE
                    joCliente.put("nombre",cliente.getNombre());
                    joCliente.put("apellido",cliente.getApellido());
                    joCliente.put("correoElectronico",cliente.getCorreoElectronico());
                    joCliente.put("medioDePago",cliente.getMedioDePago());
                    joCliente.put("cantPersonas",cliente.getCantPersonas());
                    ///REALIZO EL PUT DE MIJSONOBJECT DE CLIENTE
                    joReserva.put("cliente",joCliente);


                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
            //A MIO JSON ARRAY LE ASIGNO MI JSONOBJECT DE RESERVA
            jaReservas.put(joReserva);
        }
        JSONObject joReserva = new JSONObject();
        ///CREO UN NUEVOJSON OBJECT QUE VA ATENER MI JSONARRAY
        try {
            joReserva.put("reserva",jaReservas);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        ///GRABO
        JsonUtiles.grabar(joReserva,"reserva");
        System.out.println((JsonUtiles.leer("reserva")));


    }
    ///PASAR DE JSON A JAVA. CLIENTE
    public void jsonAJavaClientes() {

        //leo mi archivo que contiene el json.
        String rta = JsonUtiles.leer("clientes");
        //CREO UN JSONARRAY Y UN JSONOBJECT
        JSONArray JA = null;
        //ASIGNO MI STRING QUE CONTENDRA EL CONTENIDO DE MI JSONCLIENTE
        try {
            JA = new JSONArray(rta);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        JSONObject jo =new JSONObject();
/// RECORRO ARRAY
        for (int i = 0; i < JA.length(); i++) {
            try {
                //Mi Json object Tendra cada objeto de mi arreglo
                jo = JA.getJSONObject(i);
                //INSTANCIO MI CLIENTE
                Cliente cliente = new Cliente();
                ///ASIGNO ATRIBUTOS A MI CLIENTE
                cliente.setNombre(jo.getString("nombre"));
                cliente.setApellido(jo.getString("apellido"));
                cliente.setCorreoElectronico(jo.getString("correoElectronico"));
                cliente.setMedioDePago(jo.getString("medioDePago"));
                cliente.setCantPersonas(jo.getInt("cantPersonas"));
                //Añado a mi HashSet
                clienteHashSet.agregar(cliente);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

        }
        System.out.println(clienteHashSet.toString());

    }
    ///PASAR A JSON A JAVA ALOJAMIENTO
    public void jsonAJavaAlojamiento(){

        //Leo mi archivo que contiene el json.
        String rta = JsonUtiles.leer("alojamientos");
        JSONObject jo = null;
        //lanzo una excepcion de JSON Object y este tendra el contenido de el JSON OBJECT de alojamientos
        try {
            jo = new JSONObject(rta);

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        JSONArray jsonArrayDepto = new JSONArray();
        JSONArray jsonArrayHotel = new JSONArray();
        //Lanzo Excepcion de JSON ARRAY y mi JsonArray inicializado en null ahora contrendra el jsonArray que tiene mi JsonObject
        try {
            jsonArrayDepto = jo.getJSONArray("Departamento");
            jsonArrayHotel = jo.getJSONArray("HabitacionesHotel");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        //Instancio mi objeto



        //Recorro mi array
        for (int i = 0; i < jsonArrayDepto.length(); i++) {
            try {
                //Mi Json object Tendra cada objeto de mi arreglo
                Departamento Depto = new Departamento();
                jo = jsonArrayDepto.getJSONObject(i);

                Depto.setPrecioXAlojar(jo.getDouble("PrecioXAlojar"));
                Depto.setValoracion(jo.getDouble("valoracion"));
                Depto.setDescripcion(jo.getString("descripcion"));
                Depto.setDireccion(jo.getString("direccion"));
                Depto.setZona(jo.getString("zona"));
                Depto.setComentarios(jo.getString("comentarios"));
                Depto.setEstado(EstadoAlojamiento.valueOf(jo.getString("estado")));
                Depto.setNumeroPiso(jo.getInt("numeroPiso"));
                Depto.setTamañoDepartamento(jo.getInt("tamañoDepartamento"));
                Depto.setServicioExtra(jo.getString("servicioExtra"));
                agregarAlojamiento(Depto);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            //Añado a mi HashSet

            System.out.println(alojamientoHashSet);

        }
        for (int i = 0; i < jsonArrayHotel.length(); i++) {
            try {
                HabitacionHotel Hotel = new HabitacionHotel();
                //Mi Json object Tendra cada objeto de mi arreglo
                jo = jsonArrayHotel.getJSONObject(i);

                Hotel.setPrecioXAlojar(jo.getDouble("PrecioXAlojar"));
                Hotel.setValoracion(jo.getDouble("valoracion"));
                Hotel.setCantReservas(jo.getInt("cantReservas"));
                Hotel.setDescripcion(jo.getString("descripcion"));
                Hotel.setNombre(jo.getString("nombre"));
                Hotel.setDireccion(jo.getString("direccion"));
                Hotel.setZona(jo.getString("zona"));
                Hotel.setComentarios(jo.getString("comentarios"));
                Hotel.setEstado(EstadoAlojamiento.valueOf(jo.getString("estado")));
                Hotel.setServicios(jo.getString("servicios"));
                Hotel.setTipoHabitacion(jo.getString("tipoHabitacion"));
                Hotel.setNumeroHabitacion(jo.getInt("numeroHabitacion"));
                agregarAlojamiento(Hotel);

            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            //Añado a mi HashSet

           // System.out.println(alojamientoHashSet);
        }


    }

}

