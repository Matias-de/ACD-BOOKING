import Modelo.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.Scanner;

public class Main {
    static Scanner scan;

    public static void main(String[] args) {
        BookingACD nuevoBooking = new BookingACD();
        menu(nuevoBooking);

    }
    public static void opcionesMenu(){ //aca pongan las opciones del menu
        System.out.println("BIENVENIDO!");
        System.out.println("Ingrese la opcion que desee realizar:");
        System.out.println("1)Cargar un nuevo cliente.");
        System.out.println("2)Cargar un alojamiento.");
        System.out.println("3)Realizar una reserva.");
        System.out.println("4)Mostrar Las reservas del Sistema.");
        System.out.println("(5)Mostrar los Clientes del Sistema.");
        System.out.println("(6)Mostrar los Alojamientos del Sistema.");
    }
    public static void menu(BookingACD nuevoBooking){
        //declaracion de variables
        int opc = 0;
        char inicio = 's';

        scan = new Scanner(System.in);
        //texto en pantalla
        while ( inicio == 's')
        {
            opcionesMenu();
            opc = scan.nextInt(); // cargamos la opcion elegida por el administrador
            switch (opc)
            {
                case 1:
                    Cliente nuevoCliente = cargaCliente();
                    nuevoBooking.agregarCliente(nuevoCliente);
                    break;
                case 2:
                    Alojamiento nuevoAlojamiento = cargarAlojamiento();
                    nuevoBooking.agregarAlojamiento(nuevoAlojamiento);
                    break;
                case 3:
                    System.out.println("Desea usar los clientes ya cargados o cargar uno nuevo?: (1 para uno cargado, 2 para uno nuevo: ");
                    int auxInt= scan.nextInt();
                    if(auxInt==1){ //falta terminar se termino la clase juajua
                        System.out.println("Clientes ya cargados: ");
                        System.out.println(nuevoBooking.getClienteHashSet().toString());
                        //aca iria buscarPorCliente
                    }
                    Alojamiento nuevoAlojamiento1 = cargarAlojamiento(); //aca habria que buscar la forma para que busque entre los clientes y alojamientos ya cargados
                    Cliente nuevoCliente2 = cargaCliente();
                    preguntarEstadia(nuevoCliente2);
                    System.out.println(nuevoBooking.reservar(nuevoCliente2,nuevoAlojamiento1));

                    break;
                case 4:
                    System.out.println(nuevoBooking.mostrarSetReserva());
                    break;
                case 5:
                    System.out.println(nuevoBooking.getClienteHashSet().toString());
                    break;
                case 6:
                    System.out.println(nuevoBooking.getAlojamientoHashSet().toString());
                    break;

                default:
                    System.out.println("ERROR, OPCION INVALIDA");
                    break;
            }
            System.out.println("Desea volver al menu? (si/no)");
            inicio = scan.next().charAt(0);
        }
    }
    public static Alojamiento cargarAlojamiento()
    {
        System.out.println("Ingrese nombre");
        Alojamiento nuevo = new Departamento(20,"ads","gonza","22","corriente","nada",true,2,2,12,"no");
        return nuevo;
    }
    public static Cliente cargaCliente()
    {
        String nombre,apellido,email,medioDePago;

        int cantDePersonas=0;
        System.out.println("Ingrese nombre: ");
        scan.next(); //para que no se pisen los datos
        nombre = scan.nextLine();
        System.out.println("Ingrese apellido:");
        apellido = scan.nextLine();
        System.out.println("Ingrese mail:");
        email = scan.nextLine();
        do{
            System.out.println("Ingrese medio de pago (Tarjeta / efectivo / transferencia):");
            medioDePago = scan.nextLine();
            //se va a ejecutar hasta que medio de pago sea uno de los que se pide
        }while(!medioDePago.equalsIgnoreCase("tarjeta") && !medioDePago.equalsIgnoreCase("efectivo") && !medioDePago.equalsIgnoreCase("transferencia"));


            System.out.println("Ingrese la cantidad de personas:");//deberiamos de saber si hay niños
            cantDePersonas= scan.nextInt();


        Cliente nuevoCliente = new Cliente(nombre,apellido,email,medioDePago,cantDePersonas);
       // nuevoCliente.asignarFecha(diaInicio,diaFin,mesInicio,mesFin,añoInicio,añoFin); //su uso es para cuando se reserva
        return nuevoCliente;


    }
    public static boolean validarIngresoFecha(int dia, int mes){ //valida que el dia y el mes sean reales y correspondan a una fecha que exista
        boolean rta=true; //bandera
        if(dia>31){
            System.out.println("ERROR, no existen dias mayores a 31..");
            rta = false; //false= rebota y pide denuevo los datos
        }else if(mes<1 || mes>12){ //verifica que los meses sean validos
            System.out.println("ERROR, el dia no corresponde al mes..");
            rta=false;
        }else{

            LocalDate fecha= LocalDate.of(LocalDate.now().getYear(), mes, LocalDate.now().getDayOfMonth()); //guardo una fecha para revisar la longitud del mes
            int diaMaximoDelMes = fecha.lengthOfMonth(); //guardo esa longitud en una variable
            if(dia<0 || dia>diaMaximoDelMes){ //si el dia es negativo o si supera el limite del mes
                System.out.println("ERROR, el dia no corresponde al mes o queres reservar un dia negativo(?");
                rta=false;
                 }
        }

        return rta;
    }

    public static void ingresarAnioValidado(int anioAux, boolean añoInicio){ //revisa que el usuario ingrese 1 o 2
        do{
            System.out.println("si La reserva es para este año, ingrese 1, sino, ingrese 2");
            anioAux= scan.nextInt();
            if(anioAux==1){

                añoInicio = false;
            }else{
                añoInicio=true;
            }
        }while(anioAux!=1 && anioAux!=2);
    }

    public static boolean verificarFechas(int diaInicio, int diaFin, int mesInicio, int mesFin, boolean anioInicio, boolean aniofin){ //revisa que la fecha de inicio no sea mayor a la de egreso
        boolean rta=true;
        Date auxInicio =null, auxFin=null;
        if(anioInicio){
            auxInicio= new Date(LocalDate.now().getYear()+1, mesInicio, diaInicio); //creo fechas con los datos que me dio el usuario
        }else{
            auxInicio= new Date(LocalDate.now().getYear(), mesInicio, diaInicio);
        }
        if(aniofin){
             auxFin = new Date(LocalDate.now().getYear()+1, mesFin, diaFin);
        }else{
            auxFin = new Date(LocalDate.now().getYear(), mesFin, diaFin);
        }

        if((auxInicio.after(auxFin))){ //si la fecha de inicio es posterior(after) a la fecha final, tira error
            System.out.println("ERROR, la fecha de inicio es despues de la fecha final");
            rta=false;
        }

        return rta;
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
            ingresarAnioValidado(anioAux, añoInicio); //asigna a los booleanos si la reserva es de este año o no

        } while (!validarIngresoFecha(diaInicio, mesInicio)); //este do-while va a realizarse siempre que el user ponga mal los datos, cuando los ponga bien lo dejara avanzar
        do {
            System.out.println("Ingrese el dia cuando se va a retirar de la propiedad:");
            diaFin = scan.nextInt();
            System.out.println("Ingrese el mes final de la estadia: ");
            mesFin = scan.nextInt();
            ingresarAnioValidado(anioAux, añoFin);
        } while (!validarIngresoFecha(diaFin, mesFin)); //lo mismo aca

        }while(!verificarFechas(diaInicio,diaFin,mesInicio,mesFin, añoInicio, añoFin)); //revisa que la fecha de inicio no sea posterior a la final
        //si logra pasar todos los filtros, le asigna la fecha :)
        cliente.asignarFecha(diaInicio, diaFin, mesInicio, mesFin, añoInicio, añoFin);

    }

}