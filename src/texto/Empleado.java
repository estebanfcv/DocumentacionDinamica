/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package texto;

import java.lang.reflect.Field;

/**
 *
 * @author BMTHP02
 */
public class Empleado {

    private String nombre;
    private String apellido;
    private Jefe jefe;

    public Empleado(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Jefe getJefe() {
        return jefe;
    }

    public void setJefe(Jefe jefe) {
        this.jefe = jefe;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Empleado(String nombre, String apellido) {
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public Empleado(String nombre, String apellido, Jefe jefe) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.jefe = jefe;
    }
    public final Object getValor(String nombre) {
        Object valor = new Object();
        try {
            Field f = getClass().getDeclaredField(nombre);
            f.setAccessible(true);
            valor = f.get(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return valor;
    }
}
