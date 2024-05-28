import Modelo.BookingACD;
import Modelo.Cliente;
import Modelo.Reserva;

import java.util.Calendar;
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
            case 1:
                nuevoBooking.cargarHashMap();


        }
    }
    public static void menu(int opc)
    {

    }
}