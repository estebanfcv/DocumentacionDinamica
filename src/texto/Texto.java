/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package texto;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author BMTHP02
 */
public class Texto {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException, ClassNotFoundException {
//        $P{Usuario|user}
        List<String> listaParametros = new ArrayList<>();
        List<String> listaCampos = new ArrayList<>();
        String mensaje = "La fecha es::::: $P{Usuario|fecha}\n"
                + "Buenos días señor $P{Usuario|user} le informo que sus empleados se llaman:\n"
                + "Nombre: $F{Empleado|nombre} Apellido: $F{Empleado|apellido} Edad: $F{Empleado|jefe|edad}\n"
                + "Saludos";
        Pattern patternParametros = Pattern.compile("\\$[P]\\{(.*?)\\}");
        System.out.println("Mensaje Original \n" + mensaje);
        Matcher matcherParametros = patternParametros.matcher(mensaje);
        while (matcherParametros.find()) {
            listaParametros.add(matcherParametros.group(0));
        }
        Pattern patternCampos = Pattern.compile("\\$[F]\\{(.*?)\\}");
        Matcher matcherCampos = patternCampos.matcher(mensaje);
        while (matcherCampos.find()) {
            listaCampos.add(matcherCampos.group(0));
        }
        String rutaClase;
        List<String> parametros;

        for (String lp : listaParametros) {
            parametros = new ArrayList<>();
            rutaClase = lp.substring(lp.indexOf("{") + 1, lp.length() - 1);
            StringTokenizer st = new StringTokenizer(rutaClase, "|");
            while (st.hasMoreTokens()) {
                parametros.add(st.nextToken());
            }
            mensaje = mensaje.replaceFirst("\\$[P]\\{(.*?)\\}", cambiarParametros(parametros));
        }
        System.out.println("mensaje con Parametros $P{} \n" + mensaje);

        List<String> campos;

        List<Empleado> empleados = new ArrayList<>();
        empleados.add(new Empleado("Andrea", "Alvarez", new Jefe(30)));
        empleados.add(new Empleado("Iliana", "Perez", new Jefe(25)));
        empleados.add(new Empleado("Juan", "Sarrelangue", new Jefe(28)));

        String contenidoFinal = "";
        for (StringTokenizer st = new StringTokenizer(mensaje, "\n"); st.hasMoreTokens();) {
            String token = st.nextToken();
            if (token.contains("$F")) {
                String tokenAux = token;
                int numeroLista = empleados.size();
                for (int i = 0; i < numeroLista; i++) {
                    contenidoFinal += tokenAux + "\n";
                }
                for (Empleado e : empleados) {
                    for (String lp : listaCampos) {
                        campos = new ArrayList<>();
                        rutaClase = lp.substring(lp.indexOf("{") + 1, lp.length() - 1);
                        StringTokenizer stz = new StringTokenizer(rutaClase, "|");
                        while (stz.hasMoreTokens()) {
                            campos.add(stz.nextToken());
                        }
                        contenidoFinal = contenidoFinal.replaceFirst("\\$[F]\\{(.*?)\\}", cambiarCampos(campos, e));
                    }
                }
            } else {
                contenidoFinal += token + "\n";
            }
        }
        System.out.println("mensaje con Fields $F{}\n " + contenidoFinal);
//       
    }

    public static String cambiarParametros(List<String> lista) throws NoSuchFieldException, IllegalArgumentException,
            IllegalAccessException, ClassNotFoundException {
        String valor = "";
        Usuario u = new Usuario("admin");
        u.setEmpleado(new Empleado("Esteban"));
        Field field;
        Class claseRaiz = Usuario.class;
        Class klass = claseRaiz;
        Object objeto = u;
        Object instancia;
        for (int i = 1; i < lista.size(); i++) {
            field = klass.getDeclaredField(lista.get(i));
            field.setAccessible(true);
            if (isPrimitivo(field)) {
                instancia = objeto.getClass().equals(Field.class) ? u.getValor(((Field) objeto).getName()) : objeto;
                return String.valueOf(field.get(instancia));
            } else {
                for (Field f : klass.getDeclaredFields()) {
                    if (f.getType().equals(field.getType())) {
                        f.setAccessible(true);
                        objeto = f;
                        break;
                    }
                }
                klass = field.getType();
            }
        }
        return valor;
    }

    public static String cambiarCampos(List<String> lista, Empleado e) throws NoSuchFieldException, IllegalArgumentException,
            IllegalAccessException, ClassNotFoundException {
        String valor = "";
        Field field;
        Class claseRaiz = e.getClass();
        Class klass = claseRaiz;
        Object objeto = e;
        Object instancia;
        for (int i = 1; i < lista.size(); i++) {
            field = klass.getDeclaredField(lista.get(i));
            field.setAccessible(true);
            if (isPrimitivo(field)) {
                instancia = objeto.getClass().equals(Field.class) ? e.getValor(((Field) objeto).getName()) : objeto;
                return String.valueOf(field.get(instancia)).trim();
            } else {
                for (Field f : klass.getDeclaredFields()) {
                    if (f.getType().equals(field.getType())) {
                        f.setAccessible(true);
                        objeto = f;
                        break;
                    }
                }
                klass = field.getType();
            }
        }
        return valor;
    }

    private static boolean isPrimitivo(Field f) {
        if (f.getType().isPrimitive()) {
            return true;
        }
        Class clas = f.getType();
        return clas.equals(Boolean.class) || clas.equals(Byte.class) || clas.equals(Short.class) || clas.equals(Integer.class) || clas.equals(Long.class)
                || clas.equals(Float.class) || clas.equals(Double.class) || clas.equals(String.class) || clas.equals(Date.class);
    }

    public static String formatearPlantilla(String contenido) {
        String cadenaFinal = "";
        for (StringTokenizer st = new StringTokenizer(contenido, "\n"); st.hasMoreTokens();) {
            String token = st.nextToken();
            if (token.contains("<tr>") || token.contains("<td ") || token.contains("<td>") || token.contains("</td>")) {
                cadenaFinal += token;
            } else {
                cadenaFinal += token + "\n";
            }
        }
        cadenaFinal = cadenaFinal.replaceAll("\\t", "");
        return cadenaFinal;
    }
}
