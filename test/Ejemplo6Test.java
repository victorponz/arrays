import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Ejemplo6Test {

    @Test
    @DisplayName("El mínimo es el último elemento")
    void minimoUltimoElemento() {
        assertEquals(1, Ejemplo6.minimo(5 , 6, 8 , 1));

    }
    @Test
    @DisplayName("El mínimo es el primer elemento")
    void minimoPrimerElemento() {
        assertEquals(2, Ejemplo6.minimo(2, 8, 4, 3));
    }
    @Test
    @DisplayName("El mínimo es un elemento intermedio")
    void minimoIntermedio() {
        assertEquals(4, Ejemplo6.minimo(5, 6, 4, 8));
    }

}
