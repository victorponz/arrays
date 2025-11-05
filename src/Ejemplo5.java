public class Ejemplo5 {

    public static int maximo(int ...numeros)
    {	
    	int maximo;
		//Suponemos que el máximo es el primer número del array
		maximo = numeros[0];
		for (int i = 1; i < numeros.length; i++) {
			//Si el número actual es mayor que el máximo, este número es el nuevo máximo
			if (numeros[i] > maximo) {
				maximo = numeros[i]; 
			}
		}
		return  maximo;
    }
}
