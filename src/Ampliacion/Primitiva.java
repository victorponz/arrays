import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

/**
 * Práctica 3: La Primitiva.
 *
 * Combinaciones de 6 números distintos entre 1 y 49.
 * El método contiene() se reutiliza para generar combinaciones y para validar la apuesta.
 */
public class Primitiva {

    static final int CANTIDAD = 6;
    static final int MAXIMO = 49;

    static Scanner sc = new Scanner(System.in);
    static Random random = new Random();

    public static void main(String[] args) {
        int opcion;
        do {
            System.out.println("\n===== LA PRIMITIVA =====");
            System.out.println("1. Jugar con mi apuesta");
            System.out.println("2. Jugar con apuesta automática");
            System.out.println("3. Simular sorteos hasta conseguir 5 aciertos");
            System.out.println("0. Salir");
            opcion = leerEntero("Opción: ");

            switch (opcion) {
                case 1 -> jugar(pedirApuesta());
                case 2 -> jugar(generarCombinacion());
                case 3 -> simular();
                case 0 -> System.out.println("¡Suerte!");
                default -> System.out.println("Opción no válida.");
            }
        } while (opcion != 0);
    }

    // ===================== OPCIONES =====================

    static void jugar(int[] apuesta) {
        int[] ganadora = generarCombinacion();

        System.out.println("\nTu apuesta:            " + Arrays.toString(apuesta));
        System.out.println("Combinación ganadora:  " + Arrays.toString(ganadora));

        int[] acertados = numerosAcertados(apuesta, ganadora);
        System.out.println("Aciertos: " + acertados.length
                + (acertados.length > 0 ? " -> " + Arrays.toString(acertados) : ""));
    }

    static void simular() {
        System.out.print("¿Quieres usar tu propia apuesta? (s/n): ");
        int[] apuesta = sc.nextLine().trim().equalsIgnoreCase("s") ? pedirApuesta() : generarCombinacion();
        System.out.println("Apuesta: " + Arrays.toString(apuesta));
        System.out.println("Simulando...");

        // frecuencias[n] = veces que ha salido el número n (la posición 0 no se usa)
        int[] frecuencias = new int[MAXIMO + 1];
        long sorteos = 0;
        int aciertos;

        do {
            int[] ganadora = generarCombinacion();
            sorteos++;
            for (int numero : ganadora) {
                frecuencias[numero]++;
            }
            aciertos = numerosAcertados(apuesta, ganadora).length;
        } while (aciertos < 5);

        System.out.printf("%nConseguidos %d aciertos tras %,d sorteos.%n", aciertos, sorteos);
        System.out.printf("Con un sorteo por semana, habrías tardado %.1f años.%n", sorteos / 52.0);

        mostrarMasFrecuentes(frecuencias, 5);
    }

    // ===================== MÉTODOS PRINCIPALES =====================

    /** Devuelve true si valor está en el array. */
    static boolean contiene(int[] arr, int valor) {
        for (int n : arr) {
            if (n == valor) return true;
        }
        return false;
    }

    /**
     * Genera 6 números distintos. Funciona con contiene() sobre el array completo
     * porque las posiciones aún sin rellenar valen 0, y el 0 nunca puede salir.
     */
    static int[] generarCombinacion() {
        int[] combinacion = new int[CANTIDAD];
        int generados = 0;

        while (generados < CANTIDAD) {
            int numero = random.nextInt(MAXIMO) + 1; // de 1 a 49
            if (!contiene(combinacion, numero)) {
                combinacion[generados] = numero;
                generados++;
            }
        }
        Arrays.sort(combinacion);
        return combinacion;
    }

    static int[] pedirApuesta() {
        int[] apuesta = new int[CANTIDAD];
        System.out.println("Introduce " + CANTIDAD + " números distintos entre 1 y " + MAXIMO + ":");

        for (int i = 0; i < CANTIDAD; i++) {
            while (true) {
                int numero = leerEntero("  Número " + (i + 1) + ": ");
                if (numero < 1 || numero > MAXIMO) {
                    System.out.println("    Fuera de rango: debe estar entre 1 y " + MAXIMO + ".");
                } else if (contiene(apuesta, numero)) {
                    System.out.println("    El " + numero + " ya está en tu apuesta.");
                } else {
                    apuesta[i] = numero;
                    break;
                }
            }
        }
        Arrays.sort(apuesta);
        return apuesta;
    }

    /** Devuelve un array (de tamaño justo) con los números de la apuesta que están en la ganadora. */
    static int[] numerosAcertados(int[] apuesta, int[] ganadora) {
        // Primera pasada: contar, para saber de qué tamaño crear el array
        int total = 0;
        for (int n : apuesta) {
            if (contiene(ganadora, n)) total++;
        }

        // Segunda pasada: rellenar
        int[] acertados = new int[total];
        int pos = 0;
        for (int n : apuesta) {
            if (contiene(ganadora, n)) {
                acertados[pos] = n;
                pos++;
            }
        }
        return acertados;
    }

    /** Ordena un array de números (1..49) según su frecuencia, de mayor a menor, y muestra los primeros. */
    static void mostrarMasFrecuentes(int[] frecuencias, int cuantos) {
        int[] numeros = new int[MAXIMO];
        for (int i = 0; i < MAXIMO; i++) {
            numeros[i] = i + 1;
        }

        for (int i = 0; i < numeros.length - 1; i++) {
            for (int j = 0; j < numeros.length - 1 - i; j++) {
                if (frecuencias[numeros[j]] < frecuencias[numeros[j + 1]]) {
                    int temp = numeros[j];
                    numeros[j] = numeros[j + 1];
                    numeros[j + 1] = temp;
                }
            }
        }

        System.out.println("\nNúmeros que más han salido:");
        for (int i = 0; i < cuantos; i++) {
            System.out.printf("  %2d. El %2d ha salido %,d veces%n", i + 1, numeros[i], frecuencias[numeros[i]]);
        }
    }

    // ===================== LECTURA DE DATOS =====================

    static int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("    Debes introducir un número entero.");
            }
        }
    }
}
