import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MoverCerosTest {

    @Test
    void moverCeros() {
        assertArrayEquals(new int[]{1, 2, 4, 5, 0}, MoverCeros.moverCeros(new int[]{1, 2, 0, 4, 5}));
    }
    @Test
    void moverCerosAlPrincipio() {
        assertArrayEquals(new int[]{2, 3, 4, 5, 0},
                MoverCeros.moverCeros(new int[]{0, 2, 3, 4, 5}),
                "No funciona cuando el 0 está al principio");
    }

    @Test
    void moverCerosAlFinal() {
        assertArrayEquals(new int[]{1, 2, 3, 4, 0},
                MoverCeros.moverCeros(new int[]{1, 2, 3, 4, 0}),
                "No funciona cuando el 0 está al final");
    }
    @Test
    void moverVariosCeros() {
        assertArrayEquals(new int[]{1, 3, 5, 0, 0},
                MoverCeros.moverCeros(new int[]{1, 0, 3, 0, 5}),
                "No funciona cuando hay varios ceros");
    }

    @Test
    void moverVariosCerosSeguidos() {
        assertArrayEquals(new int[]{1, 3, 5, 0, 0},
                MoverCeros.moverCeros(new int[]{1, 0, 0, 3, 5}),
                "No funciona cuando hay varios ceros seguidos");
    }

}