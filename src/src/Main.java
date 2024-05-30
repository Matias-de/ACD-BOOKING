import Modelo.*;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class Main {
    static Scanner scan;

    public static void main(String[] args) {
        //declaracion de variables
        int opc = 0;
        char inicio = 's';
        BookingACD nuevoBooking = new BookingACD();
        scan = new Scanner(System.in);
        //texto en pantalla
        while ( inicio == 's')
        {
            System.out.println("BIENVENIDO!");
            System.out.println("Ingrese la opcion que desee realizar:");
            System.out.println("1)Cargar un nuevo cliente.");
            System.out.println("2)Cargar un alojamiento.");
            System.out.println("3)Realizar una reserva.");
            System.out.println("4)Mostrar Set de reservas.");
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
                    //agregar default al final
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
    public static void preguntarEstadia(Cliente cliente){
       int diaInicio=0,diaFin=0,mesInicio=0,mesFin=0;
        boolean añoInicio, añoFin;
        System.out.println("Ingrese cuando va a ingresar a la propiedad en orden de DIA, MES, Y AÑO(maximo 1 año):"); //como nosotros vamos a ser los que cargan clientes y demas vamos a asignar
        diaInicio = scan.nextInt();                                                 //el valor del booleano que nos dice si el año es el actual o el siguiente
        mesInicio = scan.nextInt();
        añoInicio = scan.nextBoolean();

        System.out.println("Ingrese cuando se va a retirar de la propiedad:");
        diaFin = scan.nextInt();
        mesFin = scan.nextInt();
        añoFin = scan.nextBoolean();
       cliente.asignarFecha(diaInicio, diaFin, mesInicio, mesFin, añoInicio, añoFin);

    }
}