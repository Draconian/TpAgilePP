/**
 * JourTest - INF2015 - TP Agile - EQUIPE 17
 *
 * @author Francois Dufault
 * @author Lyes Tamazouzt
 * @author Abdessamad Essakhi
 */
package inf2015.tp;

import inf2015.tp.Jour.TypeJour;
import org.junit.Test;
import static org.junit.Assert.*;

public class JourTest {

    @Test
    public void testEstJourneeCongeParental() {
        boolean estJourneeCongeParental;

        Jour jour = new Jour("jour1", TypeJour.OUVRABLE);
        Projet p1 = new Projet(Projet.PROJET_ID_CONGE_PARENTAL, 0);
        jour.ajoutProjet(p1);

        estJourneeCongeParental = jour.estJourneeCongeParental();

        assertTrue(estJourneeCongeParental);
    }

    @Test
    public void testGetMinutesCongeParental() {
        int minutesProjet = 480;
        Jour jour = new Jour("jour1", TypeJour.OUVRABLE);
        Projet p1 = new Projet(Projet.PROJET_ID_CONGE_PARENTAL, minutesProjet);
        jour.ajoutProjet(p1);

        int minutesRecu = jour.getMinutesJourneeCongeParental();

        assertEquals(minutesProjet, minutesRecu);
    }

    @Test
    public void testAnalyserCongeParental() {
        int minutesProjet = 480;

        Jour jour = new Jour("jour1", TypeJour.OUVRABLE);
        Projet p1 = new Projet(Projet.PROJET_ID_CONGE_PARENTAL, minutesProjet);

        jour.ajoutProjet(p1);

        jour.analyserJourParental();
        int nbErreur = ErreurJournal.Instance().erreurs.size();

        assertEquals(nbErreur, 0);
    }
    
    @Test
    public void testContientPasAutresProjetsQueCongeParental()
    {
        Jour jour = new Jour("Jour1",TypeJour.OUVRABLE);
        Projet p1 = new Projet(Projet.PROJET_ID_CONGE_PARENTAL, 0);

        jour.ajoutProjet(p1);
        
        boolean jourContientAutresProjet = jour.contientAutresProjetsQue(Projet.PROJET_ID_CONGE_PARENTAL);
        
        assertFalse(jourContientAutresProjet);
    }
    
    @Test
    public void testContientAutresProjetsQueCongeParental()
    {
        Jour jour = new Jour("Jour1",TypeJour.OUVRABLE);
        Projet p1 = new Projet(Projet.PROJET_ID_CONGE_PARENTAL, 0);
        Projet p2 = new Projet(Projet.PROJET_ID_CONGE_VACANCE, 0);
        
        jour.ajoutProjet(p1);
        jour.ajoutProjet(p2);
        
        boolean jourContientAutresProjet = jour.contientAutresProjetsQue(Projet.PROJET_ID_CONGE_PARENTAL);
        
        assertTrue(jourContientAutresProjet);
    }
}
