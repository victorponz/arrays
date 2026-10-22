import java.util.Random;
import java.util.Scanner;

/**
 * Práctica 4: Tres en raya.
 *
 * Incluye la ampliación: modo contra el ordenador (que juega con 'O').
 * El ordenador gana si puede, bloquea si el rival va a ganar y, si no, juega al azar.
 */
public class TresEnRaya {

    static final char VACIO = ' ';
    static final char EMPATE = 'E';

    static Scanner sc = new Scanner(System.in);
    static Random random = new Random();

    public static void main(String[] args) {
        System.out.println("===== TRES EN RAYA =====");
        System.out.println("1. Dos jugadores");
        System.out.println("2. Contra el ordenador (tú eres X)");
        boolean contraOrdenador = leerEntero("Modo: ", 1, 2) == 2;

        int victoriasX = 0, victoriasO = 0, empates = 0;
        char empieza = 'X';

        do {
            char[][] tablero = crearTablero();
            char resultado = jugarPartida(tablero, empieza, contraOrdenador);

            if (resultado == 'X') victoriasX++;
            else if (resultado == 'O') victoriasO++;
            else empates++;

            System.out.printf("%nMARCADOR -> X: %d | O: %d | Empates: %d%n", victoriasX, victoriasO, empates);

            empieza = cambiarTurno(empieza); // alterna quién empieza cada partida
        } while (preguntarSiNo("¿Jugar otra partida? (s/n): "));

        System.out.println("¡Gracias por jugar!");
    }

    // ===================== DESARROLLO DE LA PARTIDA =====================

    /** Juega una partida completa y devuelve 'X', 'O' o EMPATE. */
    static char jugarPartida(char[][] tablero, char empieza, boolean contraOrdenador) {
        char turno = empieza;
        System.out.println("\nEmpieza " + turno);

        while (true) {
            mostrarTablero(tablero);

            if (contraOrdenador && turno == 'O') {
                jugadaOrdenador(tablero);
            } else {
                pedirJugada(tablero, turno);
            }

            if (hayGanador(tablero, turno)) {
                mostrarTablero(tablero);
                System.out.println("¡Gana " + turno + "!");
                return turno;
            }
            if (tableroLleno(tablero)) {
                mostrarTablero(tablero);
                System.out.println("¡Empate!");
                return EMPATE;
            }
            turno = cambiarTurno(turno);
        }
    }

    static void pedirJugada(char[][] tablero, char jugador) {
        System.out.println("Turno de " + jugador);
        while (true) {
            // El usuario usa 1-3; internamente restamos 1 para trabajar con 0-2
            int fila = leerEntero("  Fila (1-3): ", 1, 3) - 1;
            int col = leerEntero("  Columna (1-3): ", 1, 3) - 1;

            if (tablero[fila][col] != VACIO) {
                System.out.println("  Esa casilla ya está ocupada.");
            } else {
                tablero[fila][col] = jugador;
                return;
            }
        }
    }

    // ===================== TABLERO =====================

    static char[][] crearTablero() {
        char[][] tablero = new char[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                tablero[i][j] = VACIO;  // un char[][] nuevo contiene '\u0000', no espacios
            }
        }
        return tablero;
    }

    static void mostrarTablero(char[][] t) {
        System.out.println("\n    1   2   3");
        for (int i = 0; i < 3; i++) {
            System.out.printf("%d   %c | %c | %c%n", i + 1, t[i][0], t[i][1], t[i][2]);
            if (i < 2) {
                System.out.println("   ---+---+---");
            }
        }
        System.out.println();
    }

    static boolean hayGanador(char[][] t, char j) {
        for (int i = 0; i < 3; i++) {
            if (t[i][0] == j && t[i][1] == j && t[i][2] == j) return true; // fila i
            if (t[0][i] == j && t[1][i] == j && t[2][i] == j) return true; // columna i
        }
        if (t[0][0] == j && t[1][1] == j && t[2][2] == j) return true;     // diagonal principal
        if (t[0][2] == j && t[1][1] == j && t[2][0] == j) return true;     // diagonal secundaria
        return false;
    }

    static boolean tableroLleno(char[][] t) {
        for (char[] fila : t) {
            for (char casilla : fila) {
                if (casilla == VACIO) return false;
            }
        }
        return true;
    }

    static char cambiarTurno(char jugador) {
        return (jugador == 'X') ? 'O' : 'X';
    }

    // ===================== ORDENADOR (AMPLIACIÓN) =====================

    static void jugadaOrdenador(char[][] t) {
        System.out.println("Turno del ordenador (O)...");

        // 1. Si puede ganar en esta jugada, gana
        if (buscarJugadaGanadora(t, 'O', 'O')) return;
        // 2. Si X puede ganar en la siguiente, le bloquea (pone O donde ganaría X)
        if (buscarJugadaGanadora(t, 'X', 'O')) return;
        // 3. Si no, juega en una casilla libre al azar
        int fila, col;
        do {
            fila = random.nextInt(3);
            col = random.nextInt(3);
        } while (t[fila][col] != VACIO);
        t[fila][col] = 'O';
    }

    /**
     * Prueba cada casilla libre: coloca 'quien' y comprueba si ganaría.
     * Si encuentra una, coloca allí 'ficha' y devuelve true. Siempre deshace la prueba.
     */
    static boolean buscarJugadaGanadora(char[][] t, char quien, char ficha) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (t[i][j] == VACIO) {
                    t[i][j] = quien;
                    boolean gana = hayGanador(t, quien);
                    t[i][j] = VACIO; // deshacemos la prueba
                    if (gana) {
                        t[i][j] = ficha;
                        return true;
                    }
                }
            }
        }
        return false;
    }

    // ===================== LECTURA DE DATOS =====================

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

    static boolean preguntarSiNo(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String r = sc.nextLine().trim().toLowerCase();
            if (r.equals("s")) return true;
            if (r.equals("n")) return false;
            System.out.println("  Responde s o n.");
        }
    }
}
