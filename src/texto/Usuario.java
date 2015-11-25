/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package texto;

import java.lang.reflect.Field;
import java.util.Date;

/**
 *
 * @author BMTHP02
 */
public class Usuario {

    private String user;
    private Date fecha = new Date();
    private Empleado empleado;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Usuario(String user) {
        this.user = user;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public final Object getValor(String nombre) {
        Object valor = new Object();
        try {
            Field f = Usuario.class.getDeclaredField(nombre);
            f.setAccessible(true);
            valor = f.get(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return valor;
    }
}
