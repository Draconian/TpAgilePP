/**
 * MinuteHeureConvertionTest - INF2015 - TP Agile - EQUIPE 17
 *
 * @author Francois Dufault
 * @author Lyes Tamazouzt
 * @author Abdessamad Essakhi
 */
package inf2015.tp;

import org.junit.Test;
import static org.junit.Assert.*;

public class MinuteHeureConvertionTest {

    @Test
    public void testMinutesVersHeures() {
        int minutes = 90;
        Object heuresExp = 1.5f;
        
        Object resultat = MinuteHeureConvertion.minutesVersHeures(minutes);

        assertEquals(heuresExp, resultat);
    }

    @Test
    public void testHeuresVersMinutes_int() {
        int heures = 2;
        int minutesExp = 120;
        
        Object resultat = MinuteHeureConvertion.heuresVersMinutes(heures);
        
        assertEquals(minutesExp, resultat);
    }

    @Test
    public void testHeuresVersMinutes_float() {
        float heures = 1.5f;
        Object minutesExp = 90;
        
        Object resultat = MinuteHeureConvertion.heuresVersMinutes(heures);
        
        assertEquals(minutesExp, resultat);
        
    }
}
