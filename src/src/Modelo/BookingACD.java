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

public class BookingACD{

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
        hashMapAlojamiento.pasarMapaAArchivo(nombreArchiCliente);  //al guardar un cliente y su reserva, dicha reserva tambien tendra el dato del alojamiento
        hashMapAlojamiento.pasarMapaAArchivo(nombreArchiAlojamiento);//por lo cual con cargar cualquiera de las dos colecciones ya nos brindaria toda la informacion necesaria
                                                                    // para la persistencia de los mapas en el sistema.
    }

    public void pasarArchiAMapa(String nombreArchi) { //esta funcion va a pasar todos los datos de cualquier archivo a los
        ObjectInputStream objectInputStream = null; // respectivos mapas ya que los archivos van a guardar todas las reservas
        try {                                       // que existan, y luego se guardan en su correspondiente mapa
            FileInputStream fileInputStream = new FileInputStream(nombreArchi);
            objectInputStream = new ObjectInputStream(fileInputStream);
            while (true) {
                Reserva nuevaReserva = (Reserva) objectInputStream.readObject();
                hashMapAlojamiento.agregar(nuevaReserva.getAlojamiento(), nuevaReserva);
                hashMapCliente.agregar(nuevaReserva.getCliente(), nuevaReserva);
                reservaHashSet.agregar(nuevaReserva);
            }
        } catch (EOFException e) {
            System.out.println("Archivos traidos al sistema.");
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


    public void actualizarEstadoAlojamiento(Alojamiento alojamiento) {
        Date fechaActual = new Date(); //guardo la fecha actual del sistema
        HashSet<Reserva> reservas = hashMapAlojamiento.getReserva(alojamiento); //guardo el set de las reservas en un auxiliar
        boolean reservado = false; //bandera

        if (reservas != null) { // si hay reservas
            Iterator<Reserva> it = reservas.iterator(); //iterador
            while (it.hasNext() && !reservado) { //mientras que el iterator tenga un siguiente y el booleano no cambie a true(quiere decir que hoy hay una reserva actualmente)
                Reserva reserva = it.next(); //reserva auxiliar
                if (!fechaActual.before(reserva.getCliente().getFechaInicio()) && !fechaActual.after(reserva.getCliente().getFechaFinal())) {
                    //si la fecha actual del sistema no es anterior a la fecha de inicio del cliente de la reserva
                    //y la fecha actual del sistema no es posterior a la de la reserva (queriendo decir el rango en que esta la reserva)
                    reservado = true; //la reserva sigue en camino, por lo que se prende el boolean
                }
            }
        }

        if(reservado){ //si el flag devuelve true
            alojamiento.setEstado(EstadoAlojamiento.RESERVADO); //el alojamiento esta reservado
        } else {
            alojamiento.setEstado(EstadoAlojamiento.DISPONIBLE); //sino el dia de hoy esta disponible
        }


    }

    public boolean verificacionFechaCliente(Cliente clienteAAnalizar){ //ahora las trata booking directamente, para que GHashMap solo trate con la genericidad

        boolean flag = true; //bandera
        HashSet<Reserva> auxHashSet = hashMapCliente.getReserva(clienteAAnalizar); //guardo las reservas del cliente

        if(auxHashSet != null){ //mientras tenga reservas
            Iterator<Reserva> it = auxHashSet.iterator(); //creo el iterador
            while (it.hasNext() && flag) { //mientras haya reservas y el flag sea true
                Reserva reserva = it.next();//guardo el it en un auxiliar
                if((clienteAAnalizar.getFechaInicio().before(reserva.getCliente().getFechaFinal()) && clienteAAnalizar.getFechaFinal().after(reserva.getCliente().getFechaInicio()))){
                   //si la fecha inicial del cliente es anterior a la final de la de la reserva y la fecha final del cliente es posterior a la  inicial de la reserva
                    flag = false; //quiere decir que el cliente tiene una reserva
                }
            }
        }

        return flag;
    }

    public boolean verificarFechaAlojamiento(Alojamiento alojamientoAAnalizar, Date fechaInicioCliente, Date fechaFinCliente) {
        boolean flag = true; //bandera
        HashSet<Reserva> auxAlojamientos = hashMapAlojamiento.getReserva(alojamientoAAnalizar); //guardo en un auxiliar las reservas con ese alojamiento

        if(auxAlojamientos != null){ //si hay reservas
            Iterator<Reserva> it = auxAlojamientos.iterator(); //uso iterador para recorrer
            while(it.hasNext() && flag){ //mientras haya un siguiente y el flag sea verdadero
                Reserva reserva = it.next(); //guardo en una reserva aux
                if (fechaInicioCliente.before(reserva.getCliente().getFechaFinal()) && fechaFinCliente.after(reserva.getCliente().getFechaInicio())) {
                   //si la fecha de inicio del cliente es anterior a la final de la reserva y la final del cliente es posterior a la de inicio de la reserva
                    flag = false; //el alojamiento no esta disponible
                }
            }
        }

        return flag;
    }




    public boolean reservar(Cliente cliente, Alojamiento alojamiento){
        boolean reservada = false; //bandera


        if(verificacionFechaCliente(cliente)){// Verificar que el cliente no tenga reservas en la misma fecha

            //si esto es correcto pasa al siguiente, osea revisar la dispo del alojamiento

            if(verificarFechaAlojamiento(alojamiento, cliente.getFechaInicio(), cliente.getFechaFinal())){ // si el alojamiento esta disponible crea la reserva
                alojamiento.aumentarCantReservas(); //aumenta 1 la cantidad de reservas
                Reserva nuevaReserva =new Reserva(alojamiento, cliente, alojamiento.getPrecioXAlojar() * 1.21); //nuestro costo extra es ese 21%


                hashMapCliente.agregar(cliente, nuevaReserva); // se agregan a los mapas
                hashMapAlojamiento.agregar(alojamiento, nuevaReserva);
                reservaHashSet.agregar(nuevaReserva); //se agrega la reserva al set

                alojamiento.setEstado(EstadoAlojamiento.RESERVADO); // el alojamiento esta reservado
                reservada = true; //se concreto la reserva, si devuelve false no
            }
        }

        return reservada;
    }
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




    /*
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

     */


    public String devolverAlojamientosDisponibles(Cliente cliente){ //muestra los alojamientos disponibles en la fecha que el cliente quiere reservar
        String rta = "";
        if(alojamientoHashSet != null){
            Iterator<Alojamiento> alojamientoIterator = alojamientoHashSet.iterator();
            while(alojamientoIterator.hasNext()){
                Alojamiento aux = alojamientoIterator.next();
               if(verificarFechaAlojamiento(aux, cliente.getFechaInicio(), cliente.getFechaFinal())){
                   rta+="\n"+aux.toString();
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
    public String mostrarSetCliente()
    {
        return clienteHashSet.listar();
    }

    public String mostrarMapAlojamiento()
    {
        String rta = "";
        rta = hashMapAlojamiento.listar();
        return rta;
    }
    public String mostrarMapCliente()
    {
        String rta = "";
        rta = hashMapCliente.listar();
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
            if (aux.equalsXNombre(nombre))
            {
                cliente = aux;
            }
        }
        return cliente;
    }

    public ArrayList<Alojamiento> buscarAlojamientosPorNombre(String nombre){
       ArrayList<Alojamiento> alojamientos = new ArrayList<>(); //creo una lista donde van a estar los alojamientos con ese nombre(por las habitaciones de hotel que comparten nombre)
       if(alojamientoHashSet!=null){ //mientras haya alojamientos
           Iterator<Alojamiento> alojamientoIterator = alojamientoHashSet.iterator(); //creo un iterador
           while(alojamientoIterator.hasNext()){ //se recorre
               Alojamiento alojamiento = alojamientoIterator.next(); //guardo el next en un alojamiento auxiliar
               if (alojamiento.getNombre().equalsIgnoreCase(nombre)){ //si el alojamiento tiene el mismo nombre lo guarda en el arreglo que voy a retornar
                   alojamientos.add(alojamiento);
               }
           }

       }
       //no usamos instanceof porque tranquilamente el departamento puede tener mas de un nombre Y PUEDEN HABER MAS ALOJAMIENTOS CON IGUAL NOMBRE
        return alojamientos;

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
   // System.out.println(ja.toString());

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
       // System.out.println((JsonUtiles.leer("alojamientos")));
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
            //A MI JSON ARRAY LE ASIGNO MI JSONOBJECT DE RESERVA
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
       // System.out.println((JsonUtiles.leer("reserva")));


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
       // System.out.println(clienteHashSet.toString());

    }
    ///PASAR A JSON A JAVA ALOJAMIENTO
    public void jsonAJavaAlojamiento() {

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
                JSONObject joDepto = jsonArrayDepto.getJSONObject(i);

                Depto.setPrecioXAlojar(joDepto.getDouble("PrecioXAlojar"));
                Depto.setValoracion(joDepto.getDouble("valoracion"));
                Depto.setDescripcion(joDepto.getString("descripcion"));
                Depto.setNombre(joDepto.getString("nombre"));

                Depto.setDireccion(joDepto.getString("direccion"));
                Depto.setZona(joDepto.getString("zona"));
                Depto.setComentarios(joDepto.getString("comentarios"));
                Depto.setEstado(EstadoAlojamiento.valueOf(joDepto.getString("estado")));
                Depto.setNumeroPiso(joDepto.getInt("numeroPiso"));
                Depto.setTamañoDepartamento(joDepto.getInt("tamañoDepartamento"));
                Depto.setServicioExtra(joDepto.getString("servicioExtra"));
                agregarAlojamiento(Depto);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            //Añado a mi HashSet

        }
        for (int i = 0; i < jsonArrayHotel.length(); i++) {
            try {
                HabitacionHotel Hotel = new HabitacionHotel();
                //Mi Json object Tendra cada objeto de mi arreglo
                JSONObject joHotel = jsonArrayHotel.getJSONObject(i);

                Hotel.setPrecioXAlojar(joHotel .getDouble("PrecioXAlojar"));
                Hotel.setValoracion(joHotel .getDouble("valoracion"));
                Hotel.setCantReservas(joHotel .getInt("cantReservas"));
                Hotel.setDescripcion(joHotel .getString("descripcion"));
                Hotel.setNombre(joHotel .getString("nombre"));
                Hotel.setDireccion(joHotel .getString("direccion"));
                Hotel.setZona(joHotel .getString("zona"));
                Hotel.setComentarios(joHotel .getString("comentarios"));
                Hotel.setEstado(EstadoAlojamiento.valueOf(joHotel .getString("estado")));
                Hotel.setServicios(joHotel .getString("servicios"));
                Hotel.setTipoHabitacion(joHotel .getString("tipoHabitacion"));
                Hotel.setNumeroHabitacion(joHotel .getInt("numeroHabitacion"));
                agregarAlojamiento(Hotel);

            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            //Añado a mi HashSet

            //System.out.println(alojamientoHashSet);
        }


    }

}