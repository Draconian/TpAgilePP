/**
 * ErreurEmployeMaximumBureauTest - INF2015 - TP Agile - EQUIPE 17
 *
 * @author Francois Dufault
 * @author Lyes Tamazouzt
 * @author Abdessamad Essakhi
 */
package inf2015.tp.erreur;

import inf2015.tp.employe.Employe;
import inf2015.tp.employe.EmployeAdministration;
import static org.junit.Assert.*;
import org.junit.Test;

public class ErreurEmployeMaximumBureauTest {

    public ErreurEmployeMaximumBureauTest() {
    }

    @Test
    public void testAfficherErreur() {
        Employe employe = new EmployeAdministration(500, null);
        int maxMinutes = 90;

        String messageExpecter = String.format("L'employé Administration a dépassé le "
                + "nombre d'heures maximum au bureau. Celui-ci ne peut pas dépasser"
                + ": %.2f heures (90 minutes).", 1.5f);

        Erreur erreur = new ErreurEmployeMaximumBureau(employe, maxMinutes);
        String messageRecu = erreur.afficherErreur();

        assertEquals(messageExpecter, messageRecu);
    }
}
