import java.util.Arrays;

public class Eliminar {
	public static int[] eliminar(int[] numeros, int indice){
		int numeroAMover = numeros[indice];
		for (int i = indice ; i < numeros.length - 1; i++) {
			// Ahora movemos una posición hacia la izquierda
			// todos los elementos a partir de removeIndex
			// Por ejemplo, si removeIndex = 3;
			// my_array[3] = my_array[4];
			// my_array[4] = my_array[5];
			// y así sucesivamente hasta llegar al penúltimo
			numeros[i] = numeros[i + 1];
		}
		// Y ahora ponemos el número a mover en la última posición
		numeros[numeros.length - 1] = numeroAMover;
		return numeros;
	}
	public static void main(String[] args) {
		int[] misNumeros = new int[5];
		Utilidades.rellenaArray(misNumeros, 1, 15);

		System.out.println("Original Array : " + Arrays.toString(misNumeros));

		int removeIndex = -1;
		do {
			removeIndex = Utilidades.leerEntero("Introduce un índice entre 0 y " + (misNumeros.length - 1) + ":");
		} while ((removeIndex < 0) || (removeIndex > misNumeros.length - 1));
        // Hacemos una copia del elemento a eliminar antes de empezar a mover
        int numeroAMover = misNumeros[removeIndex];
        for (int i = removeIndex ; i < misNumeros.length - 1; i++) {
            // Ahora movemos una posición hacia la izquierda
            // todos los elementos a partir de removeIndex
            // Por ejemplo, si removeIndex = 3;
            // my_array[3] = my_array[4];
            // my_array[4] = my_array[5];
            // y así sucesivamente hasta llegar al penúltimo
            misNumeros[i] = misNumeros[i + 1];
        }
        // Y ahora ponemos el número a mover en la última posición
        misNumeros[misNumeros.length - 1] = numeroAMover;
        
		// No podemos alterar el tamaño del array,
		// por lo tanto el último y el penúltimo elementos son iguales
		System.out.println("Después de eliminar el " + removeIndex + " elemento: " + Arrays.toString(misNumeros));

	}

}
