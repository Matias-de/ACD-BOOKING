import classes.Alojamiento;
import classes.Cliente;
import classes.Departamento;
import classes.HabitacionHotel;
import org.json.JSONException;

import java.util.Date;

public class Main {
    public static void main(String[] args) {



        Alojamiento Departamento1 = new Departamento(3000, "Vista al Mar. 2 Habitaciones. Muy comodo y espacioso",
                "Chalet de Pedro", "Av. Independencia y Luro 2332", "Luro", "", true, 0, 2332, 40,"Parrilla y Piscina");
        Alojamiento Departamento2= new Departamento(2500,"Comodo y Economico. Balcon","MonoAmbiente Economico","Olazabal 1345","Olazabal","",true,0,1345,25,"Internet");
        Alojamiento Departamento3 = new Departamento(5000,"3 Habitaciones. Gran Espacio. Zona Centrica. Patio y Pileta","Departamento Lujoso Zona Centrica","Guemes y Olavarria 122","Guemes","",true,0,122,30,"Pileta. Parrila. Internet. Parquero Incluido");

        Alojamiento HabitacionHotel1 = new HabitacionHotel(1500,"Gran Balcon. Vista al Lago. 2 habitaciones","Hotel Dodo","Guemes 1374","Guemes","",true,0,"Desayuno y Merienda incluidas. Parrila 24hs","doble",375);
        Alojamiento HabitacionHotel2 = new HabitacionHotel(6000,"Hotel Lujoso con patio grande, piscina y quincho. 4 habitaciones. 3 baños. Garage. Cancha de Futbol","Hotel Costa Galana","Av. Patricio Peralta Ramos 576","Costanera","",true,0,"Cancha de Futbol. Servicio a la Habitacion. Internet. Sala de juegos. Gimnasio","premium",343);
        Alojamiento HabitacionHotel3 = new HabitacionHotel(1000,"Balcon.Comodo.Monoambiente.Economico","Hotel Dodo","Guemes 1374","Guemes","",true,0,"Desayuno incluido.","economica",377);
        Alojamiento HabitacionHotel4 = new HabitacionHotel(2500,"Balcon.Piscina.3 Habitaciones. Pequeño patio","Hotel Artico","Av. Colon 2434","Costanera y Colon","",true,0,"Parrila. Internet. Piscina. Limpieza","triple",60);
        Cliente cliente1 = new Cliente("Gonzalo","de Andrade","gonza1231@gmail.com","Efectivo","progcom101",2);
        Cliente cliente2 = new Cliente("Matias","Antonelli","matias@gmail.com","Debito","matianto",3);
        Cliente cliente3 = new Cliente("Andres","Canevello","topovello@gmail.com","Credito","andruvello",1);
        Cliente cliente4 = new Cliente("Gonzalo","Benoffi","gonzabenoffi@gmail.com","Transferencia","gonzaprofe",4);
        Cliente cliente5 = new Cliente("Chiqui","Tapia","chiqui@gmail.com","Cuenta DNI","chiquitap",5);
        Cliente cliente6 = new Cliente("Fausto","Benzo","fausbenzo@gmail.com","Mercado Pago","fausbenzo",3);


        Envoltorio myEnvoltorio = new Envoltorio();

        /*myEnvoltorio.agregarAlojamiento(Departamento1);
        myEnvoltorio.agregarAlojamiento(Departamento2);
        myEnvoltorio.agregarAlojamiento(Departamento3);
        myEnvoltorio.agregarAlojamiento(HabitacionHotel1);
        myEnvoltorio.agregarAlojamiento(HabitacionHotel2);
        myEnvoltorio.agregarAlojamiento(HabitacionHotel3);
        myEnvoltorio.agregarAlojamiento(HabitacionHotel4);*/
       /* myEnvoltorio.agregarCliente(cliente1);
        myEnvoltorio.agregarCliente(cliente2);
        myEnvoltorio.agregarCliente(cliente3);
        myEnvoltorio.agregarCliente(cliente4);
        myEnvoltorio.agregarCliente(cliente5);
        myEnvoltorio.agregarCliente(cliente6);*/



        //myEnvoltorio.jsonCliente();
        //myEnvoltorio.jsonAlojamiento();
        myEnvoltorio.jsonAJavaAlojamiento();
        //myEnvoltorio.jsonAJava();
       


    }


}