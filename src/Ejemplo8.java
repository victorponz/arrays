public class Ejemplo8 {
    public static int sumaPositivos(int ...numeros)
    {
        int sumaPositivos = 0;
        for (int numero: numeros){
            if (numero > 0 )
                sumaPositivos += numero;
        }
        return sumaPositivos;
    }
}
