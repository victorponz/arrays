public class Ejemplo10 {
	public static int[] eliminarElemento(int indice, int ...numeros) {
		// Hacemos una copia del elemento a eliminar antes de empezar a mover
        int numeroAMover = numeros[indice];
        for (int i = indice ; i < numeros.length - 1; i++) {
            // Ahora movemos una posición hacia la izquierda todos los elementos a partir de
            // removeIndex
            // Por ejemplo, si removeIndex = 3;
            // my_array[3] = my_array[4];
            // my_array[4] = my_array[5];
            // y así sucesivamente hasta llegar al penúltimo
			numeros[i] = numeros[i + 1];
        }
        // Y ahora ponemos el número a mover en la última posición
		numeros[numeros.length - 1] = numeroAMover;
        return  numeros;
	}

}
