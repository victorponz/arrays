import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import static org.junit.jupiter.api.Assertions.*;

class Ejemplo4Test {

    @Test
    @DisplayName("Media con resultado entero")
    void mediaEntera() {
        assertAll(
                () -> assertEquals(6, Ejemplo4.media(5, 9, 4, 4, 8, 6 )),
                () -> assertEquals(8, Ejemplo4.media(8, 9, 6, 8, 9, 8 ))
        );
    }

    @Test
    @DisplayName("Media con resultado decimal")
    void mediaDecimal() {
        DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
        simbolos.setDecimalSeparator('.');
        DecimalFormat df = new DecimalFormat("##.##", simbolos);
        df.setRoundingMode(RoundingMode.DOWN);
       //El resultado de la media con dos decimales es 7.33
        assertEquals(Float.toString(7.33F), df.format(Ejemplo4.media(10, 9, 4, 8, 7, 6)));
    }

}
