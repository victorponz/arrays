import java.util.Random;
import java.util.Scanner;

/**
 * Práctica 6: Ventas de una cadena de tiendas.
 *
 * Matriz ventas[tienda][mes]: cada FILA es una tienda y cada COLUMNA un mes.
 * Los métodos de cálculo devuelven arrays en lugar de imprimir, y el resto los reutiliza.
 */
public class VentasTiendas {

    static final String[] TIENDAS = {"Centro", "Norte", "Sur", "Playa"};
    static final String[] MESES = {"Ene", "Feb", "Mar", "Abr", "May", "Jun",
                                   "Jul", "Ago", "Sep", "Oct", "Nov", "Dic"};

    static final double VENTA_MIN = 5000;
    static final double VENTA_MAX = 30000;

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("1. Generar ventas aleatorias");
        System.out.println("2. Introducir ventas a mano");
        double[][] ventas = (leerEntero("Opción: ", 1, 2) == 1) ? generarVentas() : introducirVentas();

        int opcion;
        do {
            System.out.println("\n===== VENTAS =====");
            System.out.println("1. Tabla de ventas con totales");
            System.out.println("2. Mejor tienda y peor mes");
            System.out.println("3. Mejor y peor mes de cada tienda");
            System.out.println("4. Crecimiento (1er trimestre vs 4º trimestre)");
            System.out.println("5. Comisiones");
            System.out.println("6. Ranking de tiendas");
            System.out.println("0. Salir");
            opcion = leerEntero("Opción: ", 0, 6);
            System.out.println();

            switch (opcion) {
                case 1 -> mostrarTabla(ventas);
                case 2 -> mostrarMejorTiendaYPeorMes(ventas);
                case 3 -> mostrarMejorYPeorMesPorTienda(ventas);
                case 4 -> mostrarCrecimiento(ventas);
                case 5 -> mostrarComisiones(ventas);
                case 6 -> mostrarRanking(ventas);
            }
        } while (opcion != 0);
    }

    // ===================== CARGA DE DATOS =====================

    static double[][] generarVentas() {
        Random random = new Random();
        double[][] ventas = new double[TIENDAS.length][MESES.length];
        for (int t = 0; t < ventas.length; t++) {
            for (int m = 0; m < ventas[t].length; m++) {
                double valor = VENTA_MIN + random.nextDouble() * (VENTA_MAX - VENTA_MIN);
                ventas[t][m] = Math.round(valor * 100) / 100.0; // redondeo a céntimos
            }
        }
        return ventas;
    }

    static double[][] introducirVentas() {
        double[][] ventas = new double[TIENDAS.length][MESES.length];
        for (int t = 0; t < ventas.length; t++) {
            System.out.println("Tienda " + TIENDAS[t] + ":");
            for (int m = 0; m < ventas[t].length; m++) {
                ventas[t][m] = leerDouble("  " + MESES[m] + ": ");
            }
        }
        return ventas;
    }

    // ===================== MÉTODOS DE CÁLCULO (devuelven, no imprimen) =====================

    /** Suma de cada fila: un total por tienda. */
    static double[] totalesPorTienda(double[][] ventas) {
        double[] totales = new double[ventas.length];
        for (int t = 0; t < ventas.length; t++) {
            totales[t] = sumaRango(ventas[t], 0, ventas[t].length - 1);
        }
        return totales;
    }

    /** Suma de cada columna: un total por mes. Fíjate en que el bucle externo recorre columnas. */
    static double[] totalesPorMes(double[][] ventas) {
        double[] totales = new double[MESES.length];
        for (int m = 0; m < MESES.length; m++) {
            for (int t = 0; t < ventas.length; t++) {
                totales[m] += ventas[t][m];
            }
        }
        return totales;
    }

    /** Suma los elementos de un array entre las posiciones desde y hasta (ambas incluidas). */
    static double sumaRango(double[] fila, int desde, int hasta) {
        double suma = 0;
        for (int i = desde; i <= hasta; i++) {
            suma += fila[i];
        }
        return suma;
    }

    /** Sirve para cualquier double[]: totales, o una fila de la matriz (ventas[t]). */
    static int posicionMaximo(double[] arr) {
        int pos = 0;
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > arr[pos]) pos = i;
        }
        return pos;
    }

    static int posicionMinimo(double[] arr) {
        int pos = 0;
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] < arr[pos]) pos = i;
        }
        return pos;
    }

    /** Comisión anual de cada tienda: 5% de los meses > 25.000 €, 2% de los meses > 20.000 €. */
    static double[] calcularComisiones(double[][] ventas) {
        double[] comisiones = new double[ventas.length];
        for (int t = 0; t < ventas.length; t++) {
            for (int m = 0; m < ventas[t].length; m++) {
                double v = ventas[t][m];
                if (v > 25000) {
                    comisiones[t] += v * 0.05;   // se comprueba primero la condición más restrictiva
                } else if (v > 20000) {
                    comisiones[t] += v * 0.02;
                }
            }
        }
        return comisiones;
    }

    /** Devuelve los índices de las tiendas ordenados de más a menos ventas. */
    static int[] ordenarTiendasPorVentas(double[] totales) {
        int[] indices = new int[totales.length];
        for (int i = 0; i < indices.length; i++) {
            indices[i] = i;
        }
        for (int i = 0; i < indices.length - 1; i++) {
            for (int j = 0; j < indices.length - 1 - i; j++) {
                if (totales[indices[j]] < totales[indices[j + 1]]) {
                    int temp = indices[j];
                    indices[j] = indices[j + 1];
                    indices[j + 1] = temp;
                }
            }
        }
        return indices;
    }

    // ===================== OPCIONES DEL MENÚ (imprimen) =====================

    static void mostrarTabla(double[][] ventas) {
        double[] totTiendas = totalesPorTienda(ventas);
        double[] totMeses = totalesPorMes(ventas);

        // Cabecera
        System.out.printf("%-8s", "");
        for (String mes : MESES) System.out.printf("%8s", mes);
        System.out.printf("%11s%n", "TOTAL");

        // Una fila por tienda
        for (int t = 0; t < ventas.length; t++) {
            System.out.printf("%-8s", TIENDAS[t]);
            for (int m = 0; m < ventas[t].length; m++) {
                System.out.printf("%8.0f", ventas[t][m]);
            }
            System.out.printf("%11.0f%n", totTiendas[t]);
        }

        // Fila de totales por mes; la esquina es el total general
        System.out.printf("%-8s", "TOTAL");
        for (double total : totMeses) System.out.printf("%8.0f", total);
        System.out.printf("%11.0f%n", sumaRango(totTiendas, 0, totTiendas.length - 1));
    }

    static void mostrarMejorTiendaYPeorMes(double[][] ventas) {
        double[] totTiendas = totalesPorTienda(ventas);
        double[] totMeses = totalesPorMes(ventas);

        int mejorTienda = posicionMaximo(totTiendas);
        int peorMes = posicionMinimo(totMeses);

        System.out.printf("Tienda con más ventas: %s (%,.2f €)%n", TIENDAS[mejorTienda], totTiendas[mejorTienda]);
        System.out.printf("Mes con menos ventas:  %s (%,.2f €)%n", MESES[peorMes], totMeses[peorMes]);
    }

    static void mostrarMejorYPeorMesPorTienda(double[][] ventas) {
        for (int t = 0; t < ventas.length; t++) {
            // ventas[t] es la fila de la tienda t: un double[] normal
            int mejor = posicionMaximo(ventas[t]);
            int peor = posicionMinimo(ventas[t]);
            System.out.printf("%-7s Mejor: %s (%,10.2f €)   Peor: %s (%,10.2f €)%n",
                    TIENDAS[t], MESES[mejor], ventas[t][mejor], MESES[peor], ventas[t][peor]);
        }
    }

    static void mostrarCrecimiento(double[][] ventas) {
        for (int t = 0; t < ventas.length; t++) {
            double primerTrimestre = sumaRango(ventas[t], 0, 2);   // enero a marzo
            double ultimoTrimestre = sumaRango(ventas[t], 9, 11);  // octubre a diciembre
            double variacion = (ultimoTrimestre - primerTrimestre) / primerTrimestre * 100;

            System.out.printf("%-7s 1T: %,11.2f €   4T: %,11.2f €   Variación: %+7.2f %%%n",
                    TIENDAS[t], primerTrimestre, ultimoTrimestre, variacion);
        }
    }

    static void mostrarComisiones(double[][] ventas) {
        double[] comisiones = calcularComisiones(ventas);
        for (int t = 0; t < comisiones.length; t++) {
            System.out.printf("%-7s Comisión anual: %,10.2f €%n", TIENDAS[t], comisiones[t]);
        }
    }

    static void mostrarRanking(double[][] ventas) {
        double[] totales = totalesPorTienda(ventas);
        int[] orden = ordenarTiendasPorVentas(totales);

        System.out.println("RANKING DE TIENDAS");
        for (int i = 0; i < orden.length; i++) {
            int t = orden[i];
            System.out.printf("%d. %-7s %,12.2f €%n", i + 1, TIENDAS[t], totales[t]);
        }
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
                System.out.println("  Debes introducir un número entero.");
            }
        }
    }

    static double leerDouble(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                double valor = Double.parseDouble(sc.nextLine().trim().replace(',', '.'));
                if (valor >= 0) return valor;
                System.out.println("  Las ventas no pueden ser negativas.");
            } catch (NumberFormatException e) {
                System.out.println("  Debes introducir un número.");
            }
        }
    }
}
