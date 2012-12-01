/**
 * ErreurEmployeMinimalUnJourOuvrableBureauTest - INF2015 - TP Agile - EQUIPE 17
 *
 * @author Francois Dufault
 * @author Lyes Tamazouzt
 * @author Abdessamad Essakhi
 */
package inf2015.tp.erreur;

import inf2015.tp.Employe;
import inf2015.tp.jour.Jour;
import inf2015.tp.jour.JourOuvrable;
import static org.junit.Assert.*;
import org.junit.Test;

public class ErreurEmployeMinimalUnJourOuvrableBureauTest {

    @Test
    public void testAfficherErreur() {
        String messageExpecter = "L'employé employé n'a pas "
            + "travaillé le nombre d'heures minimal au bureau (jour ouvrable) pour "
            + "le jour jour1. L'employé employé doit travailler au moins 60 minutes par jour.";
        Employe employe = new Employe(500, null);
        Jour jour = new JourOuvrable("jour1", null);
        int minMinutes = 60;
        
        Erreur erreur = new ErreurEmployeMinimalUnJourOuvrableBureau(employe, jour, minMinutes);
        String messageRecu = erreur.afficherErreur();
        
        
        assertEquals(messageExpecter, messageRecu);
    }
}
