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
import org.junit.Test;
import static org.junit.Assert.*;

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
    public void TestAnalyserFeuilleTempsValide()
    {
        ErreurJournal erreurJournal = new ErreurJournal();
        Employe employe = new EmployeDirection(5200, erreurJournal);
        employe.minutesJoursOuvrableBureau = 2600;
        employe.minutesWeekendBureau = 200;
        employe.analyserFeuilleTemps();        
        assertTrue(erreurJournal.estVide());
    }
    @Test
     public void TestAnalyserFeuilleTempsInvalide()
    {
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
    public void TestMaximumTempsTransport() {
        ErreurJournal erreurJournal = new ErreurJournal();
        
        Employe employe = new EmployeDirection(5200, erreurJournal);
        int minuteInvalide = 400;
        
        employe.validerMinutesTransport(minuteInvalide);
        Erreur erreur = erreurJournal.getErreurAIndex(0);
        assertEquals(ErreurTempsMaximaleTransport.class, erreur.getClass());
    }

    @Test
    public void TestValideTempsTransport() {
        ErreurJournal erreurJournal = new ErreurJournal();
        
        Employe employe = new EmployeDirection(5200, erreurJournal);
        int minuteInvalide = 200;
        
        employe.validerMinutesTransport(minuteInvalide);
     
        assertEquals(erreurJournal.getNombresErreurs(), 0);
    }
     @Test
    public void testVerifierAjoutMinutesTeleTravailTransport() {
        ErreurJournal erreurJournal = new ErreurJournal();
       int minutes=200;
        Employe employe = new EmployeDirection(5200, erreurJournal);   
        employe.minutesTeleTravail = 200;
        employe.ajusterLesMinutes(200);
        assertEquals(employe.minutesTeleTravail,400);
    }
          @Test
    public void testVerifierPasAjouterMinutesTeleTravailTransport() {
        ErreurJournal erreurJournal = new ErreurJournal();
        int minutes=200;
        Employe employe = new EmployeDirection(5200, erreurJournal);   
        employe.minutesTeleTravail = 200;
        employe.ajusterLesMinutes(302);
        assertEquals(employe.minutesTeleTravail,200);
    }
      


}
