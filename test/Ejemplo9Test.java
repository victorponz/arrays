import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Ejemplo9Test {

    @Test
    void pares() {
        assertAll(
                () -> assertEquals(3, Ejemplo9.pares(4 , 1, 5, 2, 8, 1, 3)),
                () -> assertEquals(4, Ejemplo9.pares(4 , 1, 5, 2, 8, 1, 8)),
                () -> assertEquals(2, Ejemplo9.pares( 1, 1, 5, 2, 8, 1, 3))
        );
    }
}
