package  Ampliacion;

import java.util.Random;
import java.util.Scanner;

/**
 * Práctica 5: El ahorcado.
 *
 * Incluye la ampliación: palabras por categorías (matriz String[][])
 * y tratamiento de tildes (la 'a' descubre también la 'á').
 */
public class Ahorcado {

    static final int MAX_FALLOS = 6;

    static final String[] CATEGORIAS = {"Animales", "Frutas", "Informática"};

    // Una fila por categoría, en el mismo orden que CATEGORIAS
    static final String[][] PALABRAS = {
        {"elefante", "murciélago", "jirafa", "pingüino", "cocodrilo"},
        {"plátano", "melocotón", "sandía", "cereza", "mandarina"},
        {"variable", "compilador", "algoritmo", "servidor", "teclado"}
    };

    // Un dibujo por número de fallos (posición 0 = ningún fallo)
    static final String[] DIBUJOS = {
        "  +---+\n  |   |\n      |\n      |\n      |\n      |\n=========",
        "  +---+\n  |   |\n  O   |\n      |\n      |\n      |\n=========",
        "  +---+\n  |   |\n  O   |\n  |   |\n      |\n      |\n=========",
        "  +---+\n  |   |\n  O   |\n /|   |\n      |\n      |\n=========",
        "  +---+\n  |   |\n  O   |\n /|\\  |\n      |\n      |\n=========",
        "  +---+\n  |   |\n  O   |\n /|\\  |\n /    |\n      |\n=========",
        "  +---+\n  |   |\n  O   |\n /|\\  |\n / \\  |\n      |\n========="
    };

    static Scanner sc = new Scanner(System.in);
    static Random random = new Random();

    public static void main(String[] args) {
        System.out.println("===== EL AHORCADO =====");
        for (int i = 0; i < CATEGORIAS.length; i++) {
            System.out.println((i + 1) + ". " + CATEGORIAS[i]);
        }
        int categoria = leerEntero("Elige categoría: ", 1, CATEGORIAS.length) - 1;

        String[] lista = PALABRAS[categoria];
        String palabra = lista[random.nextInt(lista.length)];

        jugar(palabra);
    }

    static void jugar(String palabra) {
        char[] secreta = palabra.toCharArray();

        // Progreso: mismo tamaño que la palabra, todo con '_'
        char[] progreso = new char[secreta.length];
        for (int i = 0; i < progreso.length; i++) {
            progreso[i] = '_';
        }

        // Registro de letras usadas (como mucho, las 27 del abecedario)
        char[] usadas = new char[27];
        int numUsadas = 0;

        char[] falladas = new char[MAX_FALLOS];
        int numFallos = 0;

        while (numFallos < MAX_FALLOS && !palabraCompleta(progreso)) {
            mostrarEstado(progreso, falladas, numFallos);

            char letra = pedirLetra();

            if (contiene(usadas, numUsadas, letra)) {
                System.out.println("Ya habías probado la '" + letra + "'.");
                continue;
            }
            usadas[numUsadas] = letra;
            numUsadas++;

            int descubiertas = descubrirLetra(secreta, progreso, letra);
            if (descubiertas > 0) {
                System.out.println("¡Bien! La '" + letra + "' aparece " + descubiertas + " vez/veces.");
            } else {
                System.out.println("La '" + letra + "' no está.");
                falladas[numFallos] = letra;
                numFallos++;
            }
        }

        mostrarEstado(progreso, falladas, numFallos);
        if (palabraCompleta(progreso)) {
            System.out.println("¡Has ganado! La palabra era: " + palabra.toUpperCase());
        } else {
            System.out.println("¡Ahorcado! La palabra era: " + palabra.toUpperCase());
        }
    }

    // ===================== LÓGICA DEL JUEGO =====================

    /** Descubre TODAS las apariciones de la letra y devuelve cuántas había. */
    static int descubrirLetra(char[] secreta, char[] progreso, char letra) {
        int contador = 0;
        for (int i = 0; i < secreta.length; i++) {
            if (quitarTilde(secreta[i]) == letra) {
                progreso[i] = secreta[i]; // mostramos la letra original, con su tilde
                contador++;
            }
        }
        return contador;
    }

    static boolean palabraCompleta(char[] progreso) {
        for (char c : progreso) {
            if (c == '_') return false;
        }
        return true;
    }

    /** Busca solo en las posiciones ocupadas (de 0 a ocupadas - 1). */
    static boolean contiene(char[] arr, int ocupadas, char c) {
        for (int i = 0; i < ocupadas; i++) {
            if (arr[i] == c) return true;
        }
        return false;
    }

    static char quitarTilde(char c) {
        switch (c) {
            case 'á': return 'a';
            case 'é': return 'e';
            case 'í': return 'i';
            case 'ó': case 'ö': return 'o';
            case 'ú': case 'ü': return 'u';
            default:  return c;
        }
    }

    // ===================== ENTRADA / SALIDA =====================

    static void mostrarEstado(char[] progreso, char[] falladas, int numFallos) {
        System.out.println("\n" + DIBUJOS[numFallos]);

        System.out.print("\nPalabra: ");
        for (char c : progreso) {
            System.out.print(Character.toUpperCase(c) + " ");
        }

        System.out.print("\nFallos:  ");
        for (int i = 0; i < numFallos; i++) {
            System.out.print(falladas[i] + " ");
        }
        System.out.println("\nIntentos restantes: " + (MAX_FALLOS - numFallos));
    }

    /** Pide una única letra; la devuelve en minúscula y sin tilde. */
    static char pedirLetra() {
        while (true) {
            System.out.print("Letra: ");
            String entrada = sc.nextLine().trim().toLowerCase();

            if (entrada.length() == 1 && Character.isLetter(entrada.charAt(0))) {
                return quitarTilde(entrada.charAt(0));
            }
            System.out.println("  Introduce una sola letra.");
        }
    }

    static int leerEntero(String mensaje, int min, int max) {
        while (true) {
            System.out.print(mensaje);
            try {
                int valor = Integer.parseInt(sc.nextLine().trim());
                if (valor >= min && valor <= max) return valor;
                System.out.println("  Debe estar entre " + min + " y " + max + ".");
            } catch (NumberFormatException e) {
                System.out.println("  Debes introducir un número.");
            }
        }
    }
}
