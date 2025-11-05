public class Ejemplo4 {

	public static float media(int ...notas) {
		int sumaNotas = 0;

		for (int nota : notas) {
			sumaNotas += nota;
		}

		return ((float) sumaNotas / (float) notas.length);

	}
}
