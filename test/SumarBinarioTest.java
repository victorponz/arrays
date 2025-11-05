import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SumarBinarioTest {

    @Test
    void sumaIgual() {
        assertEquals("10100", SumarBinario.sumarBinario("1001", "1011"));
        assertEquals("101110110", SumarBinario.sumarBinario("10101010", "11001100"));
        assertEquals("100101", SumarBinario.sumarBinario("10011", "10010"));
    }
}