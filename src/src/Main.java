import Modelo.*;

import java.time.LocalDate;
import java.util.Calendar;
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
        if(mes<1 || mes>12){ //verifica que los meses sean validos
            System.out.println("ERROR, el dia no corresponde al mes..");
            rta=false;
        }else{
        Date aux= new Date();
        LocalDate fecha= LocalDate.of((aux.getYear()+1900), mes, dia); //local date permite ver la longitud de los meses
        int diaMaximoDelMes = fecha.lengthOfMonth(); //guardo esa longitud en una variable
        if(dia<0 || dia>diaMaximoDelMes){ //si el dia es negativo o si supera el limite del mes
            System.out.println("ERROR, el dia no corresponde al mes o queres reservar un dia negativo(?");
            rta=false;
        }
        }
        return rta;
    }

    public static void preguntarEstadia(Cliente cliente){
       int diaInicio=0,diaFin=0,mesInicio=0,mesFin=0;
        boolean añoInicio, añoFin;
        do{
            System.out.println("Ingrese cuando va a ingresar a la propiedad en orden de DIA, MES, Y AÑO(maximo 1 año):"); //como nosotros vamos a ser los que cargan clientes y demas vamos a asignar
            diaInicio = scan.nextInt();//el valor del booleano que nos dice si el año es el actual o el siguiente
            mesInicio = scan.nextInt();
            añoInicio = scan.nextBoolean();
        }while(!validarIngresoFecha(diaInicio, mesInicio)); //este do-while va a realizarse siempre que el user ponga mal los datos, cuando los ponga bien lo dejara avanzar
        do{
            System.out.println("Ingrese cuando se va a retirar de la propiedad:");
            diaFin = scan.nextInt();
            mesFin = scan.nextInt();
            añoFin = scan.nextBoolean();
        }while(!validarIngresoFecha(diaFin, mesFin)); //lo mismo aca
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