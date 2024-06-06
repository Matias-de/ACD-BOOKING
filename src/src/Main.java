import Enumeraciones.EstadoAlojamiento;
import Modelo.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Main {
    static Scanner scan;

    public static void main(String[] args) {


        BookingACD nuevoBooking = new BookingACD();
      nuevoBooking.pasarArchiAMapa("ArchivoCliente"); // (se carga mapas)esta funcion se debe usar de manera unica con cualquier archivo ya sea cliente o alojamiento.nuevoBooking.jsonAJavaClientes(); //se carga set cliente
       nuevoBooking.jsonAJavaAlojamiento();//se carga set alojamiento
        nuevoBooking.jsonAJavaReserva();
        nuevoBooking.jsonAJavaClientes();
        System.out.println(nuevoBooking.getHashMapAlojamiento());
        System.out.println(nuevoBooking.getHashMapCliente());
       // System.out.println(nuevoBooking.getHashMapCliente());
        menu(nuevoBooking);
        nuevoBooking.guardarDatosEnArchi("ArchivoCliente","ArchivoAlojamiento");//se guardan datos en el archivo
        nuevoBooking.jsonCliente();
        nuevoBooking.jsonAlojamiento();
        nuevoBooking.jsonReservas();
    }
    public static void opcionesMenu(){ //aca pongan las opciones del menu
        System.out.println("BIENVENIDO!");
        System.out.println("Ingrese la opcion que desee realizar:");
        System.out.println("1)Cargar un nuevo cliente.");
        System.out.println("2)Cargar un alojamiento.");
        System.out.println("3)Realizar una reserva.");
        System.out.println("4)Finalizar una reserva.");
        System.out.println("5)Mostrar las reservas actuales");
        System.out.println("6)Mostrar Las reservas alguna vez ocurridas en el Sistema.");
        System.out.println("7)Mostrar los Clientes del Sistema.");
        System.out.println("8)Mostrar los Alojamientos del Sistema.");
        System.out.println("9)Ingresar al menu de modificación de Clientes");

    }

    public static void menu(BookingACD nuevoBooking){
        //declaracion de variables
        int opc = 0, auxInt=0;
        char inicio = 's';
        Cliente clienteAux= null;
        Alojamiento alojamientoAux = null;
        String stringAux="";
        scan = new Scanner(System.in);
        //texto en pantalla
        while ( inicio == 's') {
            opcionesMenu();
            opc = scan.nextInt(); // cargamos la opcion elegida por el administrador
            switch (opc) {
                case 1:
                    Cliente nuevoCliente = cargaCliente();
                    nuevoBooking.agregarCliente(nuevoCliente);
                    break;
                case 2:
                    alojamientoAux = cargarAlojamiento();
                    nuevoBooking.agregarAlojamiento(alojamientoAux);
                    //scan.nextLine();
                    break;
                case 3:
                   String apellidoAux="";
                    if (!nuevoBooking.getClienteHashSet().isEmpty()) {
                        do{
                            System.out.println("Desea usar los clientes ya cargados o cargar uno nuevo?: (1 para uno cargado, 2 para uno nuevo)");
                            auxInt = scan.nextInt();
                            scan.nextLine();

                            if(auxInt == 1){
                                boolean clienteEncontrado = false;

                                while(!clienteEncontrado){
                                    System.out.println("Clientes ya cargados: ");
                                    System.out.println(nuevoBooking.getClienteHashSet().toString());

                                    System.out.println("Ingrese el nombre del cliente que quiere elegir: ");
                                    stringAux = scan.nextLine();

                                    System.out.println("Ingrese el apellido del cliente: ");
                                    apellidoAux = scan.nextLine();

                                    clienteAux = nuevoBooking.buscarClientePorNombre(stringAux, apellidoAux);

                                    if(clienteAux != null){
                                        clienteEncontrado = true;
                                        System.out.println("Cliente encontrado! Se le asignará la reserva al cliente: " + clienteAux);
                                        preguntarEstadia(clienteAux);
                                    }else{
                                        System.out.println("Nombre de cliente incorrecto, por favor elija un nombre válido:");
                                    }
                                }
                            }else if(auxInt == 2){
                                System.out.println("Carga de cliente nuevo:");
                                clienteAux= cargaCliente();
                                nuevoBooking.agregarCliente(clienteAux);
                                preguntarEstadia(clienteAux);

                            }else{
                                System.out.println("Error al ingresar, favor de ingresar solamente el boton 1 o el 2.");
                                auxInt = scan.nextInt();
                            }
                        }while (auxInt != 1 && auxInt != 2);

                    } else{
                        System.out.println("No hay clientes cargados, cargue uno para poder continuar.");
                        clienteAux= cargaCliente();
                        nuevoBooking.agregarCliente(clienteAux);
                        preguntarEstadia(clienteAux);
                    }

                    if (!nuevoBooking.getAlojamientoHashSet().isEmpty()){
                        do {
                            System.out.println("Desea usar los alojamientos ya cargados o cargar uno nuevo? (1 usar ya cargado, 2 cargar nuevo): ");
                            auxInt = scan.nextInt();
                            if (auxInt == 1) {
                                System.out.println("Alojamientos disponibles: ");
                                System.out.println(nuevoBooking.devolverAlojamientosDisponibles(clienteAux));
                                System.out.println("Ingrese el nombre del Alojamiento elegido: ");
                                scan.nextLine();
                                stringAux = scan.nextLine();
                                ArrayList<Alojamiento> alojamientos = nuevoBooking.buscarAlojamientosPorNombre(stringAux);

                                while (alojamientos.isEmpty()) {
                                    System.out.println("Nombre de alojamiento incorrecto, Favor de elegir un nombre valido:");
                                    stringAux = scan.nextLine();
                                    alojamientos = nuevoBooking.buscarAlojamientosPorNombre(stringAux);
                                }

                                System.out.println("Alojamientos encontrados: ");
                                for (int i = 0; i < alojamientos.size(); i++) {
                                    System.out.println((i + 1) + ". " + alojamientos.get(i));
                                }

                                System.out.println("Ingrese el número del Alojamiento que desea elegir: ");
                                int opcionAlojamiento = scan.nextInt();
                                while (opcionAlojamiento < 1 || opcionAlojamiento > alojamientos.size()) {
                                    System.out.println("Opción incorrecta. Favor de elegir un número válido:");
                                    opcionAlojamiento = scan.nextInt();
                                }
                                alojamientoAux = alojamientos.get(opcionAlojamiento - 1);
                                System.out.println("Alojamiento elegido: " + alojamientoAux);

                            } else if (auxInt == 2) {
                                System.out.println("Cargar alojamiento nuevo:");
                                alojamientoAux = cargarAlojamiento();
                                nuevoBooking.agregarAlojamiento(alojamientoAux);
                                System.out.println("Perfecto, se le asignara la reserva al alojamiento: " + alojamientoAux);
                            } else {
                                System.out.println("Error al ingresar, favor de ingresar solamente el boton 1 o el 2.");
                                auxInt = scan.nextInt();
                            }
                        } while (auxInt != 1 && auxInt != 2);
                    } else {
                        System.out.println("No hay alojamientos cargados, cargue uno para poder continuar.");
                        alojamientoAux = cargarAlojamiento();
                        nuevoBooking.agregarAlojamiento(alojamientoAux);
                    }
                    System.out.println(nuevoBooking.reservar(clienteAux,alojamientoAux));
                  /*cambiar*/ // System.out.println("(True=exitoso)/(False=no se pudo reservar)--->Rta:"+nuevoBooking.reservar(clienteAux,alojamientoAux));
                    break;
                case 4:
                    String motivo="", apellido="";
                    System.out.println("Estas son las reservas que terminan hoy!: \n"+nuevoBooking.mostrarReservasAPuntoDeTerminar());
                    do {
                        System.out.println("Desea terminar la reserva por fin de fecha o antes de la misma? Ingrese 'fecha' u 'otro' según corresponda: ");
                        scan.nextLine();
                         motivo = scan.nextLine();
                    } while (!motivo.equalsIgnoreCase("fecha") && !motivo.equalsIgnoreCase("otro"));
                    System.out.println("Clientes ya cargados: ");
                    System.out.println(nuevoBooking.getClienteHashSet().toString());
                    System.out.println("Ingrese el nombre del cliente, se buscara una reserva a cargo de ese nombre:");
                    stringAux = scan.nextLine();
                    System.out.println("Ahora ingrese el apellido: ");
                    apellido= scan.nextLine();
                    while (nuevoBooking.buscarClientePorNombre(stringAux, apellido) == null) {
                        System.out.println("Nombre de cliente incorrecto, favor de elegir un nombre válido:");
                        stringAux = scan.nextLine();
                    }
                    clienteAux = nuevoBooking.buscarClientePorNombre(stringAux, apellido);
                    String muestraReservas = nuevoBooking.mostrarReservasDeCliente(clienteAux);
                    if(muestraReservas.equalsIgnoreCase("cliente no encontrado/sin reservas")){
                        System.out.println("no se encontraron reservas para ese cliente..");
                    }else{
                        System.out.println(muestraReservas);

                        System.out.println("Ingrese el nombre del alojamiento:");
                        stringAux = scan.nextLine();
                        ArrayList<Alojamiento> alojamientos = nuevoBooking.buscarAlojamientosPorNombre(stringAux);

                        while (alojamientos.isEmpty()) {
                            System.out.println("Nombre de alojamiento incorrecto, favor de elegir un nombre válido:");
                            stringAux = scan.nextLine();
                            alojamientos = nuevoBooking.buscarAlojamientosPorNombre(stringAux);
                        }

                        System.out.println("Alojamientos encontrados:");
                        for (int i = 0; i < alojamientos.size(); i++) {
                            System.out.println((i + 1) + ". " + alojamientos.get(i));
                        }

                        System.out.println("Ingrese el número del alojamiento que desea elegir:");
                        int opcionAlojamiento = scan.nextInt();
                        while (opcionAlojamiento < 1 || opcionAlojamiento > alojamientos.size()) {
                            System.out.println("Opción incorrecta. Favor de elegir un número válido:");
                            opcionAlojamiento = scan.nextInt();
                        }
                        alojamientoAux = alojamientos.get(opcionAlojamiento - 1);

                        int valoracion = 0;
                        do{
                            System.out.println("Ingrese una valoración (1-5):");
                            valoracion=scan.nextInt();
                        }while(valoracion<1 || valoracion>5);


                        String ticket = nuevoBooking.finalizarReserva(clienteAux, alojamientoAux, valoracion, motivo);
                        if(!ticket.equalsIgnoreCase("No se encontró la reserva para el cliente y/o alojamiento proporcionados.")){

                            System.out.println("Reserva finalizada y valoración agregada. Ticket a imprimir:" +"\n"+ ticket);
                        }else{
                            System.out.println(ticket);
                        }

                    }
                    break;

                case 5:
                    if(nuevoBooking.getHashMapCliente().estaVacio()){
                        System.out.println("Ninguna reserva en curso..");
                    }else{
                        System.out.println(nuevoBooking.getHashMapCliente());
                    }
                break;

                case 6:
                    if (nuevoBooking.getReservaHashSet().isEmpty()) {
                        System.out.println("no hay reservas en el sistema");
                    } else {
                        System.out.println("Reservas realizadas:");
                        System.out.println(nuevoBooking.mostrarSetReserva());
                    }
                    break;
                case 7:

                    if (nuevoBooking.getClienteHashSet().isEmpty()) {
                        System.out.println("No hay clientes cargados en el sitema");
                    } else {

                        System.out.println(nuevoBooking.getClienteHashSet().toString());
                    }
                    break;
                case 8:

                    if (nuevoBooking.getAlojamientoHashSet().isEmpty()) {
                        System.out.println("No hay alojamientos cargados en el sistema");
                    } else {

                        System.out.println(nuevoBooking.getAlojamientoHashSet().toString());
                    }

                    break;
                case 9:
                    menuModificacionCliente(nuevoBooking);
                    break;

                default:
                    System.out.println("ERROR, OPCION INVALIDA");
                    break;
            }
            System.out.println("Desea volver al menu? (si/no)");
            inicio = scan.next().charAt(0);
        }
        scan.close();
    }
    public static void preguntarEstadia(Cliente cliente) { //pregunta la estadia al usuario, cuando se quiera reservar
        int diaInicio = 0, diaFin = 0, mesInicio = 0, mesFin = 0, anioAux = 0;
        boolean añoInicio = false, añoFin = false;

        do{

            do {
                System.out.println("Ingrese el dia de inicio de la estadia: ");
                diaInicio = scan.nextInt();//el valor del booleano que nos dice si el año es el actual o el siguiente
                System.out.println("Ingrese el mes de inicio de la estadia: ");
                mesInicio = scan.nextInt();
                añoInicio=ingresarAnioValidado(anioAux); //asigna a los booleanos si la reserva es de este año o no

            } while (!validarIngresoFecha(diaInicio, mesInicio)); //este do-while va a realizarse siempre que el user ponga mal los datos, cuando los ponga bien lo dejara avanzar
            do{
                System.out.println("Ingrese el dia cuando se va a retirar de la propiedad:");
                diaFin = scan.nextInt();
                System.out.println("Ingrese el mes final de la estadia: ");
                mesFin = scan.nextInt();
                añoFin=ingresarAnioValidado(anioAux);
            } while (!validarIngresoFecha(diaFin, mesFin)); //lo mismo aca

        }while(!verificarFechas(diaInicio,diaFin,mesInicio,mesFin, añoInicio, añoFin)); //revisa que la fecha de inicio no sea posterior a la final
        //si logra pasar todos los filtros, le asigna la fecha :)
        cliente.asignarFecha(diaInicio, diaFin, mesInicio, mesFin, añoInicio, añoFin);

    }
    public static Alojamiento cargarAlojamiento(){
        Alojamiento nuevo = null;
        String tipoAux = "";
        int numeroPiso = 0, numeroHabitacion = 0, tamaño = 0;
        double precioXalojar = 0;
        String nombreAlojamiento, descripcion, direccion, zona, comentarios, serviciosExtras, tipoHabitacion;

        Scanner scan = new Scanner(System.in);

        System.out.println("Ingrese el nombre del alojamiento: ");
        nombreAlojamiento = scan.nextLine();
        System.out.println("Ingrese la direccion: ");
        direccion = scan.nextLine();
        System.out.println("Ingrese la zona: ");
        zona = scan.nextLine();
        System.out.println("Ingrese una breve descripcion: ");
        descripcion = scan.nextLine();
        System.out.println("Ingrese algun comentario a agregar: ");
        comentarios = scan.nextLine();
        System.out.println("Ingrese el precio por alojar: ");
        while (!scan.hasNextDouble()) {
            System.out.println("Por favor, ingrese un número válido para el precio por alojar:");
            scan.next();
        }
        precioXalojar = scan.nextDouble();
        scan.nextLine();
        do{
            System.out.println("Desea ingresar un Departamento o una Habitacion de Hotel? Ingrese alguna de esas dos palabras segun corresponda: (departamento/habitacion) ");
            tipoAux = scan.nextLine();
        }while (!tipoAux.equalsIgnoreCase("departamento") && !tipoAux.equalsIgnoreCase("Habitacion")); // tiene que elegir sí o sí uno de los dos

        if(tipoAux.equalsIgnoreCase("departamento")){
            System.out.println("Ingrese el tamaño del Departamento: ");
            while (!scan.hasNextInt()){
                System.out.println("Por favor, ingrese un número válido para el tamaño:");
                scan.next();
            }
            tamaño = scan.nextInt();
            scan.nextLine();
            System.out.println("Ingrese el numero de piso: ");
            while(!scan.hasNextInt()){
                System.out.println("Por favor, ingrese un número válido para el número de piso:");
                scan.next();
            }
            numeroPiso = scan.nextInt();
            scan.nextLine();
            System.out.println("Ingrese (si tiene) servicios Extras: ");
            serviciosExtras = scan.nextLine();
            nuevo = new Departamento(precioXalojar, descripcion, nombreAlojamiento, direccion, zona, comentarios, EstadoAlojamiento.DISPONIBLE, numeroPiso, tamaño, serviciosExtras);
        }else if(tipoAux.equalsIgnoreCase("Habitacion")){
            System.out.println("Ingrese el numero de Habitacion: ");
            while (!scan.hasNextInt()) {
                System.out.println("Por favor, ingrese un número válido para el número de habitación:");
                scan.next();
            }
            numeroHabitacion = scan.nextInt();
            scan.nextLine();

            System.out.println("Ingrese el tipo de habitacion: ");
            tipoHabitacion = scan.nextLine();
            System.out.println("Ingrese los servicios extras: ");
            serviciosExtras = scan.nextLine();
            nuevo = new HabitacionHotel(precioXalojar, descripcion, nombreAlojamiento, direccion, zona, comentarios, EstadoAlojamiento.DISPONIBLE, serviciosExtras, tipoHabitacion, numeroHabitacion);
        }

        return nuevo;
    }
    public static Cliente cargaCliente()
    {
        String nombre,apellido,email,medioDePago;

        int cantDePersonas=0;
        scan.nextLine(); //para que no se pisen los datos
        System.out.println("Ingrese nombre: ");
        nombre = scan.nextLine();
        System.out.println("Ingrese apellido:");
        apellido = scan.nextLine();

       do{

           System.out.println("Ingrese mail:");
           email = scan.nextLine();
       }while(!email.contains("@") || !email.contains(".com"));

        do{
            System.out.println("Ingrese medio de pago (Tarjeta / efectivo / transferencia):");
            medioDePago = scan.nextLine();
            //se va a ejecutar hasta que medio de pago sea uno de los que se pide
        }while(!medioDePago.equalsIgnoreCase("tarjeta") && !medioDePago.equalsIgnoreCase("efectivo") && !medioDePago.equalsIgnoreCase("transferencia"));

            do{
                System.out.println("Ingrese la cantidad de personas(no mas que 5):");
                cantDePersonas= scan.nextInt();

            }while(cantDePersonas<0 || cantDePersonas>5);


        Cliente nuevoCliente = new Cliente(nombre,apellido,email,medioDePago,cantDePersonas);
       // nuevoCliente.asignarFecha(diaInicio,diaFin,mesInicio,mesFin,añoInicio,añoFin); //su uso es para cuando se reserva
        return nuevoCliente;

    }

    public static boolean validarIngresoFecha(int dia, int mes){ //valida que el dia y el mes sean reales y correspondan a una fecha que exista
        boolean rta=true; //bandera
        int diaMaximoDelMes = 0;
        if(dia>31){
            System.out.println("ERROR, no existen meses mayores a 31 dias..");
            rta = false; //false= rebota y pide denuevo los datos
        }else if(mes<1 || mes>12){ //verifica que los meses sean validos
            System.out.println("ERROR, el dia no corresponde al mes..");
            rta=false;
        }else{
            if(mes==1||mes==3||mes==5||mes==7||mes==8||mes==10||mes==12)
            {
                diaMaximoDelMes = 31;
            }
            else if(mes ==4||mes==6||mes==9||mes==11)
            {
                diaMaximoDelMes = 30;
            }
            else {
                diaMaximoDelMes = 28;
            }
            if(dia<0 || dia>diaMaximoDelMes){ //si el dia es negativo o si supera el limite del mes
                System.out.println("ERROR, el dia no corresponde al mes o queres reservar un dia negativo(?");
                rta=false;
            }
        }
        return rta;
    }

    public static boolean ingresarAnioValidado(int anioAux){ //revisa que el usuario ingrese 1 o 2
        boolean año=true;
        do{
            System.out.println("si La reserva es para este año, ingrese 1, sino, ingrese 2");
            anioAux= scan.nextInt();
            if(anioAux==1){

                año = false;
            }
        }while(anioAux!=1 && anioAux!=2);
        return año;
    }

    public static boolean verificarFechas(int diaInicio, int diaFin, int mesInicio, int mesFin, boolean anioInicio, boolean aniofin){ //revisa que la fecha de inicio no sea mayor a la de egreso
        boolean rta=true;
        Date auxInicio =null, auxFin=null;
        if(anioInicio){
            auxInicio= new Date(LocalDate.now().getYear()-1899, mesInicio-1, diaInicio); //creo fechas con los datos que me dio el usuario
        }else{
            auxInicio= new Date(LocalDate.now().getYear()-1900, mesInicio-1, diaInicio);
        }
        if(aniofin){
             auxFin = new Date(LocalDate.now().getYear()-1899, mesFin-1, diaFin);
        }else{
            auxFin = new Date(LocalDate.now().getYear()-1900, mesFin-1, diaFin);
        }
            Date fechaActual= new Date(LocalDate.now().getYear()-1900, LocalDate.now().getMonthValue()-1, LocalDate.now().getDayOfMonth());

        if(auxInicio.before(fechaActual) && auxFin.before(fechaActual)){
            System.out.println("ERROR, no se pueden hacer reservas de un dia que ya paso..");
            rta=false;
        }
        if((auxInicio.after(auxFin))){ //si la fecha de inicio es posterior(after) a la fecha final, tira error
            System.out.println("ERROR, la fecha de inicio es despues de la fecha final");
            rta=false;
        }

        return rta;
    }

    public static void opcionesMenuCliente(){

        System.out.println("1) Modificar nombre");
        System.out.println("2) Modificar apellido");
        System.out.println("3) Modificar correo electrónico");
        System.out.println("4) Modificar medio de pago");
        System.out.println("5) Modificar cantidad de personas de viaje");
        System.out.println("6) Mostrar al cliente");
        System.out.println("0. Volver al menu principal");
    }

    public static void menuModificacionCliente(BookingACD nuevoBooking){

        System.out.println("Estos son los clientes cargados:");
        System.out.println(nuevoBooking.getClienteHashSet());
        System.out.print("Ingrese el nombre del cliente a buscar: ");
        String nombre = scan.next();
        System.out.print("Ingrese el apellido del cliente a buscar: ");
        String apellido = scan.next();
        Cliente cliente = nuevoBooking.buscarClientePorNombre(nombre, apellido);
        if(cliente==null){
            System.out.println("no se encontro al cliente");
        }else{
            int opcion=0;
          do{


            opcionesMenuCliente();
            System.out.println("Ingrese la opcion a realizar: ");
            opcion = scan.nextInt();
            switch(opcion) {
                case 1:
                    System.out.println("Ingrese el nuevo nombre ");
                    String nombreAux = scan.next();
                    cliente.setNombre(nombreAux);

                    break;
                case 2:
                    System.out.println("Ingrese el nuevo apellido");
                    String apellidoAux = scan.next();
                    cliente.setApellido(apellidoAux);
                    break;
                case 3:
                    String email;
                    do {
                        System.out.println("Ingrese el nuevo mail:");
                        email = scan.nextLine();
                        cliente.setCorreoElectronico(email);
                    } while (!email.contains("@") || !email.contains(".com"));

                    break;
                case 4:
                    String medioDePago;
                    do{
                        System.out.println("Ingrese el nuevo medio de pago (Tarjeta / efectivo / transferencia):");
                        medioDePago = scan.nextLine();
                        cliente.setMedioDePago(medioDePago);
                    }while(!medioDePago.equalsIgnoreCase("tarjeta") && !medioDePago.equalsIgnoreCase("efectivo") && !medioDePago.equalsIgnoreCase("transferencia"));

                    break;
                case 5:
                    int cantDePersonas;
                    do{
                        System.out.println("Ingrese la cantidad de personas(no mas que 5):");
                        cantDePersonas= scan.nextInt();
                        cliente.setCantPersonas(cantDePersonas);
                    }while(cantDePersonas<0 || cantDePersonas>5);

                    break;
                case 6:
                    System.out.println(cliente);
                case 0:
                    System.out.println("Volveras al menu principal");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
          }while(opcion!=0);

            nuevoBooking.jsonCliente();

        }









    }


}