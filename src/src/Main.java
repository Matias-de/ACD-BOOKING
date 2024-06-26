import ClasesGenericas.GHashSet;
import Enumeraciones.EstadoAlojamiento;
import Modelo.*;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;

public class Main {
    static Scanner scan;

    public static void main(String[] args) {


        BookingACD nuevoBooking = new BookingACD();
        nuevoBooking.pasarArchiAMapa("ArchivoCliente"); // (se carga mapas)esta funcion se debe usar de manera unica con cualquier archivo ya sea cliente o alojamiento.nuevoBooking.jsonAJavaClientes(); //se carga set cliente
        nuevoBooking.jsonAJavaAlojamiento();//se carga set alojamiento
        nuevoBooking.jsonAJavaReserva();
        nuevoBooking.jsonAJavaClientes();
//        System.out.println(nuevoBooking.getHashMapAlojamiento());
//        System.out.println(nuevoBooking.getHashMapCliente());
        // System.out.println(nuevoBooking.getHashMapCliente());
        menu(nuevoBooking);
        nuevoBooking.guardarDatosEnArchi("ArchivoCliente", "ArchivoAlojamiento");//se guardan datos en el archivo
        nuevoBooking.jsonCliente();
        nuevoBooking.jsonAlojamiento();
        nuevoBooking.jsonReservas();
    }

    public static void opcionesMenu() { //aca pongan las opciones del menu
        System.out.println("BIENVENIDO!");
        System.out.println("Ingrese la opcion que desee realizar:");
        System.out.println("Carga:");
        System.out.println("\t1)Cargar un nuevo cliente.");
        System.out.println("\t2)Cargar un alojamiento.");
        System.out.println("\t3)Realizar una reserva.");
        System.out.println("\t4)Finalizar una reserva.");
        System.out.println("Mostrado:");
        System.out.println("\t5)Mostrar las reservas actuales");
        System.out.println("\t6)Mostrar los Clientes del Sistema.");
        System.out.println("\t7)Mostrar los Alojamientos del Sistema.");
        System.out.println("\t8)Mostrar Las reservas alguna vez ocurridas en el Sistema.");
        System.out.println("\t9)Mostrar las reservas por mes");
        System.out.println("\t10)Mostrar las reservas de un cliente especifico");
        System.out.println("\t11)Mostrar las reservas de un alojamiento especifico");
        System.out.println("Modificaciones:");
        System.out.println("\t12)Ingresar al menu de modificacion de Clientes");
        System.out.println("\t13)Ingresar al menu de modificacion de alojamientos");
        System.out.println("Estadisticas:");
        System.out.println("\t14)Mostrar el alojamiento con la mayor cantidad de Reservas");
        System.out.println("\t15)Mostrar el alojamiento mas caro");
        System.out.println("\t16)Mostrar los alojamientos por valoración especifica");
        System.out.println("\t17)Mostrar los alojamientos por valoración de mayor a menor");
        System.out.println("\t18)Mostrar total recaudado");
        System.out.println("---------------------------");
        System.out.println("\t0)Guardar Cambios.");


    }


    public static void menu(BookingACD nuevoBooking) {
        //declaracion de variables
        int opc = 0, auxInt = 0;
        char inicio = 's';
        Cliente clienteAux = null;
        Alojamiento alojamientoAux = null;
        String stringAux = "";
        scan = new Scanner(System.in);
        //texto en pantalla
        while (inicio == 's') {
            opcionesMenu();
            opc = scan.nextInt(); // cargamos la opcion elegida por el administrador
            switch (opc) {
                case 0:
                    nuevoBooking.guardarDatosEnArchi("ArchivoCliente", "ArchivoAlojamiento");//se guardan datos en el archivo
                    nuevoBooking.jsonCliente();
                    nuevoBooking.jsonAlojamiento();
                    nuevoBooking.jsonReservas();
                    break;
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
                    String apellidoAux = "";
                    if (!nuevoBooking.getClienteHashSet().isEmpty()) {
                        do {
                            System.out.println("Desea usar los clientes ya cargados o cargar uno nuevo?: (1 para uno cargado, 2 para uno nuevo)");
                            auxInt = scan.nextInt();
                            scan.nextLine();

                            if (auxInt == 1) {
                                boolean clienteEncontrado = false;

                                while (!clienteEncontrado) {
                                    System.out.println("Clientes ya cargados: ");
                                    System.out.println(nuevoBooking.getClienteHashSet().toString());

                                    System.out.println("Ingrese el nombre del cliente que quiere elegir: ");
                                    stringAux = scan.nextLine();

                                    System.out.println("Ingrese el apellido del cliente: ");
                                    apellidoAux = scan.nextLine();

                                    clienteAux = nuevoBooking.buscarClientePorNombre(stringAux, apellidoAux);

                                    if (clienteAux != null) {
                                        clienteEncontrado = true;
                                        System.out.println("Cliente encontrado! Se le asignará la reserva al cliente: " + clienteAux);
                                        preguntarEstadia(clienteAux);
                                    } else {
                                        System.out.println("Nombre de cliente incorrecto, por favor elija un nombre válido:");
                                    }
                                }
                            } else if (auxInt == 2) {
                                System.out.println("Carga de cliente nuevo:");
                                clienteAux = cargaCliente();
                                nuevoBooking.agregarCliente(clienteAux);
                                preguntarEstadia(clienteAux);

                            } else {
                                System.out.println("Error al ingresar, favor de ingresar solamente el boton 1 o el 2.");
                                auxInt = scan.nextInt();
                            }
                        } while (auxInt != 1 && auxInt != 2);

                    } else {
                        System.out.println("No hay clientes cargados, cargue uno para poder continuar.");
                        clienteAux = cargaCliente();
                        nuevoBooking.agregarCliente(clienteAux);
                        preguntarEstadia(clienteAux);
                    }

                    if (!nuevoBooking.getAlojamientoHashSet().isEmpty()) {
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
                    System.out.println(nuevoBooking.reservar(clienteAux, alojamientoAux));
                    /*cambiar*/ // System.out.println("(True=exitoso)/(False=no se pudo reservar)--->Rta:"+nuevoBooking.reservar(clienteAux,alojamientoAux));
                    break;
                case 4:
                    String motivo = "", apellido = "";
                    System.out.println("Estas son las reservas que terminan hoy!: \n" + nuevoBooking.mostrarReservasAPuntoDeTerminar());
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
                    apellido = scan.nextLine();
                    while (nuevoBooking.buscarClientePorNombre(stringAux, apellido) == null) {
                        System.out.println("Nombre de cliente incorrecto, favor de elegir un nombre válido:");
                        stringAux = scan.nextLine();
                    }
                    clienteAux = nuevoBooking.buscarClientePorNombre(stringAux, apellido);
                    String muestraReservas = nuevoBooking.getHashMapCliente().mostrarReservasPorClave(clienteAux);
                    if (muestraReservas.equalsIgnoreCase("No se encontraron reservas para lo ingresado.")) {
                        System.out.println("no se encontraron reservas para ese cliente..");
                    } else {
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
                        do {
                            System.out.println("Ingrese una valoración (1-5):");
                            valoracion = scan.nextInt();
                        } while (valoracion < 1 || valoracion > 5);


                        String ticket = nuevoBooking.finalizarReserva(clienteAux, alojamientoAux, valoracion, motivo);
                        if (!ticket.equalsIgnoreCase("No se encontró la reserva para el cliente y/o alojamiento proporcionados.")) {

                            System.out.println("Reserva finalizada y valoración agregada. Ticket a imprimir:" + "\n" + ticket);
                        } else {
                            System.out.println(ticket);
                        }

                    }
                    break;

                case 5:
                    if (nuevoBooking.getHashMapCliente().estaVacio()) {
                        System.out.println("Ninguna reserva en curso..");
                    } else {
                        System.out.println(nuevoBooking.getHashMapCliente());
                    }
                    break;

                case 6:

                    if (nuevoBooking.getClienteHashSet().isEmpty()) {
                        System.out.println("No hay clientes cargados en el sitema");
                    } else {

                        System.out.println(nuevoBooking.getClienteHashSet().toString());
                    }
                    break;
                case 7:

                    if (nuevoBooking.getAlojamientoHashSet().isEmpty()) {
                        System.out.println("No hay alojamientos cargados en el sistema");
                    } else {

                        System.out.println(nuevoBooking.getAlojamientoHashSet().toString());
                    }
                    break;
                case 8:
                    if (nuevoBooking.getReservaHashSet().isEmpty()) {
                        System.out.println("no hay reservas en el sistema");
                    } else {
                        System.out.println("Reservas realizadas:");
                        System.out.println(nuevoBooking.mostrarSetReserva());
                    }
                    break;
                case 9:
                    if (nuevoBooking.getReservaHashSet().isEmpty()) {
                        System.out.println("NO HAY RESERVAS");
                    } else {
                        System.out.println("Ingrese el mes que quiere buscar \n");
                        System.out.println("1.- ENERO | 2.-FEBRERO | 3.-MARZO | 4.- ABRIL |5.-MAYO |6.-JUNIO |7.-JULIO|8.-AGOSTO|9.-SEPTIEMBRE|10.-OCTUBRE|11.- NOVIEMBRE| 12.-DICIEMBRE");
                        int mes = scan.nextInt();
                        switch (mes) {

                            case 1:
                                System.out.println("RESERVAS DE ENERO");
                                System.out.println(nuevoBooking.mostrarReservaPorMes(mes));
                                break;
                            case 2:
                                System.out.println("RESERVAS DE FEBRERO");
                                System.out.println(nuevoBooking.mostrarReservaPorMes(mes));
                                break;
                            case 3:
                                System.out.println("RESERVAS DE MARZO");
                                System.out.println(nuevoBooking.mostrarReservaPorMes(mes));
                                break;
                            case 4:
                                System.out.println("RESERVAS DE ABRIL");
                                System.out.println(nuevoBooking.mostrarReservaPorMes(mes));
                                break;
                            case 5:
                                System.out.println("RESERVAS DE MAYO");
                                System.out.println(nuevoBooking.mostrarReservaPorMes(mes));
                                break;
                            case 6:
                                System.out.println("RESERVAS DE JUNIO");
                                System.out.println(nuevoBooking.mostrarReservaPorMes(mes));
                                break;
                            case 7:
                                System.out.println("RESERVAS DE JULIO");
                                System.out.println(nuevoBooking.mostrarReservaPorMes(mes));
                                break;
                            case 8:
                                System.out.println("RESERVAS DE AGOSTO");
                                System.out.println(nuevoBooking.mostrarReservaPorMes(mes));
                                break;
                            case 9:
                                System.out.println("RESERVAS DE SEPTIEMBRE");
                                System.out.println(nuevoBooking.mostrarReservaPorMes(mes));
                                break;
                            case 10:
                                System.out.println("RESERVAS DE OCTUBRE");
                                System.out.println(nuevoBooking.mostrarReservaPorMes(mes));
                                break;
                            case 11:
                                System.out.println("RESERVAS DE NOVIEMBRE");
                                System.out.println(nuevoBooking.mostrarReservaPorMes(mes));
                                break;
                            case 12:
                                System.out.println("RESERVAS DE DICIEMBRE");
                                System.out.println(nuevoBooking.mostrarReservaPorMes(mes));
                                break;
                            default:
                                System.out.println("ERROR, OPCION INVALIDA");
                                break;
                        }
                    }
                    break;
                case 10:
                    System.out.println("Ingrese el nombre del cliente a buscar:");
                    scan.nextLine();
                    stringAux = scan.nextLine();
                    System.out.println("Ingrese el apellido del cliente: ");
                    apellidoAux = scan.nextLine();
                    clienteAux = nuevoBooking.buscarClientePorNombre(stringAux, apellidoAux);
                    if (clienteAux == null) {
                        System.out.println("No se encontro al cliente.");
                    } else {
                        System.out.println(nuevoBooking.getHashMapCliente().mostrarReservasPorClave(clienteAux));
                    }
                    break;
                case 11:
                    int opAlojamiento = 0;
                    System.out.println("Ingrese el nombre del alojamiento a buscar: ");
                    scan.nextLine();
                    stringAux = scan.nextLine();
                    ArrayList<Alojamiento> alojamientos = nuevoBooking.buscarAlojamientosPorNombre(stringAux);

                    if(alojamientos.isEmpty()){
                        System.out.println("Alojamiento no encontrado.");
                    }else{
                        System.out.println("Alojamientos encontrados:");
                        for (int i = 0; i < alojamientos.size(); i++) {
                            System.out.println((i + 1) + ". " + alojamientos.get(i));
                        }
                        System.out.print("Ingrese el numero del alojamiento que desee: ");
                        opAlojamiento = scan.nextInt();
                        scan.nextLine();

                        if (opAlojamiento < 1 || opAlojamiento > alojamientos.size()) {
                            System.out.println("Opción inválida, REINTENTE");
                        } else {
                            Alojamiento alojamiento = alojamientos.get(opAlojamiento - 1);
                            System.out.println(nuevoBooking.getHashMapAlojamiento().mostrarReservasPorClave(alojamiento));
                        }
                    }
                    break;

                case 12:
                    menuModificacionCliente(nuevoBooking);
                    break;
                case 13:
                    menuModificacionAlojamiento(nuevoBooking);
                    break;
                case 14:
                    alojamientoAux = nuevoBooking.buscarAlojamientoConMasReservas();
                    if (alojamientoAux == null) {
                        System.out.println("No se encontraron alojamientos");
                    } else {
                        System.out.println("ALOJAMIENTO ENCONTRADO: \n" + alojamientoAux + "\n con cantidad de reservas: " + alojamientoAux.getCantReservas());
                    }
                    break;
                case 15:
                    alojamientoAux = nuevoBooking.buscarAlojamientoMasCaro();
                    if (alojamientoAux == null) {
                        System.out.println("No se encontraron alojamientos");
                    } else {
                        System.out.println("ALOJAMIENTO ENCONTRADO: \n" + alojamientoAux + "\n con precio: " + alojamientoAux.getPrecioXAlojar());
                    }
                    break;

                case 16:
                    double valoracion = 0;
                    do {
                        System.out.println("Ingrese la valoración que desea ver (0/5)");
                        valoracion = scan.nextDouble();
                    }while(valoracion<1 ||valoracion>5);
                    if (nuevoBooking.getAlojamientoHashSet().isEmpty())
                    {
                        System.out.println("No hay alojamientos \n");
                    }
                    else {
                        System.out.println(nuevoBooking.mostrarSetAlojamientoXValoracion(valoracion));
                    }
                    break;
                case 17:

                    if (nuevoBooking.getAlojamientoHashSet().isEmpty()){
                        System.out.println("NO HAY ALOJAMIENTOS");
                    }
                    else
                    {
                        System.out.println("MOSTRANDO ALOJAMIENTOS DE MAYOR VALORACION A MENOR");
                        System.out.println(nuevoBooking.mostrarAlojamientoDeMayorValAMenorVal());
                    }
                    break;
                case 18:

                    if(nuevoBooking.getReservaHashSet().isEmpty())
                    {
                        System.out.println("NO RESERVAS");
                    }
                    else {

                        System.out.println("EL TOTAL RECAUDADO ES: [ "+nuevoBooking.devolverTotalRecaudado()+" ]");
                    }
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

        do {

            do {
                System.out.println("Ingrese el dia de inicio de la estadia: ");
                diaInicio = scan.nextInt();//el valor del booleano que nos dice si el año es el actual o el siguiente
                System.out.println("Ingrese el mes de inicio de la estadia: ");
                mesInicio = scan.nextInt();
                añoInicio = ingresarAnioValidado(anioAux); //asigna a los booleanos si la reserva es de este año o no

            } while (!validarIngresoFecha(diaInicio, mesInicio)); //este do-while va a realizarse siempre que el user ponga mal los datos, cuando los ponga bien lo dejara avanzar
            do {
                System.out.println("Ingrese el dia cuando se va a retirar de la propiedad:");
                diaFin = scan.nextInt();
                System.out.println("Ingrese el mes final de la estadia: ");
                mesFin = scan.nextInt();
                añoFin = ingresarAnioValidado(anioAux);
            } while (!validarIngresoFecha(diaFin, mesFin)); //lo mismo aca

        } while (!verificarFechas(diaInicio, diaFin, mesInicio, mesFin, añoInicio, añoFin)); //revisa que la fecha de inicio no sea posterior a la final
        //si logra pasar todos los filtros, le asigna la fecha :)
        cliente.asignarFecha(diaInicio, diaFin, mesInicio, mesFin, añoInicio, añoFin);

    }

    public static Alojamiento cargarAlojamiento() {
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
        do {
            System.out.println("Desea ingresar un Departamento o una Habitacion de Hotel? Ingrese alguna de esas dos palabras segun corresponda: (departamento/habitacion) ");
            tipoAux = scan.nextLine();
        } while (!tipoAux.equalsIgnoreCase("departamento") && !tipoAux.equalsIgnoreCase("Habitacion")); // tiene que elegir sí o sí uno de los dos

        if (tipoAux.equalsIgnoreCase("departamento")) {
            System.out.println("Ingrese el tamaño del Departamento: ");
            while (!scan.hasNextInt()) {
                System.out.println("Por favor, ingrese un número válido para el tamaño:");
                scan.next();
            }
            tamaño = scan.nextInt();
            scan.nextLine();
            System.out.println("Ingrese el numero de piso: ");
            while (!scan.hasNextInt()) {
                System.out.println("Por favor, ingrese un número válido para el número de piso:");
                scan.next();
            }
            numeroPiso = scan.nextInt();
            scan.nextLine();
            System.out.println("Ingrese (si tiene) servicios Extras: ");
            serviciosExtras = scan.nextLine();
            nuevo = new Departamento(precioXalojar, descripcion, nombreAlojamiento, direccion, zona, comentarios, EstadoAlojamiento.DISPONIBLE, numeroPiso, tamaño, serviciosExtras);
        } else if (tipoAux.equalsIgnoreCase("Habitacion")) {
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

    public static Cliente cargaCliente() {
        String nombre, apellido, email, medioDePago;

        int cantDePersonas = 0;
        scan.nextLine(); //para que no se pisen los datos
        System.out.println("Ingrese nombre: ");
        nombre = scan.nextLine();
        System.out.println("Ingrese apellido:");
        apellido = scan.nextLine();

        do {

            System.out.println("Ingrese mail:");
            email = scan.nextLine();
        } while (!email.contains("@") || !email.contains(".com"));

        do {
            System.out.println("Ingrese medio de pago (Tarjeta / efectivo / transferencia):");
            medioDePago = scan.nextLine();
            //se va a ejecutar hasta que medio de pago sea uno de los que se pide
        } while (!medioDePago.equalsIgnoreCase("tarjeta") && !medioDePago.equalsIgnoreCase("efectivo") && !medioDePago.equalsIgnoreCase("transferencia"));

        do {
            System.out.println("Ingrese la cantidad de personas(no mas que 5):");
            cantDePersonas = scan.nextInt();

        } while (cantDePersonas < 0 || cantDePersonas > 5);


        Cliente nuevoCliente = new Cliente(nombre, apellido, email, medioDePago, cantDePersonas);
        // nuevoCliente.asignarFecha(diaInicio,diaFin,mesInicio,mesFin,añoInicio,añoFin); //su uso es para cuando se reserva
        return nuevoCliente;

    }

    public static boolean validarIngresoFecha(int dia, int mes) {
        boolean flag = true; //bandera
        if (mes < 1 || mes > 12) { //si el mes es invalido tira mensaje de error
            System.out.println("ERROR: El mes debe estar entre 1 y 12.");
            flag = false;
        } else if (dia < 1 || dia > 31) { //sino si el mes esta bien pero el dia no corresponde tambien tira error
            System.out.println("ERROR: El día debe estar entre 1 y 31.");
            flag = false;
        } else {
            //si pasa por aca crea un YearMonth-->Api que toma a las fechas por mes y año sin dia en especifico (ponele mayo del 2033
            YearMonth yearMonth = YearMonth.of(LocalDate.now().getYear(), mes); //cuando lo creo le asigno el año del localdate y el mes enviado por el usuario
            int maxDaysInMonth = yearMonth.lengthOfMonth(); //saco la longitud maxima que tiene ese mes

            if (dia > maxDaysInMonth) { //si el dia es mayor tira error
                System.out.println("ERROR: El dia " + dia + " no corresponde al mes " + mes);//--> chiche
                flag = false;
            }
        }

        return flag;
    }

    public static boolean ingresarAnioValidado(int anioAux) { //revisa que el usuario ingrese 1 o 2
        boolean año = true;
        do {
            System.out.println("si La reserva es para este año, ingrese 1, sino, ingrese 2");
            anioAux = scan.nextInt();
            if (anioAux == 1) {

                año = false;
            }
        } while (anioAux != 1 && anioAux != 2);
        return año;
    }

    public static boolean verificarFechas(int diaInicio, int diaFin, int mesInicio, int mesFin, boolean anioInicio, boolean aniofin) { //revisa que la fecha de inicio no sea mayor a la de egreso
        boolean rta = true;
        Date auxInicio = null, auxFin = null;
        if (anioInicio) {
            auxInicio = new Date(LocalDate.now().getYear() - 1899, mesInicio - 1, diaInicio); //creo fechas con los datos que me dio el usuario
        } else {
            auxInicio = new Date(LocalDate.now().getYear() - 1900, mesInicio - 1, diaInicio);
        }
        if (aniofin) {
            auxFin = new Date(LocalDate.now().getYear() - 1899, mesFin - 1, diaFin);
        } else {
            auxFin = new Date(LocalDate.now().getYear() - 1900, mesFin - 1, diaFin);
        }
        Date fechaActual = new Date(LocalDate.now().getYear() - 1900, LocalDate.now().getMonthValue() - 1, LocalDate.now().getDayOfMonth());

        if (auxInicio.before(fechaActual) && auxFin.before(fechaActual)) {
            System.out.println("ERROR, no se pueden hacer reservas de un dia que ya paso..");
            rta = false;
        }
        if ((auxInicio.after(auxFin))) { //si la fecha de inicio es posterior(after) a la fecha final, tira error
            System.out.println("ERROR, la fecha de inicio es despues de la fecha final");
            rta = false;
        }

        return rta;
    }

    public static void opcionesMenuCliente() {

        System.out.println("1) Modificar nombre");
        System.out.println("2) Modificar apellido");
        System.out.println("3) Modificar correo electrónico");
        System.out.println("4) Modificar medio de pago");
        System.out.println("5) Modificar cantidad de personas de viaje");
        System.out.println("6) Mostrar al cliente");
        System.out.println("0. Volver al menu principal");
    }

    public static void menuModificacionCliente(BookingACD nuevoBooking) {

        System.out.println("Estos son los clientes cargados:");
        System.out.println(nuevoBooking.getClienteHashSet());
        System.out.print("Ingrese el nombre del cliente a buscar: ");
        String nombre = scan.next();
        System.out.print("Ingrese el apellido del cliente a buscar: ");
        String apellido = scan.next();
        Cliente cliente = nuevoBooking.buscarClientePorNombre(nombre, apellido);
        if (cliente == null) {
            System.out.println("no se encontro al cliente");
        } else {
            int opcion = 0;
            do {


                opcionesMenuCliente();
                System.out.println("Ingrese la opcion a realizar: ");
                opcion = scan.nextInt();
                switch (opcion) {
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
                        do {
                            System.out.println("Ingrese el nuevo medio de pago (Tarjeta / efectivo / transferencia):");
                            medioDePago = scan.nextLine();
                            cliente.setMedioDePago(medioDePago);
                        } while (!medioDePago.equalsIgnoreCase("tarjeta") && !medioDePago.equalsIgnoreCase("efectivo") && !medioDePago.equalsIgnoreCase("transferencia"));

                        break;
                    case 5:
                        int cantDePersonas;
                        do {
                            System.out.println("Ingrese la cantidad de personas(no mas que 5):");
                            cantDePersonas = scan.nextInt();
                            cliente.setCantPersonas(cantDePersonas);
                        } while (cantDePersonas < 0 || cantDePersonas > 5);

                        break;
                    case 6:
                        System.out.println(cliente);
                    case 0:
                        System.out.println("Volveras al menu principal");
                        break;
                    default:
                        System.out.println("Opción no válida.");
                }
            } while (opcion != 0);

            nuevoBooking.jsonCliente();

        }


    }

    public static void opcionesMenuAlojamiento(Alojamiento alojamiento) {

        System.out.println("1) Modificar nombre");
        System.out.println("2) Modificar valoración");
        System.out.println("3) Modificar descripción");
        System.out.println("4) Modificar dirección");
        System.out.println("5) Modificar zona");
        System.out.println("6) Modificar cantidad de reservas");
        System.out.println("7) Modificar precio por alojar");
        System.out.println("8) Modificar comentarios");
        System.out.println("9) Modificar estado");
        if (alojamiento instanceof Departamento) {
            System.out.println("10)Modificar el numero de piso");
            System.out.println("11)Modificar tamaño del Departamento");
            System.out.println("12)Modificar servicios extra");
        } else if (alojamiento instanceof HabitacionHotel) {
            System.out.println("10)Modificar el numero de habitacion");
            System.out.println("11)Modificar el tipo de habitacion");
            System.out.println("12)Modificar servicios extra");
        }

        System.out.println("13)Mostrar el alojamiento y sus cambios");
        System.out.println("0) Volver al menú principal");

    }

    public static void menuModificacionAlojamiento(BookingACD nuevoBooking) {
        int opcionAlojamiento = 0, opSwitch=0;
        Alojamiento alojamiento = null;
        System.out.println("Alojamientos disponibles: \n" + nuevoBooking.getAlojamientoHashSet());
        System.out.print("Ingrese el nombre del alojamiento a buscar: ");
        scan.nextLine();
        String nombre = scan.nextLine();
        ArrayList<Alojamiento> alojamientos = nuevoBooking.buscarAlojamientosPorNombre(nombre);

        if (alojamientos.isEmpty()) {
            System.out.println("Alojamiento no encontrado.");
        } else {
            System.out.println("Alojamientos encontrados:");
            for (int i = 0; i < alojamientos.size(); i++) {
                System.out.println((i + 1) + ". " + alojamientos.get(i));
            }
            System.out.print("Seleccione un alojamiento por su numero: ");
            opcionAlojamiento = scan.nextInt();
            scan.nextLine();

            if (opcionAlojamiento < 1 || opcionAlojamiento > alojamientos.size()) {
                System.out.println("Opción invalida, REINTENTE");
            } else {
                alojamiento = alojamientos.get(opcionAlojamiento - 1);
            }
        }

        if (alojamiento != null) {
            do {
                opcionesMenuAlojamiento(alojamiento);
                System.out.print("Ingrese la opcion ahora: ");
                opSwitch = scan.nextInt();
                scan.nextLine();
                switch (opSwitch) {
                    case 1:
                        System.out.print("Ingrese el nuevo nombre: ");
                        String nombreAux = scan.nextLine();
                        alojamiento.setNombre(nombreAux);
                        break;
                    case 2:
                        double valoracion;
                        do {
                            System.out.print("Ingrese la nueva valoración (0 a 5): ");
                            valoracion = scan.nextDouble();
                            scan.nextLine();
                        } while (valoracion < 0 || valoracion > 5);
                        alojamiento.setValoracion(valoracion);
                        break;
                    case 3:
                        System.out.print("Ingrese la nueva descripción: ");
                        String descripcion = scan.nextLine();
                        alojamiento.setDescripcion(descripcion);
                        break;
                    case 4:
                        System.out.print("Ingrese la nueva dirección: ");
                        String direccion = scan.nextLine();
                        alojamiento.setDireccion(direccion);
                        break;
                    case 5:
                        System.out.print("Ingrese la nueva zona: ");
                        String zona = scan.nextLine();
                        alojamiento.setZona(zona);
                        break;
                    case 6:
                        System.out.print("Ingrese la nueva cantidad de reservas: ");
                        int cantReservas = scan.nextInt();
                        scan.nextLine();
                        alojamiento.setCantReservas(cantReservas);
                        break;
                    case 7:
                        System.out.print("Ingrese el nuevo precio por alojar: ");
                        double precio = scan.nextDouble();
                        scan.nextLine();
                        alojamiento.setPrecioXAlojar(precio);
                        break;
                    case 8:
                        System.out.print("Ingrese los nuevos comentarios: ");
                        String comentarios = scan.nextLine();
                        alojamiento.setComentarios(comentarios);
                        break;
                    case 9:
                        String estadoAux;
                        do {
                            System.out.println("Ingrese el nuevo estado del alojamiento: (DISPONIBLE, RESERVADO, MANTENIMIENTO)");
                            estadoAux = scan.nextLine();
                        } while (!estadoAux.equalsIgnoreCase("DISPONIBLE") && !estadoAux.equalsIgnoreCase("MANTENIMIENTO") && !estadoAux.equalsIgnoreCase("RESERVADO"));
                        if (estadoAux.equalsIgnoreCase("DISPONIBLE")) {
                            alojamiento.setEstado(EstadoAlojamiento.DISPONIBLE);
                        } else if (estadoAux.equalsIgnoreCase("MANTENIMIENTO")) {
                            alojamiento.setEstado(EstadoAlojamiento.MANTENIMIENTO);
                        } else {
                            alojamiento.setEstado(EstadoAlojamiento.RESERVADO);
                        }
                        break;
                    case 10:
                        if (alojamiento instanceof Departamento) {
                            System.out.print("Ingrese el nuevo numero de piso: ");
                            int numeroPiso = scan.nextInt();
                            scan.nextLine();
                            ((Departamento) alojamiento).setNumeroPiso(numeroPiso);
                        } else {
                            System.out.println("Ingrese el nuevo numero de Habitacion: ");
                            int nroHabitacion = scan.nextInt();
                            scan.nextLine();
                            ((HabitacionHotel) alojamiento).setNumeroHabitacion(nroHabitacion);
                        }
                        break;
                    case 11:
                        if (alojamiento instanceof Departamento) {
                            System.out.println("Ingrese el nuevo tamaño del departamento: ");
                            int auxTamaño = scan.nextInt();
                            scan.nextLine();
                            ((Departamento) alojamiento).setTamañoDepartamento(auxTamaño);
                        } else {
                            System.out.println("Ingrese el nuevo tipo de Habitacion: ");
                            String tipoAux = scan.next();
                            ((HabitacionHotel) alojamiento).setTipoHabitacion(tipoAux);
                        }
                        break;
                    case 12:
                        System.out.println("Ingrese el/los nuevos servicios extra: ");
                        String servicios = scan.next();
                        scan.nextLine();
                        if (alojamiento instanceof Departamento) {
                            ((Departamento) alojamiento).setServicioExtra(servicios);
                        } else {
                            ((HabitacionHotel) alojamiento).setServicios(servicios);
                        }
                        break;
                    case 13:
                        if (alojamiento instanceof Departamento) {
                            System.out.println(((Departamento) alojamiento).listar());
                        } else if (alojamiento instanceof HabitacionHotel) {
                            System.out.println(((HabitacionHotel) alojamiento).listar());
                        }
                        break;
                    case 0:
                        System.out.println("Volveras al menu principal");
                        break;
                    default:
                        System.out.println("ERROR, OPCION INVALIDA; REINTENTE..");
                        break;
                }
            } while (opSwitch != 0);

            nuevoBooking.jsonAlojamiento();
        }
    }


}