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
    public void testContientPasAutresProjetsQueCongeParental() {
        Jour jour = new Jour("Jour1", TypeJour.OUVRABLE);
        Projet p1 = new Projet(Projet.PROJET_ID_CONGE_PARENTAL, 0);

        jour.ajoutProjet(p1);

        boolean jourContientAutresProjet = jour.contientAutresProjetsQue(Projet.PROJET_ID_CONGE_PARENTAL);

        assertFalse(jourContientAutresProjet);
    }

    @Test
    public void testContientAutresProjetsQueCongeParental() {
        Jour jour = new Jour("Jour1", TypeJour.OUVRABLE);
        Projet p1 = new Projet(Projet.PROJET_ID_CONGE_PARENTAL, 0);
        Projet p2 = new Projet(Projet.PROJET_ID_CONGE_VACANCE, 0);

        jour.ajoutProjet(p1);
        jour.ajoutProjet(p2);

        boolean jourContientAutresProjet = jour.contientAutresProjetsQue(Projet.PROJET_ID_CONGE_PARENTAL);

        assertTrue(jourContientAutresProjet);
    }
    
    @Test
    public void testCongeParentalPendantWeekEnd(){
        int minutesProjet = 480;
        Jour jour = new Jour("Week end",TypeJour.WEEKEND);
        Projet p1 = new Projet(Projet.PROJET_ID_CONGE_PARENTAL,minutesProjet);
        jour.ajoutProjet(p1);
        jour.analyserJourParental();
         assertEquals(ErreurJournal.Instance().erreurs.size(), 1);
         ErreurJournal.Instance().effacerTout();   
    }

    @Test
    public void testEstJourneeCongeVacance() {
        boolean estCongeVacance;

        Jour jour = new Jour("jour1", TypeJour.OUVRABLE);
        Projet p1 = new Projet(Projet.PROJET_ID_CONGE_VACANCE, 0);
        jour.ajoutProjet(p1);

        estCongeVacance = jour.estJourneeVacances();

        assertTrue(estCongeVacance);
    }

    @Test//iCI
    public void testGetMinutesCongeVacance() {
        int minutesProjet = 480;
        Jour jour = new Jour("jour1", TypeJour.OUVRABLE);
        Projet p1 = new Projet(Projet.PROJET_ID_CONGE_VACANCE, minutesProjet);
        jour.ajoutProjet(p1);
        int minutesRecu = jour.getMinutesJourneeVacance();
        assertEquals(minutesProjet, minutesRecu);
    }

    @Test
    public void testAnalyserCongeVacanceValide() {
        int minutesProjet = 480;
        Jour jour = new Jour("jour1", TypeJour.OUVRABLE);
        Projet p1 = new Projet(Projet.PROJET_ID_CONGE_VACANCE, minutesProjet);
        jour.ajoutProjet(p1);
        jour.analyserJourVacances();
        int nbErreur = ErreurJournal.Instance().erreurs.size();
        assertEquals(nbErreur, 0);
    }

    @Test
    public void testAnalyserCongeVacanceInvalide() {
        int minutesProjet = 480;

        Jour jour = new Jour("jour1", TypeJour.OUVRABLE);
        Projet p1 = new Projet(Projet.PROJET_ID_CONGE_VACANCE, minutesProjet);
        Projet p2 = new Projet(Projet.PROJET_ID_CONGE_PARENTAL, minutesProjet);
        jour.ajoutProjet(p1);
        jour.ajoutProjet(p2);

        jour.analyserJourVacances();
        int nbErreur = ErreurJournal.Instance().erreurs.size();

        assertEquals(nbErreur, 1);
        ErreurJournal.Instance().effacerTout();
    }

    @Test
    public void testContientPasAutresProjetsQueCongeVacance() {
        Jour jour = new Jour("Jour1", TypeJour.OUVRABLE);
        Projet p1 = new Projet(Projet.PROJET_ID_CONGE_VACANCE, 0);
        jour.ajoutProjet(p1);
        boolean jourContientAutresProjet = jour.contientAutresProjetsQue(Projet.PROJET_ID_CONGE_VACANCE);
        assertFalse(jourContientAutresProjet);
    }

    @Test
    public void testAnalyserCongeVacanceAvecTravail() {
        int minutesProjet = 480;

        Jour jour = new Jour("jour1", TypeJour.OUVRABLE);
        Projet p1 = new Projet(Projet.PROJET_ID_CONGE_VACANCE, minutesProjet);
        Projet p2 = new Projet(100, 400);
        jour.ajoutProjet(p1);
        jour.ajoutProjet(p2);

        jour.analyserJourVacances();
        int nbErreur = ErreurJournal.Instance().erreurs.size();

        assertEquals(nbErreur, 0);

    }
    
        @Test
    public void testCongeVacancePendantWeekEnd(){
        int minutesProjet = 480;
        Jour jour = new Jour("Week end",TypeJour.WEEKEND);
        Projet p1 = new Projet(Projet.PROJET_ID_CONGE_VACANCE,minutesProjet);
        jour.ajoutProjet(p1);
        jour.analyserJourVacances();
         assertEquals(ErreurJournal.Instance().erreurs.size(), 1);
         ErreurJournal.Instance().effacerTout();   
    }

    @Test
    public void testEstJourneeCongeFerie() {
        boolean estJourneeFerie;

        Jour jour = new Jour("jour1", TypeJour.OUVRABLE);
        Projet p1 = new Projet(Projet.PROJET_ID_CONGE_FERIE, 0);
        jour.ajoutProjet(p1);

        estJourneeFerie = jour.estJourneeFerie();

        assertTrue(estJourneeFerie);
    }

    @Test//iCI
    public void testGetMinutesCongeFerie() {
        int minutesProjet = 480;
        Jour jour = new Jour("jour1", TypeJour.OUVRABLE);
        Projet p1 = new Projet(Projet.PROJET_ID_CONGE_VACANCE, minutesProjet);
        jour.ajoutProjet(p1);
        int minutesRecu = jour.getMinutesJourneeVacance();
        assertEquals(minutesProjet, minutesRecu);
    }

    @Test
    public void testAnalyserCongeFerieValide() {
        int minutesProjet = 480;

        Jour jour = new Jour("jour1", TypeJour.OUVRABLE);
        Projet p1 = new Projet(Projet.PROJET_ID_CONGE_FERIE, minutesProjet);

        jour.ajoutProjet(p1);

        jour.analyserJourFerie();
        int nbErreur = ErreurJournal.Instance().erreurs.size();

        assertEquals(nbErreur, 0);
    }

    @Test
    public void testAnalyserCongeFerieInvalide() {
        int minutesProjet = 480;

        Jour jour = new Jour("jour1", TypeJour.OUVRABLE);
        Projet p1 = new Projet(Projet.PROJET_ID_CONGE_VACANCE, minutesProjet);
        Projet p2 = new Projet(Projet.PROJET_ID_CONGE_PARENTAL, minutesProjet);
        jour.ajoutProjet(p1);
        jour.ajoutProjet(p2);

        jour.analyserJourVacances();
        int nbErreur = ErreurJournal.Instance().erreurs.size();

        assertEquals(nbErreur, 1);
        ErreurJournal.Instance().effacerTout();
    }

    @Test
    public void testContientPasAutresProjetsQueCongeFerie() {
        Jour jour = new Jour("Jour1", TypeJour.OUVRABLE);
        Projet p1 = new Projet(Projet.PROJET_ID_CONGE_FERIE, 0);
        jour.ajoutProjet(p1);
        boolean jourContientAutresProjet = jour.contientAutresProjetsQue(Projet.PROJET_ID_CONGE_FERIE);
        assertFalse(jourContientAutresProjet);
    }

    @Test
    public void testAnalyserCongeFerieAvecTravail() {
        int minutesProjet = 480;

        Jour jour = new Jour("jour1", TypeJour.OUVRABLE);
        Projet p1 = new Projet(Projet.PROJET_ID_CONGE_FERIE, minutesProjet);
        Projet p2 = new Projet(100, 400);
        jour.ajoutProjet(p1);
        jour.ajoutProjet(p2);

        jour.analyserJourFerie();
        int nbErreur = ErreurJournal.Instance().erreurs.size();

        assertEquals(nbErreur, 0);

    }
    
        @Test
    public void testCongeFeriePendantWeekEnd(){
        int minutesProjet = 480;
        Jour jour = new Jour("Week end",TypeJour.WEEKEND);
        Projet p1 = new Projet(Projet.PROJET_ID_CONGE_FERIE,minutesProjet);
        jour.ajoutProjet(p1);
        jour.analyserJourFerie();
         assertEquals(ErreurJournal.Instance().erreurs.size(), 1);
         ErreurJournal.Instance().effacerTout();   
    }

    public void testEstJourneeMaladie() {
        boolean estJourneeMaladie;

        Jour jour = new Jour("jour1", TypeJour.OUVRABLE);
        Projet p1 = new Projet(Projet.PROJET_ID_CONGE_MALADIE, 0);
        jour.ajoutProjet(p1);
        estJourneeMaladie = jour.estJourneeCongeParental();

        assertTrue(estJourneeMaladie);
    }

    @Test
    public void testGetMinutesCongeMaladie() {
        int minutesProjet = 480;
        Jour jour = new Jour("jour1", TypeJour.OUVRABLE);
        Projet p1 = new Projet(Projet.PROJET_ID_CONGE_MALADIE, minutesProjet);
        jour.ajoutProjet(p1);
        int minutesRecu = jour.getMinutesJourneeMaladie();
        assertEquals(minutesProjet, minutesRecu);
    }

    @Test
    public void testAnalyserCongeMaladie() {
        int minutesProjet = 480;
        Jour jour = new Jour("jour1", TypeJour.OUVRABLE);
        Projet p1 = new Projet(Projet.PROJET_ID_CONGE_PARENTAL, minutesProjet);
        jour.ajoutProjet(p1);
        jour.analyserJourParental();
        int nbErreur = ErreurJournal.Instance().erreurs.size();
        assertEquals(nbErreur, 0);
    }

    
    public void testContientPasAutresProjetsQueCongeMaladie() {
        Jour jour = new Jour("Jour1", TypeJour.OUVRABLE);
        Projet p1 = new Projet(Projet.PROJET_ID_CONGE_MALADIE, 0);
        jour.ajoutProjet(p1);
        boolean jourContientAutresProjet = jour.contientAutresProjetsQue(Projet.PROJET_ID_CONGE_MALADIE);
        assertFalse(jourContientAutresProjet);
    }

    @Test
    public void testContientAutresProjetsQueCongeMaladie() {
        Jour jour = new Jour("Jour1", TypeJour.OUVRABLE);
        Projet p1 = new Projet(Projet.PROJET_ID_CONGE_MALADIE, 0);
        Projet p2 = new Projet(Projet.PROJET_ID_CONGE_FERIE, 0);
        jour.ajoutProjet(p1);
        jour.ajoutProjet(p2);
        boolean jourContientAutresProjet = jour.contientAutresProjetsQue(Projet.PROJET_ID_CONGE_PARENTAL);
        assertTrue(jourContientAutresProjet);
    }
            @Test
    public void testCongeMaladiePendantWeekEnd(){
        int minutesProjet = 480;
        Jour jour = new Jour("Week end",TypeJour.WEEKEND);
        Projet p1 = new Projet(Projet.PROJET_ID_CONGE_MALADIE,minutesProjet);
        jour.ajoutProjet(p1);
        jour.analyserJourMaladie();
         assertEquals(ErreurJournal.Instance().erreurs.size(), 1);
         ErreurJournal.Instance().effacerTout();   
    }    
}
