package Modelo;
import ClasesGenericas.GHashSet;
import ClasesGenericas.GHashMap;
import Enumeraciones.EstadoAlojamiento;
import Utils.JsonUtiles;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class BookingACD {

    //atributos

    GHashMap<Cliente> hashMapCliente;
    GHashMap<Alojamiento> hashMapAlojamiento;//Estos dos en archivos
    GHashSet<Cliente> clienteHashSet; //Guardariamos un JSON
    GHashSet<Alojamiento> alojamientoHashSet; //guardariamos un JSON
    GHashSet<Reserva> reservaHashSet; //tambien JSON

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
        hashMapCliente.pasarMapaAArchivo(nombreArchiCliente);  //al guardar un cliente y su reserva, dicha reserva tambien tendra el dato del alojamiento
        hashMapAlojamiento.pasarMapaAArchivo(nombreArchiAlojamiento);//por lo cual con cargar cualquiera de las dos colecciones ya nos brindaria toda la informacion necesaria
        // para la persistencia de los mapas en el sistema.
    }

    public void pasarArchiAMapa(String nombreArchi) {
        try (FileInputStream fileInputStream = new FileInputStream(nombreArchi);
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {

            while (true) {
                try {
                    Reserva nuevaReserva = (Reserva) objectInputStream.readObject();
                    hashMapAlojamiento.agregar(nuevaReserva.getAlojamiento(), nuevaReserva);
                    hashMapCliente.agregar(nuevaReserva.getCliente(), nuevaReserva);
                } catch (EOFException e) {
                    break;  // Fin del archivo alcanzado
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
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

        if (reservado) { //si el flag devuelve true
            alojamiento.setEstado(EstadoAlojamiento.RESERVADO); //el alojamiento esta reservado
        } else {
            alojamiento.setEstado(EstadoAlojamiento.DISPONIBLE); //sino el dia de hoy esta disponible
        }


    }

    public boolean verificacionFechaCliente(Cliente clienteAAnalizar) { //ahora las trata booking directamente, para que GHashMap solo trate con la genericidad

        boolean flag = true; //bandera
        HashSet<Reserva> auxHashSet = hashMapCliente.getReserva(clienteAAnalizar); //guardo las reservas del cliente

        if (auxHashSet != null) { //mientras tenga reservas
            Iterator<Reserva> it = auxHashSet.iterator(); //creo el iterador
            while (it.hasNext() && flag) { //mientras haya reservas y el flag sea true
                Reserva reserva = it.next();//guardo el it en un auxiliar
                if ((clienteAAnalizar.getFechaInicio().before(reserva.getCliente().getFechaFinal()) && clienteAAnalizar.getFechaFinal().after(reserva.getCliente().getFechaInicio()))) {
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

        if (auxAlojamientos != null) { //si hay reservas
            Iterator<Reserva> it = auxAlojamientos.iterator(); //uso iterador para recorrer
            while (it.hasNext() && flag) { //mientras haya un siguiente y el flag sea verdadero
                Reserva reserva = it.next(); //guardo en una reserva aux
                if (fechaInicioCliente.before(reserva.getCliente().getFechaFinal()) && fechaFinCliente.after(reserva.getCliente().getFechaInicio())) {
                    //si la fecha de inicio del cliente es anterior a la final de la reserva y la final del cliente es posterior a la de inicio de la reserva
                    flag = false; //el alojamiento no esta disponible
                }
            }
        }

        return flag;
    }


    public String reservar(Cliente cliente, Alojamiento alojamiento) {
        // boolean reservada = false; //bandera
        String rta = "";

        if (verificacionFechaCliente(cliente)) {// Verificar que el cliente no tenga reservas en la misma fecha

            //si esto es correcto pasa al siguiente, osea revisar la dispo del alojamiento

            if (verificarFechaAlojamiento(alojamiento, cliente.getFechaInicio(), cliente.getFechaFinal())) { // si el alojamiento esta disponible crea la reserva

                alojamiento.aumentarCantReservas(); //aumenta 1 la cantidad de reservas

                Reserva nuevaReserva = new Reserva(alojamiento, cliente, alojamiento.getPrecioXAlojar() * 1.21); //nuestro costo extra es ese 21%


                hashMapCliente.agregar(cliente, nuevaReserva); // se agregan a los mapas
                hashMapAlojamiento.agregar(alojamiento, nuevaReserva);
                reservaHashSet.agregar(nuevaReserva); //se agrega la reserva al set

                alojamiento.setEstado(EstadoAlojamiento.RESERVADO); // el alojamiento esta reservado
                //reservada = true; //se concreto la reserva, si devuelve false no
                rta = "Reserva realizada con exito";
            } else {
                rta = "ERROR, el alojamiento no esta disponible en esa fecha";
            }
        } else {
            rta = "ERROR, el cliente no esta disponible para reservar";
        }
        return rta;
        //return reservada;
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


    public Reserva buscarReservaPorClienteYAlojamiento(Cliente cliente, Alojamiento alojamiento) {
        HashSet<Reserva> reservas = hashMapCliente.getReserva(cliente);
        Reserva reservaRetornada = null;
        if (reservas != null) {
            Iterator<Reserva> iterator = reservas.iterator();
            while (iterator.hasNext()) {
                Reserva reserva = iterator.next();
                if (reserva.getAlojamiento().equals(alojamiento)) {
                    reservaRetornada = reserva;
                }
            }


        }
        return reservaRetornada;
    }


    public String finalizarReserva(Cliente cliente, Alojamiento alojamiento, double nuevaValoracion /*String comentario*/, String motivo) {
        String ticket = "";
        Reserva reserva = buscarReservaPorClienteYAlojamiento(cliente, alojamiento);
        if (reserva != null) {
            alojamiento.calculoValoracion(nuevaValoracion);
            // alojamiento.agregarComentario(comentario);
            ticket = "Motivo de finalización de la reserva: " + motivo + "\n" +
                    "Nombre del Alojamiento: " + reserva.getAlojamiento().getNombre() + "\n";

            if (reserva.getAlojamiento() instanceof HabitacionHotel) {
                ticket += "Habitación: " + ((HabitacionHotel) reserva.getAlojamiento()).getNumeroHabitacion() + "\n";
            } else if (reserva.getAlojamiento() instanceof Departamento) {
                ticket += "Número de Piso: " + ((Departamento) reserva.getAlojamiento()).getNumeroPiso() + "\n";
            }

            ticket += "Reserva a cargo de: " + reserva.getCliente().getNombre() + "\n" +
                    "Contacto: " + reserva.getCliente().getCorreoElectronico() + "\n" +
                    "Inicio de estadía: " + reserva.getCliente().getFechaInicio() + "\n" +
                    "Precio a abonar (precio del alojamiento + 21%): " + reserva.getPrecioTotal() + "\n" +
                    "Pin de autenticación: " + reserva.getPin() + "\n";

            //  boolean borrado = reservaHashSet.borrarReserva(reserva);
            alojamiento.setEstado(EstadoAlojamiento.DISPONIBLE);
            hashMapCliente.borrar(cliente, reserva);
            hashMapAlojamiento.borrar(alojamiento, reserva);


            // Actualiza el archivo JSON después de eliminar la reserva
            // jsonReservas();
            ticket += "\noperacion realizada con exito";

        } else {
            ticket = "No se encontró la reserva para el cliente y alojamiento proporcionados.";
        }
        return ticket;
    }

    public String devolverAlojamientosDisponibles(Cliente cliente) { //muestra los alojamientos disponibles en la fecha que el cliente quiere reservar
        String rta = "";
        if (alojamientoHashSet != null) {
            Iterator<Alojamiento> alojamientoIterator = alojamientoHashSet.iterator();
            while (alojamientoIterator.hasNext()) {
                Alojamiento aux = alojamientoIterator.next();
                if (verificarFechaAlojamiento(aux, cliente.getFechaInicio(), cliente.getFechaFinal())) {
                    rta += "\n" + aux.toString();
                }
            }

        }

        return rta;
    }


    public String mostrarSetCliente() {
        return clienteHashSet.listar();
    }

    public String mostrarMapAlojamiento() {
        String rta = "";
        rta = hashMapAlojamiento.listar();
        return rta;
    }

    public String mostrarMapCliente() {
        String rta = "";
        rta = hashMapCliente.listar();
        return rta;
    }

    public String mostrarReservasAPuntoDeTerminar() {
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

    public Alojamiento buscarAlojamientoConMasReservas() {
        Alojamiento mayorReservas = null;
        Iterator<Alojamiento> alojamientoIterator = alojamientoHashSet.iterator();
        while (alojamientoIterator.hasNext()) {
            Alojamiento aux = alojamientoIterator.next();
            if (mayorReservas == null) {
                mayorReservas = aux;
            }
            if (aux.getCantReservas() > mayorReservas.getCantReservas()) {
                mayorReservas = aux;
            }
        }


        return mayorReservas;
    }

    public Alojamiento buscarAlojamientoMasCaro() {
        Alojamiento masCaro = null;
        Iterator<Alojamiento> alojamientoIterator = alojamientoHashSet.iterator();
        while (alojamientoIterator.hasNext()) {
            Alojamiento aux = alojamientoIterator.next();
            if (masCaro == null) {
                masCaro = aux;
            }
            if (aux.getPrecioXAlojar() > masCaro.getPrecioXAlojar()) {
                masCaro = aux;
            }
        }
        return masCaro;
    }


    public Cliente buscarClientePorNombre(String nombre, String apellido) {
        Iterator<Cliente> iterator = clienteHashSet.iterator();
        Cliente cliente = null;
        while (iterator.hasNext()) {
            Cliente aux = iterator.next();
            if (aux.equalsXNombreYApellido(nombre, apellido)) {
                cliente = aux;
            }
        }
        return cliente;
    }

    public ArrayList<Alojamiento> buscarAlojamientosPorNombre(String nombre) {
        ArrayList<Alojamiento> alojamientos = new ArrayList<>(); //creo una lista donde van a estar los alojamientos con ese nombre(por las habitaciones de hotel que comparten nombre)
        if (alojamientoHashSet != null) { //mientras haya alojamientos
            Iterator<Alojamiento> alojamientoIterator = alojamientoHashSet.iterator(); //creo un iterador
            while (alojamientoIterator.hasNext()) { //se recorre
                Alojamiento alojamiento = alojamientoIterator.next(); //guardo el next en un alojamiento auxiliar
                if (alojamiento.getNombre().equalsIgnoreCase(nombre)) { //si el alojamiento tiene el mismo nombre lo guarda en el arreglo que voy a retornar
                    alojamientos.add(alojamiento);
                }
            }

        }
        //no usamos instanceof porque tranquilamente el departamento puede tener mas de un nombre Y PUEDEN HABER MAS ALOJAMIENTOS CON IGUAL NOMBRE
        return alojamientos;

    }

    public String mostrarReservaPorMes(int mes) {
        String rta = "";
        Iterator<Reserva> iterator = reservaHashSet.iterator();
        while (iterator.hasNext()) {
            Reserva reserva = iterator.next();
            int mesReserva = reserva.getCliente().getFechaInicio().getMonth();
            if (mesReserva + 1 == mes) {
                    rta += reserva.toString();
            }
        }
        if (rta.equals("")) {
            rta = "NO HAY RESERVAS EN ESTE MES";
        }
        return rta;
    }
    public String mostrarSetAlojamientoXValoracion(double valoracion)
    {
        String rta="";
        Iterator<Alojamiento> nuevoIterator = alojamientoHashSet.iterator();
        while (nuevoIterator.hasNext())
        {
            Alojamiento auxAlojamiento = nuevoIterator.next();
            if(auxAlojamiento.getValoracion() == valoracion)
            {
                rta += auxAlojamiento.toString();
            }
        }
        if(rta.equals("")){
            rta="NO HAY ALOJAMIENTOS CON ESA VALORACION";
        }
        return rta;
    }


///JSON

    //CREACION DEL JSON DE CLIENTE A PARTIR DE UN HASHSET
    public void jsonCliente() {


        JSONArray ja = new JSONArray();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Iterator<Cliente> iterator = clienteHashSet.iterator();//iterator de hashSetcliente

        while (iterator.hasNext()) {//recorremos hashset
            JSONObject jo = new JSONObject();
            Cliente cliente = iterator.next();

            ///clave valor de cliente y hacemos el put en jo atributo x atributo

            try {
                jo.put("nombre", cliente.getNombre());
                jo.put("apellido", cliente.getApellido());
                jo.put("correoElectronico", cliente.getCorreoElectronico());
                jo.put("medioDePago", cliente.getMedioDePago());
                jo.put("cantPersonas", cliente.getCantPersonas());
                jo.put("fechaInicio", dateFormat.format(cliente.getFechaInicio()));
                jo.put("fechaFinal", dateFormat.format(cliente.getFechaFinal()));
            } catch (JSONException e) {
                e.printStackTrace();
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

                } else if (alojamiento instanceof HabitacionHotel) {
                    jo.put("servicios", ((HabitacionHotel) alojamiento).getServicios());
                    jo.put("tipoHabitacion", ((HabitacionHotel) alojamiento).getTipoHabitacion());
                    jo.put("numeroHabitacion", ((HabitacionHotel) alojamiento).getNumeroHabitacion());
                    jaHabHotel.put(jo);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        JSONObject joAlojamientos = new JSONObject();//creo este jsonobject para guardarles mis jsonarray y asi tener un objeto que contenga arreglos de objetos
        try {
            joAlojamientos.put("Departamento", jaDepto);
            joAlojamientos.put("HabitacionesHotel", jaHabHotel);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonUtiles.grabar(joAlojamientos, "alojamientos");//lo grabo.

        // System.out.println(joAlojamientos);
        // System.out.println((JsonUtiles.leer("alojamientos")));
    }

    ///CREACION DEL JSON DE RESERVAS A PARTIR DE UN HASH SET
    public void jsonReservas() {
        JSONArray jaReservas = new JSONArray();
        Iterator<Reserva> iterator = reservaHashSet.iterator();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        while (iterator.hasNext()) {
            Reserva reserva = iterator.next();
            Cliente cliente = reserva.getCliente();
            Alojamiento alojamiento = reserva.getAlojamiento();
            JSONObject joReserva = new JSONObject();
            JSONObject joCliente = new JSONObject();
            JSONObject joAloj = new JSONObject();

            try {
                joReserva.put("preciototal", reserva.getPrecioTotal());
                joReserva.put("pin", reserva.getPin().toString());
                joAloj.put("PrecioXAlojar", alojamiento.getPrecioXAlojar());
                joAloj.put("valoracion", alojamiento.getValoracion());
                joAloj.put("cantReservas", alojamiento.getCantReservas());
                joAloj.put("descripcion", alojamiento.getDescripcion());
                joAloj.put("nombre", alojamiento.getNombre());
                joAloj.put("direccion", alojamiento.getDireccion());
                joAloj.put("zona", alojamiento.getZona());
                joAloj.put("comentarios", alojamiento.getComentarios());
                joAloj.put("estado", alojamiento.getEstado().name());

                if (alojamiento instanceof Departamento) {
                    joAloj.put("tipo", "Departamento");
                    joAloj.put("numeroPiso", ((Departamento) alojamiento).getNumeroPiso());
                    joAloj.put("tamañoDepartamento", ((Departamento) alojamiento).getTamañoDepartamento());
                    joAloj.put("servicioExtra", ((Departamento) alojamiento).getServicioExtra());
                } else if (alojamiento instanceof HabitacionHotel) {
                    joAloj.put("tipo", "HabitacionHotel");
                    joAloj.put("servicios", ((HabitacionHotel) alojamiento).getServicios());
                    joAloj.put("tipoHabitacion", ((HabitacionHotel) alojamiento).getTipoHabitacion());
                    joAloj.put("numeroHabitacion", ((HabitacionHotel) alojamiento).getNumeroHabitacion());
                }

                joReserva.put("alojamiento", joAloj);

                joCliente.put("nombre", cliente.getNombre());
                joCliente.put("apellido", cliente.getApellido());
                joCliente.put("correoElectronico", cliente.getCorreoElectronico());
                joCliente.put("medioDePago", cliente.getMedioDePago());
                joCliente.put("cantPersonas", cliente.getCantPersonas());
                joCliente.put("fechaInicio", dateFormat.format(cliente.getFechaInicio()));
                joCliente.put("fechaFinal", dateFormat.format(cliente.getFechaFinal()));

                joReserva.put("cliente", joCliente);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            jaReservas.put(joReserva);
        }

        JSONObject joReserva = new JSONObject();
        try {
            joReserva.put("reserva", jaReservas);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonUtiles.grabar(joReserva, "reserva");
    }

    ///PASAR DE JSON A JAVA. CLIENTE
    public void jsonAJavaClientes() {

        //leo mi archivo que contiene el json.
        String rta = JsonUtiles.leer("clientes");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //CREO UN JSONARRAY Y UN JSONOBJECT
        JSONArray JA = null;
        //ASIGNO MI STRING QUE CONTENDRA EL CONTENIDO DE MI JSONCLIENTE
        try {
            JA = new JSONArray(rta);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONObject jo = new JSONObject();
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
                try {
                    cliente.setFechaInicio(dateFormat.parse(jo.getString("fechaInicio")));
                    cliente.setFechaFinal(dateFormat.parse(jo.getString("fechaFinal")));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                //Añado a mi HashSet
                clienteHashSet.agregar(cliente);
            } catch (JSONException e) {
                e.printStackTrace();
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
            e.printStackTrace();
        }
        JSONArray jsonArrayDepto = new JSONArray();
        JSONArray jsonArrayHotel = new JSONArray();
        //Lanzo Excepcion de JSON ARRAY y mi JsonArray inicializado en null ahora contrendra el jsonArray que tiene mi JsonObject
        try {
            jsonArrayDepto = jo.getJSONArray("Departamento");
            jsonArrayHotel = jo.getJSONArray("HabitacionesHotel");
        } catch (JSONException e) {
            e.printStackTrace();
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
                Depto.setCantReservas(joDepto.getInt("cantReservas"));
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
                e.printStackTrace();
            }
            //Añado a mi HashSet

        }
        for (int i = 0; i < jsonArrayHotel.length(); i++) {
            try {
                HabitacionHotel Hotel = new HabitacionHotel();
                //Mi Json object Tendra cada objeto de mi arreglo
                JSONObject joHotel = jsonArrayHotel.getJSONObject(i);

                Hotel.setPrecioXAlojar(joHotel.getDouble("PrecioXAlojar"));
                Hotel.setValoracion(joHotel.getDouble("valoracion"));
                Hotel.setCantReservas(joHotel.getInt("cantReservas"));
                Hotel.setDescripcion(joHotel.getString("descripcion"));
                Hotel.setNombre(joHotel.getString("nombre"));
                Hotel.setDireccion(joHotel.getString("direccion"));
                Hotel.setZona(joHotel.getString("zona"));
                Hotel.setComentarios(joHotel.getString("comentarios"));
                Hotel.setEstado(EstadoAlojamiento.valueOf(joHotel.getString("estado")));
                Hotel.setServicios(joHotel.getString("servicios"));
                Hotel.setTipoHabitacion(joHotel.getString("tipoHabitacion"));
                Hotel.setNumeroHabitacion(joHotel.getInt("numeroHabitacion"));
                agregarAlojamiento(Hotel);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            //Añado a mi HashSet

            //System.out.println(alojamientoHashSet);
        }


    }

    public void jsonAJavaReserva() {

        ///leemos nuestro archivo reserva, esto nos retorna un string
        String rta = JsonUtiles.leer("reserva");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        JSONObject jo = null;
        ///creamos nuestro JSONOBject con el contenido de nuestro string que contenia el jsonobject
        try {
            jo = new JSONObject(rta);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONArray JA = new JSONArray();
        ///creamos nuestro jsonarray y lo cargamos con el jsonarray de nombre "reserva" que contiene nustro jsonobject
        try {
            JA = jo.getJSONArray("reserva");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        /// recorremos nuestro array.
        for (int i = 0; i < JA.length(); i++) {
            try {
                JSONObject joReserva = JA.getJSONObject(i);
                Reserva reserva = new Reserva();

                reserva.setPin(UUID.fromString(joReserva.getString("pin")));
                reserva.setPrecioTotal(joReserva.getDouble("preciototal"));

                JSONObject joCliente = joReserva.getJSONObject("cliente");
                Cliente cliente = new Cliente();
                cliente.setNombre(joCliente.getString("nombre"));
                cliente.setApellido(joCliente.getString("apellido"));
                cliente.setCorreoElectronico(joCliente.getString("correoElectronico"));
                cliente.setMedioDePago(joCliente.getString("medioDePago"));
                cliente.setCantPersonas(joCliente.getInt("cantPersonas"));
                try {
                    cliente.setFechaInicio(dateFormat.parse(joCliente.getString("fechaInicio")));
                    cliente.setFechaFinal(dateFormat.parse(joCliente.getString("fechaFinal")));
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }

                reserva.setCliente(cliente);

                JSONObject joAlojamiento = joReserva.getJSONObject("alojamiento");
                Alojamiento alojamiento=null;
                String tipoAlojamiento = joAlojamiento.getString("tipo");

                if ("HabitacionHotel".equals(tipoAlojamiento)) {
                    alojamiento = new HabitacionHotel();
                    ((HabitacionHotel) alojamiento).setServicios(joAlojamiento.getString("servicios"));
                    ((HabitacionHotel) alojamiento).setTipoHabitacion(joAlojamiento.getString("tipoHabitacion"));
                    ((HabitacionHotel) alojamiento).setNumeroHabitacion(joAlojamiento.getInt("numeroHabitacion"));
                } else if ("Departamento".equals(tipoAlojamiento)) {
                    alojamiento = new Departamento();
                    ((Departamento) alojamiento).setNumeroPiso(joAlojamiento.getInt("numeroPiso"));
                    ((Departamento) alojamiento).setTamañoDepartamento(joAlojamiento.getInt("tamañoDepartamento"));
                    ((Departamento) alojamiento).setServicioExtra(joAlojamiento.getString("servicioExtra"));
                }

                if (alojamiento != null) {
                    alojamiento.setDescripcion(joAlojamiento.getString("descripcion"));
                    alojamiento.setEstado(EstadoAlojamiento.valueOf(joAlojamiento.getString("estado")));
                    alojamiento.setDireccion(joAlojamiento.getString("direccion"));
                    alojamiento.setValoracion(joAlojamiento.getDouble("valoracion"));
                    alojamiento.setNombre(joAlojamiento.getString("nombre"));
                    alojamiento.setPrecioXAlojar(joAlojamiento.getDouble("PrecioXAlojar"));
                    alojamiento.setZona(joAlojamiento.getString("zona"));
                    alojamiento.setCantReservas(joAlojamiento.getInt("cantReservas"));
                    alojamiento.setComentarios(joAlojamiento.getString("comentarios"));
                    reserva.setAlojamiento(alojamiento);


                   // hashMapAlojamiento.agregar(alojamiento, reserva);
               // hashMapCliente.agregar(cliente, reserva);
                    reservaHashSet.agregar(reserva);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}