import java.util.Scanner;

/**
 * Práctica 1: Estación meteorológica.
 *
 * Registra la temperatura máxima de cada día de la semana y muestra
 * estadísticas. El main solo lee los datos y llama a los métodos.
 */
public class EstacionMeteorologica {

    static final String[] DIAS = {"lunes", "martes", "miércoles", "jueves", "viernes", "sábado", "domingo"};
    static final double TEMP_MIN = -30;
    static final double TEMP_MAX = 50;

    // Códigos de escape ANSI: al imprimirlos, la consola cambia el color del texto que sigue.
    // RESET vuelve al color normal; si no se imprime, todo lo posterior seguiría coloreado.
    static final String RESET    = "\u001B[0m";
    static final String AZUL     = "\u001B[34m";
    static final String CIAN     = "\u001B[36m";
    static final String VERDE    = "\u001B[32m";
    static final String AMARILLO = "\u001B[33m";
    static final String ROJO     = "\u001B[31m";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        double[] temperaturas = leerTemperaturas(sc);

        System.out.println();
        mostrarMedia(temperaturas);
        mostrarMaximaYMinima(temperaturas);
        mostrarDiasSobreMedia(temperaturas);
        mostrarMayorSubida(temperaturas);
        dibujarGrafico(temperaturas);

        sc.close();
    }

    // ===================== LECTURA DE DATOS =====================

    static double[] leerTemperaturas(Scanner sc) {
        double[] temperaturas = new double[DIAS.length];
        for (int i = 0; i < temperaturas.length; i++) {
            temperaturas[i] = leerTemperatura(sc, DIAS[i]);
        }
        return temperaturas;
    }

    /** Pide una temperatura hasta que sea un número dentro del rango permitido. */
    static double leerTemperatura(Scanner sc, String dia) {
        while (true) {
            System.out.print("Temperatura del " + dia + ": ");
            if (sc.hasNextDouble()) {
                double t = sc.nextDouble();
                if (t >= TEMP_MIN && t <= TEMP_MAX) {
                    return t;
                }
                System.out.printf("  Valor no válido. Debe estar entre %.0f y %.0f.%n", TEMP_MIN, TEMP_MAX);
            } else {
                System.out.println("  Eso no es un número (recuerda usar coma decimal).");
                sc.next(); // descartamos la entrada incorrecta
            }
        }
    }

    // ===================== MÉTODOS DE CÁLCULO =====================

    static double calcularMedia(double[] t) {
        double suma = 0;
        for (double valor : t) {
            suma += valor;
        }
        return suma / t.length;
    }

    /** Devuelve la POSICIÓN del máximo, no el valor: así sabemos también el día. */
    static int posicionMaximo(double[] t) {
        int pos = 0;
        for (int i = 1; i < t.length; i++) {
            if (t[i] > t[pos]) {
                pos = i;
            }
        }
        return pos;
    }

    static int posicionMinimo(double[] t) {
        int pos = 0;
        for (int i = 1; i < t.length; i++) {
            if (t[i] < t[pos]) {
                pos = i;
            }
        }
        return pos;
    }

    // ===================== APARTADOS =====================

    static void mostrarMedia(double[] t) {
        System.out.printf("Media semanal: %.2f ºC%n", calcularMedia(t));
    }

    static void mostrarMaximaYMinima(double[] t) {
        int max = posicionMaximo(t);
        int min = posicionMinimo(t);
        System.out.printf("Máxima: %.1f ºC (%s)  |  Mínima: %.1f ºC (%s)%n",
                t[max], DIAS[max], t[min], DIAS[min]);
    }

    static void mostrarDiasSobreMedia(double[] t) {
        double media = calcularMedia(t);
        boolean alguno = false;

        System.out.print("Días por encima de la media: ");
        for (int i = 0; i < t.length; i++) {
            if (t[i] > media) {
                if (alguno) {
                    System.out.print(", ");
                }
                System.out.print(DIAS[i]);
                alguno = true;
            }
        }
        if (!alguno) {
            System.out.print("ninguno");
        }
        System.out.println();
    }

    /** Compara cada día con el siguiente: el bucle termina en length - 1 para no salirse. */
    static void mostrarMayorSubida(double[] t) {
        int pos = -1;
        double mayorSubida = 0;

        for (int i = 0; i < t.length - 1; i++) {
            double subida = t[i + 1] - t[i];
            if (subida > mayorSubida) {
                mayorSubida = subida;
                pos = i;
            }
        }

        if (pos == -1) {
            System.out.println("No hubo ninguna subida de temperatura entre días consecutivos.");
        } else {
            System.out.printf("Mayor subida: de %s a %s (+%.1f ºC)%n", DIAS[pos], DIAS[pos + 1], mayorSubida);
        }
    }

    static void dibujarGrafico(double[] t) {
        System.out.println("\nGráfico de temperaturas:");
        for (int i = 0; i < t.length; i++) {
            System.out.printf("%-10s ", DIAS[i]);

            int longitud = (int) Math.round(Math.abs(t[i]));
            // '\u2588' es el bloque lleno (█) y '\u2591' el bloque sombreado claro (░).
            // Se escriben con su código Unicode para que no dependan de la codificación del archivo.
            char simbolo = (t[i] < 0) ? '\u2591' : '\u2588';

            System.out.print(colorTemperatura(t[i]));   // activamos el color
            for (int j = 0; j < longitud; j++) {
                System.out.print(simbolo);
            }
            System.out.print(RESET);                     // y lo desactivamos tras la barra
            System.out.printf(" %.1f%n", t[i]);
        }

        System.out.println("\nLeyenda: " + AZUL + "\u2588 bajo 0" + RESET + "  "
                + CIAN + "\u2588 0-9" + RESET + "  " + VERDE + "\u2588 10-19" + RESET + "  "
                + AMARILLO + "\u2588 20-29" + RESET + "  " + ROJO + "\u2588 30 o más" + RESET);
    }

    /** Devuelve el código de color que corresponde a cada rango de temperatura. */
    static String colorTemperatura(double t) {
        if (t < 0)  return AZUL;
        if (t < 10) return CIAN;
        if (t < 20) return VERDE;
        if (t < 30) return AMARILLO;
        return ROJO;
    }
}