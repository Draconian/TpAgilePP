/**
 * ErreurEmployeCongeParentalMultipleTest - INF2015 - TP Agile - EQUIPE 17
 *
 * @author Francois Dufault
 * @author Lyes Tamazouzt
 * @author Abdessamad Essakhi
 */
package inf2015.tp.erreur;

import static org.junit.Assert.*;
import org.junit.Test;

public class ErreurEmployeCongeParentalMultipleTest {

    public ErreurEmployeCongeParentalMultipleTest() {
    }

    @Test
    public void testAfficherErreur() {
        String messageExpected = "Une semaine ne peut contenir"
                + " plus qu'un congé parental.";

        Erreur erreur = new ErreurEmployeCongeParentalMultiple();
        String messageRecu = erreur.afficherErreur();

        assertEquals(messageExpected, messageRecu);
    }
}
