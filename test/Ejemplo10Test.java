import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Ejemplo10Test {

    @Test
    @DisplayName("Eliminamos el primer elemento del array")
    void eliminarPrimerElemento() {

        assertArrayEquals(new int[]{2, 3, 4, 5, 1},
                Ejemplo10.eliminarElemento(0, new int[]{1, 2, 3, 4, 5}));
    }
    @Test
    @DisplayName("Eliminamos el último elemento del array")
    void eliminarUltimoElemento() {

        assertArrayEquals(new int[]{1, 2, 3, 4, 5},
                Ejemplo10.eliminarElemento(4, new int[]{1, 2, 3, 4, 5}));
    }

    @Test
    @DisplayName("Eliminamos cualquier otro elemento del array")
    void eliminarOtroElemento() {

        assertArrayEquals(new int[]{1, 2, 4, 5, 3},
                Ejemplo10.eliminarElemento(2, new int[]{1, 2, 3, 4, 5}));
    }
}
