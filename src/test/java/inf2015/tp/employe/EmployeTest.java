/**
 * EmployeTest - INF2015 - TP Agile - EQUIPE 17
 *
 * @author Francois Dufault
 * @author Lyes Tamazouzt
 * @author Abdessamad Essakhi
 */
package inf2015.tp.employe;

import inf2015.tp.Projet;
import inf2015.tp.erreur.Erreur;
import inf2015.tp.erreur.ErreurEmployeCongeParentalMultiple;
import inf2015.tp.erreur.ErreurJournal;
import inf2015.tp.jour.Jour;
import inf2015.tp.jour.JourOuvrable;
import inf2015.tp.jour.JourWeekend;
import static org.junit.Assert.*;
import org.junit.Test;

public class EmployeTest {

    @Test
    public void testGetNumeroEmploye() {
        int numeroEmploye = 500;
        Employe employe = new EmployeAdministration(numeroEmploye, null);

        assertEquals(numeroEmploye, employe.getNumeroEmploye());
    }

    @Test
    public void testAjout1Jour() {
        Jour jour = new JourOuvrable(null, null);
        Employe employe = new EmployeAdministration(0, null);
        employe.ajoutJour(jour);

        assertSame(jour, employe.semaines.get(0));
    }

    @Test
    public void testAjout2Jours() {
        Jour jour1 = new JourOuvrable(null, null);
        Jour jour2 = new JourOuvrable(null, null);
        Employe employe = new EmployeAdministration(0, null);
        employe.ajoutJour(jour1);
        employe.ajoutJour(jour2);

        assertSame(jour1, employe.semaines.get(0));
        assertSame(jour2, employe.semaines.get(1));
    }

    @Test
    public void testGetSemaine() {
        Employe employe = new EmployeAdministration(0, null);

        assertTrue(employe.getSemaine().isEmpty());
    }

    @Test
    public void testGetSemaineAvecJour() {
        Jour jour = new JourOuvrable(null, null);
        Employe employe = new EmployeAdministration(0, null);
        employe.ajoutJour(jour);

        assertSame(jour, employe.getSemaine().get(0));
    }

    @Test
    public void testCalculerFeuilleTemps() {
        int minutesOuvrableBureau = 500;
        int minutesTeleTravail = 300;
        int minutesWeekendBureau = 400;
        Jour jour1 = new JourOuvrable("jour1", null);
        Jour jourWeekend1 = new JourWeekend("weekend1", null);

        Projet projet = new Projet(100, 500);
        jour1.ajoutProjet(projet);
        projet = new Projet(901, 300);
        jour1.ajoutProjet(projet);

        projet = new Projet(100, 400);
        jourWeekend1.ajoutProjet(projet);

        Employe employe = new EmployeAdministration(0, null);
        employe.ajoutJour(jour1);
        employe.ajoutJour(jourWeekend1);

        employe.calculerFeuilleTemps();

        assertEquals(minutesOuvrableBureau, employe.minutesJoursOuvrableBureau);
        assertEquals(minutesTeleTravail, employe.minutesTeleTravail);
        assertEquals(minutesWeekendBureau, employe.minutesWeekendBureau);
    }

    @Test
    public void testVerifierCongeParentalValide() {
        ErreurJournal erreurJournal = new ErreurJournal();
        Employe employer = new EmployeAdministration(0, erreurJournal);
        Jour jour1 = new JourOuvrable("jour1", null);
        Jour jour2 = new JourOuvrable("jour1", null);

        jour1.ajoutProjet(new Projet(Projet.PROJET_ID_CONGE_PARENTAL, 100));
        jour2.ajoutProjet(new Projet(100, 100));

        employer.ajoutJour(jour1);
        employer.ajoutJour(jour2);

        employer.verifierCongeParental();

        assertTrue(erreurJournal.estVide());
    }

    @Test
    public void testVerifierCongeParentalInvalide() {
        ErreurJournal erreurJournal = new ErreurJournal();
        Employe employer = new EmployeAdministration(0, erreurJournal);
        Jour jour1 = new JourOuvrable("jour1", null);
        Jour jour2 = new JourOuvrable("jour2", null);

        jour1.ajoutProjet(new Projet(Projet.PROJET_ID_CONGE_PARENTAL, 100));
        jour2.ajoutProjet(new Projet(Projet.PROJET_ID_CONGE_PARENTAL, 100));

        employer.ajoutJour(jour1);
        employer.ajoutJour(jour2);

        employer.verifierCongeParental();

        assertEquals(1, erreurJournal.getNombresErreurs());

        Erreur erreur = erreurJournal.getErreurAIndex(0);
        assertEquals(ErreurEmployeCongeParentalMultiple.class, erreur.getClass());
    }
}
