import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EliminarTest {

    @Test
    void eliminar() {
        assertArrayEquals(new int[]{4, 8, 9, 5},
                Eliminar.eliminar(new int[]{4, 5, 8, 9}, 1));
    }
    @Test
    void eliminarUltimo() {
        assertArrayEquals(new int[]{4, 5, 8, 9},
                Eliminar.eliminar(new int[]{4, 5, 8, 9}, 3), "No funciona cuando se elimina el último" );
    }
    @Test
    void eliminarPrimero() {
        assertArrayEquals(new int[]{5, 8, 9, 4},
                Eliminar.eliminar(new int[]{4, 5, 8, 9}, 0), "No funciona cuando se elimina el primero");
    }


}