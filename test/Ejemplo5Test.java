import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Ejemplo5Test {

    @Test
    @DisplayName("Máximo es el último elemento")
    void maximoFinal() {

        assertEquals(10, Ejemplo5.maximo(5 , 6, 8 , 10));

    }
    @Test
    @DisplayName("Máximo es el primer elemento")
    void maximoPrimero() {
        assertEquals(9, Ejemplo5.maximo(9, 8, 4, 3));
    }
    @Test
    @DisplayName("Máximo es un elemento intermedio")
    void maximoOtroElemento() {
        assertEquals(8, Ejemplo5.maximo(5, 6, 8, 4));
    }
}
