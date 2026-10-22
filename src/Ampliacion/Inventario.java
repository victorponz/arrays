import java.util.Scanner;

/**
 * Práctica 2: Inventario de una tienda.
 *
 * Tres arrays paralelos (nombre, precio, stock) con capacidad para 20 productos.
 * La variable numProductos indica cuántas posiciones están realmente ocupadas:
 * las posiciones desde numProductos hasta el final se consideran vacías.
 *
 * Los arrays son atributos static para que todos los métodos puedan usarlos
 * sin tener que pasar cuatro parámetros en cada llamada.
 */
public class Inventario {

    static final int MAX_PRODUCTOS = 20;

    static String[] nombres = new String[MAX_PRODUCTOS];
    static double[] precios = new double[MAX_PRODUCTOS];
    static int[] stock = new int[MAX_PRODUCTOS];
    static int numProductos = 0;

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        cargarDatosDePrueba(); // comentar esta línea para empezar con el inventario vacío

        int opcion;
        do {
            mostrarMenu();
            opcion = leerEntero("Opción: ", 0, 8);
            System.out.println();

            switch (opcion) {
                case 1 -> anadirProducto();
                case 2 -> eliminarProducto();
                case 3 -> modificarStock();
                case 4 -> buscarProducto();
                case 5 -> listarProductos();
                case 6 -> listarPorPrecio();
                case 7 -> listarSinStock();
                case 8 -> mostrarValorTotal();
                case 0 -> System.out.println("¡Hasta pronto!");
            }
        } while (opcion != 0);
    }

    static void mostrarMenu() {
        System.out.println("\n===== INVENTARIO (" + numProductos + "/" + MAX_PRODUCTOS + ") =====");
        System.out.println("1. Añadir producto");
        System.out.println("2. Eliminar producto");
        System.out.println("3. Modificar stock");
        System.out.println("4. Buscar producto por nombre");
        System.out.println("5. Listar productos");
        System.out.println("6. Listar productos ordenados por precio");
        System.out.println("7. Mostrar productos sin stock");
        System.out.println("8. Valor total del inventario");
        System.out.println("0. Salir");
    }

    // ===================== OPCIONES DEL MENÚ =====================

    static void anadirProducto() {
        if (numProductos == MAX_PRODUCTOS) {
            System.out.println("El inventario está lleno. Elimina algún producto antes de añadir otro.");
            return;
        }

        String nombre = leerTexto("Nombre: ");
        if (buscarPosicion(nombre) != -1) {
            System.out.println("Ya existe un producto con ese nombre.");
            return;
        }
        double precio = leerDouble("Precio: ", 0);
        int unidades = leerEntero("Unidades en stock: ", 0, Integer.MAX_VALUE);

        // El nuevo producto ocupa la primera posición libre, que es justo numProductos
        nombres[numProductos] = nombre;
        precios[numProductos] = precio;
        stock[numProductos] = unidades;
        numProductos++;

        System.out.println("Producto añadido.");
    }

    static void eliminarProducto() {
        if (inventarioVacio()) return;

        String nombre = leerTexto("Nombre del producto a eliminar: ");
        int pos = buscarPosicion(nombre);
        if (pos == -1) {
            System.out.println("No existe ningún producto con ese nombre.");
            return;
        }

        // Desplazamos una posición a la izquierda todos los productos posteriores
        for (int i = pos; i < numProductos - 1; i++) {
            nombres[i] = nombres[i + 1];
            precios[i] = precios[i + 1];
            stock[i] = stock[i + 1];
        }
        numProductos--;

        // Limpiamos la última posición, que ha quedado duplicada
        nombres[numProductos] = null;
        precios[numProductos] = 0;
        stock[numProductos] = 0;

        System.out.println("Producto eliminado.");
    }

    static void modificarStock() {
        if (inventarioVacio()) return;

        String nombre = leerTexto("Nombre del producto: ");
        int pos = buscarPosicion(nombre);
        if (pos == -1) {
            System.out.println("No existe ningún producto con ese nombre.");
            return;
        }

        System.out.println("Stock actual de " + nombres[pos] + ": " + stock[pos] + " unidades");
        int tipo = leerEntero("1. Entrada  2. Salida: ", 1, 2);
        int cantidad = leerEntero("Cantidad: ", 1, Integer.MAX_VALUE);

        if (tipo == 1) {
            stock[pos] += cantidad;
        } else if (cantidad > stock[pos]) {
            System.out.println("No se pueden retirar " + cantidad + " unidades: solo hay " + stock[pos] + ".");
            return;
        } else {
            stock[pos] -= cantidad;
        }
        System.out.println("Stock actualizado: " + stock[pos] + " unidades.");
    }

    /** Búsqueda parcial: encuentra el texto en cualquier parte del nombre, sin distinguir mayúsculas. */
    static void buscarProducto() {
        if (inventarioVacio()) return;

        String texto = leerTexto("Texto a buscar: ").toLowerCase();
        int encontrados = 0;

        for (int i = 0; i < numProductos; i++) {
            if (nombres[i].toLowerCase().contains(texto)) {
                if (encontrados == 0) mostrarCabecera();
                mostrarFila(i);
                encontrados++;
            }
        }
        System.out.println(encontrados == 0 ? "No se ha encontrado ningún producto."
                                            : encontrados + " producto(s) encontrado(s).");
    }

    static void listarProductos() {
        if (inventarioVacio()) return;

        mostrarCabecera();
        for (int i = 0; i < numProductos; i++) {
            mostrarFila(i);
        }
    }

    /**
     * No ordenamos los arrays originales: ordenamos un array de ÍNDICES.
     * Si indices = {2, 0, 1}, significa "primero el producto 2, luego el 0, luego el 1".
     */
    static void listarPorPrecio() {
        if (inventarioVacio()) return;

        int[] indices = new int[numProductos];
        for (int i = 0; i < numProductos; i++) {
            indices[i] = i;
        }

        // Burbuja comparando los precios a los que apuntan los índices
        for (int i = 0; i < indices.length - 1; i++) {
            for (int j = 0; j < indices.length - 1 - i; j++) {
                if (precios[indices[j]] > precios[indices[j + 1]]) {
                    int temp = indices[j];
                    indices[j] = indices[j + 1];
                    indices[j + 1] = temp;
                }
            }
        }

        mostrarCabecera();
        for (int indice : indices) {
            mostrarFila(indice);
        }
    }

    static void listarSinStock() {
        if (inventarioVacio()) return;

        int sinStock = 0;
        for (int i = 0; i < numProductos; i++) {
            if (stock[i] == 0) {
                if (sinStock == 0) mostrarCabecera();
                mostrarFila(i);
                sinStock++;
            }
        }
        if (sinStock == 0) {
            System.out.println("Todos los productos tienen stock.");
        }
    }

    static void mostrarValorTotal() {
        double total = 0;
        for (int i = 0; i < numProductos; i++) {
            total += precios[i] * stock[i];
        }
        System.out.printf("Valor total del inventario: %.2f €%n", total);
    }

    // ===================== MÉTODOS AUXILIARES =====================

    /** Devuelve la posición del producto con ese nombre exacto (sin distinguir mayúsculas) o -1. */
    static int buscarPosicion(String nombre) {
        for (int i = 0; i < numProductos; i++) {
            if (nombres[i].equalsIgnoreCase(nombre)) {
                return i;
            }
        }
        return -1;
    }

    static boolean inventarioVacio() {
        if (numProductos == 0) {
            System.out.println("El inventario está vacío.");
            return true;
        }
        return false;
    }

    static void mostrarCabecera() {
        System.out.printf("%-4s %-22s %10s %8s%n", "Nº", "Producto", "Precio", "Stock");
        System.out.println("-".repeat(47));
    }

    static void mostrarFila(int i) {
        System.out.printf("%-4d %-22s %8.2f € %8d%n", i + 1, nombres[i], precios[i], stock[i]);
    }

    static void cargarDatosDePrueba() {
        String[] n = {"Camiseta azul", "Pantalón vaquero", "Gorra", "Camisa blanca", "Calcetines (pack 3)"};
        double[] p = {12.99, 34.50, 9.95, 24.00, 6.50};
        int[] s = {15, 0, 30, 8, 0};
        for (int i = 0; i < n.length; i++) {
            nombres[i] = n[i];
            precios[i] = p[i];
            stock[i] = s[i];
        }
        numProductos = n.length;
    }

    // ===================== LECTURA DE DATOS =====================
    // Se lee siempre la línea completa y se convierte, para evitar los problemas
    // de mezclar nextInt() y nextLine() con el mismo Scanner.

    static int leerEntero(String mensaje, int min, int max) {
        while (true) {
            System.out.print(mensaje);
            try {
                int valor = Integer.parseInt(sc.nextLine().trim());
                if (valor >= min && valor <= max) return valor;
                System.out.println("  Valor fuera de rango.");
            } catch (NumberFormatException e) {
                System.out.println("  Debes introducir un número entero.");
            }
        }
    }

    static double leerDouble(String mensaje, double min) {
        while (true) {
            System.out.print(mensaje);
            try {
                // Aceptamos tanto coma como punto decimal
                double valor = Double.parseDouble(sc.nextLine().trim().replace(',', '.'));
                if (valor >= min) return valor;
                System.out.println("  El valor no puede ser menor que " + min + ".");
            } catch (NumberFormatException e) {
                System.out.println("  Debes introducir un número.");
            }
        }
    }

    static String leerTexto(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String texto = sc.nextLine().trim();
            if (!texto.isEmpty()) return texto;
            System.out.println("  El texto no puede estar vacío.");
        }
    }
}
