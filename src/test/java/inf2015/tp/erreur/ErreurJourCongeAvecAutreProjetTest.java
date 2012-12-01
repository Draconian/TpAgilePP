/**
 * ErreurJourCongeAvecAutreProjetTest - INF2015 - TP Agile - EQUIPE 17
 *
 * @author Francois Dufault
 * @author Lyes Tamazouzt
 * @author Abdessamad Essakhi
 */
package inf2015.tp.erreur;

import inf2015.tp.jour.Jour;
import inf2015.tp.jour.JourOuvrable;
import static org.junit.Assert.*;
import org.junit.Test;

public class ErreurJourCongeAvecAutreProjetTest {

    @Test
    public void testAfficherErreur() {
        Jour jour = new JourOuvrable("jour4", null);
        String erreurMsgExpecter = "Le jour jour4 qui est parental "
            + "contient d'autre code de projet dans la même journée.";
        
        Erreur erreur = new ErreurJourCongeAvecAutreProjet(jour, "parental");
        String messageRecu = erreur.afficherErreur();
        
        assertEquals(erreurMsgExpecter, messageRecu);        
    }
}
