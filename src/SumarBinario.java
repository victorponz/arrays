public class SumarBinario {
    public static String sumarBinario(String num1, String num2){
        String res = "";
        int bitA, bitB;
        int suma = 0, acarreo = 0;

        for (int i = num1.length() - 1; i >= 0; i--) {
            bitA = Integer.parseInt("" + num1.charAt(i));
            bitB = Integer.parseInt("" + num2.charAt(i));
            suma = bitA + bitB + acarreo;
            res = (suma % 2 == 0 ? "0" : "1") + res;
            acarreo = (suma > 1 ? 1 : 0);
            /*
            Estas dos líneas de arriba son una simplificación del siguiente if-else
            if (suma == 0){
                res = "0" + res;
                acarreo = 0;
            } else if (suma == 1) {
                res = "1" + res;
                acarreo = 0;
            } else if (suma == 2) {
                res = "0" + res;
                acarreo = 1;
            } else {
                res = "1" + res;
                acarreo = 1;
            }*/
        }
        if (acarreo == 1)
            res = "1" + res;

        return res;

    }

    public static void main(String[] args) {
        System.out.println(sumarBinario("1001", "1011")); //10100
        System.out.println(sumarBinario("10101010", "11001100")); // 101110110
        System.out.println(sumarBinario("10011", "10010")); //100101


    }
}
