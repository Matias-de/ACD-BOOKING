import Modelo.Cliente;

import java.util.Calendar;

public class Main {
    public static void main(String[] args) {
       Cliente aux = new Cliente("", "", "", "", "", 5);
        System.out.println(aux);
        aux.asignarFecha(2,4,2,7,false, false);
        System.out.println(aux);
    }
}