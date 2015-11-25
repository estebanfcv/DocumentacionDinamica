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
public class Jefe {

    private int edad;

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public Jefe(int edad) {
        this.edad = edad;
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
