/**
 * EmployeDirectionTest - INF2015 - TP Agile - EQUIPE 17
 *
 * @author Francois Dufault
 * @author Lyes Tamazouzt
 * @author Abdessamad Essakhi
 */
package inf2015.tp.employe;

import inf2015.tp.Projet;
import inf2015.tp.erreur.Erreur;
import inf2015.tp.erreur.ErreurEmployeMinimalUnJourOuvrableBureau;
import inf2015.tp.erreur.ErreurJournal;
import inf2015.tp.erreur.ErreurTempsMaximaleTransport;
import inf2015.tp.jour.Jour;
import inf2015.tp.jour.JourOuvrable;
import static org.junit.Assert.*;
import org.junit.Test;

public class EmployeDirectionTest {

    @Test
    public void testGetTypeEmploye() {
        String typeEmploye = "Direction";

        Employe employe = new EmployeDirection(0, null);

        assertEquals(typeEmploye, employe.getTypeEmploye());
    }

    @Test
    public void testEstEmploye() {
        int numeroEmploye = 5500;

        assertTrue(EmployeDirection.estEmploye(numeroEmploye));
    }

    @Test
    public void testEstPasEmploye() {
        int numeroEmploye = 6000;

        assertFalse(EmployeDirection.estEmploye(numeroEmploye));
    }

    @Test
    public void testVerifierMinimumMinutesQuotidienneValide() {
        ErreurJournal erreurJournal = new ErreurJournal();
        Jour jour = new JourOuvrable("jour", erreurJournal);
        Projet projet = new Projet(100, 500);
        jour.ajoutProjet(projet);
        Employe employe = new EmployeDirection(0, erreurJournal);
        employe.ajoutJour(jour);
        employe.verifierMinimumMinutesQuotidiennes();
        assertTrue(erreurJournal.estVide());
    }

    @Test
    public void TestAnalyserFeuilleTempsValide() {
        ErreurJournal erreurJournal = new ErreurJournal();
        Employe employe = new EmployeDirection(5200, erreurJournal);
        employe.minutesJoursOuvrableBureau = 2600;
        employe.minutesWeekendBureau = 200;
        employe.analyserFeuilleTemps();
        assertTrue(erreurJournal.estVide());
    }

    @Test
    public void TestAnalyserFeuilleTempsInvalide() {
        ErreurJournal erreurJournal = new ErreurJournal();
        Employe employe = new EmployeDirection(5200, erreurJournal);
        employe.minutesJoursOuvrableBureau = 2000;
        employe.minutesWeekendBureau = 200;
        employe.analyserFeuilleTemps();
        assertFalse(erreurJournal.estVide());
    }

    @Test
    public void testVerifierMinimumMinutesQuotidienneInvalide() {
        ErreurJournal erreurJournal = new ErreurJournal();
        Jour jour = new JourOuvrable("jour", erreurJournal);
        Projet projet = new Projet(100, 100);
        jour.ajoutProjet(projet);
        Employe employe = new EmployeDirection(0, erreurJournal);
        employe.ajoutJour(jour);

        employe.verifierMinimumMinutesQuotidiennes();

        Erreur erreur = erreurJournal.getErreurAIndex(0);
        assertEquals(ErreurEmployeMinimalUnJourOuvrableBureau.class, erreur.getClass());
    }

    @Test
    public void testVerifierEtCalculerProjetTransportValide() {
        int minutesTransportOuvrable = 100;
        int minutesTransportWeekend = 125;
        int minutesTransportTotal = minutesTransportOuvrable + minutesTransportWeekend;
        ErreurJournal erreurJournal = new ErreurJournal();

        Employe employe = new EmployeDirection(0, erreurJournal);
        employe.minutesTransportJourOuvrable = minutesTransportOuvrable;
        employe.minutesTransportJourWeekend = minutesTransportWeekend;

        employe.verifierProjetTransport();
        assertEquals(minutesTransportTotal, employe.minutesTeleTravail);
        assertEquals(0, erreurJournal.getNombresErreurs());
    }

    @Test
    public void testVerifierEtCalculerProjetTransportInvalide() {
        int minutesTransportOuvrable = 200;
        int minutesTransportWeekend = 400;

        ErreurJournal erreurJournal = new ErreurJournal();

        Employe employe = new EmployeDirection(0, erreurJournal);
        employe.minutesTransportJourOuvrable = minutesTransportOuvrable;
        employe.minutesTransportJourWeekend = minutesTransportWeekend;

        employe.verifierProjetTransport();
        assertEquals(1, erreurJournal.getNombresErreurs());
        Erreur erreur = erreurJournal.getErreurAIndex(0);
        assertEquals(ErreurTempsMaximaleTransport.class, erreur.getClass());
    }
}
