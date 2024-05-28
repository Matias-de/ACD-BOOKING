import Modelo.Alojamiento;
import Modelo.BookingACD;
import Modelo.Cliente;
import Modelo.Reserva;

import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class Main {
    static Scanner scan;
    public static void main(String[] args) {
        //declaracion de variables
        int opc = 0;
        BookingACD nuevoBooking = new BookingACD();
        scan = new Scanner(System.in);
        //texto en pantalla
        System.out.println("BIENVENIDO!");
        System.out.println("Ingrese la opcion que desee realizar:");
        System.out.println("1)Cargar un nuevo cliente.");
        opc = scan.nextInt(); // cargamos la opcion elegida por el administrador
        switch (opc)
        {
            case 1: // me gustaria que la opcion 1 te deje cargar clientes, la 2 alojamientos y asi... //para eso estan los hashSet.

//                Cliente nuevoCliente = cargaCliente();
//                Alojamiento nuevoAlojamiento = cargarAlojamiento();
//                //Reserva nuevaReserva = nuevoBooking.reservar(nuevoCliente,nuevoAlojamiento); //necesito q esta funcion retorne null o la reserva si se pudo realizar //para eso esta el booleano, no podes retornar una reserva que nunca se creo
//
//                nuevoBooking.agregarCliente(nuevoCliente,nuevaReserva); //necesito la reserva para mandarla a esta funcion //lo hace reservar
//                nuevoBooking.agregarAlojamiento(nuevoAlojamiento,nuevaReserva);// necesito la reserva para mandarla a esta funcion //lo hace reservar


        }
    }
    public static Alojamiento cargarAlojamiento() //soy andru me falta terminar a la noche lo termino //tranqui amigo pero acordate que se cargan en el hashset
    {
        System.out.println("Ingrese nombre");
    }
    public static Cliente cargaCliente()
    {
        String nombre,apellido,email,medioDePago;
        int cantDePersonas=0,diaInicio=0,diaFin=0,mesInicio=0,mesFin=0;
        boolean añoInicio, añoFin;

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
        System.out.println("Ingrese cuando va a ingresar a la propiedad en orden de DIA, MES, Y AÑO(maximo 1 año):"); //como nosotros vamos a ser los que cargan clientes y demas vamos a asignar
        diaInicio = scan.nextInt();                                                 //el valor del booleano que nos dice si el año es el actual o el siguiente
        mesInicio = scan.nextInt();
        añoInicio = scan.nextBoolean();

        System.out.println("Ingrese cuando se va a retirar de la propiedad:");
        diaFin = scan.nextInt();
        mesFin = scan.nextInt();
        añoFin = scan.nextBoolean();
        Cliente nuevoCliente = new Cliente(nombre,apellido,email,medioDePago,cantDePersonas);
        nuevoCliente.asignarFecha(diaInicio,diaFin,mesInicio,mesFin,añoInicio,añoFin);
        return nuevoCliente;
    }
}