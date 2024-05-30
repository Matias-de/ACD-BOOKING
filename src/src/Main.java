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
                    Alojamiento nuevoAlojamiento1 = cargarAlojamiento(); //aca habria que buscar la forma para que busque entre los clientes y alojamientos ya cargados
                    Cliente nuevoCliente2 = cargaCliente();
                    preguntarEstadia(nuevoCliente2);
                    System.out.println(nuevoBooking.reservar(nuevoCliente2,nuevoAlojamiento1));

                    break;
                case 4:
                    System.out.println(nuevoBooking.mostrarSetReserva());
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
        System.out.println("Ingrese medio de pago (Tarjeta o efectivo):");
        medioDePago = scan.nextLine();
        System.out.println("Ingrese la cantidad de personas:");//deberiamos de saber si hay niños
       cantDePersonas = scan.nextInt();
        Cliente nuevoCliente = new Cliente(nombre,apellido,email,medioDePago,cantDePersonas);
       // nuevoCliente.asignarFecha(diaInicio,diaFin,mesInicio,mesFin,añoInicio,añoFin); //su uso es para cuando se reserva
        return nuevoCliente;


    }
    public static boolean validarIngresoFecha(int dia, int mes){
        boolean rta=true; //bandera
        if(dia>31){
            System.out.println("ERROR, no existen dias mayores a 31..");
            rta = false;
        }else if(mes<1 || mes>12){ //verifica que los meses sean validos
            System.out.println("ERROR, el dia no corresponde al mes..");
            rta=false;
        }else{
        Date aux= new Date();
            LocalDate fecha= LocalDate.of((aux.getYear()+1900), mes, aux.getDay()); //local date permite ver la longitud de los meses
            int diaMaximoDelMes = fecha.lengthOfMonth(); //guardo esa longitud en una variable
            if(dia<0 || dia>diaMaximoDelMes){ //si el dia es negativo o si supera el limite del mes
                System.out.println("ERROR, el dia no corresponde al mes o queres reservar un dia negativo(?");
                rta=false;
                 }
        }

        return rta;
    }

    public static void ingresarAnioValidado(int anioAux, boolean añoInicio){
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

    public static boolean verificarFechas(int diaInicio, int diaFin, int mesInicio, int mesFin){
        boolean rta=true;
        Date auxInicio= new Date(LocalDate.now().getYear(), mesInicio, diaInicio);
        Date auxFin = new Date(LocalDate.now().getYear(), mesFin, diaFin);
        if((auxInicio.after(auxFin))){
            System.out.println("ERROR, la fecha de inicio es despues de la fecha final");
            rta=false;
        }

        return rta;
    }
    public static void preguntarEstadia(Cliente cliente) {
        int diaInicio = 0, diaFin = 0, mesInicio = 0, mesFin = 0, anioAux = 0;
        boolean añoInicio = false, añoFin = false;

        do{
        do {
            System.out.println("Ingrese el dia de inicio de la estadia: ");
            diaInicio = scan.nextInt();//el valor del booleano que nos dice si el año es el actual o el siguiente
            System.out.println("Ingrese el mes de inicio de la estadia: ");
            mesInicio = scan.nextInt();
            ingresarAnioValidado(anioAux, añoInicio);

        } while (!validarIngresoFecha(diaInicio, mesInicio)); //este do-while va a realizarse siempre que el user ponga mal los datos, cuando los ponga bien lo dejara avanzar
        do {
            System.out.println("Ingrese el dia cuando se va a retirar de la propiedad:");
            diaFin = scan.nextInt();
            System.out.println("Ingrese el mes final de la estadia: ");
            mesFin = scan.nextInt();
            ingresarAnioValidado(anioAux, añoFin);
        } while (!validarIngresoFecha(diaFin, mesFin)); //lo mismo aca

        }while(!verificarFechas(diaInicio,diaFin,mesInicio,mesFin));
        //si logra pasar todos los filtros, le asigna la fecha :)
        cliente.asignarFecha(diaInicio, diaFin, mesInicio, mesFin, añoInicio, añoFin);

    }
    public static void opcionesMenu(){ //aca pongan las opciones del menu
        System.out.println("BIENVENIDO!");
        System.out.println("Ingrese la opcion que desee realizar:");
        System.out.println("1)Cargar un nuevo cliente.");
        System.out.println("2)Cargar un alojamiento.");
        System.out.println("3)Realizar una reserva.");
        System.out.println("4)Mostrar Set de reservas.");
    }
}