/**
 * ErreurJourCongeWeekendTest - INF2015 - TP Agile - EQUIPE 17
 *
 * @author Francois Dufault
 * @author Lyes Tamazouzt
 * @author Abdessamad Essakhi
 */
package inf2015.tp.erreur;

import inf2015.tp.jour.Jour;
import inf2015.tp.jour.JourWeekend;
import static org.junit.Assert.*;
import org.junit.Test;

public class ErreurJourCongeWeekendTest {
    
    public ErreurJourCongeWeekendTest() {
    }

    @Test
    public void testAfficherErreur() {
        String messageExpecter = "Le jour weekend1 qui est férié ne "
            + "doit pas être le weekend.";
        Jour jour = new JourWeekend("weekend1", null);
        String typeDeJour = "férié";
        
        Erreur erreur = new ErreurJourCongeWeekend(jour, typeDeJour);
        String messageRecu = erreur.afficherErreur();
              
        assertEquals(messageExpecter, messageRecu); 
    }
}
