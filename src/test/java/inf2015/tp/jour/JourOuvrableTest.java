/**
 * JourOuvrable - INF2015 - TP Agile - EQUIPE 17
 *
 * @author Francois Dufault
 * @author Lyes Tamazouzt
 * @author Abdessamad Essakhi
 */
package inf2015.tp.jour;

import inf2015.tp.Projet;
import inf2015.tp.erreur.Erreur;
import inf2015.tp.erreur.ErreurJourCongeAvecAutreProjet;
import inf2015.tp.erreur.ErreurJourDepasseMinute;
import inf2015.tp.erreur.ErreurJourSpecialEgalMinutes;
import inf2015.tp.erreur.ErreurJournal;
import static org.junit.Assert.*;
import org.junit.Test;

public class JourOuvrableTest {

    @Test
    public void testEstJourOuvrable() {
        Jour jour = new JourOuvrable("jour1", null);

        assertTrue(jour.estJourOuvable());
    }

    @Test
    public void testVerifierMaxMinutesJourValide() {
        ErreurJournal erreurJournal = new ErreurJournal();
        Jour jour = new JourOuvrable("jour1", erreurJournal);

        Projet p1 = new Projet(500, 500);
        jour.ajoutProjet(p1);

        jour.verifierMaxMinutesJour();

        assertTrue(erreurJournal.estVide());
    }

    @Test
    public void testVerifierMaxMinutesJourInvalide() {
        ErreurJournal erreurJournal = new ErreurJournal();
        Jour jour = new JourOuvrable("jour1", erreurJournal);

        Projet p1 = new Projet(500, Jour.MAX_MINUTES_PAR_JOURS);
        jour.ajoutProjet(p1);

        p1 = new Projet(501, Jour.MAX_MINUTES_PAR_JOURS);
        jour.ajoutProjet(p1);

        jour.verifierMaxMinutesJour();

        assertEquals(1, erreurJournal.getNombresErreurs());

        Erreur erreur = erreurJournal.getErreurAIndex(0);
        
        assertEquals(ErreurJourDepasseMinute.class, erreur.getClass());
    }

    @Test
    public void testVerifierMaxMinutesJourCongeValide() {
        ErreurJournal erreurJournal = new ErreurJournal();
        Jour jour = new JourOuvrable("jour1", erreurJournal);

        Projet p1 = new Projet(Projet.PROJET_ID_CONGE_VACANCE, Jour.MAX_MINUTES_PAR_JOURS_AVEC_CONGE - 1);
        jour.ajoutProjet(p1);

        jour.verifierMaxMinutesJour();

        assertTrue(erreurJournal.estVide());
    }

    @Test
    public void testVerifierMaxMinutesJourCongeInvalide() {
        ErreurJournal erreurJournal = new ErreurJournal();
        Jour jour = new JourOuvrable("jour1", erreurJournal);

        Projet p1 = new Projet(Projet.PROJET_ID_CONGE_VACANCE, Jour.MAX_MINUTES_PAR_JOURS_AVEC_CONGE + 1);
        jour.ajoutProjet(p1);

        jour.verifierMaxMinutesJour();

        assertEquals(1, erreurJournal.getNombresErreurs());

        Erreur erreur = erreurJournal.getErreurAIndex(0);
        
        assertEquals(ErreurJourDepasseMinute.class, erreur.getClass());
    }
    
    @Test
    public void testAnalyserJourFerieValide() {
        ErreurJournal erreurJournal = new ErreurJournal();
        Jour jour = new JourOuvrable("jour1", erreurJournal);
        Projet p = new Projet(Projet.PROJET_ID_CONGE_FERIE, Jour.MINUTES_JOURNEE_FERIEE);
        jour.ajoutProjet(p);

        jour.analyserJourFerie();

        assertTrue(erreurJournal.estVide());
    }

    @Test
    public void testAnalyserJourFerieInvalideMinutes() {
        ErreurJournal erreurJournal = new ErreurJournal();
        Jour jour = new JourOuvrable("jour1", erreurJournal);
        Projet p = new Projet(Projet.PROJET_ID_CONGE_FERIE, 10);
        jour.ajoutProjet(p);

        jour.analyserJourFerie();

        assertFalse(erreurJournal.estVide());
        Erreur erreur = erreurJournal.getErreurAIndex(0);

        assertEquals(ErreurJourSpecialEgalMinutes.class, erreur.getClass());
    }

    @Test
    public void testAnalyserJourMaladieValide() {
        ErreurJournal erreurJournal = new ErreurJournal();
        Jour jour = new JourOuvrable("jour1", erreurJournal);
        Projet p = new Projet(Projet.PROJET_ID_CONGE_MALADIE, Jour.MINUTES_JOURNEE_MALADIE);
        jour.ajoutProjet(p);

        jour.analyserJourMaladie();

        assertTrue(erreurJournal.estVide());
    }

    @Test
    public void testAnalyserJourMaladieInvalideMinutes() {
        ErreurJournal erreurJournal = new ErreurJournal();
        Jour jour = new JourOuvrable("jour1", erreurJournal);
        Projet p = new Projet(Projet.PROJET_ID_CONGE_MALADIE, 20);
        jour.ajoutProjet(p);

        jour.analyserJourMaladie();

        assertFalse(erreurJournal.estVide());

        Erreur erreur = erreurJournal.getErreurAIndex(0);
        assertEquals(ErreurJourSpecialEgalMinutes.class, erreur.getClass());
    }

    @Test
    public void testAnalyserJourMaladieInvalideMinutesAutresProjets() {
        ErreurJournal erreurJournal = new ErreurJournal();
        Jour jour = new JourOuvrable("jour1", erreurJournal);

        Projet p = new Projet(Projet.PROJET_ID_CONGE_MALADIE, 50);
        jour.ajoutProjet(p);

        p = new Projet(100, 50);
        jour.ajoutProjet(p);

        jour.analyserJourMaladie();

        assertFalse(erreurJournal.estVide());

        Erreur erreur = erreurJournal.getErreurAIndex(0);
        assertEquals(ErreurJourCongeAvecAutreProjet.class, erreur.getClass());

        erreur = erreurJournal.getErreurAIndex(1);
        assertEquals(ErreurJourSpecialEgalMinutes.class, erreur.getClass());
    }

    @Test
    public void testAnalyserJourVacancesValide() {
        ErreurJournal erreurJournal = new ErreurJournal();
        Jour jour = new JourOuvrable("jour1", erreurJournal);

        Projet p = new Projet(Projet.PROJET_ID_CONGE_VACANCE, Jour.MINUTES_JOURNEE_CONGE_VACANCES);
        jour.ajoutProjet(p);

        jour.analyserJourVacances();

        assertTrue(erreurJournal.estVide());
    }

    @Test
    public void testAnalyserJourVacancesInvalide() {
        ErreurJournal erreurJournal = new ErreurJournal();
        Jour jour = new JourOuvrable("jour1", erreurJournal);

        Projet p = new Projet(Projet.PROJET_ID_CONGE_VACANCE, 60);
        jour.ajoutProjet(p);

        jour.analyserJourVacances();

        assertFalse(erreurJournal.estVide());

        Erreur erreur = erreurJournal.getErreurAIndex(0);
        assertEquals(ErreurJourSpecialEgalMinutes.class, erreur.getClass());
    }

    @Test
    public void testAnalyserJourParentalValide() {
        ErreurJournal erreurJournal = new ErreurJournal();
        Jour jour = new JourOuvrable("jour1", erreurJournal);

        Projet p = new Projet(Projet.PROJET_ID_CONGE_PARENTAL, Jour.MINUTES_JOURNEE_CONGE_PARENTAL);
        jour.ajoutProjet(p);

        jour.analyserJourParental();

        assertTrue(erreurJournal.estVide());

    }

    @Test
    public void testAnalyserJourParentalInvalideMinutes() {
        ErreurJournal erreurJournal = new ErreurJournal();
        Jour jour = new JourOuvrable("jour1", erreurJournal);

        Projet p = new Projet(Projet.PROJET_ID_CONGE_PARENTAL, 80);
        jour.ajoutProjet(p);

        jour.analyserJourParental();

        assertFalse(erreurJournal.estVide());

        Erreur erreur = erreurJournal.getErreurAIndex(0);

        assertEquals(ErreurJourSpecialEgalMinutes.class, erreur.getClass());
    }

    @Test
    public void testAnalyserJourParentalInvalideMinutesAutresProjets() {
        ErreurJournal erreurJournal = new ErreurJournal();
        Jour jour = new JourOuvrable("jour1", erreurJournal);

        Projet p = new Projet(Projet.PROJET_ID_CONGE_PARENTAL, 80);
        jour.ajoutProjet(p);

        p = new Projet(100, 200);
        jour.ajoutProjet(p);

        jour.analyserJourParental();

        assertFalse(erreurJournal.estVide());

        Erreur erreur = erreurJournal.getErreurAIndex(0);
        assertEquals(ErreurJourCongeAvecAutreProjet.class, erreur.getClass());

        erreur = erreurJournal.getErreurAIndex(1);
        assertEquals(ErreurJourSpecialEgalMinutes.class, erreur.getClass());

    }
}
