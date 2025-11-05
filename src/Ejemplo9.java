public class Ejemplo9 {
    public static int pares(int ...numeros)
    {
        int pares = 0;

        for (int numero: numeros){
            if (numero % 2 == 0 )
                pares++;
        }
        return pares;
    }
}
