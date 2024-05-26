import classes.Cliente;
import classes.Alojamiento;
import classes.Departamento;
import classes.HabitacionHotel;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import java.util.Date;
import java.util.Iterator;
import java.util.HashSet;
import java.util.Objects;

public class Envoltorio {


    private HashSet<Alojamiento> hashSetAlojamiento;
    private HashSet<Cliente> hashSetCliente;


    public Envoltorio(HashSet<Alojamiento> hashSetAlojamiento, HashSet<Cliente> hashSetCliente) {
        this.hashSetAlojamiento = hashSetAlojamiento;
        this.hashSetCliente = hashSetCliente;
    }

    public Envoltorio() {
        hashSetAlojamiento = new HashSet<>();
        hashSetCliente = new HashSet<>();

    }

    public HashSet<Alojamiento> getHashSetAlojamiento() {
        return hashSetAlojamiento;
    }

    public void setHashSetAlojamiento(HashSet<Alojamiento> hashSetAlojamiento) {
        this.hashSetAlojamiento = hashSetAlojamiento;
    }

    public HashSet<Cliente> getHashSetCliente() {
        return hashSetCliente;
    }

    public void setHashSetCliente(HashSet<Cliente> hashSetCliente) {
        this.hashSetCliente = hashSetCliente;
    }

    public void agregarCliente(Cliente cliente) {
        hashSetCliente.add(cliente);
    }

    public void agregarAlojamiento(Alojamiento alojamiento) {
        hashSetAlojamiento.add(alojamiento);
    }

    public String listarClientes() {
        String texto = "";
        Iterator<Cliente> iterator = hashSetCliente.iterator();
        while (iterator.hasNext()) {
            texto += "\n " + iterator.next();
        }

        return texto;
    }

    public String listarAlojamientos() {
        String texto = "";
        Iterator<Alojamiento> iterator = hashSetAlojamiento.iterator();
        while (iterator.hasNext()) {
            texto += "\n " + iterator.next();
        }

        return texto;
    }
   /* public void jsonCliente()
    {
        JSONArray ja = new JSONArray();
        Iterator<Cliente> iterator = hashSetCliente.iterator();
        while(iterator.hasNext()){
            Cliente cliente =iterator.next();
            JSONObject jo = new JSONObject();

                try {
                    jo.put("Cliente",cliente);
                } catch (JSONException var8) {
                    throw new RuntimeException(var8);
                }

                ja.put(jo);


            }
        JsonUtiles.grabar(ja,"clientes");
        System.out.println(ja.toString());
        }*/

//CREACION DEL JSON DE CLIENTE A PARTIR DE UN HASHSET
    public void jsonCliente() {
        JSONArray ja = new JSONArray();
        Iterator<Cliente> iterator = hashSetCliente.iterator();
        while (iterator.hasNext()) {
            Cliente cliente = iterator.next();
            JSONObject clienteJson = new JSONObject(cliente);

               /* try {
                    jo.put("Cliente",cliente);
                } catch (JSONException var8) {
                    throw new RuntimeException(var8);
                }
*/
            ja.put(clienteJson);
        }
        JSONObject jo = new JSONObject();
        try {
            jo.put("Clientes", ja);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        JsonUtiles.grabar(jo, "clientes");
        System.out.println(ja.toString());
        System.out.println((JsonUtiles.leer("clientes")));
    }
    ///CREACION DEL JSON ALOJAMIENTO A PARTIR DE UN HASH SET
    public void jsonAlojamiento() {
        JSONArray jaDepto = new JSONArray();
        JSONArray jaHabHotel = new JSONArray();
        Iterator<Alojamiento> iterator = hashSetAlojamiento.iterator();
        while (iterator.hasNext()) {
           Alojamiento alojamiento = iterator.next();
            JSONObject alojamientoJson= new JSONObject(alojamiento);

               /* try {
                    jo.put("Cliente",cliente);
                } catch (JSONException var8) {
                    throw new RuntimeException(var8);
                }
*/              if(alojamiento instanceof Departamento)
            {
             jaDepto.put(alojamientoJson);
            }
            else if (alojamiento instanceof HabitacionHotel)
            {
                jaHabHotel.put(alojamientoJson);
            }

        }
        JSONObject joAlojamientos = new JSONObject();
        try {
            joAlojamientos.put("Departamento", jaDepto);
            joAlojamientos.put("HabitacionesHotel",jaHabHotel);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        JsonUtiles.grabar(joAlojamientos, "alojamientos");

        System.out.println(jaDepto);
        System.out.println(jaHabHotel);
        System.out.println((JsonUtiles.leer("alojamientos")));
    }

    ///PASAR DE JSON A JAVA. CLIENTE
    public void jsonAJavaClientes() {

        //leo mi archivo que contiene el json.
        String rta = JsonUtiles.leer("clientes");
        JSONObject jo = null;
        //lanzo una excepcion de JSON Object
        try {
            jo = new JSONObject(rta);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
       JSONArray jsonArray  = null;
        //Lanzo Excepcion de JSON ARRAY y ahora mi JSON ARRAY INICIALIZADO EN NULL TENDRA EL JSONARRAY DE MI JsonObject
        try {
            jsonArray = jo.getJSONArray("Clientes");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        //Instancio mi objeto
        Cliente cliente = new Cliente();

        //Recorro mi array
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                //Mi Json object Tendra cada objeto de mi arreglo
                jo = jsonArray.getJSONObject(i);

                cliente.setNombre(jo.getString("nombre"));
                cliente.setApellido(jo.getString("apellido"));
                cliente.setCorreoElectronico(jo.getString("correoElectronico"));
                cliente.setMedioDePago(jo.getString("medioDePago"));
                cliente.setContraseña(jo.getString("contraseña"));
                cliente.setCantPersonas(jo.getInt("cantPersonas"));

            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            //Añado a mi HashSet
            hashSetCliente.add(cliente);


        }
        System.out.println(hashSetCliente.toString());

    }
    ///PASAR A JSON A JAVA ALOJAMIENTO
    public void jsonAJavaAlojamiento(){

        //Leo mi archivo que contiene el json.
        String rta = JsonUtiles.leer("alojamientos");
        JSONObject jo = null;
        //lanzo una excepcion de JSON Object
        try {
            jo = new JSONObject(rta);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        JSONArray jsonArrayDepto = null;
        JSONArray jsonArrayHotel = null;
        //Lanzo Excepcion de JSON ARRAY y mi JsonArray inicializado en null ahora contrendra el jsonArray que tiene mi JsonObject
        try {
            jsonArrayDepto = jo.getJSONArray("Departamento");
            jsonArrayHotel = jo.getJSONArray("HabitacionesHotel");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        //Instancio mi objeto
        Departamento Depto = new Departamento();
        HabitacionHotel Hotel = new HabitacionHotel();

        //Recorro mi array
        for (int i = 0; i < jsonArrayDepto.length(); i++) {
            try {
                //Mi Json object Tendra cada objeto de mi arreglo
                jo = jsonArrayDepto.getJSONObject(i);

               Depto.setPrecioXAlojar(jo.getDouble("precioXAlojar"));
               Depto.setValoracion(jo.getDouble("valoracion"));
               Depto.setCantReservas(jo.getInt("cantReservas"));
               Depto.setDescripcion(jo.getString("descripcion"));
               Depto.setNombre(jo.getString("nombre"));
               Depto.setDireccion(jo.getString("direccion"));
               Depto.setZona(jo.getString("zona"));
               Depto.setComentarios(jo.getString("comentarios"));
               Depto.setDisponibilidad(jo.getBoolean("disponibilidad"));
               Depto.setNumeroPiso(jo.getInt("numeroPiso"));
               Depto.setTamañoDepartamento(jo.getInt("tamañoDepartamento"));
               Depto.setServicioExtra(jo.getString("servicioExtra"));
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            //Añado a mi HashSet
            agregarAlojamiento(Depto);
            System.out.println(hashSetAlojamiento);

        }
        for (int i = 0; i < jsonArrayHotel.length(); i++) {
            try {
                //Mi Json object Tendra cada objeto de mi arreglo
                jo = jsonArrayHotel.getJSONObject(i);

               Hotel.setPrecioXAlojar(jo.getDouble("precioXAlojar"));
                Hotel.setValoracion(jo.getDouble("valoracion"));
                Hotel.setCantReservas(jo.getInt("cantReservas"));
                Hotel.setDescripcion(jo.getString("descripcion"));
               Hotel.setNombre(jo.getString("nombre"));
                Hotel.setDireccion(jo.getString("direccion"));
               Hotel.setZona(jo.getString("zona"));
                Hotel.setComentarios(jo.getString("comentarios"));
                Hotel.setDisponibilidad(jo.getBoolean("disponibilidad"));
                Hotel.setServicios(jo.getString("servicios"));
                Hotel.setTipoHabitacion(jo.getString("tipoHabitacion"));
                Hotel.setNumeroHabitacion(jo.getInt("numeroHabitacion"));

            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            //Añado a mi HashSet
            agregarAlojamiento(Hotel);
            System.out.println(hashSetAlojamiento);
        }


    }
}


