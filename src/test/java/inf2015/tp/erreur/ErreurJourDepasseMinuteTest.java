/**
 * ErreurJourDepasseMinuteTest - INF2015 - TP Agile - EQUIPE 17
 *
 * @author Francois Dufault
 * @author Lyes Tamazouzt
 * @author Abdessamad Essakhi
 */
package inf2015.tp.erreur;

import inf2015.tp.Projet;
import inf2015.tp.jour.Jour;
import inf2015.tp.jour.JourOuvrable;
import static org.junit.Assert.*;
import org.junit.Test;

public class ErreurJourDepasseMinuteTest {

    @Test
    public void testAfficherErreur() {
        int maxMinutes = 60;
        Jour jour = new JourOuvrable("jour1", null);
        Projet projet = new Projet(500, 500);
        jour.ajoutProjet(projet);

        String erreurMsgExpecter = String.format("Le jour jour1 contient 500 "
                + "minutes (%.2f heures),il dépasse le maximum de minutes "
                + "authorisés qui est 60 minutes (%.2f heures)", 8.33f, 1.0f);

        Erreur erreur = new ErreurJourDepasseMinute(jour, maxMinutes);
        String erreurMsgRecu = erreur.afficherErreur();

        assertEquals(erreurMsgExpecter, erreurMsgRecu);
    }
}
