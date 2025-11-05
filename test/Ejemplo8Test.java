import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Ejemplo8Test {

    @Test
    void sumaPositivos() {
        assertAll(
                () -> assertEquals(29, Ejemplo8.sumaPositivos(10, 11, -1, 8)),
                () -> assertEquals(22, Ejemplo8.sumaPositivos(-2, 11, 4, 7))
        );

    }
}
