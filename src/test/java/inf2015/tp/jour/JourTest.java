/**
 * JourTest - INF2015 - TP Agile - EQUIPE 17
 *
 * @author Francois Dufault
 * @author Lyes Tamazouzt
 * @author Abdessamad Essakhi
 */
package inf2015.tp.jour;

import inf2015.tp.Projet;
import inf2015.tp.erreur.ErreurJournal;
import java.util.List;
import static org.junit.Assert.*;
import org.junit.Test;

public class JourTest {

    @Test
    public void testGetNomJour() {
        String nomJour = "jour1";

        Jour jour = new JourOuvrable(nomJour, null);

        assertEquals(nomJour, jour.getNomJour());
    }

    @Test
    public void testGetProjetsJournee() {
        Jour jour = new JourOuvrable("jour1", null);

        Projet p1 = new Projet(500, 500);
        Projet p2 = new Projet(501, 501);
        jour.ajoutProjet(p1);
        jour.ajoutProjet(p2);

        List<Projet> listProjets = jour.getProjetsJournee();

        assertSame(p1, listProjets.get(0));
        assertSame(p2, listProjets.get(1));
    }

    @Test
    public void testGetMinutesJourneeFeriee1Projet() {
        int minutesProjet = 500;
        Jour jour = new JourOuvrable("jour1", null);
        Projet p1 = new Projet(Projet.PROJET_ID_CONGE_FERIE, minutesProjet);
        jour.ajoutProjet(p1);

        assertEquals(minutesProjet, jour.getMinutesJourneeFeriee());
    }

    @Test
    public void testGetMinutesJourneeFerieePlusieursProjets() {
        int minutesProjet = 500;
        Jour jour = new JourOuvrable("jour1", null);
        Projet p1 = new Projet(Projet.PROJET_ID_CONGE_FERIE, minutesProjet);
        jour.ajoutProjet(p1);

        p1 = new Projet(Projet.PROJET_ID_CONGE_FERIE, minutesProjet);
        jour.ajoutProjet(p1);

        assertEquals(minutesProjet * 2, jour.getMinutesJourneeFeriee());
    }

    @Test
    public void testGetTotalMinutesJournee() {
        int totalMinutes = 1000;

        Jour jour = new JourOuvrable(null, null);

        Projet p1 = new Projet(100, 500);
        Projet p2 = new Projet(904, 500);

        jour.ajoutProjet(p1);
        jour.ajoutProjet(p2);

        assertEquals(totalMinutes, jour.getTotalMinutesJournee());
    }

    @Test
    public void testGetMinutesJourneeVacance() {
        int totalMinutes = 500;

        Jour jour = new JourOuvrable(null, null);

        Projet p1 = new Projet(Projet.PROJET_ID_CONGE_VACANCE, totalMinutes);
        Projet p2 = new Projet(904, 500);

        jour.ajoutProjet(p1);
        jour.ajoutProjet(p2);

        assertEquals(totalMinutes, jour.getMinutesJourneeVacance());
    }

    @Test
    public void testGetMinutesJourneeMaladie() {
        int totalMinutes = 750;

        Jour jour = new JourOuvrable(null, null);

        Projet p1 = new Projet(Projet.PROJET_ID_CONGE_MALADIE, totalMinutes);
        Projet p2 = new Projet(904, 500);

        jour.ajoutProjet(p1);
        jour.ajoutProjet(p2);

        assertEquals(totalMinutes, jour.getMinutesJourneeMaladie());
    }

    @Test
    public void testGetMinutesTeletravail() {
        int totalMinutes = 250;

        Jour jour = new JourOuvrable(null, null);

        Projet p1 = new Projet(Projet.PROJET_ID_TELETRAVAIL + 1, totalMinutes);
        Projet p2 = new Projet(500, 500);

        jour.ajoutProjet(p1);
        jour.ajoutProjet(p2);

        assertEquals(totalMinutes, jour.getMinutesTeletravail());
    }

    @Test
    public void testGetMinutesBureau() {
        int totalMinutes = 250;

        Jour jour = new JourOuvrable(null, null);

        Projet p1 = new Projet(Projet.PROJET_ID_CONGE_MALADIE, totalMinutes);
        Projet p2 = new Projet(500, totalMinutes);

        jour.ajoutProjet(p1);
        jour.ajoutProjet(p2);

        assertEquals(totalMinutes * 2, jour.getMinutesBureau());
    }

    @Test
    public void testGetMinutesJourneeCongeParental() {
        int totalMinutes = 250;

        Jour jour = new JourOuvrable(null, null);

        Projet p1 = new Projet(Projet.PROJET_ID_CONGE_PARENTAL, totalMinutes);
        Projet p2 = new Projet(500, 400);

        jour.ajoutProjet(p1);
        jour.ajoutProjet(p2);

        assertEquals(totalMinutes, jour.getMinutesJourneeCongeParental());
    }

    @Test
    public void testEstJourneeVacances() {
        Jour jour = new JourOuvrable(null, null);

        Projet p1 = new Projet(Projet.PROJET_ID_CONGE_VACANCE, 0);
        Projet p2 = new Projet(500, 0);

        jour.ajoutProjet(p1);
        jour.ajoutProjet(p2);

        assertTrue(jour.estJourneeVacances());
    }

    @Test
    public void testEstJourneeFerie() {
        Jour jour = new JourOuvrable(null, null);

        Projet p1 = new Projet(Projet.PROJET_ID_CONGE_FERIE, 0);
        Projet p2 = new Projet(500, 0);

        jour.ajoutProjet(p1);
        jour.ajoutProjet(p2);

        assertTrue(jour.estJourneeFerie());
    }

    @Test
    public void testEstJourMaladie() {
        Jour jour = new JourOuvrable(null, null);

        Projet p1 = new Projet(Projet.PROJET_ID_CONGE_MALADIE, 0);
        Projet p2 = new Projet(500, 0);

        jour.ajoutProjet(p1);
        jour.ajoutProjet(p2);

        assertTrue(jour.estJourMaladie());
    }

    @Test
    public void testEstJourneeCongeParental() {
        Jour jour = new JourOuvrable(null, null);

        Projet p1 = new Projet(Projet.PROJET_ID_CONGE_PARENTAL, 0);
        Projet p2 = new Projet(500, 0);

        jour.ajoutProjet(p1);
        jour.ajoutProjet(p2);

        assertTrue(jour.estJourneeCongeParental());
    }

    @Test
    public void testContientTeleTravail() {
        Jour jour = new JourOuvrable(null, null);

        Projet p1 = new Projet(Projet.PROJET_ID_TELETRAVAIL + 1, 0);
        Projet p2 = new Projet(500, 0);

        jour.ajoutProjet(p1);
        jour.ajoutProjet(p2);

        assertTrue(jour.contientTeleTravail());
    }

    @Test
    public void testContientTravailBureau() {
        Jour jour = new JourOuvrable(null, null);

        Projet p1 = new Projet(Projet.PROJET_ID_TELETRAVAIL - 1, 0);
        Projet p2 = new Projet(500, 0);

        jour.ajoutProjet(p1);
        jour.ajoutProjet(p2);

        assertTrue(jour.contientTravailBureau());
    }

    @Test
    public void testContientAutresProjetsQueValide() {
        Jour jour = new JourOuvrable(null, null);
        int projetID = 500;

        Projet p1 = new Projet(Projet.PROJET_ID_TELETRAVAIL, 0);
        Projet p2 = new Projet(projetID, 0);

        jour.ajoutProjet(p1);
        jour.ajoutProjet(p2);

        assertTrue(jour.contientAutresProjetsQue(projetID));
    }

    @Test
    public void testContientAutresProjetsQueInvalide() {
        Jour jour = new JourOuvrable(null, null);
        int projetID = 500;
        Projet p1 = new Projet(projetID, 0);
        Projet p2 = new Projet(projetID, 0);

        jour.ajoutProjet(p1);
        jour.ajoutProjet(p2);

        assertFalse(jour.contientAutresProjetsQue(projetID));
    }

    @Test
    public void testAjoutProjet() {
        Projet projet = new Projet(0, 0);
        Jour jour = new JourOuvrable(null, null);
        jour.ajoutProjet(projet);

        assertSame(projet, jour.getProjetsJournee().get(0));
    }

    @Test
    public void testToString() {
        String nomJour = "jour";
        Jour jour = new JourOuvrable(nomJour, null);

        assertEquals(nomJour, jour.toString());
    }

    @Test
    public void testContientTransport() {
        Jour jour = new JourOuvrable(null, null);
        jour.ajoutProjet(new Projet(Projet.PROJET_ID_TRANSPORT, 100));
        jour.ajoutProjet(new Projet(1000, 100));

        assertTrue(jour.contientTransport());
    }

    @Test
    public void testContientPasTransport() {
        Jour jour = new JourOuvrable(null, null);
        jour.ajoutProjet(new Projet(500, 100));
        jour.ajoutProjet(new Projet(1000, 100));

        assertFalse(jour.contientTransport());
    }

    @Test
    public void testGetMinutesTransport() {
        Jour jour = new JourOuvrable(null, null);
        jour.ajoutProjet(new Projet(Projet.PROJET_ID_TRANSPORT, 100));
        jour.ajoutProjet(new Projet(Projet.PROJET_ID_TRANSPORT, 200));
        jour.ajoutProjet(new Projet(1000, 200));
        
        assertEquals(300, jour.getMinutesTransport());
    }

    @Test
    public void testGetMinutesTransportZero() {
       Jour jour = new JourOuvrable(null, null);
        jour.ajoutProjet(new Projet(50, 100));
        jour.ajoutProjet(new Projet(30, 200));
        jour.ajoutProjet(new Projet(1000, 200));
        
        assertEquals(0, jour.getMinutesTransport());
    }
    
    @Test
    public void testAnalyserJour() {
        ErreurJournal erreurJournal = new ErreurJournal();
        Jour jour = new JourOuvrable(null, erreurJournal);
        Projet p1 = new Projet(Projet.PROJET_ID_CONGE_FERIE, Jour.MINUTES_JOURNEE_FERIEE + 1);
        Projet p2 = new Projet(Projet.PROJET_ID_CONGE_MALADIE, Jour.MINUTES_JOURNEE_MALADIE + 1);
        Projet p3 = new Projet(Projet.PROJET_ID_CONGE_PARENTAL, Jour.MINUTES_JOURNEE_CONGE_PARENTAL + 1);
        Projet p4 = new Projet(Projet.PROJET_ID_CONGE_VACANCE, Jour.MINUTES_JOURNEE_CONGE_VACANCES + 1);

        jour.ajoutProjet(p1);
        jour.ajoutProjet(p2);
        jour.ajoutProjet(p3);
        jour.ajoutProjet(p4);

        jour.analyserJour();

        assertEquals(6, erreurJournal.getNombresErreurs());
    }
}
