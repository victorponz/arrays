import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DuplicadosTest {
    @Test
    void duplicados(){
        assertEquals("10 30", Duplicados.duplicados(new int[]{10, 10, 4, 20, 30, 65, 30}).trim());
    }
}