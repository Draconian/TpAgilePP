/**
 * ErreurEmployeTeletravailMaximumTest - INF2015 - TP Agile - EQUIPE 17
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

public class ErreurEmployeTeletravailMaximumTest {
    
    public ErreurEmployeTeletravailMaximumTest() {
    }

    @Test
    public void testAfficherErreur() {        
        String messageExpecter = "L'employé Administration a dépassé le nombre "
            + "d'heures de télétravail. Il ne peut pas dépasser 1,5 heures.";
        int maxMinutes = 90;
        Employe employe = new EmployeAdministration(800, null);
        
        Erreur erreur = new ErreurEmployeTeletravailMaximum(employe, maxMinutes);
        String messageRecu = erreur.afficherErreur();
    
        assertEquals(messageExpecter, messageRecu);
    }
    
}
