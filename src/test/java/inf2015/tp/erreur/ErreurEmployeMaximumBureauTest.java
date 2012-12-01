/**
 * ErreurEmployeMaximumBureauTest - INF2015 - TP Agile - EQUIPE 17
 *
 * @author Francois Dufault
 * @author Lyes Tamazouzt
 * @author Abdessamad Essakhi
 */
package inf2015.tp.erreur;

import inf2015.tp.Employe;
import static org.junit.Assert.*;
import org.junit.Test;

public class ErreurEmployeMaximumBureauTest {

    public ErreurEmployeMaximumBureauTest() {
    }

    @Test
    public void testAfficherErreur() {
        Employe employe = new Employe(500, null);
        int maxMinutes = 90;

        String messageExpecter = "L'employé employé a dépassé le "
                + "nombre d'heures maximum au bureau. Celui-ci ne peut pas dépasser"
                + ": 1,5 heures.";

        Erreur erreur = new ErreurEmployeMaximumBureau(employe, maxMinutes);
        String messageRecu = erreur.afficherErreur();

        assertEquals(messageExpecter, messageRecu);
    }
}
