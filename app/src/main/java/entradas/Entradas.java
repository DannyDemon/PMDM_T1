package entradas;

import java.util.Scanner;

/**
 *
 * @author DAM114
 */
public class Entradas {

    static Scanner leer = new Scanner(System.in);

    public static String introducirString() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    public static int pedirIntCC() {
        String num;
        boolean compr;

        do {
            num = leer.next();
            compr = comprNumerico(num);

            if (compr) {
                break;
            } else {
                System.out.println("No has introducido un número, vuelve a intentarlo");
            }
        } while (!compr);
        return Integer.parseInt(num);
    }

    public static float pedirFloat() {
        String num;
        boolean compr;

        do {
            num = leer.next();
            compr = comprNumerico(num);

            if (compr) {
                break;
            } else {
                System.out.print("No has introducido un número, o has utilizado coma en vez de punto, vuelve a intentarlo");
            }
        } while (!compr);
        return Float.parseFloat(num);
    }

    public static boolean comprNumerico(String cadena) {
        try {
            Float.parseFloat(cadena);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    public static String continuar(String mensaje) {
        String sn;
        for (;;) {
            System.out.println(mensaje);
            sn = Entradas.introducirString();
            if (sn.equals("s") | sn.equals("n")) {
                break;
            } else {
                System.out.println("Introduce s o n");
            }
        }
        return sn;
    }

    public static void continuarr(String mensaje) {
        System.out.println(mensaje);
        String sn = Entradas.introducirString();
    }
}

