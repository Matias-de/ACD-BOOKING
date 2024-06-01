package Modelo;

import Enumeraciones.EstadoAlojamiento;

import java.io.Serializable;

public class Departamento extends Alojamiento  implements Serializable {

    //atributos
    private int numeroPiso;
    private int tamañoDepartamento;
    private String servicioExtra;

    //constructores


    public Departamento(double precioXAlojar, String descripcion, String nombre, String direccion, String zona, String comentarios, EstadoAlojamiento estado, int numeroPiso, int tamañoDepartamento, String servicioExtra)
    {
        super(precioXAlojar,descripcion, nombre, direccion,zona,comentarios,estado);
        this.numeroPiso = numeroPiso;
        this.tamañoDepartamento = tamañoDepartamento;
        this.servicioExtra = servicioExtra;
    }
    public Departamento()
    {
        super();
        this.numeroPiso = 0;
        this.tamañoDepartamento = 0;
        this.servicioExtra = "";
    }
    ///setters

    public void setServicioExtra(String servicioExtra) {
        this.servicioExtra = servicioExtra;
    }

    public void setNumeroPiso(int numeroPiso) {
        this.numeroPiso = numeroPiso;
    }

    public void setTamañoDepartamento(int tamañoDepartamento) {
        this.tamañoDepartamento = tamañoDepartamento;
    }

    //getters
    public String getServicioExtra() {
        return servicioExtra;
    }
    public int getTamañoDepartamento() {
        return tamañoDepartamento;
    }
    public int getNumeroPiso() {
        return numeroPiso;
    }

    //metodos

    @Override
    public String toString() {
        return super.toString()+"Departamento{" +
                "numeroPiso=" + numeroPiso +
                ", tamañoDepartamento=" + tamañoDepartamento +
                ", servicioExtra='" + servicioExtra + '\'' +
                '}';
    }
    @Override
    public int hashCode() {
        return 1;
    }

    @Override
    public boolean equals(Object obj) {
        boolean rta = super.equals(obj);
        if ( obj != null && obj instanceof Departamento)
        {
            Departamento aux = (Departamento) obj;
            if( aux.getNumeroPiso() != numeroPiso && tamañoDepartamento!= aux.tamañoDepartamento )
            {
                rta = false;
            }
        }
        return rta;
    }
}
