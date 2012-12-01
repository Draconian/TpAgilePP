/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package inf2015.tp.erreur;

import inf2015.tp.Projet;
import inf2015.tp.jour.Jour;
import inf2015.tp.jour.JourOuvrable;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author fdufault
 */
public class ErreurJourSpecialEgalMinutesTest {


    @Test
    public void testAfficherErreur() {
        int maxMinutes = 60;
        String typeDeJour ="férié";
        Jour jour = new JourOuvrable("jour2", null);
        Projet projet = new Projet(500,500);
        jour.ajoutProjet(projet);
        
        String erreurMsgExpecter = "Le jour jour2 qui est férié, " +
                "doit contenir 60 minutes. (Il contient 500 minutes.)";
        
        Erreur erreur = new ErreurJourSpecialEgalMinutes(jour,typeDeJour, maxMinutes);
        String erreurMsgRecu = erreur.afficherErreur();
        
        assertEquals(erreurMsgExpecter, erreurMsgRecu);
    }
}
