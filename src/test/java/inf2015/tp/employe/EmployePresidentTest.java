/**
 * EmployePresidentTest - INF2015 - TP Agile - EQUIPE 17
 *
 * @author Francois Dufault
 * @author Lyes Tamazouzt
 * @author Abdessamad Essakhi
 */
package inf2015.tp.employe;

import inf2015.tp.Projet;
import inf2015.tp.erreur.Erreur;
import inf2015.tp.erreur.ErreurEmployeMinimalUnJourOuvrableBureau;
import inf2015.tp.erreur.ErreurEmployeMinimumBureau;
import inf2015.tp.erreur.ErreurJournal;
import inf2015.tp.jour.Jour;
import inf2015.tp.jour.JourOuvrable;
import static org.junit.Assert.*;
import org.junit.Test;

public class EmployePresidentTest {

    @Test
    public void testGetTypeEmploye() {
        String typeEmploye = "President";
        EmployePresident employe = new EmployePresident(0, null);
        assertEquals(typeEmploye, employe.getTypeEmploye());
    }

    @Test
    public void testEstEmploye() {
        int employeID = 6000;
        assertTrue(EmployePresident.estEmploye(employeID));
    }

    @Test
    public void testEstPasEmploye() {
        int employeID = 1001;
        assertFalse(EmployePresident.estEmploye(employeID));
    }

    @Test
    public void testVerifierMinimumMinutesQuotidienneValide() {
        ErreurJournal erreurJournal = new ErreurJournal();
        Jour jour = new JourOuvrable("jour", erreurJournal);
        Projet projet = new Projet(100, 500);
        jour.ajoutProjet(projet);
        Employe employe = new EmployePresident(6000, erreurJournal);
        employe.ajoutJour(jour);
        employe.verifierMinimumMinutesQuotidiennes();
        assertTrue(erreurJournal.estVide());
    }

    @Test
    public void testVerifierMinimumMinutesQuotidienneInvalide() {
        ErreurJournal erreurJournal = new ErreurJournal();
        Jour jour = new JourOuvrable("jour", erreurJournal);
        Projet projet = new Projet(100, 100);
        jour.ajoutProjet(projet);
        Employe employe = new EmployePresident(6000, erreurJournal);
        employe.ajoutJour(jour);
        employe.verifierMinimumMinutesQuotidiennes();
        Erreur erreur = erreurJournal.getErreurAIndex(0);
        assertEquals(ErreurEmployeMinimalUnJourOuvrableBureau.class, erreur.getClass());
    }

    @Test
    public void TestAnalyserFeuilleTempsValide() {
        ErreurJournal erreurJournal = new ErreurJournal();
        Employe employe = new EmployePresident(5200, erreurJournal);
        employe.minutesJoursOuvrableBureau = 2600;
        employe.minutesWeekendBureau = 200;
        employe.analyserFeuilleTempsDirection();
        assertTrue(erreurJournal.estVide());
    }

    @Test
    public void TestAnalyserFeuilleTempsInvalide() {
        ErreurJournal erreurJournal = new ErreurJournal();
        Employe employe = new EmployePresident(5200, erreurJournal);
        employe.minutesJoursOuvrableBureau = 2000;
        employe.minutesWeekendBureau = 200;
        employe.analyserFeuilleTemps();
        Erreur erreur = erreurJournal.getErreurAIndex(0);
        assertEquals(ErreurEmployeMinimumBureau.class, erreur.getClass());
    }

    @Test
    public void testVerifierEtCalculerProjetTransportValideMoins5h() {
        int minutesTransportOuvrable = 100;
        int minutesTransportWeekend = 125;

        ErreurJournal erreurJournal = new ErreurJournal();
        Employe employe = new EmployePresident(0, erreurJournal);
        employe.minutesTransportJourOuvrable = minutesTransportOuvrable;
        employe.minutesTransportJourWeekend = minutesTransportWeekend;

        employe.verifierProjetTransport();
        assertEquals(minutesTransportOuvrable, employe.minutesJoursOuvrableBureau);
        assertEquals(minutesTransportWeekend, employe.minutesWeekendBureau);
        assertEquals(0, erreurJournal.getNombresErreurs());
    }

    @Test
    public void testVerifierEtCalculerProjetTransportValidePlus5h() {
        int minutesTransportOuvrable = 200;
        int minutesTransportWeekend = 125;

        ErreurJournal erreurJournal = new ErreurJournal();
        Employe employe = new EmployePresident(0, erreurJournal);
        employe.minutesTransportJourOuvrable = minutesTransportOuvrable;
        employe.minutesTransportJourWeekend = minutesTransportWeekend;

        employe.verifierProjetTransport();
        assertEquals(minutesTransportOuvrable, employe.minutesJoursOuvrableBureau);
        assertEquals(minutesTransportWeekend, employe.minutesWeekendBureau);
        assertEquals(0, erreurJournal.getNombresErreurs());
    }
}
